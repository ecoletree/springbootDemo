/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 29
 * File Name : MemberController.java
 * DESC : 
*****************************************************************/
package com.example.demo.login;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.TokenInfo;

import kr.co.ecoletree.common.util.ResultUtil;
import kr.co.ecoletree.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
 
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String memberId = params.get("email");
        String password = params.get("password");
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return ResultUtil.getResultMap(!StringUtil.isEmpty(tokenInfo), tokenInfo);
    }
    
    @PostMapping("/test")
    public String test() {
    	return "하하하";
    }
}
