package top.cellargalaxy.service.monitor;

import top.cellargalaxy.bean.personnel.Person;

/**
 * Created by cellargalaxy on 17-12-31.
 */
public interface PersonelApi {
	/**
	 * 检查账号密码
	 *
	 * @param id
	 * @param password
	 * @return
	 */
	Person checkPassword(String id, String password);

	/**
	 * 检测账号是否被禁用
	 *
	 * @param id
	 * @return
	 */
	boolean checkMonitorDisabled(String id);

	/**
	 * 检查是否有监控的管理员权限
	 *
	 * @param id
	 * @return
	 */
	boolean checkMonitorAdmin(String id);

	/**
	 * 检测是否有监控的root权限
	 *
	 * @param id
	 * @return
	 */
	boolean checkMonitorRoot(String id);
}
