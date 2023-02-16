/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.trunkMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrunkMgtMapper {

	public List<Map<String,Object>> selectTrunkList(Map<String, Object> params);
	
	public int updateTrunk(Map<String, Object> params);
	public int insertTrunk(Map<String, Object> params);
	public int deleteTrunk(Map<String, Object> params);
	public int selectTrunkCheck(Map<String, Object> params);

	public List<Map<String,Object>> selectStationList(Map<String, Object> params);
	public List<Map<String,Object>> selectStationGrpList(Map<String, Object> params);
	public List<Map<String,Object>> selectSkillGrpList(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectRoutesList(Map<String, Object> params);
}
