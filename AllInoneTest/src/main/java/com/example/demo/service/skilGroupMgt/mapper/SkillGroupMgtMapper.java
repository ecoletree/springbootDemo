/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : SkillGroupMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.skilGroupMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkillGroupMgtMapper {

	public List<Map<String,Object>> selectAgentSkillList(Map<String, Object> params);
	
	public int updateAgentSkill(Map<String, Object> params);
	public int insertAgentSkill(Map<String, Object> params);
	public int deleteAgentSkill(Map<String, Object> params);
	public int selectAgentSkillCheck(Map<String, Object> params);

	public List<Map<String, Object>> getSoundList(Map<String, Object> params);
}
