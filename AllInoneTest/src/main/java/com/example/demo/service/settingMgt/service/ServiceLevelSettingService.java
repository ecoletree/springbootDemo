/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : ServiceLevelSettingService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.service;

import java.util.List;
import java.util.Map;

public interface ServiceLevelSettingService {

	public List<Map<String,Object>> getServiceLevelList(Map<String,Object> params);
	
	public int setServiceLevel(Map<String,Object> params);
}
