/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustBlackListMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custBlackList.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustBlackListMapper {

	public List<Map<String,Object>> selectBlackList(Map<String, Object> params);
	
	public int updateBlackList(Map<String, Object> params);
	public int insertBlackList(Map<String, Object> params);
	public int deleteBlackList(Map<String, Object> params);
}
