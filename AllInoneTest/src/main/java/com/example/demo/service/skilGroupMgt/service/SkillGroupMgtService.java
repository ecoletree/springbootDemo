/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : SkillGroupMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.skilGroupMgt.service;

import java.util.Map;

public interface SkillGroupMgtService {

	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getSkillGroupList(Map<String, Object> param);

	/**
	 * @param param
	 * @return
	 */
	public int setSkillGroup(Map<String, Object> param);

	/**
	 * @param param
	 * @return
	 */
	public int delSkillGroup(Map<String, Object> param);

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getSoundList(Map<String, Object> params);

}
