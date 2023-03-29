/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 28
 * File Name : JwtAuthenticationFilter.java
 * DESC : 
*****************************************************************/
package com.example.demo.commons.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider provider;
	
	
	public JwtAuthenticationFilter(JwtTokenProvider provider) {
		this.provider = provider;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = resolveToken((HttpServletRequest) request);
		
		// 토큰 유효성 검사
		if (token != null && provider.validateToken(token)) {
			Authentication authentication = provider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		chain.doFilter(request, response);
	}
	
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}

