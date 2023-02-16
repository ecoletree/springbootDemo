/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 로그 관리
*****************************************************************/
package com.example.demo.service.systemlog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemLogMapper {

	public List<Map<String,Object>> selectLogList(Map<String, Object> params);
}
