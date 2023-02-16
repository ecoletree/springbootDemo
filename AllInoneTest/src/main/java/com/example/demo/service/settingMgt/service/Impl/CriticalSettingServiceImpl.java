/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : CriticalSettingServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.util.TreeUtil;
import com.example.demo.service.settingMgt.mapper.CriticalSettingMapper;
import com.example.demo.service.settingMgt.service.CriticalSettingService;

@Service
public class CriticalSettingServiceImpl extends ETBaseService implements CriticalSettingService {
	
	@Autowired
	CriticalSettingMapper mapper;

	@Override
	public List<Map<String, Object>> getCriticalStatus(Map<String, Object> params) {
		List<Map<String, Object>> list = mapper.selectMonitoringStatusList(params);
		List<Map<String,Object>> tree = TreeUtil.groupCategoryCompare(list, "code_cd", "p_code_cd" );
		List<Map<String,Object>> menuList = setMenuList(tree);
		return menuList;
	}
	/** 리스트정리
	 * @param roleTree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> setMenuList(List<Map<String, Object>> roleTree) {
		for(int i = 0; i <= roleTree.size()-1;i++) {
			Map<String, Object> map = roleTree.get(i);
			
			if(map.get("p_code_cd") == null) {
				if(!map.containsKey("children")) {
					roleTree.remove(i);
				}else {
					ArrayList<Map<String,Object>> children = (ArrayList<Map<String,Object>>)map.get("children");
					ArrayList<Map<String,Object>> rmvList = new ArrayList<Map<String,Object>>();
					for(int j = 0; j<= children.size()-1; j++) {
						Map<String, Object> child = children.get(j);
						if(child.get("p_code_cd") == null) {
							rmvList.add(child);
						}
					}
					children.removeAll(rmvList);
					if (children.isEmpty()) {
						roleTree.remove(i);
					}
					
				}
			}
			
		}
		return roleTree;
	}
	@Override
	public int setCriticalSettingMgt(Map<String, Object> params) {
		System.out.println(params);
		int i = mapper.insertCriticalSetting(params);
		return i;
	}

}
