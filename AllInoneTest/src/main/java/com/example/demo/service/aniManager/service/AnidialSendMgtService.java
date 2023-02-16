package com.example.demo.service.aniManager.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AnidialSendMgtService {

	public Map<String, Object> getSendMgtList(Map<String, Object> params);


	public Map<String, Object> sendAnidialerList(Map<String, Object> param, HttpServletRequest req);

	public Map<String, Object> getCodeList(Map<String, Object> params);

}
