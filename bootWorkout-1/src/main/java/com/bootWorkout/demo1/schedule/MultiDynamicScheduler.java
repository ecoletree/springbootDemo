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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class MultiDynamicScheduler {

	@Autowired
	ThreadPoolTaskScheduler threadScheduler;
	
	private static Map<String, ScheduledFuture<?>> schedulerMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	/**
	 * 스레드풀 안에 있는 스레드(=스케줄러)를 생성하고 
	 * 스케줄러 맵에 넣기 (->캔슬하기 위해)
	 * @param name
	 * @param exp
	 */
	public void startScheduler(Runnable run ,Map<String, Object> param) {
		ScheduledFuture<?> sss 
		= threadScheduler.schedule(getRunnable(run), getTrigger((String) param.get("schedule")));
		schedulerMap.put((String) param.get("schedule_name"),sss);
	}

	/**
	 * @return
	 */
	private Runnable getRunnable(Runnable run) {
		return run;
	}
	
	/**
	 * @param exp
	 * @return
	 */
	private Trigger getTrigger(String exp) {
		return new CronTrigger(exp);
	}
	
	/**
	 * shutdown()이 아니라 cancel을 해야 스레드 하나만 종료된다
	 * @param name
	 */
	public void stopScheduler(String name) {
		if(!schedulerMap.isEmpty()) {
			schedulerMap.get(name).cancel(true);
			schedulerMap.remove(name);
			System.out.println("schedule Stop!!!");
		}
	}
	
}
