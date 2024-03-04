/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 4.
 * File Name : NotificationServiceImpl.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sse.service.Impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.bootWorkout.demo1.sse.repository.EmitterRepository;
import com.bootWorkout.demo1.sse.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
	private final EmitterRepository emitterRepository;

	@Override
	public SseEmitter subscribe(Long id) {
		SseEmitter sseEmitter = createEmitter(id);
		sendToClient(id,"EventStream Created. [userId="+id+"]");
		return sseEmitter;
	}

	/** 사용자 아이디 기반으로 이벤트 emitter 생성
	 * @param id
	 * @return SseEmitter 생성된 이벤트 저장된 SseEmitter
	 */
	private SseEmitter createEmitter(Long id) {
		// TODO Auto-generated method stub
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitterRepository.save(id,emitter);
		// Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
	}

	/** 클라이언트에게 데이터 전송
	 * @param id - 데이터 받을 사용자 아이디
	 * @param data 전송할 데이터
	 */
	private void sendToClient(Long id, Object data) {

		SseEmitter emitter = emitterRepository.get(id);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().id(String.valueOf(id)).name("sse").data(data));
			} catch (IOException e) {
				//client에 전송 안됨
				emitterRepository.deleteById(id);
				emitter.completeWithError(e);
			}
		}

	}

	@Override
	public void notify(Long id,Object event) {
		sendToClient(id,event);

	}

}
