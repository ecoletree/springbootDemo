/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : AgentMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.agentMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AgentMgtMapper {

	public List<Map<String,Object>> selectAgentList(Map<String, Object> params);
	
	public int updateAgent(Map<String, Object> params);
	public int insertAgent(Map<String, Object> params);
	public int deleteAgent(Map<String, Object> params);
	public int selectAgentCheck(Map<String, Object> params);

	public List<Map<String, Object>> selectGroupList(Map<String, Object> params);

	public int updateAgentPW(Map<String, Object> params);

	public int selectSkillGrpCheck(Map<String, Object> param);

	public int selectAgentSkillCheck(Map<String, Object> param);

	public List<Map<String, Object>> selectTeamList(Map<String, Object> params);
}
