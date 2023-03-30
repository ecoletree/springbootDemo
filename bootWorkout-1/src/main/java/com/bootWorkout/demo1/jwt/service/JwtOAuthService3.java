package com.bootWorkout.demo1.jwt.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
@Service
public class JwtOAuthService3 {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String baseKey = "thisisdummykeythisisdummykeythisisdummykeythisisdummykeythisisdummykey";
	private final SignatureAlgorithm signatureAlgorith = SignatureAlgorithm.HS256;
	private final List<String> tokenList = new ArrayList<>();
	/**
	 * byte 단위의 키 만들어주기 
	 * @return
	 */
	private Key createKey() {
		/*
		 * javax.crypto.spec.SecretKeySpec.SecretKeySpec(byte[] key, String algorithm)
		 */
		byte[] secretkey = DatatypeConverter.parseBase64Binary(baseKey);
		Key signingKey = new SecretKeySpec(secretkey, signatureAlgorith.getJcaName());
		return signingKey;
	}
	
	/** 
	 * 토큰 발급
	 * access token 2분 , refresh token 10분 생성
	 * access token + refresh toekn 헤더에 담아 리턴
	 * refresh token localStorage 저장 (혹은 httpOnly로 쿠키에 저장)
	 * access token 지역변수로 저장
	 * 
	 * @param request
	 * @return
	 */
	public String createJwt(Map<String, Object> param, int ExpTime, String tokenType) {
		Map<String, Object> headerMap = new HashMap<String,Object>();
		headerMap.put("typ", "JWT");
	    headerMap.put("alg", "HS256");
	    headerMap.put("tktyp", tokenType);
		
	    Map<String, Object> claims = new HashMap<String, Object>();
	    claims.put("name", "jwtTest");
	    claims.put("id", param.get("user_id"));
	    
	    Date expireTime = new Date();
	    expireTime.setTime(expireTime.getTime() + ExpTime);
	    
	    JwtBuilder builder = Jwts.builder()
	    		.setHeader(headerMap)
	    		.setClaims(claims)
	    		.setExpiration(expireTime)
	    		.signWith(createKey(), signatureAlgorith);
	    
	    String token = builder.compact();
	    if(tokenType == "refresh") {
	    	tokenList.add(token);
	    }
		return token;
	}
	
	/**
	 * 로그인 
	 * 토큰 발급해서 리턴 (access&refresh)
	 * @param request
	 * @return
	 */
	public Map<String, Object> loginJwt(Map<String, Object> param){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String resultMsg = "log_in_success";
		String user_id = param.get("user_id").toString();
		String user_pw = param.get("user_pw").toString();
		
		if(!user_id.equals("admin") || !user_pw.equals("admin") ) {
			resultMsg = "no_user_data";
		}else {
			String atoken = createJwt(param,1000 * 60 * 2,"access"); // 2분
			String rtoken = createJwt(param,1000 * 60 * 10,"refresh"); // 10분
			
			map.put("access_token", atoken);
			map.put("refresh_token", rtoken);
		}
		
		map.put("resultMsg", resultMsg);
		
		return map;
	}
//	public Map<String, Object> checkJwt(String jwt){
//		Map<String, Object> map = new HashMap<>();
//		Boolean verifyJwt = verifyJwt(jwt);
//		return map;
//	}
	/**
	 * 토큰 검증 true false
	 * @param jwt
	 * @return
	 */
	public Boolean verifyJwt(String jwt) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(DatatypeConverter.parseBase64Binary(baseKey))
					.build()
					.parseClaimsJws(jwt)
					.getBody();
			
			//access인지 refresh인지 구분자 넣는거 어떻게 할건지..?
			logger.info(claims.getSubject());
			
		} catch (SignatureException e) {
			e.printStackTrace();
			return false;
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			return false;
		} catch (JwtException e) {
			e.printStackTrace();
			return false;
		} 
		// getBody()가 true일 경우에만
		// getSubject() 해서 map으로 리턴 받을수 있게 하기 
		return true; 
	}
}
