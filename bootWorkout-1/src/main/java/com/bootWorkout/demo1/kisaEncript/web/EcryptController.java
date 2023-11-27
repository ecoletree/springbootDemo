/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : kh201
 * Create Date : 2023. 11. 27.
 * File Name : EcriptController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript.web;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ecoletree.common.base.web.ETBaseController;

@Controller
@RequestMapping("/encrypt")
public class EcryptController extends ETBaseController {

	private static final String key = "aesEncryptionKey"; //16Byte == 128bit
	private static final String initVector = "encryptionIntVec"; //16Byte
	
	//인코더 생성
	private static final Base64.Encoder enc = Base64.getEncoder(); 
	//디코더 생성
	private static final Base64.Decoder dec = Base64.getDecoder();
	
	@RequestMapping("/CBC")
	public @ResponseBody Map<String, Object> getDataTest(@RequestBody Map<String,Object> param){
		System.out.println("test");
		Map<String, Object> resultMap = new HashMap<>();
		String origin = param.get("text").toString();
		String type = param.get("type").toString();
		
		String result = "";
		
		if(type.equals("cbc_encrypt")) {
			result = encryptCBC(origin);
		}else {
			result = decryptCBC(origin);
		}
		logInfo("encript_result:::"+result);
		resultMap.put("resultTxt", result);
		return resultMap;
	}
	
	public String encryptCBC(String origin) {
		try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8")); // 초기화백터 byte로 변경
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES"); // byte로 변경
	 
	        //cipher를 만들
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); //AES, CBC모드, partial block 채우기
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); // mode 
	 
	        //실제로 암호화 하는 부분
	        byte[] encrypted = cipher.doFinal(origin.getBytes()); 
	        return enc.encodeToString(encrypted); //암호문을 base64로 인코딩하여 출력 해줌
	        
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
		return null;
	}
	
	public String decryptCBC(String encrypted) {
		try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] original = cipher.doFinal(dec.decode(encrypted)); //base64 to byte 

	        return new String(original);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
		
	}
}

