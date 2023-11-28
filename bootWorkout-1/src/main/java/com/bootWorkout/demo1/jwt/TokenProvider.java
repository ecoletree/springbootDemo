package com.bootWorkout.demo1.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
/**
 * @author 김경현
 * TokenProvider.java - 토큰의 생성, 토큰의 유효성 검증 등을 담당
 * @Component : 어노테이션을 이용하면 Bean Configuration 파일에 Bean을 따로 등록하지 않아도 사용할 수 있다(생성자 없이 생성 바로 됨)
 * @Slf4j : 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음
 */
@Slf4j
@Component
public class TokenProvider {

	private final Key key;
	
	/** 키 생성
	 * @param secretKey
	 */
	public TokenProvider(@Value("${jwt.secret}") String secretKey) {
		/**
		 *  ** @Value(”${jwt.secret}”) String secretKey 
		 * :기존에 mvc모델에서 properties 파일에 있는 암복호화 키를 갖다 쓰려고 하는거였는데 
		 *  스프링부트에서는 yml에 jwt.secret으로 설정된 것을 String secretKey로 갖다 씀. 
		 *   64글자 이상을 써야됨(만들어주는 곳도 있어) 키가 고유해야하고 같아야함.  
		 *  
		 *  ** 
		 *  secret값을 Base64 Decode해서 key변수에 할당
		 */
		byte[] keyBytes = Decoders.BASE64.decode(secretKey); //byte단위의 key를 만들어줘야함. 
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	/** accessToken, refreshToken 생성
	 * TokenInfo.java에 토큰 정보 저장
	 * @param id
	 * @return
	 */
	public TokenInfo generateToken(String id) {
		
		long now = (new Date()).getTime();
		Date accessTokenExpiresIn = new Date(now + 86400000);// 24hour
		
		// Access Token 
		String accessToken = Jwts.builder() //header나 claim 정보를 넣을 수 있다. 
				.claim("email",id)
				.setSubject(id) // user id 같은거
				.setExpiration(accessTokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS256) // 암호화할 키와, 알고리즘
				.compact();// String으로 단순화
		
		// Refresh Token 
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000)) //24hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        /**
         * ** builder pattern 
         * 객체를 생성할 수 있는 빌더를 builder() 함수를 통해 얻고 거기에 셋팅하고자 하는 값을 셋팅하고 
         * 마지막에 build()를 통해 빌더를 작동 시켜 객체를 생성한다.
         * 빌더 패턴을 적용할 객체에 @Builder 어노테이션을 달아둔다.
         * 이점 : 생성자 파라미터의 수가 많아져도 가독성을 해치지 않음. 값 설정 순서 상관 없음.
         */
		return TokenInfo.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.grantType("Bearer")
				.build();
		
	}
	
	/** 토큰 검증 
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {
		try {
			// Claim.claims = 
			Jwts.parserBuilder() //검증할땐 parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token); // true false반환됨
			//.getBody() ; 
			// claims.getSubject() 하면 subject 꺼낼수 있음
			return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        } catch(Exception e) {
        	log.info("JWT Exception.", e);
        }
		return false;
	}

}
