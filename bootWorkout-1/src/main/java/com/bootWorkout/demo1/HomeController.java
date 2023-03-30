package com.bootWorkout.demo1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
@Controller
public class HomeController {
	
	
	@RequestMapping(value = "/")
	public String home(HttpServletRequest request) {
		//세션유무 확인
//		Object sessionAttribute = RequestContextHolder.getRequestAttributes().getAttribute("sessionVO", RequestAttributes.SCOPE_SESSION);
//		return sessionAttribute == null ? "REDIRECT:/LOGIN":"redirect:/main";
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/timeout")
	public String sessionTimeout(HttpServletRequest request) {
		//세션유무 확인
		return "sessionTimeout";
	}
	
}
