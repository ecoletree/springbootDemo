package com.example.demo.service.code.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.util.TreeUtil;
import com.example.demo.service.code.mapper.CodeMapper;
import com.example.demo.service.code.service.CodeService;

@Service
public class CodeServiceImpl extends ETBaseService implements CodeService {
	
	@Autowired
	CodeMapper mapper;

	@Override
	public List<Map<String, Object>> getCodeList(Map<String, Object> params) {
		List<Map<String, Object>> allCode = mapper.selectCodeList(params);
		List<Map<String, Object>> list = TreeUtil.groupCategoryCompare(allCode);
		
		return list;
	}
}
