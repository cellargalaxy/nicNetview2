package top.cellargalaxy.bean.personnel;


/**
 * Created by cellargalaxy on 17-12-7.
 */
public class Authorized {
	private String personId;
	private int permission;
	
	public Authorized() {
	}
	
	public Authorized(String personId, int permission) {
		this.personId = personId;
		this.permission = permission;
	}
	
	public String getPersonId() {
		return personId;
	}
	
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public int getPermission() {
		return permission;
	}
	
	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	@Override
	public String toString() {
		return "Authorized{" +
				"personId='" + personId + '\'' +
				", permission=" + permission +
				'}';
	}
}
