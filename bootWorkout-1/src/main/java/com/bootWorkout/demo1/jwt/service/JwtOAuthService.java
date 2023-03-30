package com.bootWorkout.demo1.jwt.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtOAuthService {
	
	//서버 공통 키
	private final String BASEKEY = "thisisdummykeythisisdummykeythisisdummykeythisisdummykeythisisdummykey";
	
	//토큰 생성하는 메서드 (로그인 서비스를 던질때 같이함)
	public String createToken(String subject, long expTime) {
		if(expTime <= 0) { // 0이하면 exception
			throw new RuntimeException("만료시간이 0보다 커야함!!!");
		}
		//알고리즘 타입 정하기
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		//====JWT "서명(Signature)" 발급을 해주는 메서드
		
		//byte단위의 key를 만들어줘야 함
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(BASEKEY);
		//java.security javax.crypto.spec.SecretKeySpec.SecretKeySpec(byte[] key, String algorithm)
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		//====
		
		return Jwts.builder() //header값이나 claim 정보를 넣을 수 있음
				.setSubject(subject) // 유저 아이디같은거
				.signWith(signingKey, signatureAlgorithm)
//				.signWith(signingKey,signatureAlgorithm) // 키만든거랑, 알고리즘 
				.setExpiration(new Date(System.currentTimeMillis()+expTime)) //만료시간 
				.compact()
				;
		
	}

	//토큰 검증하는 메서드 (보통은 boolean으로 return) 
	public String getSubject(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(DatatypeConverter.parseBase64Binary(BASEKEY)) // 32라인처럼 byte단위로
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		//subject만 꺼내오면 됨 (String)
		return claims.getSubject();
	}
	
}
