package top.cellargalaxy.service.monitor;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.bean.monitor.Equipment;
import top.cellargalaxy.bean.monitor.Malfunction;
import top.cellargalaxy.bean.monitor.Place;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.bean.serviceBean.Build;
import top.cellargalaxy.configuration.NetviewConfiguration;
import top.cellargalaxy.dao.EquipmentMapper;
import top.cellargalaxy.dao.MalfunctionMapper;
import top.cellargalaxy.dao.PlaceMapper;
import top.cellargalaxy.util.CsvDeal;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cellargalaxy on 17-12-8.
 */
@Service
@Transactional
public class NetviewServiceImpl implements NetviewService {
	@Autowired
	private EquipmentMapper equipmentMapper;
	@Autowired
	private MalfunctionMapper malfunctionMapper;
	@Autowired
	private PlaceMapper placeMapper;
	@Autowired
	private PersonelApi personelApi;
	@Autowired
	private WxApi wxApi;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final String NULL_BUILD_NAME = "空闲设备";

	private final String token;
	private final int listMalfunctionLength;

	private volatile ConcurrentHashMap<String, Build> builds;
	private volatile ConcurrentHashMap<String, Build> netview;
	private volatile int malfunctionPageCount;

	@Autowired
	public NetviewServiceImpl(NetviewConfiguration netviewConfiguration) {
		token = netviewConfiguration.getToken();
		listMalfunctionLength = netviewConfiguration.getListMalfunctionLength();
		malfunctionPageCount = -1;
	}

