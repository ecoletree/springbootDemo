package com.example.demo.service.aniManager.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface AnidialMgtService {

	public Map<String, Object> getAniDialerList(Map<String, Object> params);

	public int setAnidialerList(Map<String, Object> param);

	public int deleteAnidialerList(Map<String, Object> param);

	public Map<String, Object> excelDownloadAnidialList(Map<String, Object> param);

	public Map<String, Object> getCodeList(Map<String, Object> params);

	public boolean excelUploadAnidialList(MultipartHttpServletRequest request);

}
