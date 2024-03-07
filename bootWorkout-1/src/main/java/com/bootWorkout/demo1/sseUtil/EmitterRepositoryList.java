/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 4.
 * File Name : EmitterRepository.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.co.ecoletree.common.util.MapBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
@RequiredArgsConstructor
public class EmitterRepositoryList {
	 // vo로 변경하거나 List<Map<String,SseEmitter>> 로변경
	private final List<Map<String, Object>> emitterList = new ArrayList<>();

	/**
	 * @param id
	 * @return
	 */
	public void deleteById(Long id) {
		boolean keyExists = isKeyExist(id);
		if(keyExists) {
			// 리스트에 맵 삭제
			emitterList.stream().filter(map-> !id.equals(map.get("id"))).collect(Collectors.toList());
		}

	}

	/**
     * 주어진 아이디와 이미터를 저장
     *
     * @param id      - 사용자 아이디.
     * @param emitter - 이벤트 Emitter.
     */
    public void save(Long id, SseEmitter emitter) {

    	boolean keyExists = isKeyExist(id);

    	Map<String, Object> emit = MapBuilder.of("id", id, "emitter", emitter);

    	if(keyExists) {
    		// 값이 존재하면 해당 emitter map replace
    		log.info("emitter 존재");
    		emitterList.stream()
    		.map(map -> id.equals(map.get("id")) ? emit : map)
    		.collect(Collectors.toList());
    	}else {
    		// 값이 존재하지 않으면 새로운 emitter map 추가
    		log.info("emitter 신규");
    		emitterList.add(emit);
    	}
    }
    /** 아이디 구독중인지 확인
	 * @param id
	 * @return
	 */
	private boolean isKeyExist(Long id) {
		boolean keyExists = emitterList.stream().anyMatch(map -> map.containsKey("id") && id.equals(map.get("id")));
		return keyExists;
	}

	/**
     * 주어진 아이디와 이미터를 가져옴
     *
     * @param id      - 사용자 아이디.
     * @param emitter - 이벤트 Emitter.
     */
    public SseEmitter get(Long id) {
    	SseEmitter emits = null;
    	Map<String,Object> emitMap = emitterList.stream().filter(map-> id.equals(map.get("id"))).findFirst().orElse(null);

    	if (emitMap != null) {
			emits = (SseEmitter)emitMap.get("emitter");
		}

    	return emits;
    }

}
