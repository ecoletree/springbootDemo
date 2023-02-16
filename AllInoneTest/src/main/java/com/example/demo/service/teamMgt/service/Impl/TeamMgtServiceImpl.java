/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.teamMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.StringUtil;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.groupMgt.mapper.GroupMgtMapper;
import com.example.demo.service.teamMgt.mapper.TeamMgtMapper;
import com.example.demo.service.teamMgt.service.TeamMgtService;
@Service
@Transactional
public class TeamMgtServiceImpl extends ETBaseService implements TeamMgtService {

	@Autowired
	TeamMgtMapper mapper;
	
	@Autowired
	GroupMgtMapper groupMapper;

	@Override
	public Map<String, Object> getTeamList(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = groupMapper.selectGroupMgtList(param);
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
	public int deleteGroupMgt(Map<String, Object> params) {
		params.put("user_id", ETSessionHelper.getUserId());
		return groupMapper.deleteGroupMgt(params);
	}

	@Override
	public int setGroupMgt(Map<String, Object> params) {
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
				params.put("user_id", ETSessionHelper.getUserId());
				int i=0;
				if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
					int count = groupMapper.selectGroupMgtCheck(params);
					if (0 < count) {
						// -1 일 경우 이미등록된 고객번호가 있음
						i = -1;
					} else {
						params.put("group_cd", StringUtil.getUUID("GROUP"));
						i = groupMapper.insertGroupMgt(params);
					}
				} else {
					i = groupMapper.updateGroupMgt(params);
				}
				return i;
	}
	

}
