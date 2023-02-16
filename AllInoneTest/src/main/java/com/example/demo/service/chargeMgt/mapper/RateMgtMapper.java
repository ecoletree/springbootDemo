package com.example.demo.service.chargeMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RateMgtMapper {

	public List<Map<String,Object>> selectRateInfoList(Map<String, Object> params);
	public List<Map<String,Object>> selectRateCodeList(Map<String, Object> params);
	
	public int insertRateInfo(Map<String, Object> params);
	public int updateRateInfo(Map<String, Object> params);
	public int deleteRateInfo(Map<String, Object> params);
	
	public List<Map<String,Object>> selectRateList(Map<String, Object> params);
	public int insertRate(Map<String, Object> params);
	public int updateRate(Map<String, Object> params);
	public int deleteRate(Map<String, Object> params);
}
