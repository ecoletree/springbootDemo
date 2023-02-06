package com.bootWorkout.demo1.login.service.Impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.bootWorkout.demo1.login.SessionVO;
import com.bootWorkout.demo1.login.mapper.LoginMapper;
import com.bootWorkout.demo1.login.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	LoginMapper mapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> doLogin(Map<String, Object> param, HttpServletRequest request) {
		logger.info("============ doLogin");
		String resultMsg = "log_in_success";
		
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> userInfo = new HashMap<>();
		
		String user_id = param.get("user_id").toString();
		String user_pw = param.get("user_pw").toString();
		
		if(!user_id.equals("admin") || !user_pw.equals("admin") ) {
			userInfo = null;
		}else {
			userInfo.put("user_id", user_id);
			userInfo.put("user_pw", user_pw);
		}
		
		/*
		 * 1.로그인 체크 : 있는 회원 인지, 있는 회원이면 중복된 세션인지  
		 * 2. 로그인 세션 저장 : 세션에 담긴 정보가 있으면 기존 세션 정보 삭제 하고 로그인
		 */
		if(userInfo == null) {
			resultMsg = "no_user_data";
		}else {
			Object sessionAttribute = RequestContextHolder.getRequestAttributes().getAttribute("sessionVO", RequestAttributes.SCOPE_SESSION);
			
			if(sessionAttribute != null) {// 세션있음
				SessionVO session = (SessionVO) request.getSession().getAttribute("sessionVO");
				
				if((session.getUser_id()).equals( userInfo.get("user_id"))) {
					// 원래 있던 세션과 같음
					resultMsg = "logged_in_data";
					userInfo = (Map<String, Object>) session.getUser_info();
				}else {
					// 원래 있던 세션과 다름 : 원래 세션정보 로그아웃
					request.getSession().invalidate();
					resultMsg = "log_in_success";
				}
			}
			
			//세션정보 저장하기
			SessionVO sessionVO = new SessionVO();
			sessionVO.setUser_id(userInfo.get("user_id").toString());
			sessionVO.setUser_pw(userInfo.get("user_pw").toString());
			sessionVO.setUser_info(userInfo);
			request.getSession().setAttribute("sessionVO", sessionVO);
			
			
			resultMap.put("userData", userInfo);
				

		}
		
		resultMap.put("resultMsg", resultMsg);
		return resultMap;
	}

}
