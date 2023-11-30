/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : kh201
 * Create Date : 2023. 11. 29.
 * File Name : ECBEncryptController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.encrypt.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecb")
public class ECBEncryptController {
	
	private static byte pbUserKey[] = "0123456789abcdef".getBytes(); // 16
	private static byte pbCipher[] = new byte[50];

	@RequestMapping("/text")
	public @ResponseBody Map<String, Object> textCrypto(@RequestBody Map<String,Object> param){
		Map<String, Object> resultMap = new HashMap<>();
		String result = "";
		
		String encryptYN =  param.get("encrypt").toString();
		String text = param.get("text").toString();
		
		
		
		
		resultMap.put("resultTxt", result);
		return resultMap;
	}
}

