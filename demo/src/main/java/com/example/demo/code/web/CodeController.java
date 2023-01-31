package com.example.demo.code.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.code.service.CodeService;
import com.example.demo.utils.ResultUtil;

@Controller
public class CodeController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CodeService service;
	
	@RequestMapping("/")
	public String hello() {
		log.debug("TEST");
		log.info("TEST");
		log.error("TEST");
		log.warn("TTT");
		return "index";
	}
	
	@RequestMapping("/getList")
	public @ResponseBody Map<String, Object> getList(@RequestBody Map<String, Object> params) {
		List<Map<String, Object>> list = service.selectList(params);
		
		return ResultUtil.getResultMap(true,list);
	}
}
