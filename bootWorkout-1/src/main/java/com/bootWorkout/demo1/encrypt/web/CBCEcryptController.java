/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : kh201
 * Create Date : 2023. 11. 27.
 * File Name : EcriptController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.encrypt.web;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.encrypt.CryptoUtil;

import kr.co.ecoletree.common.base.web.ETBaseController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cbc")
public class CBCEcryptController {

	private static final String FILE_PATH = "C:/Users/User/OneDrive/바탕 화면/실습/";
	
	CryptoUtil cbcUtil = new CryptoUtil();
	
	/** text cbc 암호화 {"encrypt" : "Y/N" , "text" : "sample" }
	 * @param param
	 * @return
	 */
	@RequestMapping("/text")
	public @ResponseBody Map<String, Object> textCrypto(@RequestBody Map<String,Object> param){
		Map<String, Object> resultMap = new HashMap<>();
		
		String encryptYN =  param.get("encrypt").toString();
		String text = param.get("text").toString();
		cbcUtil.CBC();
		String result = encryptYN.equals("Y") 
				? cbcUtil.encryptString(text)
				: cbcUtil.decryptString(text);
		
		log.info("encript_result:::"+result);
		
		resultMap.put("resultTxt", result);
		return resultMap;
	}
	
	/** file cbc 암호화
	 * @param param {"encrypt" : "Y/N" , "file" : "sampleFileName.txt" }
	 * @return
	 */
	@RequestMapping("/file")
	public @ResponseBody Map<String, Object> fileCrypto(@RequestBody Map<String,Object> param){
		Map<String, Object> resultMap = new HashMap<>();
		
		String encryptYN =  param.get("encrypt").toString();
		String fileName = param.get("file").toString();
		
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
		String outFileName = fm.format(new Date());
		
		String inputFilePath = FILE_PATH+fileName;
		String result = "fail";
		
		if(encryptYN.equals("Y")) {
			result = cbcUtil.fileEncrypt(inputFilePath,FILE_PATH+outFileName+".aes");
		}else {
			result = cbcUtil.fileDecrypt(inputFilePath,FILE_PATH+(fileName.replace(".aes", ""))+".txt");
		}
		
		log.info("encript_result:::"+result);
		
		resultMap.put("resultTxt", result);
		return resultMap;
	}
	
	
	
}

