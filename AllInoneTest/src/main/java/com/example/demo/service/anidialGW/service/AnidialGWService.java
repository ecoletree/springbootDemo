/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : AnidialGWService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.anidialGW.service;

import java.util.List;
import java.util.Map;

public interface AnidialGWService {

	Map<String, Object> getAnidialGWList(Map<String, Object> params);

	int setAnidialGW(Map<String, Object> params);

	int delAnidialGW(Map<String, Object> params);

	List<Map<String, Object>> getGWGroupList(Map<String, Object> param);

}
