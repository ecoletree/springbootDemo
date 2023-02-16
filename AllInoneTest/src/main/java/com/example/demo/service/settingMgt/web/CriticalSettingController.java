/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 8. 9.
 * File Name : CriticalSettingController.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.settingMgt.web;

import java.util.HashMap;
import java.util.List;
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
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.code.service.CodeService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.settingMgt.service.CriticalSettingService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/settings/critical")
public class CriticalSettingController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	CriticalSettingService service;
	
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
		mav.setViewName(JSP_PATH + ".criticalSetting");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", "0");
		params.put("length", "9999999999");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				, "userInfo", ETSessionHelper.getSessionVO()
				,"codeList", codeService.getCodeList(new HashMap<String,Object>())
				))
		);
		return mav;
	}

	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCriticalStatus")
	public @ResponseBody Map<String, Object> getCriticalStatus(@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = service.getCriticalStatus(params);
		
		return ResultUtil.getResultMap(list.size()>0, list);
	}
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setCriticalSettingMgt")
	public @ResponseBody Map<String, Object> setCriticalSettingMgt(@RequestBody Map<String, Object> params) throws Exception {
		int i = service.setCriticalSettingMgt(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
}
