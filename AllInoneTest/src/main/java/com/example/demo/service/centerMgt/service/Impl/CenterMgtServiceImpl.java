/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.centerMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.StringUtil;
import com.example.demo.service.centerMgt.mapper.CenterMgtMapper;
import com.example.demo.service.centerMgt.service.CenterMgtService;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.groupMgt.mapper.GroupMgtMapper;
@Service
@Transactional
public class CenterMgtServiceImpl extends ETBaseService implements CenterMgtService {

	@Autowired
	CenterMgtMapper mapper;
	
	@Autowired
	GroupMgtMapper groupMapper;
	
	@Override
	public Map<String, Object> getCenterList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectCenterList(params);
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
	public int setCenter(Map<String, Object> params) {
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		params.put("user_id", ETSessionHelper.getUserId());
		int i=0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectTenantCheck(params);
			if (0 < count) {
				// -1 일 경우 이미등록된 고객번호가 있음
				i = -1;
			} else {
				params.put("group_cd", StringUtil.getUUID("GROUP"));
				i = mapper.insertTenant(params);
				i = groupMapper.insertGroupMgt(params);
			}
		} else {
			int count = mapper.selectTenantCheck(params);
			if (0 < count) {
				i = mapper.updateTenant(params);
			}else {
				i = mapper.insertTenant(params);
			}
			i = groupMapper.updateGroupMgt(params);
		}
		return i;
	}


	@Override
	public int deleteCenter(Map<String, Object> params) {
		int del = mapper.deleteTenant(params);
		del += groupMapper.deleteGroupMgt(params);
		return del;
	}
	

}
