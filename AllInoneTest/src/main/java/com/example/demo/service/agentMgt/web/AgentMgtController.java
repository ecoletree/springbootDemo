/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : AgentMgtController.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.agentMgt.web;

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
import com.example.demo.service.agentMgt.service.AgentMgtService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/agent/agentMgt")
public class AgentMgtController extends ETBaseController {

	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";

	@Autowired
	AgentMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	@Value("${SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	/**
	 * 상담사 관리로 이동
	 * @param mav
	 * @return
	 */
//	@Auth
	@RequestMapping("")
	public ModelAndView open(final ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".agentMgt");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", "0");
		params.put("length", "9999999999");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	}
	
	/**
	 * 상담사 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getAgentList")
	public @ResponseBody Map<String, Object> getAgentList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getAgentList(param);
		return map;
	}
	
	/**
	 * 상담사 수정,추가
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setAgentMgt")
	public @ResponseBody Map<String, Object> setAgentMgt(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		String str = service.setAgentMgt(params);
		return ResultUtil.getResultMap(str.equals(""),str);
	}
	/** 상담사 비밀번호 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setPWAgentChange")
	public @ResponseBody Map<String, Object> setPWAgentChange(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setPWAgentChange(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/**
	 * 상담사 삭제
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delAgentMgt")
	public @ResponseBody Map<String, Object> delAgentMgt(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		return ResultUtil.getResultMap(0 < service.delAgentMgt(params));
	}
	/**
	 * 상담사 그룹리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getSkillGroupList")
	public @ResponseBody Map<String, Object> getSkillGroupList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getgroupList(param);
		return map;
	}
	/**
	 * 상담사 그룹리스트 가져오기
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getTeamList")
	public @ResponseBody Map<String, Object> getTeamList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> map = service.getTeamList(params);
		return ResultUtil.getResultMap(!map.isEmpty() , map);
	}
}
