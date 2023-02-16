/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 5. 31.
 * File Name : ListSubmitCheck.java
 * DESC : 일괄등록 시에 사용
*****************************************************************/
package com.example.demo.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.net.InetAddresses;

import kr.co.ecoletree.common.util.MapBuilder;

public class ListSubmitCheck {

	public List<Map<String,Object>> noDupList(List<Map<String, Object>> list,String keyName){
		List<Map<String, Object>> checkList= new ArrayList<>();
		Map<String, Object> checkMap = new HashMap<String, Object>();
		if(!list.isEmpty()) {
			for(Map<String, Object> map : list) {
				if( map.get(keyName) != null ) {
					checkMap.put(String.valueOf(map.get(keyName)), map.get(keyName));
				}
			}
		}
		if(!checkMap.isEmpty()) {
			for (String key : checkMap.keySet()) {
				Map<String,Object> idMap = MapBuilder.of(keyName,Integer.parseInt(key));
				checkList.add(idMap);
			}
		}
		
		return checkList;
		
	}
	/** 일괄등록시 ip check
	 * @param list
	 * @return
	 */
	public boolean ipCheck(List<Map<String,Object>> list ) {
		boolean b = true;
		
		for(Map<String, Object> map : list) {
			b = InetAddresses.isInetAddress(String.valueOf( map.get("station_ip")));
			if(b == false) break;
		}
		return b;
	}
}
