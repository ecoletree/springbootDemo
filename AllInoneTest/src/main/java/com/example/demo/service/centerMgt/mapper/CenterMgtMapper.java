/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.centerMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CenterMgtMapper {

	public List<Map<String,Object>> selectCenterList(Map<String, Object> params);
	
	public int updateTenant(Map<String, Object> params);
	public int insertTenant(Map<String, Object> params);
	public int deleteTenant(Map<String, Object> params);
	public int selectTenantCheck(Map<String, Object> params);
}
