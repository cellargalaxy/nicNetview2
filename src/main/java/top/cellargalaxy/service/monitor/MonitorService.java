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
public interface MonitorService {
	boolean init();
	
	void addChangeStatusEquipment(Equipment equipment);
	
	LinkedList<Build> findWarmNetview();
	
	LinkedList<Build> findNetview();
	
	///////////////////////////////////////////////////
	boolean addEquipment(Equipment equipment);
	
	LinkedList<Equipment> addEquipments(File file);
	
	boolean writeEquipmentFile(File file);
	
	boolean removeEquipment(String id);
	
	Equipment findEquipmentById(String id);
	
	LinkedList<Build> findAllEquipment();
	
	boolean changeEquipment(Equipment equipment);
	
	///////////////////////////////////////////////////
	boolean addPlace(Place place);
	
	boolean removePlace(Place place);
	
	Place[] findAllPlace();
	
	//////////////////////////////////////////////////
	boolean removeMalfunction(Malfunction malfunction);
	
	Malfunction[] findMalfunctions(int page);
	
	int getMalfunctionPageCount();
	
	//////////////////////////////////////////////////
	boolean checkToken(String token);
	
	boolean checkPassword(Person person);
	
	boolean checkMonitorDisabled(Person person);
	
	boolean checkMonitorAdmin(Person person);
	
	boolean checkMonitorRoot(Person person);
}
