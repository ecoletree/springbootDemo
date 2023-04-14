/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 14.
 * File Name : ThreadScheduler.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 스레드풀을 한번만 생성하도록 configuration에
 */
@Configuration
public class ThreadSchedulerConfig {

	/**
	 * ThreadPoolTaskScheduler 생성하는 메소드를 bean으로 등록하고
	 * 스케줄러 생성하는 곳에서 autowired 받는다
	 * @return
	 */
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		scheduler.setPoolSize(10);
		scheduler.setThreadNamePrefix("thead-scheduler-");
		return scheduler;
	}
}
