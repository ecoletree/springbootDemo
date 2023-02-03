package com.bootWorkout.demo1.login.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

	public Map<String, Object> selectLoginUser(Map<String, Object> param);

}
