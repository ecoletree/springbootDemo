/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 4. 13.
 * File Name : ScheduleController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private DynamicScheduler dynamicScheduler;
	@Autowired
	private MultiDynamicScheduler multiDynamicScheduler;

	@RequestMapping(value = "")
	public String schedule(HttpServletRequest request) {
		return "schedule";
	}
	
	@RequestMapping("/set")
	public @ResponseBody Map<String, Object> setSchedule(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> map =new HashMap<>();
		System.out.println("schedule Start!!!");
		dynamicScheduler.startScheduler((String) param.get("schedule"));
		map.put("msg", "success");
		return map;
	}
	@RequestMapping("/stop")
	public @ResponseBody Map<String, Object> stopSchedule(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> map =new HashMap<>();
		dynamicScheduler.stopScheduler();
		map.put("msg", "success");
		return map;
	}
	@RequestMapping("/set/multi")
	public @ResponseBody Map<String, Object> setMultiSchedule(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> map =new HashMap<>();
		System.out.println("schedule Start!!!");
		multiDynamicScheduler.startScheduler(new Runnable() {
			@Override
			public void run() {
				//실행 메소드
				System.out.println(new Date() + " : " +Thread.currentThread().getName());
				// TODO Auto-generated method stub
			}
		},param);
		map.put("msg", "success");
		return map;
	}
	@RequestMapping("/stop/multi")
	public @ResponseBody Map<String, Object> stopMultiSchedule(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> map =new HashMap<>();
		multiDynamicScheduler.stopScheduler((String) param.get("schedule_name"));
		map.put("msg", "success");
		return map;
	}	
}
