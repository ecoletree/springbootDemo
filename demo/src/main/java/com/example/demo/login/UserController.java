/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 28
 * File Name : UserController.java
 * DESC : 
*****************************************************************/
package com.example.demo.login;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.commons.jwt.JwtToken;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtToken> login (@RequestBody Map<String, String> params){
		JwtToken token = service.login(params.get("email"), params.get("password"));
		return ResponseEntity.ok(token);
	}
}

