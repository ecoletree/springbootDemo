package com.example.demo.service.recordMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordSendMgtMapper {

	public List<Map<String, Object>> selectRecordSendList(Map<String, Object> params);

	public List<Map<String, Object>> selectPreedSendRecordList(Map<String, Object> params);

	public List<Map<String, Object>> selectPreedCertificationLists(Map<String, Object> params);

	public int updateRecordState(Map<String, Object> map);

	public int updateRecordCertificationState(Map<String, Object> map);

	public int updateRecordSendList(Map<String, Object> ftpmap);

}
