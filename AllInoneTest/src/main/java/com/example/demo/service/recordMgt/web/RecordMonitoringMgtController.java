/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2022. 10. 05.
 * File Name : RecordMonitoringMgtController.java
 * DESC :  녹음 모니터링 
*****************************************************************/
package com.example.demo.service.recordMgt.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import com.example.demo.service.recordMgt.service.RecordMonitoringMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record/monitoring")
public class RecordMonitoringMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RecordMonitoringMgtService service;
	
	/** 내선별 리포트 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".recordMonitoring");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(service.getCodeList(params)));

		return mav;
	}
	
	/** 리스트 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRecordMgtList")
	public @ResponseBody Map<String, Object> getRecordMgtList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getRecordMgtList(params);
	}
	
}
