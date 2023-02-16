/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : RecordUploadMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.recordUpload.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordUploadMapper {

	public List<Map<String,Object>> selectRecordUploadList(Map<String, Object> params);
	
	public int upsertRecordUpload(Map<String, Object> params);
	public int deleteRecordUpload(Map<String, Object> params);
}
