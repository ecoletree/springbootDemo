/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Jang Yoon Seok
 * Create Date : 2022. 4. 11.
 * File Name : LoginServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.login.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.ETSessionManager;
import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.util.CryptoUtil;
import kr.co.ecoletree.common.util.TreeUtil;
import kr.co.ecoletree.common.util.Utility;
import kr.co.ecoletree.common.vo.ETSessionVO;
import com.example.demo.service.login.mapper.LoginMapper;
import com.example.demo.service.login.mapper.RoleMapper;
import com.example.demo.service.login.service.LoginService;
import com.example.demo.util.AllInoneLogUtil;
import com.example.demo.util.AllInoneLogUtil.LOG_MESSAGE;

@Service
@Transactional
public class LoginServiceImpl extends ETBaseService implements LoginService {

	@Autowired
	LoginMapper mapper;
	
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	AllInoneLogUtil logUtil;
	
	@Override
	public Map<String, Object> login(Map<String, Object> param, HttpServletRequest request)
			throws ETException, Exception {
		String resultMsg = ETCommonConst.SUCCESS;
		Map<String, Object> tmrInfo = null;
		// 유저 데이터 검색
		if (param.containsKey("user_type")) {
			String userType = (String)param.get("user_type");
			if (userType.equals("system")) {
				tmrInfo = mapper.selectLoginInfo(param);
			} else {
				tmrInfo = mapper.selectGroupLoginInfo(param);
			}
		} else {
			tmrInfo = mapper.selectLoginInfo(param);
		}

		if (tmrInfo == null) {
			tmrInfo = new HashMap<String, Object>();
			resultMsg = "login.error.no_match_data";
			logUtil.setLog(LOG_MESSAGE.LOGIN_FAILED, (String)param.get("user_id"));

		} else {
			// 접속 ip 추출
			String ip = Utility.getIP();
			tmrInfo.put("login_ip", ip);
			tmrInfo.put("insert_ip", ip);
			tmrInfo.put("session_user_id", tmrInfo.get("user_id"));
			
			Map<String, Object> rollFirst = roleMapper.selectUserFirstRole(tmrInfo);
			
			resultMsg = checkLogin(param, tmrInfo, rollFirst);
			
			// 로그인 성공
			if (resultMsg.equals(ETCommonConst.SUCCESS)) {
				
				//메뉴 권한 가져오기
				List<Map<String,Object>> roleList = roleMapper.selectUserRoleList(tmrInfo);
				List<Map<String,Object>> roleTree = TreeUtil.groupCategoryCompare(roleList, "menu_cd", "p_menu_cd" );
				
				List<Map<String,Object>> menuList = setMenuList(roleTree); // 메뉴권한관리
				
				tmrInfo.put("first_menu_url", rollFirst.get("menu_url"));
				
				
				//세션 정보 저장
				ETSessionVO etSessionVO = new ETSessionVO();
				etSessionVO.setUser_id((String)tmrInfo.get("user_id"));
				etSessionVO.setUser_pw((String)tmrInfo.get("user_pw"));
				etSessionVO.setUser_info(tmrInfo);
				request.getSession().setAttribute(ETCommonConst.SESSION_VO, etSessionVO);
				if (menuList != null && !menuList.isEmpty()) {
					request.getSession().setAttribute(ETCommonConst.ROLE_MENU, menuList);
				}

				ETSessionManager.getInstance().setSession(request.getSession(), etSessionVO.getUser_id());
				logUtil.setLog(LOG_MESSAGE.LOGIN, (String)tmrInfo.get("user_id"));
			} else {
				String msg = "";
				if (resultMsg.equals("login.error.no_match_data")) {
					msg = LOG_MESSAGE.LOGIN_FAILED;
				} else if (resultMsg.equals("login.error.no_auth")){
					msg = LOG_MESSAGE.LOGIN_IP_NO_AUTH;
				} else if (resultMsg.equals("login.error.no_role")){
					msg = LOG_MESSAGE.LOGIN_NO_ROLE;
				} else {
					msg = LOG_MESSAGE.LOGIN_FAILED;
				}
				logUtil.setLog(msg, (String)tmrInfo.get("user_id"));
			}
		}
		
		tmrInfo.put("type", resultMsg);

		return tmrInfo;
	}
	
	/** 메뉴 권한 관리
     * @param roleTree
     * @return
     */
    @SuppressWarnings("unchecked")
	private List<Map<String, Object>> setMenuList(List<Map<String, Object>> roleTree) {
        for(Map<String, Object> map : roleTree) {
        	if(map.containsKey("children")) {
        		List<Map<String,Object>> children = (List<Map<String, Object>>) map.get("children");
        		int hasChild = 0;
        		for(Map<String, Object> child : children) {
        			if(child.get("is_view").equals("Y")) {
        				hasChild ++;
        			}
        		}
    	        if (0 < hasChild) {
    	        	map.put("is_view", "Y");
    	        } else {
    	        	map.put("is_view", "N");
    	        }
    	        
        	}
        }
        
        return roleTree;
    }



	/**
	 * 로그인 가능 체크
	 * @param param
	 * @param tmrInfo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private String checkLogin(Map<String, Object> param, Map<String, Object> tmrInfo, Map<String, Object> rollFirst) throws Exception {
		String resultMsg = ETCommonConst.SUCCESS;
		String cryptoPasswd = CryptoUtil.encodePasswordSha256((String)param.get("user_pw"));// 비밀번호 체크
		// 비밀번호 체크
		if (!tmrInfo.get("user_pw").equals(cryptoPasswd)) {
			resultMsg = "login.error.no_match_data";
		} else if (tmrInfo.get("is_super").equals("Y")) {
			// 로그인 IP 체크
			if (tmrInfo.get("user_ip") != null && !tmrInfo.get("user_ip").equals("")) {
				if (!tmrInfo.get("user_ip").equals(tmrInfo.get("login_ip"))) {
					resultMsg = "login.error.no_auth";
				}
			}
		} else {
			if (tmrInfo.get("user_ip") != null && !tmrInfo.get("user_ip").equals("")) {
				if (!tmrInfo.get("user_ip").equals(tmrInfo.get("login_ip"))) {
					resultMsg = "login.error.no_auth";
				}
			} else {
				resultMsg = "login.error.no_auth";
			}
		}
		
		if (rollFirst == null) {
			resultMsg = "login.error.no_role";
		}
		
		/*
		 * 다중로그인 체크
		 */
		/*
		if (ETSessionManager.getInstance().isLogon((String)tmrInfo.get("user_id"))) {
			resultMsg = "login.error.login_user";
			return resultMsg;
		}
		*/

		return resultMsg;
	}

}

