/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustBlackListService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custBlackList.service;

import java.util.Map;

public interface CustBlackListService {

	public Map<String,Object> getBlackList(Map<String, Object> params);
	
	public int setBlackList(Map<String, Object> params);
	public int deleteBlackList(Map<String, Object> params);
}
