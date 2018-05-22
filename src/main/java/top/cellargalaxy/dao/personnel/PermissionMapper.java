package top.cellargalaxy.dao.personnel;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.bean.personnel.Permission;

/**
 * Created by cellargalaxy on 17-12-6.
 */
@Mapper
public interface PermissionMapper {
	@Insert("insert into permission(permission, remark) values(#{permission}, #{remark})")
	int insertPermission(Permission permission);
	
	@Delete("delete from permission where permission=#{permission}")
	int deletePermission(@Param("permission") int permission);
	
	@Select("select * from permission where permission=#{permission} limit 1")
	Permission selectPermission(@Param("permission") int permission);
	
	@Select("select * from permission")
	Permission[] selectAllPermission();
	
	@Update("update permission set remark=#{remark} where permission=#{permission}")
	int updatePermission(Permission permission);
}
