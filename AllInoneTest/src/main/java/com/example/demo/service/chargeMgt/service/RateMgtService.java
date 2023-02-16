package com.example.demo.service.chargeMgt.service;

import java.util.Map;

public interface RateMgtService {

	Map<String, Object> getRateInfoList(Map<String, Object> params);
	int setRateInfo(Map<String, Object> params);
	int delRateInfo(Map<String, Object> params);
	
	Map<String, Object> getRateList(Map<String, Object> params);
	
	Map<String, Object> getCodeList();

	int setRate(Map<String, Object> params) throws Exception;
	int delRate(Map<String, Object> params);
	Map<String, Object> getRateGroupList(Map<String, Object> params);

}
