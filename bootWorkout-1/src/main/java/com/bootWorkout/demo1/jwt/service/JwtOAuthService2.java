package com.bootWorkout.demo1.jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtOAuthService2 {

	//key 값이 256bit가 넘어가지 않으면 WeakKeyException이 뜬다.
	private final String baseKey = "thisisdummykeythisisdummykeythisisdummykeythisisdummykeythisisdummykey";
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
	
	/** 키생성
	 * @return
	 */
	private Key createKey() {
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(baseKey);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	    return signingKey;
	}
	
	/** 생성
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String createJwt(HttpServletRequest request) throws Exception {
	    Map<String, Object> headerMap = new HashMap<String,Object>();
	    headerMap.put("typ", "JWT");
	    headerMap.put("alg", "HS256");

	    Map<String, Object> claims = new HashMap<String, Object>();
	    claims.put("name", request.getParameter("name"));
	    claims.put("id", request.getParameter("id"));

	    Date expireTime = new Date();
	    expireTime.setTime(expireTime.getTime() + 1000 * 60 * 5);//5분

	    JwtBuilder builder = Jwts.builder()
	            .setHeader(headerMap)
	            .setClaims(claims)
	            .setExpiration(expireTime)
	            .signWith(createKey(), signatureAlgorithm);

	    String result = builder.compact();
	    System.out.println("serviceTester " + result);
	    return result;
	}
	
	
	/** 검증
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public Boolean checkJwt(String jwt) throws Exception {
	    try {
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(DatatypeConverter.parseBase64Binary(baseKey))
	                .build()
	                .parseClaimsJws(jwt)
	                .getBody();
	        System.out.println("Id : " + claims.get("id"));
	        System.out.println("Name : " + claims.get("name"));
	    } catch (ExpiredJwtException e) {
	        e.printStackTrace();
	        return false;
	    } catch (JwtException e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
	
}
