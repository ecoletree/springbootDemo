package com.bootWorkout.demo1.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

import com.bootWorkout.demo1.jwt.JwtAuthFilter;
import com.bootWorkout.demo1.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * @author 김경현
 * @RequiredArgsConstructor : 필수 argument 생성자
 * 기존에는 web.xml에 필터들이 들어가 있어,, context-servlet.cml안에는 인터셉터  
 *  1.filter를 구현한 java class가 있어야해
 *  2.url패턴이 들어가야 됨 (어떤 url로 들어왔을때 저 필터를 돌겠다) 
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {
	
	/**
	 * @RequiredArgsConstructor 로 인해 생성자를 쓰지 않아도 자동 생성된다.
	 *  public JwtAuthentificationFilter(JwtTokenprovider provider){
	 *  		this.provider = provider;
	 *  } <- 이렇게 할 필요 없어짐
	 *  
	 */
	private final TokenProvider tokenProvider;
	
	/** FilterRegistrationBean 에 만든 필터를 등록한다. 
	 * 	접근 시에 필터 적용할 url 등록한다.
	 * @return
	 */
	public FilterRegistrationBean<JwtAuthFilter> filterChain(){
		FilterRegistrationBean<JwtAuthFilter>  bean = new FilterRegistrationBean<>();
		bean.setFilter(new JwtAuthFilter(tokenProvider));
		bean.addUrlPatterns("/*"); 
		return bean;
	}

}
