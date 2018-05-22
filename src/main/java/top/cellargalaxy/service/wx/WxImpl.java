package top.cellargalaxy.service.wx;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.cellargalaxy.configuration.GlobalConfiguration;
import top.cellargalaxy.configuration.WxConfiguration;
import top.cellargalaxy.util.HttpRequestBaseDeal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cellargalaxy on 17-12-31.
 */
@Service
public class WxImpl implements Wx {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private volatile String accessToken;

	private final String appId;
	private final String appSecret;
	private final boolean able;
	private final String templateId;
	private final String netviewUrl;
	private final String coding;
	private final int timeout;
	private final String token;

	@Autowired
	public WxImpl(WxConfiguration wxConfiguration) {
		appId = wxConfiguration.getAppID();
		appSecret = wxConfiguration.getAppSecret();
		able = wxConfiguration.isAble();
		templateId = wxConfiguration.getTemplateId();
		netviewUrl = wxConfiguration.getNetviewUrl();
		coding = GlobalConfiguration.CODING;
		timeout = wxConfiguration.getTimeout();
		token = wxConfiguration.getToken();
	}

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
				logger.info("openId数据异常 " + openIdJSONObject);
				return false;
			}
			JSONArray openIds = openIdJSONObject.getJSONObject("data").getJSONArray("openid");
			for (Object openId : openIds) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("value", string);
				JSONObject data = new JSONObject();
				data.put("info", jsonObject);
				sendTemplate(openId.toString(), templateId, netviewUrl, data);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkToken(String token) {
		return this.token.equals(token);
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
			httpPost.addHeader("Content-Type", "application/json;charset=" + coding);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("touser", openId);
			jsonObject.put("template_id", templateId);
			jsonObject.put("url", url);
			jsonObject.put("data", data);
			StringEntity stringEntity = new StringEntity(jsonObject.toString(), coding);
			stringEntity.setContentEncoding(coding);
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
		if (!able) {
			return;
		}
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("grant_type", "client_credential"));
		params.add(new BasicNameValuePair("appid", appId));
		params.add(new BasicNameValuePair("secret", appSecret));
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
			logger.info("成功刷新accessToken " + accessToken);
		} else {
			logger.info("失败刷新accessToken " + jsonObject);
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
