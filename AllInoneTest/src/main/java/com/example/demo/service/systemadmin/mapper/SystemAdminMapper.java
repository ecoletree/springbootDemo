/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.systemadmin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemAdminMapper {

	public List<Map<String,Object>> selectAdminList(Map<String, Object> params);
	
	public int updateAdmin(Map<String, Object> params);
	public int updateAdminPW(Map<String, Object> params);
	public int insertAdmin(Map<String, Object> params);
	public int deleteAdmin(Map<String, Object> params);
	public int selectAdminCheck(Map<String, Object> params);
}
