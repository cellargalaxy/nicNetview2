package top.cellargalaxy.controlor.personnel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.controlor.ControlorUtil;
import top.cellargalaxy.service.personnel.PersonnelService;
import top.cellargalaxy.util.LogUtil;

/**
 * Created by cellargalaxy on 17-12-13.
 */
@Controller
@RequestMapping(PersonnelControlor.PERSONNEL_CONTROLOR_URL)
public class PersonnelControlor {
	public static final String PERSONNEL_CONTROLOR_URL = "/personnel";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PersonnelService personnelService;
	
	@GetMapping("/signUp")
	public String signUp() {
		return "signUp";
	}
	
	@ResponseBody
	@PostMapping("/signUp")
	public String signUp(Person person, String pw) {
		String string = checkPerson(person, pw);
		if (string != null) {
			return string;
		}
		if (personnelService.addPerson(person)) {
			LogUtil.info(logger, person, "注册成功");
			return ControlorUtil.createJSONObject(true, "注册成功").toString();
		} else {
			LogUtil.info(logger, person, "注册失败");
			return ControlorUtil.createJSONObject(false, "注册失败").toString();
		}
	}
	
	public static final String checkPerson(Person person, String pw) {
		if (person == null) {
			return ControlorUtil.createJSONObject(false, "信息缺失").toString();
		}
		if (person.getId() == null || person.getId().length() == 0) {
			return ControlorUtil.createJSONObject(false, "写个学号好让我知道你是谁吧").toString();
		}
		if (person.getName() == null || person.getName().length() == 0) {
			return ControlorUtil.createJSONObject(false, "写个名字好让我知道你是谁吧").toString();
		}
		if (person.getCollege() == null || person.getCollege().length() == 0) {
			return ControlorUtil.createJSONObject(false, "填个学院呗").toString();
		}
		if (person.getGrade() < 1 || person.getGrade() > 99) {
			return ControlorUtil.createJSONObject(false, "年级呢").toString();
		}
		if (person.getProfessionClass() == null || person.getProfessionClass().length() == 0) {
			return ControlorUtil.createJSONObject(false, "专业班级是啥").toString();
		}
		if (person.getPhone() == null || person.getPhone().length() == 0) {
			return ControlorUtil.createJSONObject(false, "留个手机吧").toString();
		}
		if (person.getQq() == null || person.getQq().length() == 0) {
			return ControlorUtil.createJSONObject(false, "给个qq方便联系").toString();
		}
		if (person.getBirthday() == null) {
			return ControlorUtil.createJSONObject(false, "写一下生日呢").toString();
		}
		if (person.getPassword() == null || person.getPassword().length() == 0 || pw == null || pw.length() == 0) {
			return ControlorUtil.createJSONObject(false, "密码怎么可以为空").toString();
		}
		if (!person.getPassword().equals(pw)) {
			return ControlorUtil.createJSONObject(false, "两次密码不一样了呀").toString();
		}
		return null;
	}
}
