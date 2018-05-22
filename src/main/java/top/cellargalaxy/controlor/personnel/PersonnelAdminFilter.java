package top.cellargalaxy.controlor.personnel;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.controlor.ControlorUtil;
import top.cellargalaxy.service.personnel.PersonnelService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-12-13.
 */
@WebFilter(filterName = "personnelAdminFilter", urlPatterns = PersonnelAdminControlor.PERSONNEL_ADMIN_CONTROLOR_URL + "/*")
public class PersonnelAdminFilter implements Filter {
	@Autowired
	private PersonnelService personnelService;
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		
		Person loginPerson = ControlorUtil.getPerson(httpServletRequest.getSession());
		if (loginPerson == null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", false);
			jsonObject.put("data", "no login");
			httpServletResponse.getWriter().write(jsonObject.toString());
			return;
		}
		if (!personnelService.checkPersonnelAdmin(loginPerson)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", false);
			jsonObject.put("data", "no admin permissions");
			httpServletResponse.getWriter().write(jsonObject.toString());
			return;
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	@Override
	public void destroy() {
	
	}
}
