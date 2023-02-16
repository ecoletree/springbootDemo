/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : CriticalSettingService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.service;

import java.util.List;
import java.util.Map;

public interface CriticalSettingService {

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getCriticalStatus(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public int setCriticalSettingMgt(Map<String, Object> params);


}
