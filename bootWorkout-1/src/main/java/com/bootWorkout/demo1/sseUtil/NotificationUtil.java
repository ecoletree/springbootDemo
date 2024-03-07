/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 7.
 * File Name : NotificationCommon2.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.sseUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotificationUtil {

	public String SSE_CONNECT;
	public String SSE_SUCCESS;
	public String SSE_FAIL;

	public NotificationUtil(@Value("${sse.message.stream-create}") String sse_connect,@Value("${sse.message.success}") String sse_success,
			@Value("${sse.message.fail}") String sse_fail) {

		this.SSE_CONNECT = sse_connect;
		this.SSE_SUCCESS = sse_success;
		this.SSE_FAIL = sse_fail;

	}

	public void connectionTestMethod(Long id) {
		log.info("연결됨_id:{}",id);
	}

}
