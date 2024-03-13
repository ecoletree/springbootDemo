/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 4.
 * File Name : NotificationService.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sse.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

	/**
	 * @param id
	 * @return
	 */
	public SseEmitter subscribe(String id);

	/**
	 * @param id
	 * @param string
	 */
	public void notify(String id, Object event);

	/**
	 * @param sseId
	 */
	public void remove(String sseId);

}
