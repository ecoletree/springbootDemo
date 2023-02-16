/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2022. 10. 13
 * File Name : LogUtil.java
 * DESC : 
*****************************************************************/
package com.example.demo.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.Utility;
import kr.co.ecoletree.common.vo.ETSessionVO;
import com.example.demo.service.log.service.LogService;

@Component
public class AllInoneLogUtil {

	@SuppressWarnings("unused")
	private static AllInoneLogUtil instance = null;
	
	public final class LOG_MESSAGE {
		public static final String LOGIN_FAILED = "로그인 정보를 확인해주세요.";
		public static final String LOGIN_IP_NO_AUTH = "로그인 허용 IP가 다릅니다. 확인 부탁드립니다.";
		public static final String LOGIN_NO_ROLE = "허용된 권한 메뉴가 없습니다.";
		public static final String RECORD_PLAY = "녹취 재생($data)";
		public static final String RECORD_DOWNLOAD = "녹취 다운($data)";
		public static final String CERTIFICATE_UPDATE = "증서번호 업데이트($data)";
		public static final String LOGIN = "로그인";
		public static final String LOGOUT = "로그아웃";
	}
	
	
	@Autowired
	LogService service;
	
	/**
	 * 로그를 담는다 메시지만 담을 경우에는 session에서 정보를 찾는다
	 * 고객관리자, 상담사 화면에서 사용
	 * 슈퍼관리자에서는 사용 불가함(SystemAdmin에서 정보를 가져오기때문에)
	 * @param logMsg
	 * @return
	 */
	public boolean setLog(String logMsg) {
		
		return setLog(logMsg, null);
	}
	/**
	 * 로그를 담는다 메시지만 담을 경우에는 session에서 정보를 찾는다
	 * 슈퍼관리자화면에서 사용
	 * @param logMsg
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean setLog(String logMsg, String userId) {
		Map<String, Object> map = null;
		map = MapBuilder.of("log_msg", logMsg, "login_ip",Utility.getIP());
		if (userId == null) {
			ETSessionVO sessionVO = ETSessionHelper.getSessionVO();
			if (sessionVO != null) {
				Map<String, Object> userInfo = (Map<String, Object>)sessionVO.getUser_info();
				map.put("agent_id", sessionVO.getUser_id());
				map.put("group_id", userInfo.get("group_id"));
				map.put("tenant_id", userInfo.get("tenant_id"));
				map.put("team_id", userInfo.get("team_id"));
			}
		} else {
			map.put("agent_id", userId);
		}
		return service.setLog(map);
	}
	
	
	
}

