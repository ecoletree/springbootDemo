/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : HuntGroupMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.huntGroupMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HuntGroupMgtMapper {

	public List<Map<String,Object>> selectStationGRPList(Map<String, Object> params);
	
	public int updateStationGRP(Map<String, Object> params);
	public int insertStationGRP(Map<String, Object> params);
	public int deleteStationGRP(Map<String, Object> params);
	
	public int selectStationGrpIdCheck(Map<String, Object> params);


}
