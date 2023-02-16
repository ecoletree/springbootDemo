/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.systemadmin.web;

import java.util.List;
import java.util.Map;

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
import com.example.demo.service.systemadmin.service.SystemAuthorityService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/settings/systemauth")
public class SystemAuthorityController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	SystemAuthorityService service;
	
	/** 권한 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".systemAuthority");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"userTypeList", service.getUserTypeList()
				))
		);
		return mav;
	}
	
	/** 권한 리스트 가져오기
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("/getAuthList")
	public @ResponseBody Map<String, Object> getAuthList(@RequestBody Map<String, Object> params) {
		List<Map<String,Object>> list = service.getAuthList(params);
		return ResultUtil.getResultMap(true,list);
	}
	
	/** 권한 리스트 저장하기
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("/setAuthList")
	public @ResponseBody Map<String, Object> setAuthList(@RequestBody Map<String, Object> params) throws Exception{
		return ResultUtil.getResultMap(0 < service.setAuthList(params));
	} 
	
}
