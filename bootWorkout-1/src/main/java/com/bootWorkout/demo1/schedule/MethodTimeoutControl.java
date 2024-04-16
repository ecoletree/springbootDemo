/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : kh201
 * Create Date : 2024. 4. 16.
 * File Name : MethodTimeoutControl.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MethodTimeoutControl {
	public static void main(String[] args) {
		boolean test = call(50);
		if(test) {
			System.out.println("test Success");
		}else {
			System.out.println("test fail");
		}
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean call(long time){
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		FutureTask task = new FutureTask(
		    new Callable() {
		        public Boolean call() throws Exception {
		        	Thread.sleep(60);//-> 60 fail
		            return true;
		        }
		    });
		
		threadPool.execute(task);
		Boolean result = false;
		try {
			try {
				result = (Boolean) task.get(time, TimeUnit.MILLISECONDS);
			} catch (TimeoutException e) {
				result = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}