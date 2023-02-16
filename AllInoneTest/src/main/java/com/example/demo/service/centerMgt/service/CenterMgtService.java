/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.centerMgt.service;

import java.util.Map;

public interface CenterMgtService {

	

	public Map<String, Object> getCenterList(Map<String, Object> param);
	public int setCenter(Map<String, Object> params);
	public int deleteCenter(Map<String, Object> params);
}
