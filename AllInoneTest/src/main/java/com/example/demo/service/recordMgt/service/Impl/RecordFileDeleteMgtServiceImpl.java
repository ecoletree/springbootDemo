package com.example.demo.service.recordMgt.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import com.example.demo.service.recordMgt.mapper.RecordFileDeleteMgtMapper;
import com.example.demo.service.recordMgt.service.RecordFileDeleteMgtService;
@Service
public class RecordFileDeleteMgtServiceImpl extends ETBaseService implements RecordFileDeleteMgtService {

	@Autowired
	RecordFileDeleteMgtMapper mapper;

	@Override
	public List<Map<String, Object>> getRecordRemoveList(Map<String, Object> params) {
		 return mapper.selectRecordRemoveList(params);
	}

	@Override
	public int setRecordRemove(Map<String, Object> params) {
		params.put("session_user_id", ETSessionHelper.getUserId());
		return mapper.upsertRecordRemove(params);
	}
}
