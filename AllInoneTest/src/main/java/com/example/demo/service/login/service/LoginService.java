/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Jang Yoon Seok
 * Create Date : 2022. 4. 11.
 * File Name : LoginService.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.login.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.ecoletree.common.exception.ETException;

public interface LoginService {

	public Map<String, Object> login(Map<String, Object> param, HttpServletRequest request) throws ETException, Exception;
}

