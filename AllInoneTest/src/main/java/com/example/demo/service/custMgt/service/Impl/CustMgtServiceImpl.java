/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custMgt.service.Impl;

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
import com.example.demo.service.custMgt.service.CustMgtService;
import com.example.demo.service.groupMgt.mapper.GroupMgtMapper;
@Service
@Transactional
public class CustMgtServiceImpl extends ETBaseService implements CustMgtService {

	@Autowired
	GroupMgtMapper groupMapper;
	
	@Override
	public Map<String, Object> selectGroupMgtList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = groupMapper.selectGroupMgtList(params);
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
	public int setGroupMgt(Map<String, Object> params) {
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		params.put("user_id", ETSessionHelper.getUserId());
		int i=0;
		int chk =0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = groupMapper.selectGroupMgtCheck(params);
			if (0 < count) {
				// -1 일 경우 이미등록된 고객번호가 있음
				i = -1;
			} else {
				if(!params.get("group_dialer_name").equals("")){// 다이얼러명 중복체크
					chk = groupMapper.selectGroupDialerNameCheck(params);
				}
				if(0 < chk) {
					i = -2;
				}else {
					params.put("group_cd", StringUtil.getUUID("GROUP"));
					i = groupMapper.insertGroupMgt(params);
				}
			}
		} else {
			if(!params.get("group_dialer_name").equals("")){// 다이얼러명 중복체크
				chk = groupMapper.selectGroupDialerNameCheck(params);
			}
			if(0 < chk) {
				i = -2;
			}else {
				i = groupMapper.updateGroupMgt(params);
			}
		}
		return i;
	}


	@Override
	public int deleteGroupMgt(Map<String, Object> params) {
		params.put("user_id", ETSessionHelper.getUserId());
		int i = 0;
		i += groupMapper.deleteGroupMgt(params);
		if(params.get("tenantList") != null) {
			i += groupMapper.deleteTenantId(params); 
		}
		return i;
	}

}
