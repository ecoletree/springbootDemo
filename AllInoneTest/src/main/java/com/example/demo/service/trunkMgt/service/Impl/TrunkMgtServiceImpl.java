/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.trunkMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.trunkMgt.mapper.TrunkMgtMapper;
import com.example.demo.service.trunkMgt.service.TrunkMgtService;
@Service
@Transactional
public class TrunkMgtServiceImpl extends ETBaseService implements TrunkMgtService {

	@Autowired
	TrunkMgtMapper mapper;
	
	@Override
	public Map<String, Object> getTrunkMgtList(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectTrunkList(param);
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
	public int setTrunkMgt(Map<String, Object> param) {
		int i=0;
		if (param.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectTrunkCheck(param);
			if (0 < count) {
				// -1 일 경우 중복
				i = -1;
			} else {
				i = mapper.insertTrunk(param);
			}
		} else {
			i = mapper.updateTrunk(param);
		}
		return i;
	}

	@Override
	public int delTrunkMgt(Map<String, Object> param) {
		return mapper.deleteTrunk(param);
	}

	@Override
	public Map<String,Object> getTransferIDList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = mapper.selectRoutesList(params);
		
		for(Map<String, Object> map : list) {
			String tenantId = map.get("tenant_id").toString();
			resultMap.put(tenantId, map);
		}
		return resultMap;
		
	}
	
	 

}
