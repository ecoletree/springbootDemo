/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 27.
 * File Name : MainController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.main.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bootWorkout.demo1.login.LoginValidation;
import com.bootWorkout.demo1.login.LoginValidation.LoginParam.LoginParamBuilder;
import com.bootWorkout.demo1.main.service.MainService;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import net.sf.json.JSONObject;

@Controller
public class MainController extends ETBaseController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	MainService service;
	
	@Autowired 
	LoginValidation loginValidation;
	
	private static int LOGIN_COUNT =0;
	
	public final class LOG_MESSAGE {
		public static final String delete_account = "1년이 지나 '계정 삭제";
		public static final String change_pw = "비밀번호 변경해야함";
		public static final String close_account = "90일 지남";
		public static final String initialized_pw = "최초 비번 변경";
		public static final String locked_account = "계정잠김";
		public static final String password_check_fail = "비밀번호 틀림";
//		public static final String LOGOUT = "로그아웃";
	}
	
	@RequestMapping("/")
	public ModelAndView main(final ModelAndView mav,Map<String, Object> params) {
		
		mav.setViewName("home");
		mav.addObject("initData", JSONObject.fromObject(
				MapBuilder.<String, Object>of(
						"groupList", service.getGroupList(params)
						))
				);
		return mav;
	}
	
	@RequestMapping("/select")
	public ModelAndView selectPage(final ModelAndView mav,Map<String, Object> params) {
		
		mav.setViewName("makeSelect");
		mav.addObject("initData", JSONObject.fromObject(
				MapBuilder.<String, Object>of(
						"groupList", service.getGroupList(params)
						))
				);
		return mav;
	}
	@RequestMapping("/datePickers")
	public ModelAndView datePickers(final ModelAndView mav,Map<String, Object> params) {
		
		mav.setViewName("datePickers");
		mav.addObject("initData", JSONObject.fromObject(
				MapBuilder.<String, Object>of(
						"groupList", service.getGroupList(params)
						))
				);
		return mav;
	}
	
	
	@RequestMapping("/loginChk")
	public @ResponseBody Map<String, Object> loginCheck(HttpServletRequest request, @RequestBody Map<String, Object> param) throws NoSuchAlgorithmException, UnsupportedEncodingException, ETException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Map<String, Object> map = new HashMap<String,Object>();
		String id = "kkh";
		String  db_pwd = "686948483256794c5a465a2b7539454f3075332f79555430735175774635516f536d7055317231326f53593d";
		/**
		 * 1. 아이디 유무 체크
		 * 2. 중복 로그인 체크
		 * 3. 로그인 실패 카운트 (5건) -> 타임 아웃 체크 (1분)  
		 * 4. 초기 비밀번호 체크 (같으면 메세지 ->js)
		 * 5. 마지막 로그인 날자 (1년/90일/90일비번변경없음) 
		 * 6. 비밀번호 체크
		 */
		if( 5 < LOGIN_COUNT) {
			LOGIN_COUNT = 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		logger.info("dateTime::::"+cal.getTime());
		LoginParamBuilder builder = LoginValidation.LoginParam.builder()
				.user_id(id)
				.user_pwd(param.get("pwd").toString())
				.db_pwd(db_pwd)
				.init_pwd("ccc12345!!!")
				.login_count(LOGIN_COUNT)
				.last_login_dttm(cal.getTime())
				.last_pw_change_dttm(cal.getTime())
				;
		
		String resultMsg = loginValidation.doValidation(builder);
		
		map.put("resultMsg", resultMsg);
		if(!resultMsg.equals(ETCommonConst.SUCCESS)) {
			LOGIN_COUNT++;
		}
		
		String logMsg = getLogMessage(resultMsg);
//		map.put("failCount", LOGIN_COUNT);
		map.put("logMessage", logMsg);
		return ResultUtil.getResultMap(true,map);
	}

	private String getLogMessage(String resultMsg) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		LOG_MESSAGE logMesssage = new LOG_MESSAGE();
		Field fd = logMesssage.getClass().getDeclaredField(resultMsg);
		return (String)fd.get(logMesssage);
	}
	
}
