package top.cellargalaxy.service.wx;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.cellargalaxy.configuration.WxConfiguration;
import top.cellargalaxy.util.HttpRequestBaseDeal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cellargalaxy on 17-12-31.
 */
@Service
public class WxImpl implements Wx {
	@Autowired
	private WxConfiguration wxConfiguration;
	private volatile String accessToken;
	private final int timeout = 5000;
	
	
	@Override
	public boolean sendNetviewWarm(String string) {
		try {
			if (string == null || string.length() == 0) {
				return false;
			}
			JSONObject openIdJSONObject = getOpenIds();
			if (openIdJSONObject == null) {
				return false;
			}
			if (!openIdJSONObject.has("data")) {
				return false;
			}
			JSONArray openIds = openIdJSONObject.getJSONObject("data").getJSONArray("openid");
			for (Object openId : openIds) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("value", string);
				JSONObject data = new JSONObject();
				data.put("info", jsonObject);
				sendTemplate(openId.toString(), wxConfiguration.getTemplateId(), wxConfiguration.getNetviewUrl(), data);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean checkToken(String token) {
		try {
			return token != null && token.equals(wxConfiguration.getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private JSONObject getOpenIds() {
		try {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("access_token", getAccessToken()));
			HttpGet httpGet = HttpRequestBaseDeal.createHttpGet("https://api.weixin.qq.com/cgi-bin/user/get", params);
			if (httpGet == null) {
				return null;
			}
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpGet, timeout);
			if (result == null) {
				return null;
			}
			return new JSONObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private JSONObject sendTemplate(String openId, String templateId, String url, JSONObject data) {
		try {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("access_token", getAccessToken()));
			HttpPost httpPost = HttpRequestBaseDeal.createHttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send", params);
			if (httpPost == null) {
				return null;
			}
			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("touser", openId);
			jsonObject.put("template_id", templateId);
			jsonObject.put("url", url);
			jsonObject.put("data", data);
			StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
			stringEntity.setContentEncoding("utf-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpPost, timeout);
			if (result == null) {
				return null;
			}
			return new JSONObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Scheduled(fixedRate = 1000 * 60 * 30)
	public void flushAccessToken() {
		try {
			if (!wxConfiguration.isAble()) {
				return;
			}
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("grant_type", "client_credential"));
			params.add(new BasicNameValuePair("appid", wxConfiguration.getAppID()));
			params.add(new BasicNameValuePair("secret", wxConfiguration.getAppSecret()));
			HttpGet httpGet = HttpRequestBaseDeal.createHttpGet("https://api.weixin.qq.com/cgi-bin/token", params);
			if (httpGet == null) {
				return;
			}
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpGet, timeout);
			if (result == null) {
				return;
			}
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("access_token")) {
				accessToken = jsonObject.getString("access_token");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getAccessToken() {
		if (accessToken == null) {
			flushAccessToken();
		}
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
