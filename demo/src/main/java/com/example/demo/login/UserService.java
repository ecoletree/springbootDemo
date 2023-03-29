/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 28
 * File Name : UserService.java
 * DESC : 
*****************************************************************/
package com.example.demo.login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.commons.jwt.JwtToken;
import com.example.demo.commons.jwt.JwtTokenProvider;

@Service
public class UserService {

	private final BCryptPasswordEncoder encoder;
	private final AuthenticationManagerBuilder authBuilder;
	private final JwtTokenProvider provider;
	
	public UserService(BCryptPasswordEncoder encoder, AuthenticationManagerBuilder authBuilder, JwtTokenProvider provider) {
		this.encoder = encoder;
		this.authBuilder = authBuilder;
		this.provider = provider;
	}
	
	public JwtToken login (String email, String password) {
		// Authentication 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		Authentication authentication = authBuilder.getObject().authenticate(authenticationToken);
		
		// 검증된 인증 정보로 JWT 토큰 생성
		JwtToken token = provider.generateToken(authentication);
		
		return token;
	}
	
}

