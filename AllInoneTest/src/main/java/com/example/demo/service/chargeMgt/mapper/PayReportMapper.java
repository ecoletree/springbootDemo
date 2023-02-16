package com.example.demo.service.chargeMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayReportMapper {

	public List<Map<String,Object>> selectRecordList(Map<String, Object> params);
}
