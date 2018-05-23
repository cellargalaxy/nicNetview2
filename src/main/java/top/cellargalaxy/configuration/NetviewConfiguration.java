package top.cellargalaxy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Component
public class NetviewConfiguration {
	@Value("${netview.ping.maxDelay:1000}")
	private int maxDelay;
	@Value("${netview.ping.pingWaitTime:50}")
	private int pingWaitTime;
	@Value("${netview.ping.pingTimes:1}")
	private int pingTimes;
	@Value("${netview.ping.coding:UTF-8}")
	private String pingCoding;

	@Value("${netview.token:token}")
	private String token;
	@Value("${netview.listMalfunctionLength:10}")
	private int listMalfunctionLength;

	@Value("${netview.personnel.coding:UTF-8}")
	private String personnelCoding;
	@Value("${netview.personnelApi.timeout:5000}")
	private int personnelTimeout;
	@Value("${netview.personnelApi.token:token}")
	private String personnelToken;
	@Value(("${netview.personnelApi.inquirePersonPasswordUrl:http://127.0.0.1/personnel/api/inquirePersonPassword}"))
	private String inquirePersonPasswordUrl;
	@Value(("${netview.personnelApi.inquireExistAuthorizedUrl:http://127.0.0.1/personnel/api/inquireExistAuthorized}"))
	private String inquireExistAuthorizedUrl;

	@Value("${netview.wxApi.coding:UTF-8}")
	private String wxCoding;
	@Value("${netview.wxApi.timeout:60000}")
	private int wxTimeout;
	@Value("${netview.wxApi.token:token}")
	private String wxToken;
	@Value("${netview.wxApi.url:http://127.0.0.1/wx/sendNetviewWarm}")
	private String wxUrl;

	public String getPingCoding() {
		return pingCoding;
	}

	public void setPingCoding(String pingCoding) {
		this.pingCoding = pingCoding;
	}

	public String getWxCoding() {
		return wxCoding;
	}

	public void setWxCoding(String wxCoding) {
		this.wxCoding = wxCoding;
	}

	public String getPersonnelCoding() {

		return personnelCoding;
	}

	public void setPersonnelCoding(String personnelCoding) {
		this.personnelCoding = personnelCoding;
	}

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

	public int getPersonnelTimeout() {
		return personnelTimeout;
	}

	public void setPersonnelTimeout(int personnelTimeout) {
		this.personnelTimeout = personnelTimeout;
	}

	public String getPersonnelToken() {
		return personnelToken;
	}

	public void setPersonnelToken(String personnelToken) {
		this.personnelToken = personnelToken;
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
