/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.recordMgt.service;

import java.util.List;
import java.util.Map;

public interface RecordListenOptionService {
	
	public List<Map<String, Object>> getUserTypeList();
	
	public Map<String, Object> getListenOptionList(Map<String, Object> params);
	
	public int setColumnList(Map<String, Object> params);
	public int setOptionList(Map<String, Object> params);
}
