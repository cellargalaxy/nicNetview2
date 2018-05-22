package top.cellargalaxy.bean.personnel;

/**
 * 授权
 * Created by cellargalaxy on 17-12-7.
 */
public class Authorized {
	/**
	 * 被授权人的id
	 */
	private String personId;
	/**
	 * 被授予的权限
	 */
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
