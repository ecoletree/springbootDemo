/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : AgentMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.agentMgt.service;

import java.util.Map;

public interface AgentMgtService {

	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getAgentList(Map<String, Object> param);

	/**
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String setAgentMgt(Map<String, Object> params) throws Exception;

	/**
	 * @param params
	 * @return
	 */
	public int delAgentMgt(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getgroupList(Map<String, Object> params);

	/**
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public int setPWAgentChange(Map<String, Object> param) throws Exception;

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getSkillGroupList(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public Map<String, Object> getTeamList(Map<String, Object> params);


}
