package top.cellargalaxy.service.monitor;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.configuration.NetviewConfiguration;
import top.cellargalaxy.util.HttpRequestBaseDeal;
import top.cellargalaxy.util.TextUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cellargalaxy on 17-12-31.
 */
@Component
public class PersonelApiImpl implements PersonelApi {
	private final String personelToken;
	private final String inquirePersonPasswordUrl;
	private final String inquireExistAuthorizedUrl;
	private final int personelTimeout;

	@Autowired
	public PersonelApiImpl(NetviewConfiguration netviewConfiguration) {
		personelToken = netviewConfiguration.getPersonnelToken();
		inquirePersonPasswordUrl = netviewConfiguration.getInquirePersonPasswordUrl();
		inquireExistAuthorizedUrl = netviewConfiguration.getInquireExistAuthorizedUrl();
		personelTimeout = netviewConfiguration.getPersonnelTimeout();
	}

	@Override
	public Person checkPassword(String id, String password) {
		try {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("token", personelToken));
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("password", password));
			HttpGet httpGet = HttpRequestBaseDeal.createHttpGet(inquirePersonPasswordUrl, params);
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpGet, personelTimeout);
			if (result == null || result.length() == 0) {
				return null;
			}
			JSONObject jsonObject = new JSONObject(result);
			return json2Person(jsonObject.optJSONObject("data"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpGet, personelTimeout);
			if (result == null || result.length() == 0) {
				return false;
			}
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.getBoolean("result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private Person json2Person(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		String id = jsonObject.optString("id");
		String name = jsonObject.optString("name");
		String sex = jsonObject.optString("sex");
		String college = jsonObject.optString("college");
		int grade = jsonObject.has("grade") ? jsonObject.getInt("grade") : -1;
		String professionClass = jsonObject.optString("professionClass");
		String phone = jsonObject.optString("phone");
		String cornet = jsonObject.optString("cornet");
		String qq = jsonObject.optString("qq");
		Date birthday = jsonObject.has("birthday") ? TextUtil.getDateFromText(jsonObject.getString("birthday")) : null;
		String introduction = jsonObject.optString("qq");
		String team = jsonObject.optString("team");
		String password = jsonObject.optString("password");
		return new Person(id, name, sex, college, grade, professionClass, phone, cornet, qq, birthday, introduction, team, password);
	}
}
