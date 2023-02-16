/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : SkillGroupMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.skilGroupMgt.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.skilGroupMgt.mapper.SkillGroupMgtMapper;
import com.example.demo.service.skilGroupMgt.service.SkillGroupMgtService;
@Service
@Transactional
public class SkillGroupMgtServiceImpl extends ETBaseService implements SkillGroupMgtService {
	
	@Autowired
	SkillGroupMgtMapper mapper;

	@Override
	public Map<String, Object> getSkillGroupList(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectAgentSkillList(param);
			int totalCount = list.size();
			
			resultMap.put("recordsTotal", totalCount);
			resultMap.put("recordsFiltered", totalCount);
			resultMap.put("data", list);
		} catch (Exception e) {
			logError(e.getMessage(),e);
			throw e;
		}
		
		return resultMap;
	}

	@Override
	public int setSkillGroup(Map<String, Object> param) {
		int i=0;
		if (param.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectAgentSkillCheck(param);
			if (0 < count) {
				// -1 일 경우 중복
				i = -1;
			} else {
				i = mapper.insertAgentSkill(param);
			}
		} else {
			i = mapper.updateAgentSkill(param);
		}
		return i;
	}

	@Override
	public int delSkillGroup(Map<String, Object> param) {
		return mapper.deleteAgentSkill(param);
		}

	@Override
	public Map<String, Object> getSoundList(Map<String, Object> params) {
		List<Map<String, Object>> list =mapper.getSoundList(params); 
		List<Map<String, Object>> child = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> soundMap = new HashMap<String, Object>();
		
		String preGroupId = null;
		if(!list.isEmpty()) {
			for (Map<String, Object> map : list) {
				if(map.get("group_id") != null) {
					String groupId = map.get("group_id").toString();
					if (preGroupId == null) {
						child = new ArrayList<Map<String,Object>>();
					} else if (!preGroupId.equals(groupId) ) {
						soundMap = setSoundMap(child);
						returnMap.put(preGroupId, soundMap);
						child = new ArrayList<Map<String,Object>>();
					}
					child.add(map);
					preGroupId = groupId;
				}
			}
			
		}
		
		if (child != null && !child.isEmpty()) {
			soundMap = setSoundMap(child);
			returnMap.put(preGroupId, soundMap);
		}
		
		return returnMap;
	}

	/**
	 * @param list
	 * @return
	 */
	private Map<String, Object> setSoundMap(List<Map<String, Object>> list) {
		List<Map<String, Object>> child = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String preGroupId = null;
		for (Map<String, Object> map : list) {
			String groupId = map.get("tenant_id").toString();
			if (preGroupId == null) {
				child = new ArrayList<Map<String,Object>>();
			} else if (!preGroupId.equals(groupId) ) {
				returnMap.put(preGroupId, child);
				child = new ArrayList<Map<String,Object>>();
			}
			child.add(map);
			preGroupId = groupId;
		}
		
		if (child != null && !child.isEmpty()) {
			returnMap.put(preGroupId, child);
		}
		
		return returnMap;
	}
	

}
