package top.cellargalaxy.dao.personnel;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.bean.personnel.Person;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Mapper
public interface PersonMapper {
	@Insert("insert into person(id, name, sex, college, grade, professionClass, phone, cornet, qq, birthday, introduction, team, password) " +
			"values(#{id}, #{name}, #{sex}, #{college}, #{grade}, #{professionClass}, #{phone}, #{cornet}, #{qq}, #{birthday}, #{introduction}, #{team}, #{password})")
	int insertPerson(Person person);
	
	@Delete("delete from person where id=#{id}")
	int deletePerson(@Param("id") String id);
	
	@Select("select * from person where id=#{id} limit 1")
	Person selectPersonById(@Param("id") String id);
	
	@Select("select * from person limit #{off},#{len}")
	Person[] selectPersons(@Param("off") int off, @Param("len") int len);
	
	@Select("select * from person")
	Person[] selectAllPerson();
	
	@Select("select count(*) from person")
	int selectPersonCount();
	
	@Update("update person set name=#{name}, sex=#{sex}, college=#{college}, grade=#{grade}, professionClass=#{professionClass}, phone=#{phone}, " +
			"cornet=#{cornet}, qq=#{qq}, birthday=#{birthday}, introduction=#{introduction}, team=#{team}, password=#{password} where id=#{id}")
	int updatePerson(Person person);
}
