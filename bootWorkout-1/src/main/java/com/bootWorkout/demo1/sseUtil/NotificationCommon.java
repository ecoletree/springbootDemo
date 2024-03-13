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
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.bootWorkout.demo1.common.util.YmlPropertyUtil;

import kr.co.ecoletree.common.util.MapBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationCommon {


	private final YmlPropertyUtil ymlPropertyUtil;
	private final EmitterRepository emitterRepository;

	private static String CONNECT_OPEN = "open";
	private static String SEND_DATA = "message";
//	private static String CONNECT_ERROR = "error";
	private Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
//	private Long DEFAULT_TIMEOUT = 60L * 1000;

	/** 구독 이벤트 등록
	 * @param id
	 * @return
	 */
	public SseEmitter subscribe(String id) {
		SseEmitter sseEmitter = createEmitter(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		sendToClient(id,CONNECT_OPEN,MapBuilder.of("id",id,"message",ymlPropertyUtil.getProperty("sse.message.stream-create")
				,"connected_time",sdf.format(now)));
		return sseEmitter;
	}

	/** 전송
	 * @param id
	 * @param data
	 */
	public void notify(String id, Object data) {
		sendToClient(id,SEND_DATA,data);
	}

	/**
	 * @param id
	 */
	public void expire(String id) {
		SseEmitter emitter = emitterRepository.get(id);
		boolean isComplete = true;
		if(emitter != null) {
			emitter.complete();
		}

	}

	/** 클라이언트에게  전송
	 * @param id - 데이터 받을 사용자 아이디
	 * @param data 전송할 데이터
	 */
	@SuppressWarnings("static-access")
	public void sendToClient(String id,String eventName, Object data) {

		SseEmitter emitter = emitterRepository.get(id);

		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
						.id(id)
						.name(eventName)
						.data(data)
						.comment("create_emitter")
						.reconnectTime(3000L));
			} catch (IOException e) {
				log.info("클라이언트에 전송되지 않음:{}",id);
				emitterRepository.deleteById(id);
				emitter.completeWithError(e);
			}
		}

	}

	/** 사용자 아이디 기반으로 이벤트 emitter 생성
	 * @param id
	 * @return SseEmitter 생성된 이벤트 저장된 SseEmitter
	 */
	private SseEmitter createEmitter(String id) {
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		// Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() ->{

        	 emitterRepository.deleteById(id);
        });
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> {
        	log.info("SseEmitter timeOut");
        	emitter.complete();
    	});
        emitter.onError((e)->{ // error 났을때
        	log.error("SseEmitter send error",e);
        	emitter.complete();
        });

        emitterRepository.save(id,emitter);
        return emitter;
	}






}
