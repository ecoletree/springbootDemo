/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 12. 14.
 * File Name : CryptoCommon.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.cryptoFinal;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 사용예
 * CryptoType typeSet = CryptoType.builder()
				.aes_type(param.get("aes_type").toString())
				.is_kisa(param.get("is_kisa").toString())
				.build();
 * String Crypted = CryptoCommon.setCryptoType(CryptoType).encryptString("StringToDoCypt");
 * String result = CryptoCommon.setCryptoType(CryptoType).encryptFile("inputPath","outputPath"); //-> 확장자 포함
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CryptoCommon {
	
	public static String CHARSET = "utf-8";
	public static String CBC = "CBC";
	public static String ECB = "ECB";
	
	private final static String INIT_KEY = "aesCryptoKeyTest";
	private final static String INIT_VECTOR = "encryptionIntVec";
	
	public static byte pbszUserKey[] = INIT_KEY.getBytes();
	public static byte bsxIV[] = INIT_VECTOR.getBytes();
	
	
	/**
	 * is_kisa : KISA/AES 구분 (Y/N)
	 * aes_type : CBC/ECB 구분
	 */
	@Builder
	public static class CryptoType{
		private final String is_kisa;
		private final String aes_type;
	}
	
	/** 암복호화 타입 지정
	 * @param CryptoType cryptoType
	 * @return
	 */
	public static CryptoCommon setCryptoType(CryptoType cryptoType) {
		
		CryptoCommon cc = null;
		
		switch(cryptoType.is_kisa) {
			case "N" :
				cc = new AesCryptoUtil(cryptoType.aes_type);
				break;
			default :
				cc = new SeedCryptoUtil(cryptoType.aes_type);
				break;
		}
		
		return cc;

	}
	
	
	/** String 암호화
	 * @param origin
	 * @return
	 */
	public String encryptString(String origin) {
		return null;
	}
	
	/** String 복호화
	 * @param encrypted
	 * @return
	 */
	public String decryptString(String encrypted) {
		return null;
	}
	
	/** File 암호화
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public String encryptFile(String inputFile, String outputFile) {
		return null;
	}
	
	/** File 복호화
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public String decryptFile(String inputFile, String outputFile) {
		return null;
	}
}
