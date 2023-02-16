/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : ExtensionMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.extensionMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExtensionMgtMapper {

	public List<Map<String,Object>> selectStationList(Map<String, Object> params);
	
	public int updateStation(Map<String, Object> params);
	public int insertStation(Map<String, Object> params);
	public int deleteStation(Map<String, Object> params);
	public int selectStationCheck(Map<String, Object> params);

	public List<Map<String, Object>> selectHuntGroupList(Map<String, Object> params);

	public int selectPhoneDupCheck(Map<String, Object> param);

	public int selectTenantCheck(Map<String, Object> param);

	public int selectHuntCheck(Map<String, Object> param);
}
