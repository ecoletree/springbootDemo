/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 17.
 * File Name : TilesConfig.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
public class TilesConfig {

	/**
	 * UrlBasedViewResolver : ViewResolver의 구현체로 특별한 맵핑 정보 없이 
	 * view 이름을 URL로 사용.
	 * View 이름과 실제 view 자원과의 이름이 같을 때 사용할 수 있음.
	 * setOrder : 뷰 우선순위
	 * @return
	 */
	@Bean
    public UrlBasedViewResolver viewResolver() {
    	UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
    	tilesViewResolver.setViewClass(TilesView.class);
    	tilesViewResolver.setOrder(1);
    	return tilesViewResolver;
    }
	
	/**
	 * tiles configurer
	 * setDefinitions : tiles-defs.xml 경로설정
	 * setCheckRefresh: refresh 가능 여부 설정 (true 가능)
	 * @return
	 */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer configurer = new TilesConfigurer();

        configurer.setDefinitions(new String[]{"/WEB-INF/tiles/tiles-defs.xml"});
        configurer.setCheckRefresh(true);
        return configurer;
    }
}
