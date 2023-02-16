/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustManagerMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custManagerMgt.service;

import java.util.Map;

public interface CustManagerMgtService {

	public Map<String, Object> getCustManagerList(Map<String, Object> params);
	
	public int setCustManager(Map<String, Object> params) throws Exception;
	public int deleteCustManager(Map<String, Object> params);

	int setPWCustManagerChange(Map<String, Object> params) throws Exception;

}
