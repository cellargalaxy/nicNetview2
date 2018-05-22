package top.cellargalaxy.dao.personnel;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.bean.personnel.Authorized;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Mapper
public interface AuthorizedMapper {
	@Insert("insert into authorized(personId, permission) values(#{personId}, #{permission})")
	int insertAuthorized(Authorized authorized);
	
	@Delete("delete from authorized where personId=#{personId} and permission=#{permission}")
	int deleteAuthorized(Authorized authorized);
	
	@Delete("delete from authorized where personId=#{personId}")
	int deleteAuthorizedByPersonId(@Param("personId") String personId);
	
	@Delete("delete from authorized where permission=#{permission}")
	int deleteAuthorizedByPermission(@Param("permission") int permission);
	
	@Select("select * from authorized where personId=#{personId} and permission=#{permission} limit 1")
	Authorized selectAuthorized(Authorized authorized);
	
	@Select("select * from authorized where personId=#{personId}")
	Authorized[] selectAuthorizedByPersonId(@Param("personId") String personId);
	
	@Select("select * from authorized where permission=#{permission}")
	Authorized[] selectAuthorizedByPermission(@Param("permission") int permission);
	
	@Select("select * from authorized")
	Authorized[] selectAllAuthorized();
}
