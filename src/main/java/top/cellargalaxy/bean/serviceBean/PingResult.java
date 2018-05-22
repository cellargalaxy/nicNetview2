package top.cellargalaxy.bean.serviceBean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * ping结果
 * Created by cellargalaxy on 17-12-7.
 */
public class PingResult {
	/**
	 * 默认延时
	 */
	public static final int DEFAULT_DELAY_NUM = -1;

	/**
	 * ping检测的时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "MM-dd kk:mm:ss")
	private final Date date;
	/**
	 * 延时
	 */
	private int delay;

	public PingResult() {
		date = new Date();
		delay = DEFAULT_DELAY_NUM;
	}

	public PingResult(Date date, int delay) {
		this.date = date;
		this.delay = delay;
	}

	public Date getDate() {
		return date;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public String toString() {
		return "PingResult{" +
				"date=" + date +
				", delay=" + delay +
				'}';
	}
}
