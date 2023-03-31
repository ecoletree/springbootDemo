package com.bootWorkout.demo1.jwt.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
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
	public String createJwt(Map<String, Object> param, String tokenType) {
		Map<String, Object> headerMap = new HashMap<String,Object>();
		headerMap.put("typ", "JWT");
	    headerMap.put("alg", "HS256");
		
	    Map<String, Object> claims = new HashMap<String, Object>();
	    claims.put("name", "jwtTest");
	    claims.put("id", param.get("user_id"));
	    claims.put("tktyp", tokenType);
	    
	    int expTime = tokenType == "refresh" ? 1000 * 60 * 10 : 1000 * 60 * 2;
	    
	    Date expireTime = new Date();
	    expireTime.setTime(expireTime.getTime() + expTime);
	    
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
			String atoken = createJwt(param,"access"); // 2분
			String rtoken = createJwt(param,"refresh"); // 10분
			
			map.put("access_token", atoken);
			map.put("refresh_token", rtoken);
		}
		
		map.put("resultMsg", resultMsg);
		
		return map;
	}
	public Map<String, Object> checkJwt(Map<String,Object> param){
		Map<String, Object> map = new HashMap<>();
		String type = param.get("type").toString();
		String jwt = param.get("token").toString();
		Boolean verifyJwt = true;
		//token이 refresh 일때는 서버에 있는 거랑 비교한 뒤 true/false 반환
		if(type == "refresh") {
			verifyJwt = tokenList.contains(jwt) ? verifyJwt(jwt) : false;
		}else {
			verifyJwt = verifyJwt(jwt);
		}
		map.put("type", type);
		map.put("valid", verifyJwt);
		//token이 access 일때는 그냥 true/false 반환
		
		
		return map;
	}
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
		} catch (ExpiredJwtException e) {
			logger.info("Token Expired!!!");
			return false;
		} catch (JwtException e) {
			logger.info("Token Error!!!");
			return false;
		} 
		return true; 
	}
}
