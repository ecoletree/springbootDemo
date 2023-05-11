/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 3. 29
 * File Name : MemberController.java
 * DESC : 
*****************************************************************/
package com.example.demo.login;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.scheduler.SchedulerUtil;

import kr.co.ecoletree.common.util.ResultUtil;
import kr.co.ecoletree.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
//    private final MemberService memberService;
    
    @Autowired
	SchedulerUtil sUtil;
 
    
    @PostMapping("/test")
    public String test() {
    	return "하하하";
    }
    
    @PostMapping("/start")
    public @ResponseBody Map<Object, Object> start(@RequestBody Map<Object, Object> params) throws Exception{
//    	ps.stopScheduler();
//		Thread.sleep(1000);
//		ps.changeCronSet((String) params.get("cron"));
//		ps.startScheduler();
    	
    	long seq = sUtil.startScheduler(new Runnable() {
			
			@Override
			public void run() {
				// 쿼리 데이트 찍어도 됨
				log.info(LocalDateTime.now().toString() + "-" + Thread.currentThread().getName() );
				
			}
		}, (String) params.get("cron"));
		
		HashMap<Object, Object> res = new HashMap<Object, Object>();
		res.put("seq", seq);
		return res;
    }
    
    @PostMapping("/stop")
    public @ResponseBody Map<Object, Object> stop(@RequestBody Map<Object, Object> params) throws Exception{
//    	ps.stopScheduler();
    	long seq = (long)params.get("seq");
    	sUtil.stopScheduler(seq);
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put("res", "success");
		return res;
    }
}
