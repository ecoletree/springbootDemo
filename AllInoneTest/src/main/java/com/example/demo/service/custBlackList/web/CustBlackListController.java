/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustBlackListController.java
 * DESC : 고객사블랙리스트 
*****************************************************************/
package com.example.demo.service.custBlackList.web;

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
import com.example.demo.service.custBlackList.service.CustBlackListService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/tenant/custBlackList")
public class CustBlackListController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	CustBlackListService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 고객사블랙리스트로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".custBlackList");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	}
	
	/** 고객사블랙리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getCustBlackList")
	public @ResponseBody Map<String, Object> getCustBlackList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getBlackList(params);
	}
	
	/** 고객사블랙리스트 수정, 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setCustBlackList")
	public @ResponseBody Map<String, Object> setCustBlackList(@RequestBody Map<String, Object> param) throws Exception{
		int i = service.setBlackList(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 고객사블랙리스트 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delCustBlackList")
	public @ResponseBody Map<String, Object> delCustBlackList(@RequestBody Map<String, Object> param) throws Exception{
		return ResultUtil.getResultMap(0 < service.deleteBlackList(param));
	}
	
	
}
