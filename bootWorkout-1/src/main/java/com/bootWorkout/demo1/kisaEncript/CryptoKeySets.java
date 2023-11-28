/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 11. 28.
 * File Name : CryptoKeys.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class CryptoKeySets {

	public static String CBC_KEY;
	public static String CBC_INIT_VECTOR;
	public static String CBC_CRYPTO_TYPE;
	
	public CryptoKeySets(@Value("${cbc.key}") String cbcKey
			,@Value("${cbc.type}") String cbcType,@Value("${cbc.vector}") String cbcVecor) {
		CryptoKeySets.CBC_KEY = cbcKey;
		CryptoKeySets.CBC_CRYPTO_TYPE = cbcType;
		CryptoKeySets.CBC_INIT_VECTOR = cbcVecor;
	}
	
}
