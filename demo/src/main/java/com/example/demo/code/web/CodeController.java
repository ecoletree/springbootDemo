package com.example.demo.code.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.code.service.CodeService;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;


@Controller
public class CodeController extends ETBaseController{

	private static final String JSP_PATH = ".service.body";
	
	@Autowired
	CodeService service;
	
	@RequestMapping("/")
	public String hello() {
		logDebug("test");
		logError("test");
		logInfo("test");
		return "index";
	}
	
	@RequestMapping("/tail")
	public ModelAndView open(final ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".tailBody");
		return mav;
	}
	
	
	@RequestMapping("/getList")
	public @ResponseBody Map<String, Object> getList(@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = service.selectList(params);
		
		return ResultUtil.getResultMap(true,list);
	}
	
	@RequestMapping("/getList2")
	public @ResponseBody Map<String, Object> getList2(@RequestBody Map<String, Object> params) throws Exception {
		int list = service.insertCode(params);
		
		return ResultUtil.getResultMap(true,list);
	}
}
