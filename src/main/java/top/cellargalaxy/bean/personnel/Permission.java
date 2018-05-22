package top.cellargalaxy.bean.personnel;

/**
 * 权限
 * Created by cellargalaxy on 17-12-6.
 */
public class Permission {
	/**
	 * 权限编号
	 */
	private int permission;
	/**
	 * 备注
	 */
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
