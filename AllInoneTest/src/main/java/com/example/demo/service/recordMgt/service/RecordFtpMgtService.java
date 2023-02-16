/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 1. 25.
 * File Name : RecordFtpMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.recordMgt.service;

import java.util.Map;

public interface RecordFtpMgtService {

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getCodeList(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getFtpList(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public int setFtpInfo(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public int deleteFtpInfo(Map<String, Object> params);

}
