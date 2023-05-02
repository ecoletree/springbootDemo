/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 27.
 * File Name : MainServiceImpl.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.main.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootWorkout.demo1.main.mapper.MainMapper;
import com.bootWorkout.demo1.main.service.MainService;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.StringUtil;
import kr.co.ecoletree.common.vo.ETSessionVO;

@Service
public class MainServiceImpl extends ETBaseService implements MainService {
	
	@Autowired
	MainMapper mapper;

	/**
	 * 공통으로 가져오는 그룹-센터-팀
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getGroupList(Map<String, Object> param) {
		
		List<Map<String,Object>> selectGrpList = mapper.selectGroupList(param);
		
		List<Map<String,Object>> grpList = setGroupListHierarchy(selectGrpList,"group_id","tenant_id");
		for(Map<String,Object> map : grpList) {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(map.containsKey("children")) {
				list = setGroupListHierarchy((List<Map<String, Object>>) map.get("children"),"tenant_id","team_id");
				if(!list.isEmpty()) {
					map.replace("children", list);
				}
			}
			
		}
		
		return grpList;
	}
	
	/** 정보를 트리형으로 변경
	 * @param selectGrpList
	 * @param pcd
	 * @param cd
	 * @return
	 */
	public static List<Map<String, Object>> setGroupListHierarchy(List<Map<String, Object>> selectGrpList, String pcd, String cd) {
		
		List<Map<String,Object>> grpList = new ArrayList<Map<String,Object>>();
		
		for(Map<String, Object> vo1 : selectGrpList) {
			if(vo1.get(cd) == null) {
				grpList.add(vo1);
			}
		}
		
		for(Map<String, Object> vo1 : grpList) {
			List<Map<String,Object>> child = new ArrayList<Map<String,Object>>(); 
			for(Map<String,Object> vo2 : selectGrpList) {
				if(vo1.get(pcd).equals(vo2.get(pcd)) && vo2.get(cd) != null) {
					child.add(vo2);
				}
			}
			if(!child.isEmpty()) {
				vo1.put("children", child);				
			}
		}
		
		return grpList;
	}
}
