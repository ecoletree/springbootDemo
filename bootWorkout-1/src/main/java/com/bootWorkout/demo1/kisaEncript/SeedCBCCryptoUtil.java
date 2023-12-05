/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 11. 30.
 * File Name : SeedCryptoUtil.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.bootWorkout.demo1.common.util.KISA_SEED_CBC;
import com.bootWorkout.demo1.encrypt.CryptoKeySets;
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SeedCBCCryptoUtil {

	private static String CHARSET = "utf-8";
	private static final String PBUserKey = CryptoKeySets.CRYPTO_KEY;
	private static final String DEFAULT_IV = CryptoKeySets.INIT_VECTOR;
	
	private static byte pbszUserKey[] = PBUserKey.getBytes();
	private static byte bsxIV[] = DEFAULT_IV.getBytes();
	
	/** CBC 암호화 
	 * Initial Vector를 암호문 대신 사용
	 * String to String
	 * @param origin
	 * @return
	 */
	public String encryptString(String origin) {
		String result = null;

		byte[] enc = null;
		
		try {
			enc = KISA_SEED_CBC.SEED_CBC_Encrypt(pbszUserKey, bsxIV, origin.getBytes(CHARSET), 0, origin.getBytes(CHARSET).length);
			result = Base64.getEncoder().encodeToString(enc);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return result; 
	}
	
	
	/** CBC 복호화 
	 * String to String
	 * @param origin
	 * @return
	 */
	public String decryptString(String origin) {
		String result = "";
		
		byte[] enc = Base64.getDecoder().decode(origin);
		byte[] dec= null;
		
		try {
			dec = KISA_SEED_CBC.SEED_CBC_Decrypt(pbszUserKey, bsxIV, enc, 0, enc.length);
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
			
			byte[] outputBytes = crypt ? KISA_SEED_CBC.SEED_CBC_Encrypt(pbszUserKey, bsxIV, inputBytes, 0, inputBytes.length) 
											: KISA_SEED_CBC.SEED_CBC_Decrypt(pbszUserKey, bsxIV, inputBytes, 0, inputBytes.length);
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
