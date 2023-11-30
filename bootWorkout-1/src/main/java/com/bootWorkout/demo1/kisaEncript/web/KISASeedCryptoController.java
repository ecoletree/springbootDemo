/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 11. 30.
 * File Name : KISASeedCryptoController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.kisaEncript.SeedCBCCryptoUtil;

@RestController
@RequestMapping("/seed")
public class KISASeedCryptoController {
	
	private static final String FILE_PATH = "C:/Users/User/OneDrive/바탕 화면/실습/";
	
	SeedCBCCryptoUtil cbc = new SeedCBCCryptoUtil();
	
	/** text cbc 암호화 {"encrypt" : "Y/N" , "text" : "sample" }
	 * @param param
	 * @return
	 */
	@RequestMapping("/text")
	public @ResponseBody Map<String, Object> textCrypto(@RequestBody Map<String,Object> param){
		Map<String, Object> resultMap = new HashMap<>();
		
		String origin = param.get("text").toString();
		String encrypt = param.get("encrypt").toString();
		
		
		String result = "";
		if(encrypt.equals("Y")) {
			result = cbc.encryptString(origin);
		}else {
			result = cbc.decryptString(origin);
		}
			
		resultMap.put("result", result);
		
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
		String fileName = param.get("text").toString();
		
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
		String outFileName = fm.format(new Date());
		
		String inputFilePath = FILE_PATH+fileName;
		String result = "fail";
		
		if(encryptYN.equals("Y")) {
			result = cbc.encryptFile(inputFilePath,FILE_PATH+outFileName+".aes");
		}else {
			result = cbc.decryptFile(inputFilePath,FILE_PATH+(fileName.replace(".aes", ""))+".txt");
		}
		
		
		resultMap.put("resultTxt", result);
		return resultMap;
	}
}
