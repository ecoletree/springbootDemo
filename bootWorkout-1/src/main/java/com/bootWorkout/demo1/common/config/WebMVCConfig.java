/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2023. 2. 6.
 * File Name : WebMvcConfig.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.ecoletree.common.auth.ETLoginCheckInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer{

	Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 인터셉터 설정
	 * interceptor는 이콜트리 공통라이브러리에서 가져옴
	 */
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		log.info("addInterceptors 시작");
		registry.addInterceptor(new ETLoginCheckInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/resources/**")
				.excludePathPatterns("/jslib/**");
	}

	/**
	 * js등 리소스 핸들러 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("addResourceHandlers 시작");
		registry.addResourceHandler("/resources/**")
        .addResourceLocations("classpath:/static/");

		registry.addResourceHandler("/jslib/**")
		.addResourceLocations("/webjars/")
		.resourceChain(false);
	}

    /**
     * CommonsMultipartResolver : 파일 업/다운로드 관련
     * MultipartFile클래스를 이용하여 업로드한 파일정보를 얻어온다.
     * JSP파일의 enctype을 multipart/form-data로 설정해야 한다.
     * commons-fileupload, commons-io jar 파일이 있어야 한다.
     * @return
     */
//    @Bean
//    public CommonsMultipartResolver multipartResolver(){
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//        commonsMultipartResolver.setDefaultEncoding("UTF-8");
//        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
//        return commonsMultipartResolver;
//    }

	/**
	 * cross origin 설정(필요nono)
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
			 registry.addMapping("/**")
					 .allowedOrigins("http://localhost:8080")
					 .allowedOrigins("http://localhost:8090")
					 .allowedMethods("GET", "POST")
//  		 			 .allowedOrigins("http://localhost:8080", "http://localhost:8081");
				 	;
	}


}