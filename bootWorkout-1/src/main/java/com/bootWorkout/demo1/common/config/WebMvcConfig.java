///*****************************************************************
// * Copyright (c) 2017 EcoleTree. All Rights Reserved.
// * 
// * Author : Kim Kyung Hyun 
// * Create Date : 2023. 2. 6.
// * File Name : WebMvcConfig.java
// * DESC : 
//*****************************************************************/
//package com.bootWorkout.demo1.common.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer{
//
//	@Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/*")
//                .excludePathPatterns("/login","/error"); // 해당 경로는v 인터셉터가 가로채지 않는다.
//    }
//}