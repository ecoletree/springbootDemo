package com.bootWorkout.demo1.code.web;

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
import org.springframework.web.servlet.ModelAndView;

import com.bootWorkout.demo1.code.service.CodeListService;

@Controller
public class CodeListController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String JSP_PATH = ".service.body";
	
	@Autowired
	CodeListService service;
	
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
