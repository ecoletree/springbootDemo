package com.example.demo.code.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.code.mapper.CodeMapper;
import com.example.demo.code.service.CodeService;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	CodeMapper mapper;
	
	@Override
	public List<Map<String, Object>> selectList (Map<String, Object> params) {
		
		return mapper.selectList(params);
	}
}
