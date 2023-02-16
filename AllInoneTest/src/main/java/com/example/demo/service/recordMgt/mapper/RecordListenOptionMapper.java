/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.recordMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordListenOptionMapper {

	public List<Map<String,Object>> selectUserTypeList();
	public List<Map<String,Object>> selectColumnList(Map<String, Object> params);
	public List<Map<String,Object>> selectSearchList(Map<String, Object> params);
	
	public int upsertColumnList(Map<String, Object> params);
	public int upsertSearchList(Map<String, Object> params);
}
