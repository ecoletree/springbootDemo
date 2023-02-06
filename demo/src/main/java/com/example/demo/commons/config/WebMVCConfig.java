package com.example.demo.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.commons.interceptor.LoggerInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/resources/**")
				.excludePathPatterns("/jslib/**");
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/")
                ;
        
        registry.addResourceHandler("/jslib/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false)
        ;
    }

}
