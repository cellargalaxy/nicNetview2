package top.cellargalaxy.service.wx;

/**
 * Created by cellargalaxy on 17-12-31.
 */
public interface Wx {
	boolean sendNetviewWarm(String string);
	
	boolean checkToken(String token);
}
