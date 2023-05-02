package com.bootWorkout.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
 * bean 으로 등록될 component를 스캔해준다 
 * <context:component-scan base-package="kr.co.ecoletree" />
 * basePackages = {"base 패키지","추가할 패키지"}
 * basePackages 는 String[] 형태로 추가
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.bootWorkout.demo1","kr.co.ecoletree"})
public class BootWorkout1Application {

	public static void main(String[] args) {
		SpringApplication.run(BootWorkout1Application.class, args);
	}

}

