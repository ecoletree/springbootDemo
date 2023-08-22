package com.bootWorkout.demo1.flutterTest.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootWorkout.demo1.flutterTest.service.FCMSendService;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;

@Controller
@RequestMapping("/flutter")
public class FCMSendController extends ETBaseController {
	
	@Autowired
	FCMSendService service;

	@RequestMapping("/sendMsg")
	public @ResponseBody Map<String, Object> sendMessage(HttpServletRequest request, @RequestBody Map<String, Object> param){
		Map<String, Object> result = service.sendMessage(param);
		return ResultUtil.getResultMap(true, result);
		
	}
	
}
