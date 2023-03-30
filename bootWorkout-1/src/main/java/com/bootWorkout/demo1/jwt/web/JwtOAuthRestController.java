package com.bootWorkout.demo1.jwt.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.jwt.service.JwtOAuthService;
import com.bootWorkout.demo1.jwt.service.JwtOAuthService2;

@RestController
@RequestMapping("/jwt")
public class JwtOAuthRestController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private JwtOAuthService jwtOAuthService; 
	
	@Autowired
	private JwtOAuthService2 jwtOAuthService2; 
	
	/** 토큰 생성
	 * @param subject
	 * @return
	 */
	@RequestMapping("/create/token")
	public Map<String, Object> createToken(@RequestParam(value="subject") String subject) {
		Map<String, Object> map = new LinkedHashMap<>();
		String token = jwtOAuthService.createToken(subject, (5*1000*60)); // 2분
		map.put("result",token);
		return map;
	}
	
	/** 토큰 검증
	 * @param subject
	 * @return
	 */
	@RequestMapping("/get/subject")
	public Map<String, Object> getSubject(@RequestParam(value="token") String token) {
		Map<String, Object> map = new LinkedHashMap<>();
		String subject = jwtOAuthService.getSubject(token);
		map.put("result",subject);
		return map;
	}
	/** 토큰 생성2
	 * @param subject
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/createToken")
	public Map<String, Object> createToken2(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new LinkedHashMap<>();
		String token = jwtOAuthService2.createJwt(request);
		map.put("result",token);
		return map;
	}
	
	/** 토큰 검증2
	 * @param subject
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getSubject")
	public Map<String, Object> getSubject2(@RequestParam(value="token") String token) throws Exception {
		Map<String, Object> map = new LinkedHashMap<>();
		boolean subject = jwtOAuthService2.checkJwt(token);
		map.put("result",subject);
		return map;
	}
}
