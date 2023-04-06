package com.bootWorkout.demo1.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 김경현
 * @Builder : builder pattern 쓸 수 있게 함
 * @Data : @Getter / @Setter, @ToString, @EqualsAndHashCode와 
 * 	      @RequiredArgsConstructor 를 합친것과 같은 효과지만 DTO에 주로 쓰고
 *        @RequiredArgsConstructor는 bean에 많이 씀
 *  
 */
@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
	private String grantType;
	private String accessToken;
	private String refreshToken;
}
