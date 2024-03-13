/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 13.
 * File Name : NotificationController.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sse.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootWorkout.demo1.sse.service.NotificationService;

import kr.co.ecoletree.common.util.ResultUtil;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sseManual")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@RequestMapping("/remove")
	public @ResponseBody Map<String,Object> sendData(@RequestBody Map<String, Object> param) {
		String sseId = (String)param.get("id");
		notificationService.remove(sseId);
		return ResultUtil.getResultMap(true);
	}



}
