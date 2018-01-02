package top.cellargalaxy.dao.monitor;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.bean.monitor.Malfunction;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Mapper
public interface MalfunctionMapper {
	@Insert("insert into malfunction(area, build, floor, number, equipmentId, malfunctionDatetime, status) " +
			"values(#{area}, #{build}, #{floor}, #{number}, #{equipmentId}, #{malfunctionDatetime}, #{status})")
	int insertMalfunction(Malfunction malfunction);
	
	@Delete("delete from malfunction where area=#{area} and build=#{build} and floor=#{floor} and number=#{number} and " +
			"equipmentId=#{equipmentId} and malfunctionDatetime=#{malfunctionDatetime}")
	int deleteMalfunction(Malfunction malfunction);
	
	@Delete("delete from malfunction where equipmentId=#{equipmentId}")
	int deleteMalfunctionByEquipmentId(String equipmentId);
	
	@Select("select * from malfunction order by malfunctionDatetime desc limit #{off},#{len}")
	Malfunction[] selectMalfunctions(@Param("off") int off, @Param("len") int len);
	
	@Select("select count(*) from malfunction")
	int selectMalfunctionCount();
}
