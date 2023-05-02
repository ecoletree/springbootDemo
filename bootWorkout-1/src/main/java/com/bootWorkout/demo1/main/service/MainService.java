/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 27.
 * File Name : MainService.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.main.service;

import java.util.List;
import java.util.Map;

public interface MainService {

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getGroupList(Map<String, Object> params);


}
