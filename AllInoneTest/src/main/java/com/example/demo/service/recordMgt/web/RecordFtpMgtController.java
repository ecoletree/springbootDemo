/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2023. 01. 25.
 * File Name : RecordFtpMgtController.java
 * DESC :  녹취전송 FTP 관리
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
import com.example.demo.service.recordMgt.service.RecordFtpMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record/ftpMgt")
public class RecordFtpMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RecordFtpMgtService service;
	
	
	
	/** 녹취전송 FTP 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".ftpMgt");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(service.getCodeList(params)));
		return mav;
		
	}
	/** 리스트 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getFtpList")
	public @ResponseBody Map<String, Object> getFtpList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getFtpList(params);
	}

	/** 수정, 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setFtpInfo")
	public @ResponseBody Map<String, Object> setFtpInfo(@RequestBody Map<String, Object> params) throws Exception{
		int i = service.setFtpInfo(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delFtpInfo")
	public @ResponseBody Map<String, Object> delFtpInfo(@RequestBody Map<String, Object> params) throws Exception{
		return ResultUtil.getResultMap(0 < service.deleteFtpInfo(params));
	}
	
	
}
