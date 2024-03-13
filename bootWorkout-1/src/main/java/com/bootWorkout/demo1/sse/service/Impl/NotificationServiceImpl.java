/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 4.
 * File Name : NotificationServiceImpl.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sse.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.bootWorkout.demo1.sse.service.NotificationService;
import com.bootWorkout.demo1.sseUtil.NotificationCommon;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationCommon notificationCommon;

	@Override
	public SseEmitter subscribe(String id) {
		return notificationCommon.subscribe(id);
	}


	@Override
	public void notify(String id,Object event) {
		notificationCommon.notify(id,event);

	}


	@Override
	public void remove(String id) {
		notificationCommon.expire(id);


	}

}
