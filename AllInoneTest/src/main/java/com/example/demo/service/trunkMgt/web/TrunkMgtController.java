/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkMgtController.java
 * DESC : 국선관리
*****************************************************************/
package com.example.demo.service.trunkMgt.web;

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
import com.example.demo.service.trunkMgt.service.TrunkMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/trunk/trunkMgt")
public class TrunkMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	TrunkMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;

	/**
	 * 국선관리로 이동
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".trunkMgt");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	}
	
	/**국선관리리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getTrunkMgtList")
	public @ResponseBody Map<String, Object> getTrunkMgtList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getTrunkMgtList(param);
		return map;
	}
	
	/** 국선관리 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setTrunkMgt")
	public @ResponseBody Map<String, Object> setTrunkMgt(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setTrunkMgt(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 국선관리 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delTrunkMgt")
	public @ResponseBody Map<String, Object> delTrunkMgt(@RequestBody Map<String, Object> param)throws Exception {
		return ResultUtil.getResultMap(0 < service.delTrunkMgt(param));
	}
	
	/**라우팅대상리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getGroupList")
	public @ResponseBody Map<String, Object> getGroupList(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
		Map<String, Object> map = service.getTransferIDList(param);
		return ResultUtil.getResultMap(!map.isEmpty() , map);
	}

}
