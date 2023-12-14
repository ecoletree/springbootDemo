/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 12. 14.
 * File Name : SeedCryptoUtil.java
 * DESC : SeedCBCCryptoUtil + SeedECBCryptoUtil 합친 유틸
*****************************************************************/
package com.bootWorkout.demo1.cryptoFinal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.bootWorkout.demo1.common.util.KISA_SEED_CBC;
import com.bootWorkout.demo1.common.util.KISA_SEED_ECB;
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeedCryptoUtil extends CryptoCommon {
	
	private final String AES_TYPE;
	
	/**
	 * @param aes_type
	 */
	public SeedCryptoUtil(String aes_type) {
		this.AES_TYPE = aes_type;
	}
	
	/**
	 * String 암호화
	 */
	public String encryptString(String origin) {
		log.info("kisa_encrypt:"+AES_TYPE);
		String result = null;

		byte[] enc = null;
		
		try {
			if(AES_TYPE.equals(CBC)) {
				enc = KISA_SEED_CBC.SEED_CBC_Encrypt(pbszUserKey, bsxIV, origin.getBytes(CHARSET), 0, origin.getBytes(CHARSET).length);
			}else {
				enc = KISA_SEED_ECB.SEED_ECB_Encrypt(pbszUserKey, origin.getBytes(CHARSET), 0, origin.getBytes(CHARSET).length);
			}
			result = Base64.getEncoder().encodeToString(enc);
		} catch (Exception e) {
			log.info(e.getMessage());
		}		
		
		return result;
	}
	
	/**
	 * String 복호화
	 */
	public String decryptString(String encrypted) {
		String result = "";
		
		byte[] enc = Base64.getDecoder().decode(encrypted);
		byte[] dec = null;
		try {
			if(AES_TYPE.equals(CBC)) {
				dec = KISA_SEED_CBC.SEED_CBC_Decrypt(pbszUserKey, bsxIV, enc, 0, enc.length);
			}else {
				dec = KISA_SEED_ECB.SEED_ECB_Decrypt(pbszUserKey, dec, 0, dec.length);
			}
			result = new String(dec,CHARSET);
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
	
	/** 파일 암/복호화
	 * @param inputFile
	 * @param outputFile
	 * @param crypt
	 * @return
	 */
	public String doCryptFile(String inputFile, String outputFile, boolean crypt ) {
		String result = "fail";
		
		try {	
			
			FileInputStream fis = new FileInputStream(inputFile);
			
			byte[] inputBytes = ByteStreams.toByteArray(fis);
			byte[] outputBytes = null;
			if(AES_TYPE.equals(CBC)) {
				outputBytes = crypt ? KISA_SEED_CBC.SEED_CBC_Encrypt(pbszUserKey, bsxIV, inputBytes, 0, inputBytes.length) 
						: KISA_SEED_CBC.SEED_CBC_Decrypt(pbszUserKey, bsxIV, inputBytes, 0, inputBytes.length);
			}else {
				outputBytes = crypt ? KISA_SEED_ECB.SEED_ECB_Encrypt(pbszUserKey, inputBytes, 0, inputBytes.length)
						: KISA_SEED_ECB.SEED_ECB_Decrypt(pbszUserKey, inputBytes, 0, inputBytes.length);
			}
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
