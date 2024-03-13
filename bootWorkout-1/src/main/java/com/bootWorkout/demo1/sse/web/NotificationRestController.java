/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 4.
 * File Name : NotificationController.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sse.web;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.bootWorkout.demo1.sse.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationRestController {

	private final NotificationService notificationService;

    /** 클라이언트에서 구독하기 위함
     * @param id
     * @return
     */
    @GetMapping(value = "/subscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable String id) {
    	System.out.println("진입");

        return notificationService.subscribe(id);
    }

    /** 클라이언트로 알림을 주기 위함
     * @param id
     */
    @PostMapping("/send-data/{id}")
    public void sendData(@PathVariable String id, @RequestBody Map<String,Object> param){
    	param.put("id", id);
        notificationService.notify(id, param);
    }

}
