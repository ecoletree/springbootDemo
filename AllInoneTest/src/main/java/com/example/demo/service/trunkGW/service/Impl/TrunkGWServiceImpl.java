/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkGWServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.trunkGW.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.trunkGW.mapper.TrunkGWMapper;
import com.example.demo.service.trunkGW.service.TrunkGWService;
@Service
@Transactional
public class TrunkGWServiceImpl extends ETBaseService implements TrunkGWService {

	@Autowired
	TrunkGWMapper mapper;
	
	@Override
	public Map<String, Object> getTrunkGWList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectTrunkGWList(params);
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
	public int setTrunkGW(Map<String, Object> params) {
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		
		int i=0;
		int gw = 0;
		params.put("create_user", ETSessionHelper.getUserId());
		gw = setTrunkGWGroup(params,"check");
		if(gw <= 0) {
			if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
				int count = mapper.selectTrunkGWCheck(params);
				if (0 < count) {
					// -1 일 경우 이미등록된 고객번호가 있음
					i = -1;
				} else {
					i = mapper.insertTrunkGW(params);
					i += setTrunkGWGroup(params,"insert");
				}
			} else {
				i = mapper.updateTrunkGW(params);
				i += setTrunkGWGroup(params,"insert");
			}
		}else {
			i = -2;
		}
		return i;
	}
	/** gw group 중복체크 및 저장
	 * @param params
	 * @param gw
	 * @return
	 */
	private int setTrunkGWGroup(Map<String, Object> params, String gw) {
		int i = 0;
		if(gw == "check") {
			if(params.get("gwIsNew").equals("Y")) {
				if(!params.get("group_gw_id").equals("")) {
					i = mapper.selectTrunkGWGroupCheck(params);
				}
			}
		}else {
			i = mapper.insertTrunkGWGroup(params);
		}
		return i;
	}


	@Override
	public int deleteTrunkGW(Map<String, Object> params) {
		return mapper.deleteTrunkGW(params);
	}

	@Override
	public List<Map<String, Object>> getGWGroupList(Map<String, Object> params) {
		List<Map<String,Object>> list = mapper.selectTrunkGWGroupList(params);
		return list;
	}

}
