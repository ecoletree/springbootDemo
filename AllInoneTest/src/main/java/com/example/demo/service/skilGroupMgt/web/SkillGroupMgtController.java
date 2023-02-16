/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : SkillGroupMgtController.java
 * DESC : 스킬그룹관리
*****************************************************************/
package com.example.demo.service.skilGroupMgt.web;

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
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.skilGroupMgt.service.SkillGroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/agent/skilGroupMgt")
public class SkillGroupMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	SkillGroupMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 스킬그룹관리 이동
	 * @param mav
	 * @return
	 */
//	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".skilGroupMgt");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", "0");
		params.put("length", "9999999999");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				,"soundList",service.getSoundList(params)
				))
		);
		return mav;
	}
	
	/** 스킬그룹관리 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getSkillGroupList")
	public @ResponseBody Map<String, Object> getSkillGroupList(HttpServletRequest request) throws Exception{
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getSkillGroupList(param);
		return map;
	}
	
	/** 스킬그룹관리 수정, 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setSkillGroup")
	public @ResponseBody Map<String, Object> setSkillGroup(@RequestBody Map<String, Object> param) throws Exception{
		int i = service.setSkillGroup(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 스킬그룹관리 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delSkillGroup")
	public @ResponseBody Map<String, Object> delSkillGroup(@RequestBody Map<String, Object> param) throws Exception{
		return ResultUtil.getResultMap(0 < service.delSkillGroup(param));
	}
	
	/**
	 * 상담사 그룹리스트 가져오기
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getSoundList")
	public @ResponseBody Map<String, Object> getSkillGroupList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> map = service.getSoundList(params);
		return ResultUtil.getResultMap(!map.isEmpty() , map);
	}


}
