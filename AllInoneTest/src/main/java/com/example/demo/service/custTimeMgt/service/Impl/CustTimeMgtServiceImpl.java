/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustTimeMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custTimeMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.custTimeMgt.mapper.CustTimeMgtMapper;
import com.example.demo.service.custTimeMgt.service.CustTimeMgtService;
@Service
@Transactional
public class CustTimeMgtServiceImpl extends ETBaseService implements CustTimeMgtService {

	@Autowired
	CustTimeMgtMapper mapper;
	
	@Override
	public Map<String, Object> getWorkSchduleList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectWorkSchduleList(params);
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
	public int setWorkSchdule(Map<String, Object> params) {
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		int i=0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			i = mapper.insertWorkSchdule(params);
		} else {
			i = mapper.updateWorkSchdule(params);
		}
		return i;
	}

	@Override
	public int deleteWorkSchdule(Map<String, Object> params) {
		return mapper.deleteWorkSchdule(params);
	}

}
