/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.systemadmin.service;

import java.util.Map;

public interface SystemAdminService {
	
	public Map<String, Object> getSystemAdminList(Map<String, Object> params);
	
	public int setSystemAdmin(Map<String, Object> params) throws Exception;
	public int deleteSystemAdmin(Map<String, Object> params);

	int setPWSystemAdminChange(Map<String, Object> params) throws Exception;
}
