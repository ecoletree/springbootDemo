/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtController.java
 * DESC : 고객사관리
*****************************************************************/
package com.example.demo.service.custMgt.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.custMgt.service.CustMgtService;

@Controller
@RequestMapping("/tenant/custMgt")
public class CustMgtController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	CustMgtService service;
	
	/** 고객사관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".custMgt");
		return mav;
	}
	
	/** 고객사관리리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getCustMgtList")
	public @ResponseBody Map<String, Object> getCustMgtList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.selectGroupMgtList(params);
	}
	
	/** 고객사관리 수정, 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setCustMgt")
	public @ResponseBody Map<String, Object> setCustMgt(@RequestBody Map<String, Object> params) throws Exception{
		int i = service.setGroupMgt(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 고객사관리 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delCustMgt")
	public @ResponseBody Map<String, Object> delCustMgt(@RequestBody Map<String, Object> params) throws Exception{
		return ResultUtil.getResultMap(0 < service.deleteGroupMgt(params));
	}
	
	


}
