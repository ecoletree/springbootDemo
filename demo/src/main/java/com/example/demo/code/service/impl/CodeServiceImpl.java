package com.example.demo.code.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.code.mapper.CodeMapper;
import com.example.demo.code.service.CodeService;

@Service
@Transactional
public class CodeServiceImpl implements CodeService {

	@Autowired
	CodeMapper mapper;
	
	@Override
	public List<Map<String, Object>> selectList (Map<String, Object> params) {
		
		return mapper.selectList(params);
	}
	
	@Override
	public int insertCode (Map<String, Object> params) throws Exception{
		try {
			mapper.insertCode();
			mapper.selectCode();
		}catch (Exception e) {
			e.getMessage();
			throw e;
		}
		return 0;
	}
}
