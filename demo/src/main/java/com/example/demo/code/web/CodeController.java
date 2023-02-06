package com.example.demo.code.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.code.service.CodeService;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;


@Controller
public class CodeController extends ETBaseController{

	@Autowired
	CodeService service;
	
	@RequestMapping("/")
	public String hello() {
		logDebug("test");
		logError("test");
		logInfo("test");
		return "index";
	}
	
	@RequestMapping("/getList")
	public @ResponseBody Map<String, Object> getList(@RequestBody Map<String, Object> params) {
		List<Map<String, Object>> list = service.selectList(params);
		
		return ResultUtil.getResultMap(true,list);
	}
}
