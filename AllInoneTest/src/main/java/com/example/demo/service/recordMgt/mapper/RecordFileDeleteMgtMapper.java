package com.example.demo.service.recordMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordFileDeleteMgtMapper {

	public List<Map<String,Object>> selectRecordRemoveList(Map<String, Object> params);
	public int upsertRecordRemove(Map<String, Object> params);
}
