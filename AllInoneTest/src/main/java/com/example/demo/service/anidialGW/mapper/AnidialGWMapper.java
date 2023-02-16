/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : AnidailGWMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.anidialGW.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnidialGWMapper {

	public List<Map<String,Object>> selectAnidialGWList(Map<String, Object> params);
	
	public int updateAnidialGW(Map<String, Object> params);
	public int insertAnidialGW(Map<String, Object> params);
	public int deleteAnidialGW(Map<String, Object> params);
	
	public int selectAnidialGWCheck(Map<String, Object> params);

	public List<Map<String, Object>> selectGWGroupList(Map<String, Object> params);

	public int selectAnidialGWGroupCheck(Map<String, Object> params);

	public int insertAnidialGWGroup(Map<String, Object> params);
}
