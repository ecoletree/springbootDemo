/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2022. 10. 05.
 * File Name : RecordAuthorityMgtController.java
 * DESC : 녹음권한관리
*****************************************************************/
package com.example.demo.service.recordMgt.web;

import java.util.Map;

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
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.recordMgt.service.RecordListenOptionService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record/recordListenOption")
public class RecordListenOptionController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RecordListenOptionService service;
	
	/** 녹음 청취 컬럼/검색 관리 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".recordListenOption");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
				MapBuilder.<String, Object>of(
						"userTypeList", service.getUserTypeList()
						))
				);
		return mav;
	}
	
	/** 녹취 컬럼/검색 리스트 가져오기
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("/getColumnSearchList")
	public @ResponseBody Map<String, Object> getColumnSearchList(@RequestBody Map<String, Object> params) {
		Map<String, Object> resultMap = service.getListenOptionList(params);
		return ResultUtil.getResultMap(true,resultMap);
	}
	
	/** 컬럼 리스트 저장하기
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("/setColumnList")
	public @ResponseBody Map<String, Object> setColumnList(@RequestBody Map<String, Object> params) throws Exception{
		return ResultUtil.getResultMap(0 < service.setColumnList(params));
	}
	
	/** 옵션 리스트 저장하기
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("/setOptionList")
	public @ResponseBody Map<String, Object> setOptionList(@RequestBody Map<String, Object> params) throws Exception{
		return ResultUtil.getResultMap(0 < service.setOptionList(params));
	} 
}
