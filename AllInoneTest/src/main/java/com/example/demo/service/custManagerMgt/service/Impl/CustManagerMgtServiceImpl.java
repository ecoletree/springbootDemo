/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustManagerMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.custManagerMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.CryptoUtil;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.custManagerMgt.mapper.CustManagetMgtMapper;
import com.example.demo.service.custManagerMgt.service.CustManagerMgtService;
@Service
@Transactional
public class CustManagerMgtServiceImpl extends ETBaseService implements CustManagerMgtService {

	@Autowired
	CustManagetMgtMapper mapper;
	
	@Override
	public Map<String, Object> getCustManagerList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectUserList(params);
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
	public int setCustManager(Map<String, Object> params) throws Exception{
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		params.put("session_user_id", ETSessionHelper.getUserId());
		int i=0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectUserCheck(params);
			if (0 < count) {
				// -1 일 경우 이미등록된 고객번호가 있음
				i = -1;
			} else {
				params.put("user_pw", CryptoUtil.encodePasswordSha256((String)params.get("user_pw")));
				i = mapper.insertUser(params);
			}
		} else {
			i = mapper.updateUser(params);
		}
		return i;
	}
	
	@Override
	public int setPWCustManagerChange(Map<String, Object> params) throws Exception{
		int i=0;
		params.put("session_user_id", ETSessionHelper.getUserId());
		params.put("user_pw", CryptoUtil.encodePasswordSha256((String)params.get("user_pw")));
		i = mapper.updateUserPW(params);
		return i;
	}

	@Override
	public int deleteCustManager(Map<String, Object> params) {
		params.put("session_user_id", ETSessionHelper.getUserId());
		return mapper.deleteUser(params);
	}

}
