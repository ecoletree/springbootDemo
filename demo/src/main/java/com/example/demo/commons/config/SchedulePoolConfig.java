/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 4. 14
 * File Name : SchedulePoolConfig.java
 * DESC : 
*****************************************************************/
package com.example.demo.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulePoolConfig {

	private final int POOL_SIZE = 5;
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		scheduler.setPoolSize(POOL_SIZE);
		scheduler.setThreadNamePrefix("thread-pool-");
		
		return scheduler; 
		// scheduler setting 
	}
	
}

