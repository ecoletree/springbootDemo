package com.example.demo.service.login.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

	List<Map<String, Object>> selectUserRoleList(Map<String, Object> tmrInfo);
	
	Map<String, Object> selectUserFirstRole(Map<String, Object> tmrInfo);

}
