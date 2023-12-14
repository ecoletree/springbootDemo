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

import com.bootWorkout.demo1.cryptoFinal.CryptoCommon;
import com.bootWorkout.demo1.cryptoFinal.CryptoCommon.CryptoType;
import com.bootWorkout.demo1.kisaEncript.SeedCBCCryptoUtil;
import com.bootWorkout.demo1.kisaEncript.SeedECBCryptoUtil;

import kr.co.ecoletree.common.util.MapBuilder;

@RestController
@RequestMapping("/seed")
public class KISASeedCryptoRestController {

	@RequestMapping("/test") // --> cryptoFinal 패키지
	public @ResponseBody Map<String,Object> cryptoTest(@RequestBody Map<String,Object> param){
		
		CryptoType typeSet = CryptoType.builder()
				.aes_type(param.get("aes_type").toString()) //CBC/ECB
				.is_kisa(param.get("is_kisa").toString()) //Y/N
				.build();
		String result = CryptoCommon.setCryptoType(typeSet).decryptString(param.get("origin").toString());
		
		return MapBuilder.of("result",result);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String FILE_PATH = "C:/Users/User/OneDrive/바탕 화면/실습/";
	
	SeedCBCCryptoUtil cbc = new SeedCBCCryptoUtil();
	SeedECBCryptoUtil ecb = new SeedECBCryptoUtil(); 
	
	
	/** text cbc 암호화 {"encrypt" : "Y/N" , "text" : "sample" }
	 * @param param
	 * @return
	 */
	@RequestMapping("/text")
	public @ResponseBody Map<String, Object> textCrypto(@RequestBody Map<String,Object> param){
		Map<String, Object> resultMap = new HashMap<>();
		
		
		
		String origin = param.get("text").toString();
		String encrypt = param.get("encrypt").toString();
		String type = param.get("type").toString();
		
		
		String result = "";
		if(type.equals("CBC")) {
			if(encrypt.equals("Y")) {
				result = cbc.encryptString(origin);
			}else {
				result = cbc.decryptString(origin);
			}
		}else {
			if(encrypt.equals("Y")) {
				result = ecb.encryptString(origin);
			}else {
				result = ecb.decryptString(origin);
			}
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
		String type = param.get("type").toString();
		
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
		String outFileName = fm.format(new Date());
		
		String inputFilePath = FILE_PATH+fileName;
		String result = "fail";
		if(type.equals("CBC")) {
			if(encryptYN.equals("Y")) {
				result = cbc.encryptFile(inputFilePath,FILE_PATH+outFileName+".aes");
			}else {
				result = cbc.decryptFile(inputFilePath,FILE_PATH+(fileName.replace(".aes", ""))+".txt");
			}
		}else {
			if(encryptYN.equals("Y")) {
				result = ecb.encryptFile(inputFilePath,FILE_PATH+outFileName+".aes");
			}else {
				result = ecb.decryptFile(inputFilePath,FILE_PATH+(fileName.replace(".aes", ""))+".txt");
			}
		}
		
		
		resultMap.put("resultTxt", result);
		return resultMap;
	}
}
