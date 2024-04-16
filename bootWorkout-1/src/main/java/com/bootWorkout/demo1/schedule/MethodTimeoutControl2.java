/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : kh201
 * Create Date : 2024. 4. 16.
 * File Name : MethodTimeoutControl.java
 * DESC : 메소드에 타임아웃 걸기
*****************************************************************/
package com.bootWorkout.demo1.schedule;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
public class MethodTimeoutControl2 {
    public static void main(String[] args) {
        boolean result = callExternalAPI(30);
        if (result) {
            System.out.println("API call successful");
        } else {
            System.out.println("API call timed out or failed");
        }
    }

    public static boolean callExternalAPI(int timeoutInSeconds) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Boolean> future = executor.submit(() -> {
            // 외부 API 호출 및 응답 대기 로직
            // 여기에 외부 API 호출 및 응답을 받는 코드를 작성하세요.
            // 예를 들어, 외부 API를 호출하는 코드는 아래와 같을 수 있습니다.
            // boolean apiResponse = externalAPICall();

            // 예시로 10초 대기 후 응답을 받는 것으로 가정
            Thread.sleep(10000);
            // 외부 API 응답 대신에 임의로 false 반환
            return false;
        });

        try {
            // 지정된 시간 내에 외부 API 호출이 완료되지 않으면 TimeoutException 발생
            return future.get(timeoutInSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            // 쓰레드가 중단되거나 실행 예외가 발생한 경우
            e.printStackTrace();
            return false;
        } catch (TimeoutException e) {
            // 지정된 시간 내에 외부 API 호출이 완료되지 않은 경우
            System.err.println("API call timed out");
            return false;
        } finally {
            executor.shutdown();
        }
    }
}
