/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : TrunkGWController.java
 * DESC : TrunkGW
*****************************************************************/
package com.example.demo.service.trunkGW.web;

import java.util.HashMap;
import java.util.List;
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
import com.example.demo.service.trunkGW.service.TrunkGWService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/gateway/trunkGW")
public class TrunkGWController extends ETBaseController {

	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	TrunkGWService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/**
	 * TrunkG/W로 이동
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(final ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".trunkGW");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	}
	
	/**TrunkG/W 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getTrunkGWList")
	public @ResponseBody Map<String, Object> getTrunkGWList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getTrunkGWList(param);
		return map;
	}
	
	/** TrunkG/W 그룹 리스트 가져오기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getTrunkGWGroup")
	public @ResponseBody Map<String, Object> getTrunkGWGroup(@RequestBody Map<String, Object> param)throws Exception {
		List<Map<String,Object>> list = service.getGWGroupList(param);
		return ResultUtil.getResultMap(0 < list.size() ,list );
	}
	/** TrunkG/W 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setTrunkGW")
	public @ResponseBody Map<String, Object> setTrunkGW(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setTrunkGW(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** TrunkG/W 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delTrunkGW")
	public @ResponseBody Map<String, Object> delTrunkGW(@RequestBody Map<String, Object> param)throws Exception {
		return ResultUtil.getResultMap(0 < service.deleteTrunkGW(param));
	}
	
}
