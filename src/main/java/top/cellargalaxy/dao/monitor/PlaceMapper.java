package top.cellargalaxy.dao.monitor;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.bean.monitor.Place;

/**
 * Created by cellargalaxy on 17-12-7.
 */
@Mapper
public interface PlaceMapper {
	@Insert("insert into place(area, build, floor, number) values(#{area}, #{build}, #{floor}, #{number})")
	int insertPlace(Place place);
	
	@Delete("delete from place where area=#{area} and build=#{build} and floor=#{floor} and number=#{number}")
	int deletePlace(Place place);
	
	@Select("select * from place")
	Place[] selectAllPlace();
	
	@Select("select distinct build from place")
	String[] selectAllBuild();
	
	@Select("select * from place limit #{off},#{len}")
	Place[] selectPlaces(@Param("off") int off, @Param("len") int len);
	
	@Select("select * from place where area=#{area}")
	Place[] selectPlacesByArea(@Param("area") String area);
	
	@Select("select * from place where build=#{build}")
	Place[] selectPlacesByBuild(@Param("build") String build);
	
	@Select("select * from place where floor=#{floor}")
	Place[] selectPlaceByFloor(@Param("floor") String floor);
	
	@Select("select * from place where number=#{number}")
	Place[] selectPlaceByNumber(@Param("number") int number);
}
