/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2022. 11. 9
 * File Name : FileCryptoUtil.java
 * DESC : 
*****************************************************************/
package com.example.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class FileCryptoUtil {

	static Logger logger = LoggerFactory.getLogger(FileCryptoUtil.class);

//	private static String runName = "E:\\opt\\OpenSSL\\bin\\openssl.exe";
	private static String runName = "openssl";
	private static String cryptoKey = "hsitx1234!";
	/**
	 * 파일을 암호화 한다
	 * @param inFilePath : 암호화 할 파일 경로
	 * @param outFilePath : 암호화 한 파일 생성 경로
	 * @return true : 정상, false : 에러
	 */
	public static boolean encrypto(String inFilePath, String outFilePath) {
		boolean b = false;
		Process p = null;
		String s = null;
		try {
			String[] cmd = {runName,"enc","-aes-256-cbc","-md","md5","-in",inFilePath,"-out",outFilePath,"-k",cryptoKey};
			String log = Arrays.toString(cmd);
			logger.info(log.replaceAll(",", " "));
			p = new ProcessBuilder(cmd).start();
			BufferedReader ebr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = ebr.readLine()) != null) {
            	logger.debug(s);
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
            	logger.info(s);
            }
            
            p.waitFor();
            b = p.exitValue() == 0 ? true : false; 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			if (p != null)
	        	p.destroy();
		}
		return b;
	}
	
	/**
	 * 파일을 복호화 한다
	 * @param inFilePath : 복호화 할 파일 경로
	 * @param outFilePath : 복호화 한 파일 생성 경로
	 * @return true : 정상, false : 에러
	 */
	public static boolean decrypto(String inFilePath, String outFilePath) {
		boolean b = false;
		Process p = null;
		String s = null;
		try {
			String[] cmd = {runName,"enc","-d","-aes-256-cbc","-md","md5","-in",inFilePath,"-out",outFilePath,"-k",cryptoKey};
			
			String log = Arrays.toString(cmd);
			p = new ProcessBuilder(cmd).start();
			
			BufferedReader ebr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = ebr.readLine()) != null) {
            	logger.debug(s);
            }
	            
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
            	logger.debug(s);
            }
            	
            p.waitFor();
            b = p.exitValue() == 0 ? true : false;
            
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
			logger.error("ERROR : "+e.getMessage(), e);
		} finally {
			if (p != null)
	        	p.destroy();
		}
		
		return b;
	}
}

