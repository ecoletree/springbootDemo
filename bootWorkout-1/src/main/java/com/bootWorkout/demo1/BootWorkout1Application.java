package com.bootWorkout.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan (base-package = {"com.bootWorkout.demo1","kr.co.ecoletree"})
// 베이스 패키지와, 라이브러리 패키지
@ComponentScan
public class BootWorkout1Application {

	public static void main(String[] args) {
		SpringApplication.run(BootWorkout1Application.class, args);
	}

}
