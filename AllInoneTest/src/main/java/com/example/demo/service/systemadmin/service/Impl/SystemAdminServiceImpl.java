/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.systemadmin.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.CryptoUtil;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.systemadmin.mapper.SystemAdminMapper;
import com.example.demo.service.systemadmin.service.SystemAdminService;

@Service
public class SystemAdminServiceImpl extends ETBaseService implements SystemAdminService {
	
	@Autowired
	SystemAdminMapper mapper;

	@Override
	public Map<String, Object> getSystemAdminList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectAdminList(params);
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
	public int setSystemAdmin(Map<String, Object> params) throws Exception{
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		params.put("session_user_id", ETSessionHelper.getUserId());
		int i=0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectAdminCheck(params);
			if (0 < count) {
				// -1 일 경우 이미등록된 고객번호가 있음
				i = -1;
			} else {
				params.put("password", CryptoUtil.encodePasswordSha256((String)params.get("password")));
				i = mapper.insertAdmin(params);
			}
		} else {
			i = mapper.updateAdmin(params);
		}
		return i;
	}
	
	@Override
	public int setPWSystemAdminChange(Map<String, Object> params) throws Exception{
		int i=0;
		params.put("session_user_id", ETSessionHelper.getUserId());
		params.put("password", CryptoUtil.encodePasswordSha256((String)params.get("password")));
		i = mapper.updateAdminPW(params);
		return i;
	}

	@Override
	public int deleteSystemAdmin(Map<String, Object> params) {
		params.put("session_user_id", ETSessionHelper.getUserId());
		return mapper.deleteAdmin(params);
	}
}
