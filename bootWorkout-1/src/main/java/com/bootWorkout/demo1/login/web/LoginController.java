//package com.bootWorkout.demo1.login.web;
//
//import java.io.IOException;
//import java.util.Map;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.bootWorkout.demo1.login.service.LoginService;
//
//@Controller
//@RequestMapping("/login")
//public class LoginController {
//
//	Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	LoginService service;
//	
//	@RequestMapping("")
//	public String login(HttpServletRequest request) {
//		logger.info("============>>>open login");
//		return "login";
//	}
//	
//	@RequestMapping("/doLogin")
//	public @ResponseBody Map<String, Object> login(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
//		Map<String, Object> map = service.doLogin(param,request);
//		return map;
//	}
//	
//	@RequestMapping("/logout")
//	public void logout(HttpServletRequest request, HttpServletResponse response) {
//		logger.info("============ doLogout");
//		HttpSession session = request.getSession();
//		session.invalidate();
//		 
//		try {
//			ServletContext servletContext = request.getServletContext();
//			String view = request.isRequestedSessionIdValid() == false ? "/login" : "/main";
//			response.sendRedirect(servletContext.getContextPath() + view);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	@RequestMapping(value = "/timeout")
//	public String sessionTimeout(HttpServletRequest request) {
//		//세션유무 확인
//		return "sessionTimeout";
//	}
//}
