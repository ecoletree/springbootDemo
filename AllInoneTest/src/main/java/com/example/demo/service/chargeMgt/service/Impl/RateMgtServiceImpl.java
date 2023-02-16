package com.example.demo.service.chargeMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.chargeMgt.mapper.RateMgtMapper;
import com.example.demo.service.chargeMgt.service.RateMgtService;
import com.example.demo.service.code.mapper.CodeMapper;
import com.example.demo.service.code.util.CodeUtil;
import com.example.demo.service.common.CommonConst;
@Service
public class RateMgtServiceImpl extends ETBaseService implements RateMgtService {

	@Autowired
	RateMgtMapper mapper;
	
	@Autowired
	CodeMapper codeMapper;
	
	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getCodeList() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap =	MapBuilder.<String, Object>of(
				"codeList", codeMapper.selectCodeList(CodeUtil.createCodeListParam("CA000")),
				"telecomList", codeMapper.selectTelecomCodeList()
		);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getRateInfoList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = mapper.selectRateInfoList(params);
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
	
	@Override
	public int setRateInfo(Map<String, Object> params) {
		params.put("session_user_id", ETSessionHelper.getUserId());
		int i = 0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			i = mapper.insertRateInfo(params);
		} else {
			i = mapper.updateRateInfo(params);
		}
		return i;
	}
	
	@Override
	public int delRateInfo(Map<String, Object> params) {
		return mapper.deleteRateInfo(params);
	}
	
	@Override
	public Map<String, Object> getRateList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = mapper.selectRateList(params);
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
	
	@Override
	public Map<String, Object> getRateGroupList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = mapper.selectRateList(params);
			List<Map<String, Object>> rateCodelist = mapper.selectRateCodeList(params);
			
			Map<String, List<Map<String, Object>>> resultList = 
					list.stream().collect(Collectors.groupingBy(map -> (map).get("bill_type").toString()));
			
			for (Map<String, Object> map : rateCodelist) {
				String billType = (String)map.get("bill_type");
				map.put("children", resultList.get(billType));
			}
			
			resultMap = ResultUtil.getResultMap(true, rateCodelist);
		} catch (Exception e) {
			logError(e.getMessage(),e);
			throw e;
		}
		return resultMap;
	}
	
	@Override
	public int setRate(Map<String, Object> params) throws Exception {
		params.put("session_user_id", ETSessionHelper.getUserId());
		int i = 0;
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			i = mapper.insertRate(params);
		} else {
			i = mapper.updateRate(params);
		}
		return i;
	}

	@Override
	public int delRate(Map<String, Object> params) {
		return mapper.deleteRate(params);
	}
}
