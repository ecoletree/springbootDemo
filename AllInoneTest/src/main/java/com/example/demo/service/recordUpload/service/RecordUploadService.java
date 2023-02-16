/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : RecordUploadService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.recordUpload.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface RecordUploadService {

	public Map<String, Object> getRecordUploadList(Map<String, Object> params);
	public int setRecordUpload(Map<String, Object> params, MultipartHttpServletRequest request);
	
	public int delRecordUpload(Map<String, Object> params);

}
