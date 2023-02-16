/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkGWMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.trunkGW.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrunkGWMapper {

	public List<Map<String,Object>> selectTrunkGWList(Map<String, Object> params);
	
	public int updateTrunkGW(Map<String, Object> params);
	public int insertTrunkGW(Map<String, Object> params);
	public int deleteTrunkGW(Map<String, Object> params);
	
	public int selectTrunkGWCheck(Map<String, Object> params);

	public List<Map<String, Object>> selectTrunkGWGroupList(Map<String, Object> params);

	public int selectTrunkGWGroupCheck(Map<String, Object> params);

	public int insertTrunkGWGroup(Map<String, Object> params);
}
