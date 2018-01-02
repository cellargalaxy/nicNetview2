package top.cellargalaxy.controlor.personnel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.controlor.RootControlor;
import top.cellargalaxy.service.personnel.PersonnelService;

import javax.servlet.http.HttpSession;

/**
 * Created by cellargalaxy on 17-12-21.
 */
@Controller
@RequestMapping(PersonnelPageControlor.PERSONNEL_PAGE_CONTROLOR_URL)
public class PersonnelPageControlor {
	public static final String PERSONNEL_PAGE_CONTROLOR_URL = "/personnel/page";
	
	@Autowired
	private PersonnelService personnelService;
	
	@GetMapping("")
	public String personnel() {
		return "personnel";
	}
	
	@GetMapping("/addPersons")
	public String addPersons() {
		return "addPersons";
	}
	
	@GetMapping("/listPermission")
	public String listPermission() {
		return "listPermission";
	}
	
	@GetMapping("/listPerson")
	public String listPerson() {
		return "listPerson";
	}
	
	@GetMapping("/listAuthorized")
	public String listAuthorized() {
		return "listAuthorized";
	}
	
	@GetMapping("/personForm/{id}")
	public String personForm(Model model, HttpSession session, @PathVariable("id") String id) {
		Object loginPerson = session.getAttribute(RootControlor.LOGIN_PERSON_NAME);
		if (loginPerson == null || !(loginPerson instanceof Person)) {
			session.setAttribute(RootControlor.INFO_NAME, "请登录");
			return "redirect:/login";
		}
		Person person = (Person) loginPerson;
		if (!person.getId().equals(id) && !personnelService.checkPersonnelAdmin(person)) {
			session.setAttribute(RootControlor.INFO_NAME, "你没有管理员权限修改他人信息，请用管理员账号登录");
			return "redirect:/login";
		}
		model.addAttribute("id", id);
		return "personForm";
	}
}
