package top.cellargalaxy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cellargalaxy on 17-12-8.
 */
@Component
public class PersonneConfiguration {
	@Value("${personne.api.listPersonLength:10}")
	private int listPersonLength;
	@Value("${personne.api.token:token}")
	private String token;

	public int getListPersonLength() {
		return listPersonLength;
	}

	public void setListPersonLength(int listPersonLength) {
		this.listPersonLength = listPersonLength;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
