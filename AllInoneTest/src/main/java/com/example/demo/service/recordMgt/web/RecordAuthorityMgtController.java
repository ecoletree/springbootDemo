/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2022. 10. 05.
 * File Name : RecordAuthorityMgtController.java
 * DESC : 녹음권한관리
*****************************************************************/
package com.example.demo.service.recordMgt.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import com.example.demo.service.recordMgt.service.RecordAuthorityMgtService;

@Controller
@RequestMapping("/record/authorityMgt")
public class RecordAuthorityMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RecordAuthorityMgtService service;
	
	/** 내선별 리포트 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".authorityMgt");
//		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
//				MapBuilder.<String, Object>of(
//						"list", list
//						))
//				);
		return mav;
	}
}
