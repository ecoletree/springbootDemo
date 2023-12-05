/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 12. 5.
 * File Name : SeedECBCryptoUtil.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.bootWorkout.demo1.common.util.KISA_SEED_ECB;
import com.bootWorkout.demo1.encrypt.CryptoKeySets;
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SeedECBCryptoUtil {
	private static String CHARSET = "utf-8";
	private static final String PBUserKey = CryptoKeySets.CRYPTO_KEY;
	private static byte pbUserKey[] = PBUserKey.getBytes(); // 16
	
	/** ECB 암호화 String
	 * @param origin
	 * @return
	 */
	public String encryptString(String origin) {
		String result = null;
		try {
			byte[] encrypted =  KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, origin.getBytes(CHARSET), 0, origin.getBytes(CHARSET).length);
			result = Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return result;
	}

	/** ECB 복호화 String
	 * @param origin
	 * @return
	 */
	public String decryptString(String encrypted) {
		
		String result = "";
		
		byte[] dec = Base64.getDecoder().decode(encrypted);
		
		try {
			byte[] pbData = KISA_SEED_ECB.SEED_ECB_Decrypt(pbUserKey, dec, 0, dec.length);
			result = new String(pbData,CHARSET);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return result;
	}
	/** 파일 암호화
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public String encryptFile(String inputFile, String outputFile) {
		return doCryptFile(inputFile,outputFile, true );
	}

	/** 파일 복호화
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public String decryptFile(String inputFile, String outputFile) {
		return doCryptFile(inputFile,outputFile, false );
	}
	/**
	 * @param inputFile
	 * @param outputFile
	 * @param b
	 * @return
	 */
	private String doCryptFile(String inputFile, String outputFile, boolean crypt) {
		String result = "fail";
		try {
			FileInputStream fis = new FileInputStream(inputFile);
			
			byte[] inputBytes = ByteStreams.toByteArray(fis);
			byte[] outputBytes = crypt ? KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, inputBytes, 0, inputBytes.length)
									: KISA_SEED_ECB.SEED_ECB_Decrypt(pbUserKey, inputBytes, 0, inputBytes.length);
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(outputBytes);
			
			fis.close();
			fos.close();
			result = "success";
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return result;
	}
}
