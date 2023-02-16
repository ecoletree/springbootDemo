package com.example.demo.service.recordMgt.service;

import java.util.Map;

public interface RecordSendMgtService {

	public Map<String, Object> getCodeList(Map<String, Object> params);

	public Map<String, Object> getRecordSendList(Map<String, Object> params);

	public int sendRecordList(Map<String, Object> params);

}
