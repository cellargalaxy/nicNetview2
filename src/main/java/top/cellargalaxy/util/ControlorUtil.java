package top.cellargalaxy.util;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.controlor.RootControlor;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-12-13.
 */
public class ControlorUtil {
	public static final JSONObject createJSONObject(boolean result, String data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		jsonObject.put("data", data);
		return jsonObject;
	}
	
	public static final File saveFile(MultipartFile multipartFile) {
		BufferedOutputStream out = null;
		try {
			if (multipartFile == null || multipartFile.isEmpty()) {
				return null;
			}
			File file = new File("src/main/resources/static/upload/" + (int) (Math.random() * 10000) + multipartFile.getOriginalFilename());
			file.getParentFile().mkdirs();
			out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(multipartFile.getBytes());
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static final Person getPerson(HttpSession session) {
		try {
			Object loginPerson = session.getAttribute(RootControlor.LOGIN_PERSON_NAME);
			if (loginPerson == null || !(loginPerson instanceof Person)) {
				return null;
			}
			return (Person) loginPerson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
