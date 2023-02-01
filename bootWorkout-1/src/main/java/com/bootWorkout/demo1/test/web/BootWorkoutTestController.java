package com.bootWorkout.demo1.test.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BootWorkoutTestController {
	
	@RequestMapping("/")
	public String home() {
		System.out.println("open home");
		return "home";
	}
	
	@RequestMapping("/getTest")
	public @ResponseBody Map<String, Object> getDataTest(@RequestBody Map<String,Object> param){
		System.out.println("getTest");
		Map<String, Object> resultMap = new HashMap<>();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");
		String text = param.get("text").toString();
		String msg = text.length() > 0 ? "success" : "fail"; 
		resultMap.put("message", msg);
		resultMap.put("now", sdf.format(now));
		resultMap.put("text", text);
		
		return resultMap;
	}
}
