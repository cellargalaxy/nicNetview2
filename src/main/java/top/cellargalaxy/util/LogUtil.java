package top.cellargalaxy.util;

import org.slf4j.Logger;
import top.cellargalaxy.bean.personnel.Person;

import javax.servlet.http.HttpSession;

/**
 * Created by cellargalaxy on 18-1-2.
 */
public class LogUtil {

	public static final void info(Logger logger, HttpSession session, String string) {
		Person person = ControlorUtil.getPerson(session);
		info(logger, person, string);
	}
	
	public static final void info(Logger logger, Person person, String string) {
		logger.info(person.getId() + "(" + person.getName() + ") " + string);
	}
}
