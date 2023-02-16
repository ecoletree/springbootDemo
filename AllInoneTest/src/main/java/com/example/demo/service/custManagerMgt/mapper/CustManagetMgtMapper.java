/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustManagetMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custManagerMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustManagetMgtMapper {

	public List<Map<String,Object>> selectUserList(Map<String, Object> params);
	
	public int updateUser(Map<String, Object> params);
	public int updateUserPW(Map<String, Object> params);
	public int insertUser(Map<String, Object> params);
	public int deleteUser(Map<String, Object> params);
	public int selectUserCheck(Map<String, Object> params);
}
