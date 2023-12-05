/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 12. 5.
 * File Name : SeedECBCryptoUtil.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.bootWorkout.demo1.common.util.KISA_SEED_ECB;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SeedECBCryptoUtil {
	private static String CHARSET = "utf-8";
	private static byte pbUserKey[] = "0123456789abcdef".getBytes(); // 16
	private static byte pbCipher[] = new byte[50];
	
	public String encryptString(String origin) {
		String result = null;
		try {
			byte[] textBytes = origin.getBytes(CHARSET);
			byte[] pbData = new byte[pbUserKey.length];
			for (int i = 0; i < textBytes.length; i++) {
				if(i< textBytes.length) {
					pbData[i] = textBytes[i];
				}else {
					pbData[i] = 0x00;
				}
			}
			pbCipher = KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, pbData, 0, pbData.length);
//			Arrays.copyOf(pbCipher, pbUserKey.length);
			result = Base64.getEncoder().encodeToString(pbCipher);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		
		
		return result;
	}

	/**
	 * @param origin
	 * @return
	 */
	public String decryptString(String encrypted) {
		
		String result = "";
		
		byte[] enc = Base64.getDecoder().decode(encrypted);
		
		try {
			byte[] pbData = KISA_SEED_ECB.SEED_ECB_Decrypt(pbUserKey, enc, 0, enc.length);
			result = new String(pbData,CHARSET);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return result;
	}
}
