/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 로그 관리
*****************************************************************/
package com.example.demo.service.systemlog.service;

import java.util.Map;

public interface SystemLogService {
	
	public Map<String, Object> getSystemLogList(Map<String, Object> params);
	
}
