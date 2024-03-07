/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 6.
 * File Name : NotificationCommon.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sseUtil;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.bootWorkout.demo1.common.util.YmlPropertyUtil;

import kr.co.ecoletree.common.util.MapBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationCommon {


	private final YmlPropertyUtil ymlPropertyUtil;
//	private final EmitterRepository emitterRepository;
	private final EmitterRepositoryList emitterRepository;

	private Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

//	Map<String,Long> subscribers = new HashMap<>();
	/** 구독 이벤트 등록
	 * @param id
	 * @return
	 */
	public SseEmitter subscribe(Long id) {
		log.info("subscribe:{}",ymlPropertyUtil.getProperty("sse.message.stream-create"));
		SseEmitter sseEmitter = createEmitter(id);
		sendToClient(id,MapBuilder.of("message",ymlPropertyUtil.getProperty("sse.message.stream-create"),"id",id,"data",""));
		return sseEmitter;
	}

	/** 클라이언트에게 데이터 전송
	 * @param id - 데이터 받을 사용자 아이디
	 * @param data 전송할 데이터
	 */
	public void sendToClient(Long id, Object data) {

		SseEmitter emitter = emitterRepository.get(id);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().id(String.valueOf(id)).name("message").data(data));
			} catch (IOException e) {
				log.info("클라이언트에 전송되지 않음:{}",id);
				//client에 전송 안됨
				emitterRepository.deleteById(id);
				emitter.completeWithError(e);
			}
		}

	}

	/** 사용자 아이디 기반으로 이벤트 emitter 생성
	 * @param id
	 * @return SseEmitter 생성된 이벤트 저장된 SseEmitter
	 */
	private SseEmitter createEmitter(Long id) {
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitterRepository.save(id,emitter);
		// Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
	}




}
