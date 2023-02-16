/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustTimeMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custTimeMgt.service;

import java.util.Map;

public interface CustTimeMgtService {

	public Map<String, Object> getWorkSchduleList(Map<String, Object> params);
	public int setWorkSchdule(Map<String, Object> params);
	public int deleteWorkSchdule(Map<String, Object> params);
}
