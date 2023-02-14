package com.example.demo.commons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.ecoletree.common.auth.ETLoginCheckInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
	
	Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		log.info("addInterceptors 시작");
		registry.addInterceptor(new ETLoginCheckInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/resources/**")
				.excludePathPatterns("/jslib/**");
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	log.info("addResourceHandlers 시작");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/")
                ;
        
        registry.addResourceHandler("/jslib/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false)
        ;
    }

}
