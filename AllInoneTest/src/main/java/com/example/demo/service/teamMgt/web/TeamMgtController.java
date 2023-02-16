/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : CustMgtController.java
 * DESC : 고객사관리
*****************************************************************/
package com.example.demo.service.teamMgt.web;

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
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.teamMgt.service.TeamMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/tenant/teamMgt")
public class TeamMgtController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	TeamMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 팀관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".teamMgt");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
				MapBuilder.<String, Object>of(
						"groupList", groupMgtService.getGroupList(params)
						))
				);
		return mav;
	}
	/**
	 * 팀 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getTableList")
	public @ResponseBody Map<String, Object> getAgentList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getTeamList(param);
		return map;
	}
	
	/** 팀 수정, 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setTeamMgt")
	public @ResponseBody Map<String, Object> setCustMgt(@RequestBody Map<String, Object> params) throws Exception{
		int i = service.setGroupMgt(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 팀 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delTeamMgt")
	public @ResponseBody Map<String, Object> delCustMgt(@RequestBody Map<String, Object> params) throws Exception{
		return ResultUtil.getResultMap(0 < service.deleteGroupMgt(params));
	}

}
