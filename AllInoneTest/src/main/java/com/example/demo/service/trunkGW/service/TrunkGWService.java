/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkGWService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.trunkGW.service;

import java.util.List;
import java.util.Map;

public interface TrunkGWService {

	public Map<String, Object> getTrunkGWList(Map<String, Object> params);
	public int setTrunkGW(Map<String, Object> params);
	public int deleteTrunkGW(Map<String, Object> params);
	public List<Map<String, Object>> getGWGroupList(Map<String, Object> params);
}
