/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2022. 10. 05.
 * File Name : RecordFileDeleteMgtController.java
 * DESC :  녹취파일 자동 삭제 관리
*****************************************************************/
package com.example.demo.service.recordMgt.web;

import java.util.HashMap;
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
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.recordMgt.service.RecordFileDeleteMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record/fileDeleteMgt")
public class RecordFileDeleteMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RecordFileDeleteMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 내선별 리포트 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".recordFileDeleteMgt");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	}
	
	@Auth
	@RequestMapping("/getRecordRemoveList")
	public @ResponseBody Map<String, Object> getRecordRemoveList(@RequestBody Map<String, Object> params) {
		
		return ResultUtil.getResultMap(true,service.getRecordRemoveList(params));
	}
	
	@Auth
	@RequestMapping("/setRecordRemove")
	public @ResponseBody Map<String, Object> setRecordRemove(@RequestBody Map<String, Object> params) {
		int result = service.setRecordRemove(params);
		return ResultUtil.getResultMap(0 < result,params);
	}
}
