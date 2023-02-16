/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.recordMgt.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.recordMgt.mapper.RecordListenOptionMapper;
import com.example.demo.service.recordMgt.service.RecordListenOptionService;

@Service
public class RecordListenOptionServiceImpl extends ETBaseService implements RecordListenOptionService {
	
	@Autowired
	RecordListenOptionMapper mapper;
	
	@Override
	public List<Map<String, Object>> getUserTypeList() {
		String[] userTypes = {CommonConst.USER_TYPE.SYSTEM,CommonConst.USER_TYPE.GROUP,CommonConst.USER_TYPE.CENTER,CommonConst.USER_TYPE.TEAM};
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> typeList = mapper.selectUserTypeList();
		for (String type : userTypes) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			for (Map<String, Object> map : typeList) {
				if (type.equals(map.get("user_type"))) {
					if (map.get("view_update_user") == null) {
						map.put("view_update_dttm","시스템");
					}
					dataMap = map;
					break;
				}
			}
			
			if (dataMap.isEmpty()) {
				dataMap.put("user_type", type);
				dataMap.put("view_update_dttm", "");
				dataMap.put("view_update_user", "시스템");
			}
			list.add(dataMap);
		}
		return list;
	}

	@Override
	public Map<String, Object> getListenOptionList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> columnList = mapper.selectColumnList(params);
			List<Map<String, Object>> optionList = mapper.selectSearchList(params);
			resultMap.put("columnList", columnList);
			resultMap.put("optionList", optionList);
		} catch (Exception e) {
			logError(e.getMessage(),e);
			throw e;
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int setColumnList(Map<String, Object> params) {
		Map<String, Object> userInfo = (Map<String, Object>)ETSessionHelper.getSessionVO().getUser_info();
		String createUser = userInfo.get("user_name") + "("+ETSessionHelper.getUserId()+")";
		params.put("create_user", createUser);
		int i = mapper.upsertColumnList(params);
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int setOptionList(Map<String, Object> params) {
		Map<String, Object> userInfo = (Map<String, Object>)ETSessionHelper.getSessionVO().getUser_info();
		String createUser = userInfo.get("user_name") + "("+ETSessionHelper.getUserId()+")";
		params.put("create_user", createUser);
		int i = mapper.upsertSearchList(params);
		return i;
	}
	
}
