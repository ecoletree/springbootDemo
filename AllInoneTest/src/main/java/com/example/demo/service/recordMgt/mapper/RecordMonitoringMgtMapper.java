package com.example.demo.service.recordMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMonitoringMgtMapper {

	List<Map<String, Object>> getRecordMonirotingList(Map<String, Object> params);

}