	@Override
	public boolean init() {
		try {
			builds = initBuilds();
			netview = initNetview(builds);
			countMalfunctionPageCount();
			return true;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public void addChangeStatusEquipment(Equipment equipment) {
		try {
			if (equipment.isStatus()) {
				logger.info("通 " + equipment.getFullName());
			} else {
				logger.info("挂 " + equipment.getFullName());
			}
			wxApi.addChangeStatusEquipment(equipment);
			malfunctionMapper.insertMalfunction(new Malfunction(equipment));
		} catch (Exception e) {
			dealException(e);
		}
	}

	@Override
	public LinkedList<Build> findWarmNetview() {
		try {
			LinkedList<Build> warmBuilds = new LinkedList<>();
			ConcurrentHashMap<String, Build> netview = getNetview();
			synchronized (netview) {
				for (Map.Entry<String, Build> entry : netview.entrySet()) {
					Build build = entry.getValue();
					if (!build.isStatus()) {
						Build b = new Build(build.getName());
						for (Equipment equipment : build.getEquipments()) {
							if (!equipment.isStatus()) {
								b.addEquipment(equipment);
							}
						}
						warmBuilds.add(b);
					}
				}
			}
			return warmBuilds;
		} catch (Exception e) {
			dealException(e);
		}
		return null;
	}

	@Override
	public LinkedList<Build> findNetview() {
		Collection<Build> collection = getNetview().values();
		LinkedList<Build> linkedList=new LinkedList<>(collection);
		Collections.sort(linkedList);
		return linkedList;
	}

	///////////////////////////////////////////////////////////////
	@Override
	public boolean addPlace(Place place) {
		try {
			if (placeMapper.insertPlace(place) > 0) {
				addBuild0(place.getBuild());
				return true;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public boolean removePlace(Place place) {
		try {
			equipmentMapper.deleteEquipmentByPlace(place);
			if (placeMapper.deletePlace(place) > 0) {
				removeBuild0(place.getBuild());
				return true;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public Place[] findAllPlace() {
		try {
			return placeMapper.selectAllPlace();
		} catch (Exception e) {
			dealException(e);
		}
		return new Place[0];
	}

	/////////////////////////////////////////////////////////////////////
	@Override
	public boolean addEquipment(Equipment equipment) {
		try {
			try {
				if (equipment.getIp() != null && equipment.getIp().length() == 0) {
					equipment.setIp(null);
				}
				Place place = new Place(equipment.getArea(), equipment.getBuild(), equipment.getFloor(), equipment.getNumber());
				addBuild0(place.getBuild());
				placeMapper.insertPlace(place);
			} catch (Exception e) {
			}

			if (equipmentMapper.insertEquipment(equipment) > 0) {
				addEquipment0(equipment);
				return true;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public LinkedList<Equipment> addEquipments(File file) {
		try {
			Iterable<CSVRecord> records = CsvDeal.createCSVRecords(file);
			if (records == null) {
				return null;
			}
			Iterator<CSVRecord> iterator = records.iterator();
			iterator.next();
			LinkedList<Equipment> equipments = new LinkedList<>();
			while (iterator.hasNext()) {
				CSVRecord record = iterator.next();
				Map<String, String> map = record.toMap();
				String id = map.get("id").trim();
				if (id == null || id.length() == 0) {
					id = UUID.randomUUID().toString();
				}
				String area = map.get("area");
				if (area == null || area.length() == 0) {
					area = "龙洞";
				}
				Integer number = CsvDeal.string2Int(map.get("number"));
				if (number == null) {
					number = 0;
				}
				Date buyDate = CsvDeal.string2Date(map.get("buyDate"));
				if (buyDate == null) {
					buyDate = new Date();
				}
				Integer checkTimes = CsvDeal.string2Int(map.get("checkTimes"));
				if (checkTimes == null) {
					checkTimes = Equipment.DEFAULT_CHECK_TIMES;
				}
				Integer isWarn = CsvDeal.string2Int(map.get("isWarn"));
				if (isWarn == null) {
					isWarn = Equipment.IS_WARM_NUM;
				}
				Date installDate = CsvDeal.string2Date(map.get("installDate"));
				equipments.add(new Equipment(id, map.get("model"), map.get("name"), buyDate, area, map.get("build"),
						map.get("floor"), number, map.get("ip"), checkTimes, isWarn, map.get("remark"), installDate));
			}
			LinkedList<Equipment> fail = new LinkedList<>();
			for (Equipment equipment : equipments) {
				if (!addEquipment(equipment)) {
					fail.add(equipment);
				}
			}
			return fail;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean writeEquipmentFile(File file) {
		try (CSVPrinter csvPrinter = CsvDeal.createCSVPrinter(file)) {
			if (csvPrinter == null) {
				return false;
			}
			csvPrinter.printRecord("id", "model", "name", "buyDate", "area", "build", "floor", "number", "ip", "checkTimes", "isWarn", "remark", "installDate");
			csvPrinter.printRecord("编号(字符串)", "机型(字符串)", "名字(字符串)", "购买日期(" + CsvDeal.DATE_FORMAT_STRING + ")", "校区(字符串)", "楼栋(字符串)", "楼层(字符串)", "序号(数字)", "ip(字符串)", "检测次数(数字)", "是否警告(0:否,1:是)", "备注(字符串)", "安装日期(" + CsvDeal.DATE_FORMAT_STRING + ")");
			ConcurrentHashMap<String, Build> builds = getBuilds();
			synchronized (builds) {
				for (Map.Entry<String, Build> entry : builds.entrySet()) {
					Build build = entry.getValue();
					for (Equipment e : build.getEquipments()) {
						String buyDate = CsvDeal.date2String(e.getBuyDate());
						String installDate = CsvDeal.date2String(e.getInstallDate());
						csvPrinter.printRecord(e.getId(), e.getModel(), e.getName(), buyDate, e.getArea(), e.getBuild(), e.getFloor(),
								e.getNumber(), e.getIp(), e.getCheckTimes(), e.getIsWarn(), e.getRemark(), installDate);
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeEquipment(String id) {
		try {
			if (equipmentMapper.deleteEquipment(id) > 0) {
				removeEquipment0(id);
				return true;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public Equipment findEquipmentById(String id) {
		try {
			return equipmentMapper.selectEquipmentById(id);
		} catch (Exception e) {
			dealException(e);
		}
		return null;
	}

	@Override
	public LinkedList<Build> findAllEquipment() {
		Collection<Build> collection=getBuilds().values();
		LinkedList<Build> linkedList=new LinkedList<>(collection);
		Collections.sort(linkedList);
		return linkedList;
	}

	@Override
	public boolean changeEquipment(Equipment equipment) {
		try {
			if (equipmentMapper.updateEquipment(equipment) > 0) {
				changeEquipment0(equipment);
				return true;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public boolean removeMalfunction(Malfunction malfunction) {
		try {
			malfunctionPageCount = -1;
			return malfunctionMapper.deleteMalfunction(malfunction) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public Malfunction[] findMalfunctions(int page) {
		try {
			int len = listMalfunctionLength;
			int off = (page - 1) * len;
			return malfunctionMapper.selectMalfunctions(off, len);
		} catch (Exception e) {
			dealException(e);
		}
		return new Malfunction[0];
	}

	@Override
	public int getMalfunctionPageCount() {
		try {
			if (malfunctionPageCount == -1) {
				countMalfunctionPageCount();
			}
			return malfunctionPageCount;
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}

	///////////////////////////////////////////////////////////////////
	@Override
	public boolean checkToken(String token) {
		return this.token.equals(token);
	}

	@Override
	public Person checkPassword(Person person) {
		return personelApi.checkPassword(person.getId(), person.getPassword());
	}

	@Override
	public boolean checkMonitorDisabled(Person person) {
		return personelApi.checkMonitorDisabled(person.getId());
	}

	@Override
	public boolean checkMonitorAdmin(Person person) {
		return personelApi.checkMonitorAdmin(person.getId());
	}

	@Override
	public boolean checkMonitorRoot(Person person) {
		return personelApi.checkMonitorRoot(person.getId());
	}

	/////////////////////////////////////////////////////////////////////////

	/**
	 * 从数据库读取全部设备，装换到Build对象里
	 *
	 * @return
	 */
	private ConcurrentHashMap<String, Build> initBuilds() {
		Equipment[] equipments = equipmentMapper.selectAllEquipment();
		ConcurrentHashMap<String, Build> builds = new ConcurrentHashMap<>();
		Build nullBuild = new Build(NULL_BUILD_NAME);
		builds.put(nullBuild.getName(), nullBuild);
		main:
		for (Equipment equipment : equipments) {
			if (equipment.getBuild() == null || equipment.getBuild().length() == 0) {
				nullBuild.addEquipment(equipment);
				continue;
			}
			Build build = builds.get(equipment.getBuild());
			if (build == null) {
				build = new Build(equipment.getBuild());
				builds.put(build.getName(), build);
			}
			build.addEquipment(equipment);
		}
		return builds;
	}

	private ConcurrentHashMap<String, Build> getBuilds() {
		if (builds == null) {
			builds = initBuilds();
		}
		return builds;
	}

	private void addBuild0(String buildName) {
		addBuildIntoMap(getBuilds(), buildName);
		addBuildIntoMap(getNetview(), buildName);
	}

	private void removeBuild0(String buildName) {
		removeBuildFromMap(getBuilds(), buildName);
		removeBuildFromMap(getNetview(), buildName);
	}

	private void addEquipment0(Equipment equipment) {
		addEquipmentIntoMap(getBuilds(), equipment);
		addEquipmentIntoMap(getNetview(), equipment);
	}

	private void removeEquipment0(String id) {
		removeEquipmentFromMap(getBuilds(), id);
		removeEquipmentFromMap(getNetview(), id);
	}

	private void changeEquipment0(Equipment equipment) {
		changeEquipmentFromMap(getBuilds(), equipment);
		changeEquipmentFromMap(getNetview(), equipment);
	}

	private void addBuildIntoMap(ConcurrentHashMap<String, Build> map, String buildName) {
		if (map == null || buildName == null || buildName.length() == 0) {
			return;
		}
		Build build = map.get(buildName);
		if (build == null) {
			build = new Build(buildName);
			map.put(build.getName(), build);
		}
	}

	private void removeBuildFromMap(ConcurrentHashMap<String, Build> map, String buildName) {
		if (map == null || buildName == null) {
			return;
		}
		map.remove(buildName);
	}

	private void addEquipmentIntoMap(ConcurrentHashMap<String, Build> map, Equipment equipment) {
		if (map == null || equipment == null) {
			return;
		}
		Build build = map.get(equipment.getBuild());
		if (build == null) {
			build = map.get(NULL_BUILD_NAME);
		}
		if (build != null) {
			synchronized (build) {
				build.addEquipment(equipment);
			}
		}
	}

	private void removeEquipmentFromMap(ConcurrentHashMap<String, Build> map, String id) {
		if (map == null || id == null) {
			return;
		}
		Iterator<Map.Entry<String, Build>> iterator = map.entrySet().iterator();
		synchronized (map) {
			while (iterator.hasNext()) {
				Map.Entry<String, Build> entry = iterator.next();
				Build build = entry.getValue();
				build.removeEquipment(id);
				if (build.getEquipments().size() == 0) {
					iterator.remove();
				}
			}
		}
	}

	private void changeEquipmentFromMap(ConcurrentHashMap<String, Build> map, Equipment equipment) {
		if (map == null || equipment == null) {
			return;
		}
		Build build = map.get(equipment.getBuild());
		if (build == null) {
			build = map.get(NULL_BUILD_NAME);
		}
		if (build != null) {
			synchronized (build) {
				build.removeEquipment(equipment.getId());
				build.addEquipment(equipment);
			}
		}
	}

	/**
	 * 从builds这个设备全集里筛选出有ip的设备进行监控
	 *
	 * @param builds
	 * @return
	 */
	private ConcurrentHashMap<String, Build> initNetview(ConcurrentHashMap<String, Build> builds) {
		if (builds == null) {
			return null;
		}
		ConcurrentHashMap<String, Build> netview = new ConcurrentHashMap<>();
		synchronized (builds) {
			for (Map.Entry<String, Build> entry : builds.entrySet()) {
				Build build = entry.getValue();
				Build b = new Build(build.getName());
				for (Equipment equipment : build.getEquipments()) {
					if (equipment.getIp() != null && equipment.getIp().length() > 0) {
						b.addEquipment(equipment);
					}
				}
				if (b.getEquipments().size() > 0) {
					netview.put(b.getName(), b);
				}
			}
		}
		return netview;
	}

	private ConcurrentHashMap<String, Build> getNetview() {
		if (netview == null) {
			netview = initNetview(getBuilds());
		}
		return netview;
	}

	private void countMalfunctionPageCount() {
		int count = malfunctionMapper.selectMalfunctionCount();
		int len = listMalfunctionLength;
		malfunctionPageCount = countPageCount(count, len);
	}

	private int countPageCount(int count, int len) {
		if (count % len == 0) {
			return count / len;
		} else {
			return count / len + 1;
		}
	}

	private void dealException(Exception e) {
		e.printStackTrace();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
