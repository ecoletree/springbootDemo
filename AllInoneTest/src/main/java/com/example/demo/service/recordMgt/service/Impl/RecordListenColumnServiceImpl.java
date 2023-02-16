package com.example.demo.service.recordMgt.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.util.MapBuilder;
import com.example.demo.service.code.mapper.CodeMapper;
import com.example.demo.service.code.util.CodeUtil;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.recordMgt.mapper.RecordListenColumnMapper;
import com.example.demo.service.recordMgt.service.RecordListenColumnService;

@Service
public class RecordListenColumnServiceImpl extends ETBaseService implements RecordListenColumnService {

	@Autowired
	RecordListenColumnMapper mapper;

	@Autowired
	GroupMgtService groupMgtService;
	
	@Autowired
	CodeMapper codeMapper;
	
	@Override
	public Map<String, Object> getCodeList(Map<String, Object> params) {
		Map<String, Object> resultMap =	MapBuilder.<String, Object>of(
				"maskingCode", codeMapper.selectCodeList(CodeUtil.createCodeListParam("MK000"))
		);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getRecordColumnList(Map<String, Object> param) {
		List<Map<String, Object>> list = mapper.selectRecordColumnList(param);
		return list;
	}

	@Override
	public int setRecordColumnList(Map<String, Object> params) {
		int i = mapper.updateRecordColumnList(params);
		return i;
	}
}
