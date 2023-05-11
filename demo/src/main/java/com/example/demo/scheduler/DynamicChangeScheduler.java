/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 4. 13
 * File Name : DynamicChangeScheduler.java
 * DESC : 
*****************************************************************/
package com.example.demo.scheduler;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class DynamicChangeScheduler {
	private ThreadPoolTaskScheduler scheduler;
	private String cron = "*/2 * * * * *";

	public void startScheduler() {
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		// scheduler setting 
		scheduler.schedule(getRunnable(), getTrigger());
	}

	public void changeCronSet(String cron) {
		this.cron = cron;
	}

	public void stopScheduler() {
		scheduler.shutdown();
	}

	private Runnable getRunnable() {
		// do something
		return () -> {
			System.out.println(LocalDateTime.now().toString());
		};
	}

	private Trigger getTrigger() {
		// cronSetting
		return new CronTrigger(cron);
	}

	@PostConstruct
	public void init() {
//		startScheduler();
	}

	@PreDestroy
	public void destroy() {
//		stopScheduler();
	}
}