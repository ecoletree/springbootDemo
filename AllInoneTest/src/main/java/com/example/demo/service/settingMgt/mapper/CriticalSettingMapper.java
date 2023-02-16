/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : CriticalSettingMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CriticalSettingMapper {

	/**
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectMonitoringStatusList(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int insertCriticalSetting(Map<String, Object> params);


}
