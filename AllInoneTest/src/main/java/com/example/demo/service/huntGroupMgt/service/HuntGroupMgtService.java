/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : HuntGroupMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.huntGroupMgt.service;

import java.util.Map;

public interface HuntGroupMgtService {

	/** 헌트그룹관리리스트 가져오기
	 * @param param
	 * @return
	 */
	public Map<String, Object> getHuntGroupList(Map<String, Object> param);

	/**
	 * @param params
	 * @return
	 */
	public int setHuntGroup(Map<String, Object> params);

	/**
	 * @param param
	 * @return
	 */
	public int deleteHuntGroup(Map<String, Object> param);

}
