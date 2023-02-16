package com.example.demo.service.recordMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordListenColumnMapper {

	public List<Map<String, Object>> selectRecordColumnList(Map<String, Object> param);

	public int updateRecordColumnList(Map<String, Object> params);

}
