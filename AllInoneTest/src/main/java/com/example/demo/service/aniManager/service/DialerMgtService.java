package com.example.demo.service.aniManager.service;

import java.util.Map;

public interface DialerMgtService {

	public Map<String, Object> getDialerList(Map<String, Object> params);

	public int setDialer(Map<String, Object> param);

	public int delDialer(Map<String, Object> param);


}
