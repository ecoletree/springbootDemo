/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustManagerMgtController.java
 * DESC : 조직관리자 관리
*****************************************************************/
package com.example.demo.service.custManagerMgt.web;

import java.util.HashMap;
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
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.custManagerMgt.service.CustManagerMgtService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/tenant/custManagerMgt")
public class CustManagerMgtController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	CustManagerMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	
	
	/**
	 * 조직관리자관리로 이동
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(final ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".custManagerMgt");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	}
	
	/** 조직관리자 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getCustManagerList")
	public @ResponseBody Map<String, Object> getCustManagerList(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParamToMap(request);
		return service.getCustManagerList(params);
	}
	
	/** 조직관리자 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setCustManager")
	public @ResponseBody Map<String, Object> setCustManager(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setCustManager(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 조직관리자 비밀번호 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setPWCustManagerChange")
	public @ResponseBody Map<String, Object> setPWCustManagerChange(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setPWCustManagerChange(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 조직관리자 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delCustManager")
	public @ResponseBody Map<String, Object> delCustManager(@RequestBody Map<String, Object> param)throws Exception {
		return ResultUtil.getResultMap(0 < service.deleteCustManager(param));
	}
	
	
}
