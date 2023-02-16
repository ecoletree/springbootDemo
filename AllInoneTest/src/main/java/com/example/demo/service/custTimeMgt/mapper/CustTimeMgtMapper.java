/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustTimeMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custTimeMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustTimeMgtMapper {

	public List<Map<String,Object>> selectWorkSchduleList(Map<String, Object> params);
	
	public int updateWorkSchdule(Map<String, Object> params);
	public int insertWorkSchdule(Map<String, Object> params);
	public int deleteWorkSchdule(Map<String, Object> params);
}
