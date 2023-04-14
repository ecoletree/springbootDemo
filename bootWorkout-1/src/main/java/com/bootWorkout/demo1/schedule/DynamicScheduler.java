/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 13.
 * File Name : ScheduleConfig.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class DynamicScheduler {

//	private Logger logger = LoggerFactory.getLogger(getClass());
	private ThreadPoolTaskScheduler scheduler;
	
	public void startScheduler(String exp) {
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		scheduler.setPoolSize(10);
		scheduler.schedule(getRunnable(), getTrigger(exp));
	}
	
	private Runnable getRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				System.out.println(new Date());
				// TODO Auto-generated method stub
			}
		};
	}
	
	private Trigger getTrigger(String exp) {
		return new CronTrigger(exp);
	}
	
	public void stopScheduler() {
		if(scheduler != null) {
			scheduler.shutdown();
			System.out.println("schedule Stop!!!");
		}
	}
}
