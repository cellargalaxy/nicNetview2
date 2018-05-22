package top.cellargalaxy.service.monitor;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.cellargalaxy.configuration.MonitorConfiguration;
import top.cellargalaxy.util.HttpRequestBaseDeal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cellargalaxy on 17-12-31.
 */
@Component
public class PersonelApiImpl implements PersonelApi {
	private final String personelToken;
	private final String inquirePersonPasswordUrl;
	private final String inquireExistAuthorizedUrl;
	private final int timeout;

	@Autowired
	public PersonelApiImpl(MonitorConfiguration monitorConfiguration) {
		personelToken = monitorConfiguration.getPersonelToken();
		inquirePersonPasswordUrl = monitorConfiguration.getInquirePersonPasswordUrl();
		inquireExistAuthorizedUrl = monitorConfiguration.getInquireExistAuthorizedUrl();
		timeout = monitorConfiguration.getPersonelTimeout();
	}

	@Override
	public boolean checkPassword(String id, String password) {
		try {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("token", personelToken));
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("password", password));
			HttpGet httpGet = HttpRequestBaseDeal.createHttpGet(inquirePersonPasswordUrl, params);
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpGet, timeout);
			if (result == null || result.length() == 0) {
				return false;
			}
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.optBoolean("result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkMonitorDisabled(String id) {
		return chackPermission(id, -1);
	}

	@Override
	public boolean checkMonitorAdmin(String id) {
		return chackPermission(id, 11);
	}

	@Override
	public boolean checkMonitorRoot(String id) {
		return chackPermission(id, 10);
	}

	private boolean chackPermission(String id, int permission) {
		try {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("token", personelToken));
			params.add(new BasicNameValuePair("personId", id));
			params.add(new BasicNameValuePair("permission", String.valueOf(permission)));
			HttpGet httpGet = HttpRequestBaseDeal.createHttpGet(inquireExistAuthorizedUrl, params);
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpGet, timeout);
			if (result == null || result.length() == 0) {
				return false;
			}
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.optBoolean("result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
