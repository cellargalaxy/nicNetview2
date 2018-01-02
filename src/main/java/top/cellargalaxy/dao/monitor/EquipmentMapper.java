package top.cellargalaxy.dao.monitor;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.bean.monitor.Equipment;
import top.cellargalaxy.bean.monitor.Place;

import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Mapper
public interface EquipmentMapper {
	@Insert("insert into equipment(id, model, name, buyDate, area, build, floor, number, ip, checkTimes, isWarn, remark, installDate) " +
			"values(#{id}, #{model}, #{name}, #{buyDate}, #{area}, #{build}, #{floor}, #{number}, #{ip}, #{checkTimes}, #{isWarn}, #{remark}, #{installDate})")
	int insertEquipment(Equipment equipment);
	
	@Delete("delete from equipment where id=#{id}")
	int deleteEquipment(@Param("id") String id);
	
	@Delete("delete from equipment where area=#{area}")
	int deleteEquipmentByArea(@Param("area")String area);
	
	@Delete("delete from equipment where build=#{build}")
	int deleteEquipmentByBuild(@Param("build")String build);
	
	@Delete("delete from equipment where floor=#{floor}")
	int deleteEquipmentByFloor(@Param("floor")String floor);
	
	@Delete("delete from equipment where area=#{area} and build=#{build} and floor=#{floor} and number=#{number}")
	int deleteEquipmentByPlace(Place place);
	
	@Select("select * from equipment where id=#{id}")
	Equipment selectEquipmentById(@Param("id") String id);
	
	@Select("select * from equipment where build=#{build}")
	LinkedList<Equipment> selectEquipmentByBuild(@Param("build") String build);
	
	@Select("select * from equipment limit #{off},#{len}")
	Equipment[] selectEquipments(@Param("off") int off, @Param("len") int len);
	
	@Select("select * from equipment")
	Equipment[] selectAllEquipment();
	
	@Update("update equipment set model=#{model}, name=#{name}, buyDate=#{buyDate}, area=#{area}, build=#{build}, floor=#{floor}, number=#{number}, " +
			"ip=#{ip}, checkTimes=#{checkTimes}, isWarn=#{isWarn}, remark=#{remark}, installDate=#{installDate} where id=#{id}")
	int updateEquipment(Equipment equipment);
}
