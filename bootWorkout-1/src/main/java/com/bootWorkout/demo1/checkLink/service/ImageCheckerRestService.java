/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 1. 17.
 * File Name : ImageCheckerRestService.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.checkLink.service;

import java.util.Map;

public interface ImageCheckerRestService {

	/**
	 * @param options
	 * @return
	 */
	public Map<String, Object> getMetaDataList(Map<String, Object> params);

}
