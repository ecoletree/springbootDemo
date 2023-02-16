/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.systemadmin.service;

import java.util.List;
import java.util.Map;

public interface SystemAuthorityService {
	
	public List<Map<String, Object>> getUserTypeList();
	public List<Map<String, Object>> getAuthList(Map<String, Object> params);
	
	public int setAuthList(Map<String, Object> params) throws Exception;
}
