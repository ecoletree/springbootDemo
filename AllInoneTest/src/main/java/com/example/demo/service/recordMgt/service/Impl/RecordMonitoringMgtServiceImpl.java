package com.example.demo.service.recordMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.util.MapBuilder;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.recordMgt.mapper.RecordMonitoringMgtMapper;
import com.example.demo.service.recordMgt.service.RecordMonitoringMgtService;
@Service
public class RecordMonitoringMgtServiceImpl extends ETBaseService implements RecordMonitoringMgtService{

	@Autowired
	RecordMonitoringMgtMapper mapper;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	@Override
	public Map<String, Object> getCodeList(Map<String, Object> params) {
		Map<String, Object> resultMap =	MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
		);
		return resultMap;
	}

	@Override
	public Map<String, Object> getRecordMgtList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.getRecordMonirotingList(params);
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
}
