package com.bootWorkout.demo1.login.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

	public Map<String, Object> doLogin(Map<String, Object> param, HttpServletRequest request);


}
