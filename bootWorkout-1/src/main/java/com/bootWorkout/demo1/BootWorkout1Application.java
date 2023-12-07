package com.bootWorkout.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.bootWorkout.demo1.polling.FilePollingUtil;

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
		// 폴더 변경 감지 테스트
//		FilePollingUtil poll = new FilePollingUtil();
//		poll.watchFiles("C:/Users/User/OneDrive/바탕 화면/실습/");
		SpringApplication.run(BootWorkout1Application.class, args);
	}

}

