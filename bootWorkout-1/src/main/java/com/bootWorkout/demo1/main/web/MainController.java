/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 27.
 * File Name : MainController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.main.web;

import java.util.Map;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bootWorkout.demo1.main.service.MainService;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.MapBuilder;

@Controller
public class MainController extends ETBaseController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	MainService service;
	
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
	
	
	
}
