/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 28
 * File Name : JwtToken.java
 * DESC : 
*****************************************************************/
package com.example.demo.commons.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {

	private String grantType;
	private String accessToken;
	private String refreshToken;
}

