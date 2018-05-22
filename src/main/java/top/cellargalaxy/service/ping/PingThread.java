package top.cellargalaxy.service.ping;

import org.apache.commons.exec.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.bean.monitor.Equipment;
import top.cellargalaxy.bean.serviceBean.Build;
import top.cellargalaxy.bean.serviceBean.PingResult;
import top.cellargalaxy.configuration.GlobalConfiguration;
import top.cellargalaxy.configuration.MonitorConfiguration;
import top.cellargalaxy.service.monitor.MonitorService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Component
public class PingThread {
	@Autowired
	private MonitorService monitorService;

	private final int maxDelay;
	private final int pingTimes;
	private final int pingWaitTime;
	private final String coding;

	private final PingDeal pingDeal;

	@Autowired
	public PingThread(MonitorConfiguration monitorConfiguration, PingDealFactory pingDealFactory) {
		maxDelay = monitorConfiguration.getMaxDelay();
		pingTimes = monitorConfiguration.getPingTimes();
		pingWaitTime = monitorConfiguration.getPingWaitTime();
		coding = GlobalConfiguration.CODING;
		pingDeal = pingDealFactory.getPingDeal();
	}

	private Result ping(Equipment equipment) {
		try {
			if (equipment == null || equipment.getIp() == null) {
				return null;
			}
			DefaultExecutor executor = new DefaultExecutor();
			ExecuteWatchdog watchdog = new ExecuteWatchdog(maxDelay * pingTimes);
			executor.setWatchdog(watchdog);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			executor.setStreamHandler(new PumpStreamHandler(baos, baos));

			CommandLine commandLine = pingDeal.createPingCommandLine(equipment.getIp(), pingTimes);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			executor.execute(commandLine, resultHandler);
			return new Result(equipment, resultHandler, baos);
		} catch (ExecuteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Scheduled(fixedDelay = 5 * 1000)
	public void run() {
		Collection<Build> netview = monitorService.findNetview();
		LinkedList<Result> results = new LinkedList<>();
		for (Build build : netview) {
			for (Equipment equipment : build.getEquipments()) {
				results.add(ping(equipment));
				try {
					Thread.sleep(pingWaitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		for (Result result : results) {
			if (result != null) {
				try {
					result.getResultHandler().waitFor();
					PingResult pingResult = new PingResult();
					String string = result.getByteArrayOutputStream().toString(coding);
					pingResult.setDelay(pingDeal.analysisDelay(string, PingResult.DEFAULT_DELAY_NUM));
					if (result.getEquipment().addPingResult(pingResult)) {
						monitorService.addChangeStatusEquipment(result.getEquipment());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class Result {
		private final Equipment equipment;
		private final DefaultExecuteResultHandler resultHandler;
		private final ByteArrayOutputStream byteArrayOutputStream;

		public Result(Equipment equipment, DefaultExecuteResultHandler resultHandler, ByteArrayOutputStream byteArrayOutputStream) {
			this.equipment = equipment;
			this.resultHandler = resultHandler;
			this.byteArrayOutputStream = byteArrayOutputStream;
		}

		public Equipment getEquipment() {
			return equipment;
		}

		public DefaultExecuteResultHandler getResultHandler() {
			return resultHandler;
		}

		public ByteArrayOutputStream getByteArrayOutputStream() {
			return byteArrayOutputStream;
		}

		@Override
		public String toString() {
			return "Result{" +
					"equipment=" + equipment.getIp() +
					'}';
		}
	}
}
