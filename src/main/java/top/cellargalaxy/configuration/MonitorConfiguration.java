package top.cellargalaxy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Component
public class MonitorConfiguration {
	@Value("${monitor.ping.maxDelay:1000}")
	private int maxDelay;
	@Value("${monitor.ping.pingWaitTime:50}")
	private int pingWaitTime;
	@Value("${monitor.ping.pingTimes:1}")
	private int pingTimes;

	@Value("${monitor.token:token}")
	private String token;
	@Value("${monitor.listMalfunctionLength:10}")
	private int listMalfunctionLength;

	@Value("${monitor.personelApi.timeout:5000}")
	private int personelTimeout;
	@Value("${monitor.personelApi.token:token}")
	private String personelToken;
	@Value(("${monitor.personelApi.inquirePersonPasswordUrl:http://127.0.0.1/personnel/api/inquirePersonPassword}"))
	private String inquirePersonPasswordUrl;
	@Value(("${monitor.personelApi.inquireExistAuthorizedUrl:http://127.0.0.1/personnel/api/inquireExistAuthorized}"))
	private String inquireExistAuthorizedUrl;

	@Value("${monitor.wxApi.timeout:60000}")
	private int wxTimeout;
	@Value("${monitor.wxApi.token:token}")
	private String wxToken;
	@Value("${monitor.wxApi.url:http://127.0.0.1/wx/sendNetviewWarm}")
	private String wxUrl;

	public int getMaxDelay() {
		return maxDelay;
	}

	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	public int getPingWaitTime() {
		return pingWaitTime;
	}

	public void setPingWaitTime(int pingWaitTime) {
		this.pingWaitTime = pingWaitTime;
	}

	public int getPingTimes() {
		return pingTimes;
	}

	public void setPingTimes(int pingTimes) {
		this.pingTimes = pingTimes;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getListMalfunctionLength() {
		return listMalfunctionLength;
	}

	public void setListMalfunctionLength(int listMalfunctionLength) {
		this.listMalfunctionLength = listMalfunctionLength;
	}

	public int getPersonelTimeout() {
		return personelTimeout;
	}

	public void setPersonelTimeout(int personelTimeout) {
		this.personelTimeout = personelTimeout;
	}

	public String getPersonelToken() {
		return personelToken;
	}

	public void setPersonelToken(String personelToken) {
		this.personelToken = personelToken;
	}

	public String getInquirePersonPasswordUrl() {
		return inquirePersonPasswordUrl;
	}

	public void setInquirePersonPasswordUrl(String inquirePersonPasswordUrl) {
		this.inquirePersonPasswordUrl = inquirePersonPasswordUrl;
	}

	public String getInquireExistAuthorizedUrl() {
		return inquireExistAuthorizedUrl;
	}

	public void setInquireExistAuthorizedUrl(String inquireExistAuthorizedUrl) {
		this.inquireExistAuthorizedUrl = inquireExistAuthorizedUrl;
	}

	public int getWxTimeout() {
		return wxTimeout;
	}

	public void setWxTimeout(int wxTimeout) {
		this.wxTimeout = wxTimeout;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}

	public String getWxUrl() {
		return wxUrl;
	}

	public void setWxUrl(String wxUrl) {
		this.wxUrl = wxUrl;
	}
}
