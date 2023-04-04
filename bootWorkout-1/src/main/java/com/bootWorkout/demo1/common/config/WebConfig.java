/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 4.
 * File Name : WebConfig.java
 * DESC : CORS 도메인 설정
*****************************************************************/
package com.bootWorkout.demo1.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
	 @Override
    public void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/**")
		 .allowedOrigins("http://localhost:8080")
		 .allowedMethods("GET", "POST");
//		 .allowedOrigins("http://localhost:8080", "http://localhost:8081");
    }
}
