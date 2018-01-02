package top.cellargalaxy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cellargalaxy on 17-12-31.
 */
@Component
public class WxConfiguration {
	@Value("${wx.able:false}")
	private boolean able;
	@Value("${wx.token:token}")
	private String token;
	@Value("${wx.appID:wx1f66a1c5c4e8af87}")
	private String appID;
	@Value("${wx.appSecret:8c87978c52155d44f8c4abb76eb7f4bd}")
	private String appSecret;
	@Value("${wx.templateId:EsMSjoBzPqQKnsNYCTisWP5EQNSeH6WB6lPiOrXkMqY}")
	private String templateId;
	@Value("${wx.netviewUrl:http://119.29.171.44:8080/netview}")
	private String netviewUrl;
	
	public boolean isAble() {
		return able;
	}
	
	public void setAble(boolean able) {
		this.able = able;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getAppID() {
		return appID;
	}
	
	public void setAppID(String appID) {
		this.appID = appID;
	}
	
	public String getAppSecret() {
		return appSecret;
	}
	
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	public String getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public String getNetviewUrl() {
		return netviewUrl;
	}
	
	public void setNetviewUrl(String netviewUrl) {
		this.netviewUrl = netviewUrl;
	}
}
