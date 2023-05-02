/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 27.
 * File Name : MainMapper.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.main.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MainMapper {

	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectGroupList(Map<String, Object> param);


}
