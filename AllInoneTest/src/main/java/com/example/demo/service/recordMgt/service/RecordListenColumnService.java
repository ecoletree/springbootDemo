package com.example.demo.service.recordMgt.service;

import java.util.List;
import java.util.Map;

public interface RecordListenColumnService {

	public Map<String,Object> getCodeList(Map<String, Object> params);

	public List<Map<String, Object>> getRecordColumnList(Map<String, Object> param);

	public int setRecordColumnList(Map<String, Object> params);

}
