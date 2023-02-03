package com.bootWorkout.demo1.test.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootWorkout.demo1.test.service.BootWorkoutTestService;

@Controller
public class BootWorkoutTestController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	BootWorkoutTestService service;
	
	@RequestMapping("/main")
	public String main() {
		logger.info("============>>>open main");
		return "home";
	}
	
	@RequestMapping("/getTest")
	public @ResponseBody Map<String, Object> getDataTest(@RequestBody Map<String,Object> param){
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String,Object>> list = service.getMenuList(param); 
		String msg = list.size() > 0 ? "success" : "fail";
		resultMap.put("message", msg);
		resultMap.put("data", list);
		
		return resultMap;
	}
	
	
	
	
}
