package top.cellargalaxy.controlor.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.bean.controlorBean.ReturnBean;
import top.cellargalaxy.service.wx.Wx;

/**
 * Created by cellargalaxy on 17-12-31.
 */
@RestController
@RequestMapping("/wx")
public class WxControlor {
	@Autowired
	private Wx wx;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	@PostMapping("/sendNetviewWarm")
	public ReturnBean sendNetviewWarm(String token, String info) {
		ReturnBean returnBean;
		if (!wx.checkToken(token)) {
			returnBean = new ReturnBean(false, "非法token");
		} else if (wx.sendNetviewWarm(info)) {
			logger.info("发送成功 " + info);
			returnBean = new ReturnBean(true, "发送成功");
		} else {
			logger.info("发送失败 " + info);
			returnBean = new ReturnBean(false, "发送失败");
		}
		return returnBean;
	}
}
