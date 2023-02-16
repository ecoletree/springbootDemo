/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석 
 * Create Date : 2022. 10. 13.
 * DESC : 시스템 관리자 관리
*****************************************************************/
package com.example.demo.service.systemadmin.web;

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
import com.example.demo.service.systemadmin.service.SystemAdminService;

@Controller
@RequestMapping("/settings/systemadmin")
public class SystemAdminController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	SystemAdminService service;
	
	/** 시스템 관리자 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".systemAdminMgt");
		return mav;
	} 
	
	/** 시스템 관리자 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getSystemAdminList")
	public @ResponseBody Map<String, Object> getSystemAdminList(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParamToMap(request);
		return service.getSystemAdminList(params);
	}
	
	/** 시스템 관리자 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setSystemAdmin")
	public @ResponseBody Map<String, Object> setSystemAdmin(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setSystemAdmin(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 시스템 관리자 비밀번호 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setPWSystemAdminChange")
	public @ResponseBody Map<String, Object> setPWSystemAdminChange(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setPWSystemAdminChange(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 시스템 관리자 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delSystemAdmin")
	public @ResponseBody Map<String, Object> delSystemAdmin(@RequestBody Map<String, Object> param)throws Exception {
		return ResultUtil.getResultMap(0 < service.deleteSystemAdmin(param));
	}
	
}
