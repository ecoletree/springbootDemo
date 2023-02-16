/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 1. 25.
 * File Name : RecordFtpMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.recordMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.StringUtil;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.recordMgt.mapper.RecordFtpMgtMapper;
import com.example.demo.service.recordMgt.service.RecordFtpMgtService;

@Service
public class RecordFtpMgtServiceImpl extends ETBaseService implements RecordFtpMgtService {

	@Autowired
	GroupMgtService groupMgtService;
	
	@Autowired
	RecordFtpMgtMapper mapper;
	
	@Override
	public Map<String, Object> getCodeList(Map<String, Object> params) {
		Map<String, Object> resultMap =	MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
		);
		return resultMap;
	}

	@Override
	public Map<String, Object> getFtpList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try {
			List<Map<String, Object>> list = mapper.selectFtpList(params);
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
	public int setFtpInfo(Map<String, Object> params) {
		int i=0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectFtpDupCheck(params);
			if(0 >= count) {
					i = mapper.insertFtpInfo(params);
			}else {
				i = -1;
			}
		}else {
			i = mapper.updateFtpInfo(params);
		}
		return i;
	}

	@Override
	public int deleteFtpInfo(Map<String, Object> params) {
		return mapper.deleteFtpInfo(params);
	}

}
