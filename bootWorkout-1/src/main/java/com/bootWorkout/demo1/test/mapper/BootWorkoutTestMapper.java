package com.bootWorkout.demo1.test.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BootWorkoutTestMapper {

	List<Map<String, Object>> getMenuList(Map<String, Object> param);

}
