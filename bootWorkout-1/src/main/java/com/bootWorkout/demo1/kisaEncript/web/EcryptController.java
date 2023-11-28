/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : kh201
 * Create Date : 2023. 11. 27.
 * File Name : EcriptController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript.web;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.kisaEncript.CBCCryptoUtil;

import kr.co.ecoletree.common.base.web.ETBaseController;

@RestController
@RequestMapping("/encrypt")
public class EcryptController extends ETBaseController {

	private static final String FILE_PATH = "C:/Users/User/OneDrive/바탕 화면/실습/";
	
	CBCCryptoUtil cbcUtil = new CBCCryptoUtil();
	
	@RequestMapping("/CBC")
	public @ResponseBody Map<String, Object> getDataTest(@RequestBody Map<String,Object> param){
		System.out.println("test");
		Map<String, Object> resultMap = new HashMap<>();
		String origin = param.get("text").toString();
		String crypto_type = param.get("crypto_type").toString();
		String type = param.get("type").toString();
		/*
		 * type: 암호화 대상 file 인지 text 인지 구분
		 * crypto_type : 암/복호화 구분
		 * text: text 일때 암복호화 대상
		 *  파일명 지정방식 바꾸기
		 *		
		 */
		String result = "";
		
		if(crypto_type.equals("cbc_encrypt")) {
			Date now = new Date();
			SimpleDateFormat fm = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
			String outFileName = fm.format(now);
			result = type.equals("file") 
					? cbcUtil.fileEncrypt(FILE_PATH+origin,FILE_PATH+outFileName+".aes")
					: cbcUtil.encryptCBCString(origin);
		}else {
			if(type.equals("file") ) {
				result = cbcUtil.fileDecrypt(FILE_PATH+origin,FILE_PATH+(origin.replace(".aes", ""))+".txt");
			}else {
				result = cbcUtil.decryptCBCString(origin);
			}
		}
		logInfo("encript_result:::"+result);
		resultMap.put("resultTxt", result);
		return resultMap;
	}
	
	
}

