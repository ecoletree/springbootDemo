/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2022. 10. 05.
 * File Name : RecordAuthorityMgtController.java
 * DESC : 녹음권한관리
*****************************************************************/
package com.example.demo.service.recordMgt.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.recordMgt.service.RecordListenColumnService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record/listenColumn")
public class RecordListenColumnController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RecordListenColumnService service;
	
	/** 녹음 청취 컬럼/검색 관리 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".recordListenColumn");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(service.getCodeList(params)));
		return mav;
	}
	
	/** 레코드 컬럼 조회
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRecordColumnList")
	public @ResponseBody Map<String, Object> getRecordColumnList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = service.getRecordColumnList(params);
		return ResultUtil.getResultMap(list.size() != 0 , list);
	}
	
	/** 레코드 컬럼 수정
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setRecordColumnList")
	public @ResponseBody Map<String, Object> setRecordColumnList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		int i = service.setRecordColumnList(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
}
