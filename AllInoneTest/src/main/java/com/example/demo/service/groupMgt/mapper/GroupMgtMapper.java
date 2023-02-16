/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2022. 7. 13
 * File Name : GroupMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.groupMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMgtMapper {

	public List<Map<String,Object>> selectGroupMgtList(Map<String, Object> params);
	
	public int updateGroupMgt(Map<String, Object> params);
	public int insertGroupMgt(Map<String, Object> params);
	public int deleteGroupMgt(Map<String, Object> params);
	public int selectGroupMgtCheck(Map<String, Object> params);

	public List<Map<String, Object>> selectGroupList(Map<String, Object> param);

	public int deleteTenantId(Map<String, Object> params);

	public int selectGroupDialerNameCheck(Map<String, Object> params);
}

