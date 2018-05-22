package top.cellargalaxy.service.ping;

import org.apache.commons.exec.CommandLine;

/**
 * Created by cellargalaxy on 17-12-8.
 */
public interface PingDeal {
	/**
	 * 创建一个ping命令对象
	 * @param ip
	 * @param pingTimes
	 * @return
	 */
	CommandLine createPingCommandLine(String ip, int pingTimes);

	/**
	 * 分析ping的延时，解析失败返回默认值
	 * @param string
	 * @return
	 */
	int analysisDelay(String string, int defaultValue);
}
