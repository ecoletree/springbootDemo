package com.bootWorkout.demo1.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 김경현
 * GenericFilterBean은 추상 클래스고 init(),doFilter(),destroy() 메소드가 있음
 * init(),destroy()는 default 이므로 구현 안해줘도 되지만 
 * doFilter()는 사용자 정의 구현 필수
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
	
	private final TokenProvider tokenProvider;
	
	/**
	 * doFilter()에서 인증/검사가 이뤄져야함 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		log.info("filter 시작");
		//1. HttpServletRequest header에서 토큰 정보 추출
		String token = resolveToken((HttpServletRequest) request);
		
		//2. 토큰 유효성 검사
		if(token != null && tokenProvider.validateToken(token)) {
			//유효토큰
			chain.doFilter(request, response);
		}else {
			//무효토큰
			//HTTP 403 Forbidden 클라이언트 오류 상태 
			//	-> 응답 코드는 서버에 요청이 전달되었지만, 권한 때문에 거절된 상태
			HttpServletResponse res = (HttpServletResponse)response;
			res.setStatus(403);
		}
		
	}

	/** HttpServletRequest header에서 토큰 정보 추출
	 * @param request
	 * @return
	 */
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
