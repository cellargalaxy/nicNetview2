package top.cellargalaxy.service.monitor;

import top.cellargalaxy.bean.monitor.Equipment;

/**
 * 记录状态改变的设备，并向微信模块发送请求
 * Created by cellargalaxy on 17-12-31.
 */
public interface WxApi {
	/**
	 * 添加一台状态改变的设备
	 *
	 * @param equipment
	 */
	void addChangeStatusEquipment(Equipment equipment);
}
