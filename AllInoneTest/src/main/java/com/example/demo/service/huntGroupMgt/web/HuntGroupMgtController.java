/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : HuntGroupMgtController.java
 * DESC : 헌트그룹관리
*****************************************************************/
package com.example.demo.service.huntGroupMgt.web;

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
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.huntGroupMgt.service.HuntGroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pbx/huntGroupMgt")
public class HuntGroupMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	HuntGroupMgtService service;
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 헌트그룹관리 이동
	 * @param mav
	 * @return
	 */
//	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".huntGroupMgt");
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
	
	/** 헌트그룹관리리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@Auth
	@RequestMapping("/getHuntGroupList")
	public @ResponseBody Map<String, Object> getHuntGroupList(HttpServletRequest request) throws Exception{
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getHuntGroupList(param);
		return map;
	}
	
	/** 헌트그룹관리 수정, 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	@Auth
	@RequestMapping("/setHuntGroup")
	public @ResponseBody Map<String, Object> setHuntGroup(@RequestBody Map<String, Object> params) throws Exception{
		int i = service.setHuntGroup(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 헌트그룹관리 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	@Auth
	@RequestMapping("/delHuntGroup")
	public @ResponseBody Map<String, Object> delHuntGroup(@RequestBody Map<String, Object> param) throws Exception{
		return ResultUtil.getResultMap(0 < service.deleteHuntGroup(param));
	}
	
	


}
