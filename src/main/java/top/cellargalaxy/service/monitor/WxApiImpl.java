package top.cellargalaxy.service.monitor;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.bean.monitor.Equipment;
import top.cellargalaxy.configuration.NetviewConfiguration;
import top.cellargalaxy.util.HttpRequestBaseDeal;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by cellargalaxy on 17-12-31.
 */
@Component
public class WxApiImpl implements WxApi {
	private final String wxCoding;
	private final String wxToken;
	private final String wxUrl;
	private final int wxTimeout;

	private final LinkedList<Build> builds;

	@Autowired
	public WxApiImpl(NetviewConfiguration netviewConfiguration) {
		wxCoding = netviewConfiguration.getWxCoding();
		wxToken = netviewConfiguration.getWxToken();
		wxUrl = netviewConfiguration.getWxUrl();
		wxTimeout = netviewConfiguration.getWxTimeout();
		builds = new LinkedList<>();
	}

	@Override
	public void addChangeStatusEquipment(Equipment equipment) {
		if (equipment == null || equipment.getIsWarn() != Equipment.IS_WARM_NUM ||
				equipment.getBuild() == null || equipment.getBuild().length() == 0) {
			return;
		}
		synchronized (builds) {
			for (Build build : builds) {
				if (equipment.getBuild().equals(build.getName())) {
					build.addEquipment(equipment);
					return;
				}
			}
			Build build = new Build(equipment.getBuild());
			build.addEquipment(equipment);
			builds.add(build);
		}
	}

	private String createInfo() {
		StringBuilder stringBuilder = new StringBuilder();
		synchronized (builds) {
			Collections.sort(builds);
			Iterator<Build> iterator = builds.iterator();
			while (iterator.hasNext()) {
				Build build = iterator.next();
				if (build.getCons().size() == 0 && build.getNotCons().size() == 0) {
					iterator.remove();
					continue;
				}
				if (build.getCons().size() == 1) {
					Equipment equipment = null;
					for (Map.Entry<String, Equipment> equipmentEntry : build.getCons().entrySet()) {
						equipment = equipmentEntry.getValue();
					}
					stringBuilder.append("通 " + equipment.getFullName() + " " + equipment.getIp() + "\n");
				} else if (build.getCons().size() > 1) {
					stringBuilder.append("通 " + build.getName() + "：" + build.getCons().size() + "台\n");
				}
				if (build.getNotCons().size() == 1) {
					Equipment equipment = null;
					for (Map.Entry<String, Equipment> equipmentEntry : build.getNotCons().entrySet()) {
						equipment = equipmentEntry.getValue();
					}
					stringBuilder.append("挂 " + equipment.getFullName() + " " + equipment.getIp() + "\n");
				} else if (build.getNotCons().size() > 1) {
					stringBuilder.append("挂 " + build.getName() + "：" + build.getNotCons().size() + "台\n");
				}
			}
		}
		return stringBuilder.toString();
	}

	@Scheduled(fixedDelay = 1000 * 60)
	public void send() {
		try {
			String info = createInfo();
			if (info == null || info.length() == 0) {
				return;
			}
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("token", wxToken));
			params.add(new BasicNameValuePair("info", info));
			HttpPost httpPost = new HttpPost(wxUrl);
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, wxCoding);
			httpPost.setEntity(urlEncodedFormEntity);
			String result = HttpRequestBaseDeal.executeHttpRequestBase(httpPost, wxTimeout);
			if (result == null) {
				return;
			}
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("result") && jsonObject.getBoolean("result")) {
				synchronized (builds) {
					builds.clear();
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private class Build implements Comparable<Build> {
		private final String name;
		private final Map<String, Equipment> cons;
		private final Map<String, Equipment> notCons;

		public Build(String name) {
			this.name = name;
			cons = new HashMap<>();
			notCons = new HashMap<>();
		}

		public synchronized void addEquipment(Equipment equipment) {
			if (equipment.isStatus()) {
				cons.put(equipment.getId(), equipment);
				notCons.remove(equipment.getId());
			} else {
				cons.remove(equipment.getId());
				notCons.put(equipment.getId(), equipment);
			}
		}

		public String getName() {
			return name;
		}

		public Map<String, Equipment> getCons() {
			return cons;
		}

		public Map<String, Equipment> getNotCons() {
			return notCons;
		}

		@Override
		public int compareTo(Build build) {
			return name.compareTo(build.getName());
		}
	}
}
