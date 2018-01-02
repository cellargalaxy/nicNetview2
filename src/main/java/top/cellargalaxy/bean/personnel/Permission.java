package top.cellargalaxy.bean.personnel;

/**
 * Created by cellargalaxy on 17-12-6.
 */
public class Permission {
	private int permission;
	private String remark;
	
	public int getPermission() {
		return permission;
	}
	
	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return "Permission{" +
				"permission=" + permission +
				", remark='" + remark + '\'' +
				'}';
	}
}
