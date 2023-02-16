/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Jang Yoon Seok
 * Create Date : 2022. 4. 11.
 * File Name : LoginController.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.login.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.login.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController extends ETBaseController {

	@Autowired
	LoginService service;
	
	/**
	 * 로그인 화면 열기
	 * @return
	 */
	@RequestMapping(value= {"","/"})
	public ModelAndView openLoginMain() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		return mv;
	}
	
	/**
	 * 로그인 
	 * @param params
	 * @param request
	 * @return
	 * @throws ETException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/doLogin")
	public @ResponseBody Map<String, Object> login(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
		String cryptoStr = (String)param.get("cryptoInfo");
		String descriptStr = descriptAES(cryptoStr);
		Map<String, Object> descriptData =  new ObjectMapper().readValue(descriptStr, Map.class);
		
		Map<String, Object> map = service.login(descriptData, request);
		return ResultUtil.getResultMap(true, map, (String)map.get("type"));
	}
		
}

