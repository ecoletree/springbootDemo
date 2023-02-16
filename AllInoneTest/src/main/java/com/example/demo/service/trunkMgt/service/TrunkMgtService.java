/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.trunkMgt.service;

import java.util.Map;

public interface TrunkMgtService {

	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getTrunkMgtList(Map<String, Object> param);

	/**
	 * @param param
	 * @return
	 */
	public int setTrunkMgt(Map<String, Object> param);

	/**
	 * @param param
	 * @return
	 */
	public int delTrunkMgt(Map<String, Object> param);

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getTransferIDList(Map<String, Object> params);

}
