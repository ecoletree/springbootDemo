package com.example.demo.service.code.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeMapper {

	List<Map<String, Object>> selectCodeList(Map<String, Object> params);
	
	List<Map<String, Object>> selectAreaCodeList();
	List<Map<String, Object>> selectBroadbandCodeList();
	List<Map<String, Object>> selectCenterLocationList();
	List<Map<String, Object>> selectHolydayList();
	List<Map<String, Object>> selectNationCodeList();
	List<Map<String, Object>> selectSaleTimeList();
	List<Map<String, Object>> selectTelecomCodeList();

}
