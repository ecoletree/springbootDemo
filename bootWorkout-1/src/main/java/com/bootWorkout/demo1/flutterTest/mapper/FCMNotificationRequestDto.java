package com.bootWorkout.demo1.flutterTest.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMNotificationRequestDto {

	private Long targetUserId;
	private String title;
	private String body;
	
	@Builder
	public FCMNotificationRequestDto(Long targetUserId, String title,String body) {
		this.targetUserId = targetUserId;
		this.body = body;
		this.title = title;
	}
}
