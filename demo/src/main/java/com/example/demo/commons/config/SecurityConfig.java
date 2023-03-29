/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 28
 * File Name : SecurityConfig.java
 * DESC : 
*****************************************************************/
package com.example.demo.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.commons.jwt.JwtAuthenticationFilter;
import com.example.demo.commons.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtTokenProvider provider;
	
	public SecurityConfig(JwtTokenProvider provider) {
		this.provider = provider;
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/api/user").permitAll()
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(provider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}

