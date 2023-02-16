/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : HuntGroupMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.huntGroupMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.huntGroupMgt.mapper.HuntGroupMgtMapper;
import com.example.demo.service.huntGroupMgt.service.HuntGroupMgtService;

@Service
@Transactional
public class HuntGroupMgtServiceImpl extends ETBaseService implements HuntGroupMgtService {

	@Autowired
	HuntGroupMgtMapper mapper;
	/**
	 * 헌트그룹관리리스트 가져오기
	 */
	@Override
	public Map<String, Object> getHuntGroupList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectStationGRPList(params);
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
	public int setHuntGroup(Map<String, Object> params) {
		int i=0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectStationGrpIdCheck(params);
			if (0 < count) {
				// -1 일 경우 중복
				i = -1;
			} else {
				i = mapper.insertStationGRP(params);
			}
		} else {
			i = mapper.updateStationGRP(params);
		}
		return i;
	}
	@Override
	public int deleteHuntGroup(Map<String, Object> params) {
		return mapper.deleteStationGRP(params);
	}

}
