/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 5. 12.
 * File Name : TimeCkeckerUtil.java
 * DESC : 
*****************************************************************/
package com.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.example.demo.util.LoginValidationUtil.LoginParam.LoginParamBuilder;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.ETSessionManager;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.util.CryptoUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginValidationUtil {

	public static Map<String, Date> TIME_MEMORY = new HashMap<String, Date>();
	private final int TIME_LIMIT = 3;
	private final int LOGIN_COUNT = 5;
	
	public final class VALIDATION_MSG {
		public static final String LOGIN_USER = "login_user";
		public static final String INITIALIZED_PW = "initialized_pw";
		public static final String LOCKED_ACCOUNT = "locked_account";
		public static final String DELETE_ACCOUNT = "delete_account";
		public static final String CLOSE_ACCOUNT = "close_account";
		public static final String CHANGE_PW = "change_pw";
		public static final String NO_MATCH_DATA = "no_match_data";
	} 
	
	@Builder
	@SuppressWarnings("unused")
	public static class LoginParam {
		private final String init_pw;
		private final String user_id;
		private final String user_db_pw;
		private final String param_pw;
		private final int login_count;
		private final Date last_login_dttm;
		private final Date last_pw_change_dttm;
	}
	
	public String validation (LoginParamBuilder builder) throws NoSuchAlgorithmException, UnsupportedEncodingException, ETException {
		String resultMsg = ETCommonConst.SUCCESS;
		
//		if (ETSessionManager.getInstance().isLogon(builder.user_id)) {
//			resultMsg = "login_user";
//		}
		if (!(resultMsg = validationLoginCount(builder)).equals(""))
			return resultMsg;
		if (!(resultMsg = validationInitPW(builder)).equals(""))
			return resultMsg;
		if (!(resultMsg = validation1Year(builder)).equals(""))
			return resultMsg;
		if (!(resultMsg = validation90Day(builder)).equals(""))
			return resultMsg;
		if (!(resultMsg = validation90PW(builder)).equals(""))
			return resultMsg;
		if (!(resultMsg = validationPWCkeck(builder)).equals(""))
			return resultMsg;
		
		return resultMsg;
		
	}
	
	private String validationInitPW (LoginParamBuilder builder) throws NoSuchAlgorithmException, UnsupportedEncodingException, ETException {
		String resultMsg = "";
		String initPw1 = CryptoUtil.encodePasswordSha256(CryptoUtil.getBase64StringUTF8(builder.init_pw));
		String initPw = CryptoUtil.encodePassword(CryptoUtil.getBase64StringUTF8(builder.init_pw));
		if (builder.user_db_pw.equals(initPw)) {
			resultMsg = VALIDATION_MSG.INITIALIZED_PW;
		}
		return resultMsg;
	}
	
	private String validationLoginCount (LoginParamBuilder builder) {
		String resultMsg = "";
		if (LOGIN_COUNT <= builder.login_count) {
			int min = timeChecker(builder.user_id);
			if (0 < min) {
				resultMsg = VALIDATION_MSG.LOCKED_ACCOUNT+min;
			}
		}
		return resultMsg;
	}
	
	private String validation1Year(LoginParamBuilder builder) {
		String resultMsg = "";
		Calendar now = Calendar.getInstance();
		Calendar dbCal = Calendar.getInstance();
		dbCal.setTime(builder.last_login_dttm);
		dbCal.add(Calendar.YEAR, 1);
		if (now.after(dbCal)) {
			resultMsg = VALIDATION_MSG.DELETE_ACCOUNT;
		}
		return resultMsg;
	}
	
	private String validation90Day(LoginParamBuilder builder) {
		String resultMsg = "";
		Calendar now = Calendar.getInstance();
		Calendar dbCal = Calendar.getInstance();
		dbCal.setTime(builder.last_login_dttm);
		dbCal.add(Calendar.DATE, 90);
		if (now.after(dbCal)) {
			resultMsg = VALIDATION_MSG.CLOSE_ACCOUNT;
		}
		return resultMsg;
	}
	
	private String validation90PW(LoginParamBuilder builder) {
		String resultMsg = "";
		if (builder.last_pw_change_dttm != null) {
			Calendar now = Calendar.getInstance();
			Calendar dbCal = Calendar.getInstance();
			dbCal.setTime(builder.last_pw_change_dttm);
			dbCal.add(Calendar.DAY_OF_MONTH, 90);
			if (now.after(dbCal)) {
				resultMsg = VALIDATION_MSG.CHANGE_PW;
			}
		}
		return resultMsg;
	}
	
	private String validationPWCkeck(LoginParamBuilder builder) throws NoSuchAlgorithmException, UnsupportedEncodingException, ETException {
		String resultMsg = "";
		String cryptoPasswd = CryptoUtil.encodePassword(builder.param_pw);
		if( !builder.user_db_pw.equals(cryptoPasswd)) {
			resultMsg = VALIDATION_MSG.NO_MATCH_DATA;
			setTimeout(builder.user_id);
		}
		return resultMsg;
	}
	
	
	
	/**
	 * locked_account시에 시간 체크, 남은시간 계산
	 * @param user_id
	 * @return
	 */
	private int timeChecker(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		int minute = 0;
		if (TIME_MEMORY.containsKey(userId)) {
			Date failed_time = TIME_MEMORY.get(userId); // 저장된 시간
			Calendar failCal = Calendar.getInstance();
			failCal.setTime(failed_time);
			failCal.add(Calendar.MINUTE, TIME_LIMIT);// 최초시간 + 3분
			Calendar cal = Calendar.getInstance();
			if (cal.after(failCal)) {
				TIME_MEMORY.remove(userId);
			} else {
				minute = getTimeout(userId, cal);
			}
		}
		
		return minute;
	}
	
	/**
	 * 남은 Timeout 시간을 리턴
	 * @param userId
	 * @return 남은 시간
	 */
	private int getTimeout (String userId, Calendar cal) {
		int minute = 0;
		if (TIME_MEMORY.containsKey(userId)) {
			Date failed_time = TIME_MEMORY.get(userId); // 저장된 시간
			Calendar failCal = Calendar.getInstance();
			failCal.setTime(failed_time);
			failCal.add(Calendar.MINUTE, TIME_LIMIT+1);// 최초시간 + 3분
			long timeDiff = failCal.getTimeInMillis() - cal.getTimeInMillis();
			minute =(int)(timeDiff / (1000*60) ) % 60;
		} 
		
		return minute;
	}
	
	public void setTimeout (String userId) {
		TIME_MEMORY.put(userId, Calendar.getInstance().getTime()); //최초실패 
	}
	
}

