/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : AnidialGWController.java
 * DESC : AnidialG/W
*****************************************************************/
package com.example.demo.service.anidialGW.web;

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

import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.anidialGW.service.AnidialGWService;

@Controller
@RequestMapping("/gateway/anidialGW")
public class AnidialGWController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	AnidialGWService service;
	
	/**
	 * AnidialG/W로 이동
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(final ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".anidialGW");
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("start", "0");
//		params.put("length", "9999999999");
//		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
//		MapBuilder.<String, Object>of(
//				"tenantList", service.getCustMgtList(params)
//				))
//		);
		return mav;
	}
	
	/**AnidialG/W리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getAnidialGWList")
	public @ResponseBody Map<String, Object> getAnidialGWList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = getParamToMap(request);
		Map<String, Object> map = service.getAnidialGWList(param);
		return map;
	}
	/** AnidialG/W 그룹 리스트 가져오기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getGWGroup")
	public @ResponseBody Map<String, Object> getGWGroup(@RequestBody Map<String, Object> param)throws Exception {
		List<Map<String,Object>> list = service.getGWGroupList(param);
		return ResultUtil.getResultMap(0 < list.size() ,list );
	}
	/** AnidialG/W 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setAnidialGW")
	public @ResponseBody Map<String, Object> setAnidialGW(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setAnidialGW(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** AnidialG/W 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delAnidialGW")
	public @ResponseBody Map<String, Object> delAnidialGW(@RequestBody Map<String, Object> param)throws Exception {
		return ResultUtil.getResultMap(0 < service.delAnidialGW(param));
	}
}
