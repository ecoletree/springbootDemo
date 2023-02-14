package com.example.demo.code.service;

import java.util.List;
import java.util.Map;

public interface CodeService {

	List<Map<String, Object>> selectList(Map<String, Object> params);
	
	public int insertCode (Map<String, Object> params) throws Exception;

}
