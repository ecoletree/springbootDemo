/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : ServiceLevelSettingMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServiceLevelSettingMapper {

	public List<Map<String, Object>> selectServiceLevelList(Map<String, Object> params);
	
	public int upsertServiceLevel(Map<String, Object> params);
}
