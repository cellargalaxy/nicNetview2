package top.cellargalaxy.bean.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 故障
 * Created by cellargalaxy on 17-12-7.
 */
public class Malfunction {
	/**
	 * 不通的状态
	 */
	public static final int NOT_CON_STATUS = 0;
	/**
	 * 通的状态
	 */
	public static final int CON_STATUS = 1;

	/**
	 * 校区
	 */
	private String area;
	/**
	 * 楼栋
	 */
	private String build;
	/**
	 * 楼层
	 */
	private String floor;
	/**
	 * 楼层编号
	 */
	private int number;
	/**
	 * 设备id
	 */
	private String equipmentId;
	/**
	 * 故障时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd kk:mm:ss")
	private Date malfunctionDatetime;
	/**
	 * 故障状态
	 */
	private int status;

	public Malfunction() {
	}

	public Malfunction(Equipment equipment) {
		area = equipment.getArea();
		build = equipment.getBuild();
		floor = equipment.getFloor();
		number = equipment.getNumber();
		equipmentId = equipment.getId();
		malfunctionDatetime = equipment.getDate();
		if (equipment.isStatus()) {
			status = CON_STATUS;
		} else {
			status = NOT_CON_STATUS;
		}
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Date getMalfunctionDatetime() {
		return malfunctionDatetime;
	}

	public void setMalfunctionDatetime(Date malfunctionDatetime) {
		this.malfunctionDatetime = malfunctionDatetime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Malfunction{" +
				"area='" + area + '\'' +
				", build='" + build + '\'' +
				", floor='" + floor + '\'' +
				", number=" + number +
				", equipmentId='" + equipmentId + '\'' +
				", malfunctionDatetime=" + malfunctionDatetime +
				", status=" + status +
				'}';
	}
}
