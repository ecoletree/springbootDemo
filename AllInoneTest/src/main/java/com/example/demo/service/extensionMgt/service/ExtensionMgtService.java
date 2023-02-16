/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : ExtensionMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.extensionMgt.service;

import java.util.Map;

public interface ExtensionMgtService {

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getHuntGoupList(Map<String, Object> params);

	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getExtensionMgtList(Map<String, Object> param);

	/**
	 * @param param
	 * @return
	 */
	public String setExtensionMgt(Map<String, Object> param);

	/**
	 * @param param
	 * @return
	 */
	public int delExtensionMgt(Map<String, Object> param);

}
