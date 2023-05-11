/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 4. 14
 * File Name : SchedulerUtil.java
 * DESC : 
*****************************************************************/
package com.example.demo.scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

@Configuration
public class SchedulerUtil {

	@Autowired
	ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	private long seq = 1;
	
	public synchronized long getNext() {
        return seq++;
    }
	
	private Map<Long,ScheduledFuture<?>> scheduledMap = new ConcurrentHashMap<Long,ScheduledFuture<?>>();
	
	
	public long startScheduler (Runnable run, String cron) {
		ScheduledFuture<?> task = threadPoolTaskScheduler.schedule(run, getTrigger(cron));
		long seq = getNext();
		scheduledMap.put(seq, task);
		return seq;
	}
	
	public boolean stopScheduler (long seq) {
		scheduledMap.get(seq).cancel(true);
		scheduledMap.remove(seq);
		return true;
	}
	
	public Trigger getTrigger(String cron) {
		return new ETTrigger().cron(cron);
	}

}

class ETTrigger {
	
	public ETTrigger() {
		
	} 
	
	public Trigger priod (long pro) {
		return new PeriodicTrigger(pro);
	}
	
	public Trigger cron(String cron) {
		return new CronTrigger(cron);
	}
}

