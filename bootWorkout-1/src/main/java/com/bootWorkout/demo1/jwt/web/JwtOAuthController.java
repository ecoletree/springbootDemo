package com.bootWorkout.demo1.jwt.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bootWorkout.demo1.jwt.service.JwtOAuthService3;

@Controller
@RequestMapping("/jwt")
public class JwtOAuthController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private JwtOAuthService3 service; 

	@RequestMapping("")
	public ModelAndView open(final ModelAndView mav,HttpServletRequest request) {
		mav.setViewName("jwtTest");
		
		return mav;
	}
	@RequestMapping("/test")
	public @ResponseBody Map<String, Object> getTest(HttpServletRequest request) {
		return service.simpleCheckJwt(request);
	}
	
	/** 로그인 후 토큰 생성
	 * @param subject
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/login")
	public @ResponseBody Map<String, Object> loginJWT(@RequestBody Map<String, Object> param) throws Exception {
		 Map<String, Object> map = service.loginJwt(param);
		return map;
	}
	
	/** 로그인 후 토큰 생성
	 * @param subject
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/check")
	public @ResponseBody Map<String, Object> checkJwt(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> map = service.checkJwt(param);
		return map;
	}
	
	
}
