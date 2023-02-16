/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : ServiceLevelSettingServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.settingMgt.mapper.ServiceLevelSettingMapper;
import com.example.demo.service.settingMgt.service.ServiceLevelSettingService;

@Service
public class ServiceLevelSettingServiceImpl extends ETBaseService implements ServiceLevelSettingService {

	@Autowired
	ServiceLevelSettingMapper mapper;
	
	@Override
	public List<Map<String, Object>> getServiceLevelList(Map<String, Object> params) {
		return mapper.selectServiceLevelList(params);
	}

	@Override
	public int setServiceLevel(Map<String, Object> params) {
		return mapper.upsertServiceLevel(params);
	}

}
