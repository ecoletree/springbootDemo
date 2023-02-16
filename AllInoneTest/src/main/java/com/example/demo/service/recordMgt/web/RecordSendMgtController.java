/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2022. 10. 05.
 * File Name : RecordSendMgtController.java
 * DESC :  녹음 전송 관리
*****************************************************************/
package com.example.demo.service.recordMgt.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.recordMgt.service.RecordSendMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record/sendMgt")
public class RecordSendMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RecordSendMgtService service;
	
	/** 내선별 리포트 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".recordSendMgt");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(service.getCodeList(params)));
		return mav;
		
	}
	/** 리스트 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRecordSendList")
	public @ResponseBody Map<String, Object> getRecordSendList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getRecordSendList(params);
	}
	
	/** 전송
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/sendRecordList")
	public @ResponseBody Map<String, Object> sendRecordList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		int i = service.sendRecordList(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
}
