/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 11. 28.
 * File Name : CryptoKeys.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.encrypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class CryptoKeySets {

	public static String CRYPTO_KEY;
	public static String INIT_VECTOR;
	public static String CBC_MODE;
	public static String ECB_MODE;
	
	public CryptoKeySets(@Value("${crypto.key}") String cbcKey
			,@Value("${crypto.cbc-mode}") String cbcMode,@Value("${crypto.ecb-mode}") String ecbMode,@Value("${crypto.vector}") String cbcVecor) {
		CryptoKeySets.CRYPTO_KEY = cbcKey;
		CryptoKeySets.CBC_MODE = cbcMode;
		CryptoKeySets.ECB_MODE = ecbMode;
		CryptoKeySets.INIT_VECTOR = cbcVecor;
	}
	
}
