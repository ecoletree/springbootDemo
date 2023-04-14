/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 13.
 * File Name : MuliDynamicScheduler.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class MultiDynamicScheduler {

	private static Map<String, ThreadPoolTaskScheduler> schedulerMap = new HashMap<>();
	
	public void startScheduler(String name,String exp) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		scheduler.setPoolSize(10);
		scheduler.schedule(getRunnable(), getTrigger(exp));
		schedulerMap.put(name,scheduler);
	}

	/**
	 * @return
	 */
	private Runnable getRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				System.out.println(new Date());
				// TODO Auto-generated method stub
			}
		};
	}
	
	/**
	 * @param exp
	 * @return
	 */
	private Trigger getTrigger(String exp) {
		return new CronTrigger(exp);
	}
	
	public void stopScheduler(String name) {
		if(!schedulerMap.isEmpty()) {
			schedulerMap.get(name).shutdown();
			System.out.println("schedule Stop!!!");
		}
	}
	
}
