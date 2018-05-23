package top.cellargalaxy.controlor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.bean.controlorBean.ReturnBean;
import top.cellargalaxy.bean.monitor.Equipment;
import top.cellargalaxy.bean.monitor.Malfunction;
import top.cellargalaxy.bean.monitor.Place;
import top.cellargalaxy.bean.serviceBean.Build;
import top.cellargalaxy.service.monitor.NetviewService;

import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-8.
 */
@RestController
@RequestMapping(NetviewApiControlor.MONITOR_API_CONTROLOR_URL)
public class NetviewApiControlor {
	public static final String MONITOR_API_CONTROLOR_URL = "/api";
	@Autowired
	private NetviewService netviewService;
	
	@ResponseBody
	@GetMapping("/inquireNetview")
	public ReturnBean inquireNetview() {
		LinkedList<Build> builds = netviewService.findNetview();
		if (builds != null) {
			return new ReturnBean(true, builds);
		} else {
			return new ReturnBean(false, null);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	@ResponseBody
	@GetMapping("/inquireEquipmentById")
	public ReturnBean inquireEquipmentById(String id) {
		Equipment equipment = netviewService.findEquipmentById(id);
		if (equipment != null) {
			return new ReturnBean(true, equipment);
		} else {
			return new ReturnBean(false, null);
		}
	}
	
	@ResponseBody
	@GetMapping("/inquireAllEquipment")
	public ReturnBean inquireAllEquipment() {
		LinkedList<Build> builds = netviewService.findAllEquipment();
		if (builds != null) {
			return new ReturnBean(true, builds);
		} else {
			return new ReturnBean(false, null);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	@ResponseBody
	@GetMapping("/inquireAllPlace")
	public ReturnBean inquirePlaceByArea() {
		Place[] places = netviewService.findAllPlace();
		if (places != null) {
			return new ReturnBean(true, places);
		} else {
			return new ReturnBean(false, null);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////
	@ResponseBody
	@GetMapping("/inquireMalfunctions")
	public ReturnBean inquireMalfunctions(int page) {
		Malfunction[] malfunctions = netviewService.findMalfunctions(page);
		if (malfunctions != null) {
			return new ReturnBean(true, malfunctions);
		} else {
			return new ReturnBean(false, null);
		}
	}
	
	@ResponseBody
	@GetMapping("/inquireMalfunctionPageCount")
	public ReturnBean inquireMalfunctionPageCount() {
		return new ReturnBean(true, netviewService.getMalfunctionPageCount());
	}
}
