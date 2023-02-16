/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : ExtensionMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.extensionMgt.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.common.ListSubmitCheck;
import com.example.demo.service.extensionMgt.mapper.ExtensionMgtMapper;
import com.example.demo.service.extensionMgt.service.ExtensionMgtService;
/**
 *
 */
@Service
@Transactional
public class ExtensionMgtServiceImpl extends ETBaseService implements ExtensionMgtService {

	ListSubmitCheck lsc = new ListSubmitCheck();
	
	@Autowired
	ExtensionMgtMapper mapper;
	
	@Override
	public Map<String, Object> getExtensionMgtList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectStationList(params);
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


	@SuppressWarnings("unchecked")
	@Override
	public String setExtensionMgt(Map<String, Object> param) {
		String str = "";
		int page = 50;
		ArrayList<Map<String, Object>> arrParam = new ArrayList<Map<String, Object>>();
		if (param.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("stationList");
			boolean tenant = hasIDcheck(list,"tenant_id");
			boolean hunt = hasIDcheck(list,"grp_id");
			int count = dupCheck(list,page);
			boolean ipCheck = lsc.ipCheck(list);
			if (0 < count) {
				// -1 일 경우 중복
				str = "common.duplicate";
			}else if(tenant == false || hunt == false || ipCheck == false){
				if (!tenant) {
					str += "common.fail_csv_tenant";
				}
				if (!hunt) {
					str += !str.equals("") ?";":"";
					str += "common.fail_csv_grp";
				}
				if (!ipCheck) {
					str += !str.equals("") ?";":"";
					str += "common.fail_csv_ip";
				}
			}else {
				try {
					if(!list.isEmpty()) {
						
						int j = 0;
						for(Map<String, Object> map : list) {
							j++;
							arrParam.add(map);
							if (j != 0 && j % page == 0) {
								param.put("stationList", arrParam);
								mapper.insertStation(param);
								arrParam.clear();
							}
						}//for
					}
					if (!arrParam.isEmpty()) {
						param.put("stationList", arrParam);
						mapper.insertStation(param);
					}
				} catch (Exception e) {
					str = "common.fail_csv_file";
				}
			}
			
		} else {
			mapper.updateStation(param);
		}
		return str;
	}
	
	/** DB 유무 체크
	 * @param list
	 * @param keyName
	 * @return	
	 */
	public boolean hasIDcheck(List<Map<String, Object>> list,String keyName){
		int cnt = 0;
		boolean b = true;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> checkList= lsc.noDupList(list,keyName);
		if(checkList.size() > 0) {
			param.put("checkList", checkList);
			if(keyName == "tenant_id") {
				cnt = mapper.selectTenantCheck(param);
			}else {
				cnt = mapper.selectHuntCheck(param);
			}
			b = checkList.size() == cnt;
			
		}
		return b;
	}
	/** DB 중복 체크
	 * @param list
	 * @param page
	 * @param name
	 * @return
	 */
	public int dupCheck(List<Map<String, Object>> list, int page) {
		ArrayList<Map<String, Object>> arrParam = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		int dupCnt = 0;
		if(!list.isEmpty()) {
			int i = 0;
			for(Map<String, Object> map : list) {
				i++;
				arrParam.add(map);
				if (i != 0 && i % page == 0) {
					param.put("stationList", arrParam);
					dupCnt += mapper.selectPhoneDupCheck(param);
					arrParam.clear();
				}
			}//for
		}
		if (!arrParam.isEmpty()) {
			param.put("stationList", arrParam);
			dupCnt += mapper.selectPhoneDupCheck(param);
		}
		return dupCnt;
	}

	@Override
	public int delExtensionMgt(Map<String, Object> param) {
		return mapper.deleteStation(param);
	}

	@Override
	public Map<String, Object> getHuntGoupList(Map<String, Object> params) {
		List<Map<String, Object>> list = mapper.selectHuntGroupList(params);
		List<Map<String, Object>> child = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String preTenantId = null;
		for (Map<String, Object> map : list) {
			String tenantId = map.get("tenant_id").toString();
			if (preTenantId == null) {
				child = new ArrayList<Map<String,Object>>();
			} else if (!preTenantId.equals(tenantId) ) {
				returnMap.put(preTenantId, child);
				child = new ArrayList<Map<String,Object>>();
			}
			child.add(map);
			preTenantId = tenantId;
		}
		
		if (child != null && !child.isEmpty()) {
			returnMap.put(preTenantId, child);
		}
		returnMap.put("all", list);
		
		return returnMap;
	}
}
