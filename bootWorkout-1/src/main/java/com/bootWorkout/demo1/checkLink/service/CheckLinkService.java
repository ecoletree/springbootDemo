/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 9. 19.
 * File Name : CheckLinkService.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.checkLink.service;

import java.io.IOException;
import java.util.Map;

public interface CheckLinkService {

	/**
	 * @param param
	 * @throws IOException 
	 */
	public void checkBrokenLink(Map<String, Object> param) throws IOException;

}
