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
		while(true) { //tread sleeping 걸어서 하거나 해서 에러 시간 늦추기
			try{
				String str = null;
				str.indexOf(0);
			}catch(Exception e) {
				/*
				 * id,message,stacktrace(원래 보내면 안되는데 개발자 보라
				 * event -> error stacktrace 넣어서 보내게 됨
				 * exception은 데이터 체크할때 많이 뜨는거고
				 * onerror는 서버가 죽거나 해서 손댈수 없는 상황일때 보내짐
				 */
				notificationCommon.notify(id,event);
			}
		}

	}

	@Override
	public void remove(String id) {
		notificationCommon.expire(id);


	}

}
