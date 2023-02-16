/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : ServiceLevelSettingController.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.code.service.CodeService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.settingMgt.service.ServiceLevelSettingService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/settings/serviceLevel")
public class ServiceLevelSettingController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	ServiceLevelSettingService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	@Autowired
	CodeService codeService;
	
	/** 스킬그룹관리 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".serviceLevelSetting");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				,"codeList", codeService.getCodeList(new HashMap<String,Object>())
				))
		);
		return mav;
	}
	
	@Auth
	@RequestMapping("/getServiceLevelList")
	public @ResponseBody Map<String, Object> getServiceLevelList(@RequestBody Map<String, Object> params) {
		
		return ResultUtil.getResultMap(true,service.getServiceLevelList(params));
	}
	
	@Auth
	@RequestMapping("/setServiceLevel")
	public @ResponseBody Map<String, Object> setServiceLevel(@RequestBody Map<String, Object> params) {
		int result = service.setServiceLevel(params);
		return ResultUtil.getResultMap(0 < result,params);
	}

}