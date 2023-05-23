package com.bootWorkout.demo1.login;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bootWorkout.demo1.login.LoginValidation.LoginParam.LoginParamBuilder;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.util.CryptoUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginValidation {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 1. 아이디 유무 체크
	 * 2. 중복 로그인 체크
	 * 3. 로그인 실패 카운트 (5건)
	 * 4. 타임 아웃 체크 (1분)
	 * 5. 초기 비밀번호 체크 (같으면 메세지)
	 * 6. 마지막 로그인 날자 (1년/90일/90일비번변경없음) 
	 * 7. 비밀번호 체크 (틀리면 틀린 시간 저장 --> 로그인 실패 카운트에서 체크)
	 */
	//타임아웃 모아두기 : builder에 있는 user_id 기준
	public static Map<String, Object> TIME_MEMORY = new HashMap<String,Object>();
	public static Map<String, Object> FAIL_COUNT = new HashMap<String,Object>();
	
	private static int TIME_LIMIT = 1; 
	
	@Builder
	@SuppressWarnings("unused")
	public static class LoginParam{
		private final String user_id;
		private final String user_pwd;
		private final String db_pwd;
		private final String init_pwd;
		private final int login_count;
		private final Date last_login_dttm;
		private final Date last_pw_change_dttm;
	}

	public String doValidation(LoginParamBuilder builder) throws NoSuchAlgorithmException, UnsupportedEncodingException, ETException {
		String resultMsg = ETCommonConst.SUCCESS;
		
//		3.로그인 실패 카운트 체크 (5건 기준) 
		if(!checkFailCount(builder).equals(ETCommonConst.SUCCESS)) return checkFailCount(builder);
//		4. 초기 비밀번호 체크 (같으면 메세지)
		if(!checkInitPassword(builder).equals(ETCommonConst.SUCCESS)) return checkInitPassword(builder);
//		5. 마지막 로그인 날짜 (근 1년간 접속 유무)
		if(!lastLoginBaseYear(builder).equals(ETCommonConst.SUCCESS)) return lastLoginBaseYear(builder);
//		6. 마지막 로그인 날짜 (근 90일간 접속 유무)
		if(!lastLoginBaseDays(builder).equals(ETCommonConst.SUCCESS)) return lastLoginBaseDays(builder);
//		7. 마지막 비밀번호 변경 날짜 (90일)
		if(!lastPasswordChangeDays(builder).equals(ETCommonConst.SUCCESS)) return lastPasswordChangeDays(builder);
//		7. 비밀번호 체크
		if(!passwordCheck(builder).equals(ETCommonConst.SUCCESS)) return passwordCheck(builder);
		
		return resultMsg;
		
	}

	
	/** 마지막 비밀번호 변경 날짜 (90일)
	 * @param builder
	 * @return
	 */
	private String lastPasswordChangeDays(LoginParamBuilder builder) {
		String resultMsg = ETCommonConst.SUCCESS;
		if (builder.last_pw_change_dttm != null) {
			Calendar now = Calendar.getInstance();
			Calendar dbCal = Calendar.getInstance();
			dbCal.setTime(builder.last_pw_change_dttm);
			dbCal.add(Calendar.DAY_OF_MONTH, 90);
			if (now.after(dbCal)) {
				resultMsg = "change_pw";
			}
		}
		return resultMsg;
	}


	/**마지막 로그인 날짜 (근 90일간 접속 유무)
	 * @param builder
	 * @return
	 */
	private String lastLoginBaseDays(LoginParamBuilder builder) {
		String resultMsg = ETCommonConst.SUCCESS;
		Calendar now = Calendar.getInstance();
		Calendar dbCal = Calendar.getInstance();
		dbCal.setTime(builder.last_login_dttm);
		dbCal.add(Calendar.DATE, 90);
		if (now.after(dbCal)) {
			resultMsg = "close_account";
		}
		return resultMsg;
	}


	/** 마지막 로그인 날자 (1년)
	 * @param builder
	 * @return
	 */
	private String lastLoginBaseYear(LoginParamBuilder builder) {
		String resultMsg = ETCommonConst.SUCCESS;
		Calendar now = Calendar.getInstance();
		Calendar dbCal = Calendar.getInstance();
		dbCal.setTime(builder.last_login_dttm);
		dbCal.add(Calendar.YEAR, 1);
		if (now.after(dbCal)) {
			resultMsg = "delete_account";
		}
		return resultMsg;
	}


	/** 초기 비밀번호 체크
	 * 같으면 메시지 체크
	 * @param builder
	 * @return
	 * @throws ETException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	private String checkInitPassword(LoginParamBuilder builder) throws NoSuchAlgorithmException, UnsupportedEncodingException, ETException {
		String resultMsg = ETCommonConst.SUCCESS;
		String initPwd = CryptoUtil.encodePassword(CryptoUtil.getBase64String(builder.init_pwd));
		if(builder.user_pwd.equals(initPwd)) {
			resultMsg = "initialized_pw";
		}
		return resultMsg;
	}


	/** 실패 카운트 체크
	 * @param builder
	 * @return
	 */
	private String checkFailCount(LoginParamBuilder builder) {
		String resultMsg = ETCommonConst.SUCCESS;
		int min = 0;
		logger.info("builder.login_count::"+builder.login_count);
		if( 5 <= builder.login_count) {// 5번 이상 실패시에
			// 타임아웃 체크 : 실패 이후에 몇분 지났는지
			min = timeOutCheck(builder);
			//1분 이상이면 메시지 
			if(0 < min)  { 
				resultMsg = "locked_account";
			}
			//0분이면 다음단계로 --> success
		}
		logger.info("checkFailCount:"+resultMsg);
		return resultMsg;
	}


	/** 타임아웃 남은 시간 체크하기
	 * @param builder
	 * @return
	 */
	private int timeOutCheck(LoginParamBuilder builder) {
		int left_min = 0;
		if(TIME_MEMORY.containsKey(builder.user_id)) {
			Calendar now = Calendar.getInstance();
			
			logger.info("nowTime:"+now);
			
			Date fTime = (Date) TIME_MEMORY.get(builder.user_id);
			Calendar failCal = Calendar.getInstance();
			failCal.setTime(fTime);
			
			logger.info("failTime:"+failCal);
			
			failCal.add(Calendar.MINUTE, TIME_LIMIT+1);
			long timeDiff = failCal.getTimeInMillis() - now.getTimeInMillis();
			left_min =(int)(timeDiff / (1000*60) ) % 60;
		
			logger.info("left_min"+left_min);
			
		}
		return left_min;
	}


	/** 패스워드 체크
	 * @param builder
	 * @return
	 * @throws ETException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	private String passwordCheck(LoginParamBuilder builder) throws NoSuchAlgorithmException, UnsupportedEncodingException, ETException {
		String resultMsg = ETCommonConst.SUCCESS;
		String cryptoPasswd = CryptoUtil.encodePassword(builder.user_pwd);
		if(!builder.db_pwd.equals(cryptoPasswd)) { // 패스워드 틀림
			resultMsg = "password_check_fail";
			// 실패한 시간 저장
			TIME_MEMORY.put(builder.user_id, Calendar.getInstance().getTime()); //최초 실패 시간
		}
		logger.info("passwordCheck:"+resultMsg);
		return resultMsg;
	}

	

}
