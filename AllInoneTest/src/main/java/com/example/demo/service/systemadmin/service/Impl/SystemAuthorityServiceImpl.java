/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.systemadmin.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.systemadmin.mapper.SystemAuthorityMapper;
import com.example.demo.service.systemadmin.service.SystemAuthorityService;

@Service
public class SystemAuthorityServiceImpl extends ETBaseService implements SystemAuthorityService {
	
	@Autowired
	SystemAuthorityMapper mapper;
	
	@Override
	public List<Map<String, Object>> getUserTypeList() {
		String[] userTypes = {CommonConst.USER_TYPE.SYSTEM,CommonConst.USER_TYPE.GROUP,CommonConst.USER_TYPE.CENTER,CommonConst.USER_TYPE.TEAM};
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> typeList = mapper.selectUserTypeList();
		for (String type : userTypes) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			for (Map<String, Object> map : typeList) {
				if (type.equals(map.get("user_type"))) {
					if (map.get("create_user") == null) {
						map.put("create_user","시스템");
					}
					dataMap = map;
					break;
				}
			}
			
			if (dataMap.isEmpty()) {
				dataMap.put("user_type", type);
				dataMap.put("view_create_dttm", "");
				dataMap.put("create_user", "시스템");
			}
			list.add(dataMap);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getAuthList(Map<String, Object> params) {
		List<Map<String, Object>> list = null;
		try {
			list = mapper.selectAuthList(params);
		} catch (Exception e) {
			logError(e.getMessage(),e);
			throw e;
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int setAuthList(Map<String, Object> params) throws Exception{
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		Map<String, Object> userInfo = (Map<String, Object>)ETSessionHelper.getSessionVO().getUser_info();
		String createUser = userInfo.get("user_name") + "("+ETSessionHelper.getUserId()+")";
		params.put("create_user", createUser);
		int i = mapper.insertAuthList(params);
		return i;
	}

	
}
