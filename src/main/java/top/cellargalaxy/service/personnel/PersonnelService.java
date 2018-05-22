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
	/**
	 * 添加一个人员
	 *
	 * @param person
	 * @return
	 */
	boolean addPerson(Person person);

	/**
	 * 从文件里加载人员
	 *
	 * @param file
	 * @return
	 */
	LinkedList<Person> addPersons(File file);

	/**
	 * 把全部人员写入文件
	 *
	 * @param file
	 * @return
	 */
	boolean writePersonFile(File file);

	/**
	 * 删除某人
	 *
	 * @param id
	 * @return
	 */
	boolean removePerson(String id);

	/**
	 * 查询某人
	 *
	 * @param id
	 * @return
	 */
	Person findPersonById(String id);

	/**
	 * 获取某一页的人
	 *
	 * @param page
	 * @return
	 */
	Person[] findPersons(int page);

	/**
	 * 修改某人
	 *
	 * @param person
	 * @return
	 */
	boolean changePerson(Person person);

	/**
	 * 获取人员页面数量
	 *
	 * @return
	 */
	int getPersonPageCount();

	///////////////////////////////////////////////

	/**
	 * 添加权限
	 *
	 * @param permission
	 * @return
	 */
	boolean addPermission(Permission permission);

	/**
	 * 删除权限
	 *
	 * @param permission
	 * @return
	 */
	boolean removePermission(int permission);

	/**
	 * 查询权限
	 *
	 * @param permission
	 * @return
	 */
	Permission findPermission(int permission);

	/**
	 * 查询全部权限
	 *
	 * @return
	 */
	Permission[] findAllPermission();

	/**
	 * 修改权限
	 *
	 * @param permission
	 * @return
	 */
	boolean changePermission(Permission permission);

	///////////////////////////////////////

	/**
	 * 添加授权
	 *
	 * @param authorized
	 * @return
	 */
	boolean addAuthorized(Authorized authorized);

	/**
	 * 删除授权
	 *
	 * @param authorized
	 * @return
	 */
	boolean removeAuthorized(Authorized authorized);

	/**
	 * 是否存在授权
	 *
	 * @param authorized
	 * @return
	 */
	boolean findExistAuthorized(Authorized authorized);

	/**
	 * 查询某人的授权
	 *
	 * @param id
	 * @return
	 */
	Authorized[] findAuthorizedById(String id);

	/**
	 * 查询被授权的全部人
	 *
	 * @param permission
	 * @return
	 */
	Authorized[] findAuthorizedByPermission(int permission);

	/**
	 * 查询全部授权
	 *
	 * @return
	 */
	Authorized[] findAllAuthorized();

	/////////////////////////////////////////////////////////////////

	/**
	 * 检查token
	 *
	 * @param token
	 * @return
	 */
	boolean checkToken(String token);

	/**
	 * 检查密码
	 *
	 * @param person
	 * @return
	 */
	boolean checkPassword(Person person);

	/**
	 * 检查人员是否被禁用
	 *
	 * @param person
	 * @return
	 */
	boolean checkPersonnelDisabled(Person person);

	/**
	 * 检查人员是否有管理员权限
	 *
	 * @param person
	 * @return
	 */
	boolean checkPersonnelAdmin(Person person);

	/**
	 * 检查人员是否有root权限
	 *
	 * @param person
	 * @return
	 */
	boolean checkPersonnelRoot(Person person);
}
