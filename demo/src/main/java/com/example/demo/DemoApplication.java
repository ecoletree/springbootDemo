package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import kr.co.ecoletree.common.provider.JwtTokenProvider;

/*
 * bean 으로 등록될 component를 스캔해준다 
 * <context:component-scan base-package="kr.co.ecoletree" />
 * basePackages = {"base 패키지","추가할 패키지"}
 * basePackages 는 String[] 형태로 추가
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo","kr.co.ecoletree"})
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
