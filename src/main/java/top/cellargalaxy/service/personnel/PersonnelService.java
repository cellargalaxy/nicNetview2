package top.cellargalaxy.service.personnel;

import top.cellargalaxy.bean.personnel.Authorized;
import top.cellargalaxy.bean.personnel.Permission;
import top.cellargalaxy.bean.personnel.Person;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-8.
 */
public interface PersonnelService {
	boolean addPerson(Person person);
	
	LinkedList<Person> addPersons(File file);
	
	boolean writePersonFile(File file);
	
	boolean removePerson(String id);
	
	Person findPersonById(String id);
	
	Person[] findPersons(int page);
	
	boolean changePerson(Person person);
	
	int getPersonPageCount();
	
	///////////////////////////////////////////////
	boolean addPermission(Permission permission);
	
	boolean removePermission(int permission);
	
	Permission findPermission(int permission);
	
	Permission[] findAllPermission();
	
	boolean changePermission(Permission permission);
	
	///////////////////////////////////////
	boolean addAuthorized(Authorized authorized);
	
	boolean removeAuthorized(Authorized authorized);
	
	boolean findExistAuthorized(Authorized authorized);
	
	Authorized[] findAuthorizedById(String id);
	
	Authorized[] findAuthorizedByPermission(int permission);
	
	Authorized[] findAllAuthorized();
	
	/////////////////////////////////////////////////////////////////
	boolean checkToken(String token);
	
	boolean checkPassword(Person person);
	
	boolean checkPersonnelDisabled(Person person);
	
	boolean checkPersonnelAdmin(Person person);
	
	boolean checkPersonnelRoot(Person person);
}
