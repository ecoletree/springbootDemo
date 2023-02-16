/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custMgt.service;

import java.util.Map;

public interface CustMgtService {

	public Map<String, Object> selectGroupMgtList(Map<String, Object> params);
	
	public int setGroupMgt(Map<String, Object> params);
	public int deleteGroupMgt(Map<String, Object> params);
}
