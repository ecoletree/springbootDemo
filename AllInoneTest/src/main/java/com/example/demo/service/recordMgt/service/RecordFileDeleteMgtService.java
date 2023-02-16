package com.example.demo.service.recordMgt.service;

import java.util.List;
import java.util.Map;

public interface RecordFileDeleteMgtService {

	public List<Map<String, Object>> getRecordRemoveList(Map<String, Object> params);
	int setRecordRemove(Map<String, Object> params);
}
