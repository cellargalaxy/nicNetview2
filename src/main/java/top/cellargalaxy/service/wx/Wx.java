package top.cellargalaxy.service.wx;

/**
 * Created by cellargalaxy on 17-12-31.
 */
public interface Wx {
	/**
	 * 发送监控警报
	 *
	 * @param string
	 * @return 发送是否成功
	 */
	boolean sendNetviewWarm(String string);

	/**
	 * 检查token
	 *
	 * @param token
	 * @return
	 */
	boolean checkToken(String token);
}
