package top.cellargalaxy.service.monitor;

import top.cellargalaxy.bean.monitor.Equipment;
import top.cellargalaxy.bean.monitor.Malfunction;
import top.cellargalaxy.bean.monitor.Place;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.bean.serviceBean.Build;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-8.
 */
public interface NetviewService {
	boolean init();

	/**
	 * 添加一台状态改变的设备
	 *
	 * @param equipment
	 */
	void addChangeStatusEquipment(Equipment equipment);

	/**
	 * 获取全部故障设备
	 *
	 * @return
	 */
	LinkedList<Build> findWarmNetview();

	/**
	 * 获取全部设备
	 *
	 * @return
	 */
	LinkedList<Build> findNetview();

	///////////////////////////////////////////////////

	/**
	 * 添加设备
	 *
	 * @param equipment
	 * @return
	 */
	boolean addEquipment(Equipment equipment);

	/**
	 * 从文件添加设备
	 *
	 * @param file
	 * @return
	 */
	LinkedList<Equipment> addEquipments(File file);

	/**
	 * 将全部设备写入文件
	 *
	 * @param file
	 * @return
	 */
	boolean writeEquipmentFile(File file);

	/**
	 * 删除设备
	 *
	 * @param id
	 * @return
	 */
	boolean removeEquipment(String id);

	/**
	 * 查找设备
	 *
	 * @param id
	 * @return
	 */
	Equipment findEquipmentById(String id);

	/**
	 * 查找全部设备
	 *
	 * @return
	 */
	LinkedList<Build> findAllEquipment();

	/**
	 * 修改设备
	 *
	 * @param equipment
	 * @return
	 */
	boolean changeEquipment(Equipment equipment);

	///////////////////////////////////////////////////

	/**
	 * 添加地点
	 *
	 * @param place
	 * @return
	 */
	boolean addPlace(Place place);

	/**
	 * 删除地点
	 *
	 * @param place
	 * @return
	 */
	boolean removePlace(Place place);

	/**
	 * 获取全部地点
	 *
	 * @return
	 */
	Place[] findAllPlace();

	//////////////////////////////////////////////////

	/**
	 * 删除故障
	 *
	 * @param malfunction
	 * @return
	 */
	boolean removeMalfunction(Malfunction malfunction);

	/**
	 * 获取故障
	 *
	 * @param page
	 * @return
	 */
	Malfunction[] findMalfunctions(int page);

	/**
	 * 获取故障页面数量
	 *
	 * @return
	 */
	int getMalfunctionPageCount();

	//////////////////////////////////////////////////

	/**
	 * 检查token
	 *
	 * @param token
	 * @return
	 */
	boolean checkToken(String token);

	/**
	 * 检查账号密码
	 *
	 * @param person
	 * @return
	 */
	Person checkPassword(Person person);

	/**
	 * 检查是否禁用
	 *
	 * @param person
	 * @return
	 */
	boolean checkMonitorDisabled(Person person);

	/**
	 * 检查是否有监控管理员权限
	 *
	 * @param person
	 * @return
	 */
	boolean checkMonitorAdmin(Person person);

	/**
	 * 检查是否有监控root权限
	 *
	 * @param person
	 * @return
	 */
	boolean checkMonitorRoot(Person person);
}
