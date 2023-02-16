package com.example.demo.service.recordMgt.service;

import java.util.List;
import java.util.Map;

public interface RecordListeningMgtService {

	public Map<String, Object> getRecordList(Map<String, Object> params);
	public List<Map<String, Object>> getCertificateList(Map<String, Object> params);
	
	public List<Map<String, Object>> getColumnList(Map<String, Object> params);
	public List<Map<String, Object>> getSearchList(Map<String, Object> params);

	int sendRecordFileTrans(Map<String, Object> params);
}
