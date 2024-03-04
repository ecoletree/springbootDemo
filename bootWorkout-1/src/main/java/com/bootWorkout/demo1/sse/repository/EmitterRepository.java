/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 4.
 * File Name : EmitterRepository.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sse.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class EmitterRepository {
	 // 모든 Emitters를 저장하는 ConcurrentHashMap
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	/**
	 * @param id
	 * @return
	 */
	public void deleteById(Long id) {
		emitters.remove(id);
	}

	/**
     * 주어진 아이디와 이미터를 저장
     *
     * @param id      - 사용자 아이디.
     * @param emitter - 이벤트 Emitter.
     */
    public void save(Long id, SseEmitter emitter) {
        emitters.put(id, emitter);
    }
    /**
     * 주어진 아이디와 이미터를 가져옴
     *
     * @param id      - 사용자 아이디.
     * @param emitter - 이벤트 Emitter.
     */
    public SseEmitter get(Long id) {
    	return emitters.get(id);
    }

}
