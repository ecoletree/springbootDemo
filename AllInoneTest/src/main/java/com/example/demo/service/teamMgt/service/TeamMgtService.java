/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.teamMgt.service;

import java.util.Map;

public interface TeamMgtService {

	public Map<String, Object> getTeamList(Map<String, Object> param);

	public int deleteGroupMgt(Map<String, Object> params);

	public int setGroupMgt(Map<String, Object> params);

}
