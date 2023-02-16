package com.example.demo.service.chargeMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.chargeMgt.mapper.PayReportMapper;
import com.example.demo.service.chargeMgt.service.PayReportService;

@Service
public class PayReportServiceImpl extends ETBaseService implements PayReportService {
	
	@Autowired
	PayReportMapper mapper;

	@Override
	public Map<String, Object> getReportList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = mapper.selectRecordList(params);
			int totalCount = list.size();
			
			resultMap.put("recordsTotal", totalCount);
			resultMap.put("recordsFiltered", totalCount);
			resultMap.put("data", list);
		} catch (Exception e) {
			logError(e.getMessage(),e);
			throw e;
		}
		return resultMap;
	}

}
