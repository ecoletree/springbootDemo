/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 29
 * File Name : MemberService.java
 * DESC : 
*****************************************************************/
package com.example.demo.login;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.jwt.TokenInfo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
 
    private final JwtTokenProvider jwtTokenProvider;
 
    @Transactional
    public TokenInfo login(String memberId, String password) {
    	TokenInfo tokenInfo = null; 
    	if (memberId.equals("test")) {
    		tokenInfo = jwtTokenProvider.generateToken(memberId);
    	}
        return tokenInfo;
    }
}

