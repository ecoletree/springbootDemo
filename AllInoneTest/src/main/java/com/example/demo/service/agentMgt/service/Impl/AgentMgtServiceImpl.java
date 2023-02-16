/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : AgentMgtServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.agentMgt.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.util.CryptoUtil;
import kr.co.ecoletree.common.util.MapBuilder;
import com.example.demo.service.agentMgt.mapper.AgentMgtMapper;
import com.example.demo.service.agentMgt.service.AgentMgtService;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.common.ListSubmitCheck;

@Service
@Transactional
public class AgentMgtServiceImpl extends ETBaseService implements AgentMgtService {

	ListSubmitCheck lsc = new ListSubmitCheck();
	
	@Autowired
	AgentMgtMapper mapper;
	
	@Override
	public Map<String, Object> getAgentList(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = mapper.selectAgentList(param);
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
	@SuppressWarnings("unchecked")
	public String setAgentMgt(Map<String, Object> params) throws Exception{
		String str = "";
		int page = 2;
		ArrayList<Map<String, Object>> arrParam = new ArrayList<Map<String, Object>>();
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("agentList");
			int count = dupCheck(list,page); // 중복체크
			boolean skill = hasIDcheck(list,"grp_id"); //스킬그룹체크
			// In/Out 구분
			for(Map<String,Object> map : list) {
				if(map.get("io_flag") instanceof String) {
					int flagNum = 1;
					String flag = (String) map.get("io_flag");
					switch(flag){
					case "I":
						flagNum = 1;
						break;
					case "O":
						flagNum = 2;
						break;
					default:
						flagNum = 3;
					}
					map.put("io_flag", flagNum);
				}
			}
			if (0 < count) {
				// -1 일 경우 중복
				str = "common.duplicate";
			}else if(skill== false ){
				str = "common.fail_csv_skill";
			} else {
				try {
					if(!list.isEmpty()) {
						
						int j = 0;
						for(Map<String, Object> map : list) {
							map.put("agent_pw", CryptoUtil.encodePasswordSha256((String)map.get("agent_pw")));
							j++;
							arrParam.add(map);
							if (j != 0 && j % page == 0) {
								params.put("agentList", arrParam);
								mapper.insertAgent(params);
								arrParam.clear();
							}
						}//for
					}
					if (!arrParam.isEmpty()) {
						params.put("agentList", arrParam);
						mapper.insertAgent(params);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					str = "common.fail_csv_file";
				}
				
			}
		} else {
			params.put("agent_pw", CryptoUtil.encodePasswordSha256((String)params.get("agent_pw")));
			mapper.updateAgent(params);
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
		param.put("checkList", checkList);
		cnt = mapper.selectSkillGrpCheck(param);
		b = checkList.size() == cnt;
		return b;
	}

	/** DB 중복체크
	 * @param list
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int dupCheck(List<Map<String, Object>> list, int page) {
		ArrayList<Map<String, Object>> arrParam = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = new HashedMap<String, Object>();
		int dupCnt = 0;
		if(!list.isEmpty()) {
			int i = 0;
			for(Map<String, Object> map : list) {
				i++;
				arrParam.add(map);
				if (i != 0 && i % page == 0) {
					param.put("agentList", arrParam);
					dupCnt += mapper.selectAgentCheck(param);
					dupCnt += mapper.selectAgentSkillCheck(param);
					arrParam.clear();
				}
			}//for
		}
		if (!arrParam.isEmpty()) {
			param.put("agentList", arrParam);
			dupCnt += mapper.selectAgentCheck(param);
			dupCnt += mapper.selectAgentSkillCheck(param);
		}
		return dupCnt;
	}
	@Override
	public int delAgentMgt(Map<String, Object> params) {
		return mapper.deleteAgent(params);
	}

	@Override
	public Map<String, Object> getgroupList(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = mapper.selectGroupList(param);
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
	/**
	 * 팀 리스트 센터id 기준으로 분리 
	 */
	@Override
	public Map<String, Object> getTeamList(Map<String, Object> params) {
		List<Map<String, Object>> list = mapper.selectTeamList(params);
		List<Map<String, Object>> child = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String preTenantId = null;
		for(Map<String, Object> map : list) {
			String tenantId = map.get("tenant_id").toString();
			if(preTenantId == null) {
				child = new ArrayList<Map<String, Object>>();
			}else if (!preTenantId.equals(tenantId)) {
				returnMap.put(preTenantId, child );
				child = new ArrayList<Map<String, Object>>();
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

	@Override
	public int setPWAgentChange(Map<String, Object> params) throws Exception {
		int i=0;
		params.put("agent_pw", CryptoUtil.encodePasswordSha256((String)params.get("agent_pw")));
		i = mapper.updateAgentPW(params);
		return i;
	}

	@Override
	public Map<String, Object> getSkillGroupList(Map<String, Object> params) {
		List<Map<String,Object>> list =  mapper.selectGroupList(params);
		return MapBuilder.of("resultMsg",list.size()>0 ? ETCommonConst.SUCCESS : ETCommonConst.FAILED , "data", list);
	}




}
