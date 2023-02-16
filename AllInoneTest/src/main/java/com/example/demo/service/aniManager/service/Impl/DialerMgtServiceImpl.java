package com.example.demo.service.aniManager.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import com.example.demo.service.aniManager.mapper.DiallerMgtMapper;
import com.example.demo.service.aniManager.service.DialerMgtService;
import com.example.demo.service.common.CommonConst;

@Service
public class DialerMgtServiceImpl extends ETBaseService implements DialerMgtService {

	@Autowired
	DiallerMgtMapper mapper;

	@Override
	public Map<String, Object> getDialerList(Map<String, Object> params) {
Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectDialerList(params);
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
	public int setDialer(Map<String, Object> params) {
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		params.put("user_id", ETSessionHelper.getUserId());
		int i=0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int count = mapper.selectDialerCheck(params);
			if (0 < count) {
				// -1 일 경우 이미등록된 다이얼러가 있음
				i = -1;
			} else {
				i = mapper.insertDialerList(params);
			}
		} else {
			i = mapper.updateDialerList(params);
		}
		return i;
	}

	@Override
	public int delDialer(Map<String, Object> params) {
		return mapper.deleteDialerList(params);
	}


}
