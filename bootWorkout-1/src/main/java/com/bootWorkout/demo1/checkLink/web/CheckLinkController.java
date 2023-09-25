/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 9. 19.
 * File Name : CheckLinkController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.checkLink.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootWorkout.demo1.checkLink.service.CheckLinkService;

import kr.co.ecoletree.common.base.web.ETBaseController;

@Controller
public class CheckLinkController extends ETBaseController{

	@Autowired
	CheckLinkService service;
	
	@RequestMapping("/checkLink")
	public @ResponseBody Map<String, Object> checkLink(@RequestBody Map<String,Object> param) throws IOException{
		Map<String, Object> resultMap = new HashMap<>();
		logInfo("check broken link");
		service.checkBrokenLink(param);
		return resultMap;
	}
}
