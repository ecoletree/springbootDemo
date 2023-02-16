package com.example.demo.service.recordMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordListeningMgtMapper {

	public List<Map<String,Object>> selectRecordList(Map<String, Object> params);
	public List<Map<String,Object>> selectRECTransList(Map<String, Object> params);
	public int insertRECTrans(Map<String, Object> params);
	public int updateRECTrans(Map<String, Object> params);
	
	public List<Map<String,Object>> selectRecordColumnList(Map<String, Object> params);
	public List<Map<String,Object>> selectRecordSearchList(Map<String, Object> params);
}
