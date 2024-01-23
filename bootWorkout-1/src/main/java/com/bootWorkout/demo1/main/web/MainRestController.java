/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 1. 18.
 * File Name : MainRestController.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.main.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.excel.ExcelUtil;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;

@RestController
@RequestMapping("/test")
public class MainRestController extends ETBaseController{


	@RequestMapping("/excel")
	public Map<String, Object> writeExecel(@RequestBody Map<String, Object> param) {

		List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("list");
		ExcelUtil.WriteCsv(list,"C:/Users/User/OneDrive/바탕 화면/csvtest/sample2.csv");

		return ResultUtil.getResultMap(true);
	}

}
