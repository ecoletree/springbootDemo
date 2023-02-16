/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Jang Yoon Seok
 * Create Date : 2022. 4. 11.
 * File Name : LoginMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.login.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

	public Map<String, Object> selectLoginInfo(Map<String, Object> param);
	public Map<String, Object> selectGroupLoginInfo(Map<String, Object> param);
}

