/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 1. 18.
 * File Name : ExcelUtil.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ExcelUtil {

	public static void WriteCsv(List<Map<String, Object>> list,String filepath) {

			log.info("csv 출력");
			File csv = new File("C:/Users/User/OneDrive/바탕 화면/csvtest/sample2.csv");
//			File csv = new File(filePath);
	        BufferedWriter bw = null; // 출력 스트림 생성
	        try {
	            bw = new BufferedWriter(new FileWriter(csv, true));
	            // csv파일의 기존 값에 이어쓰려면 위처럼 true를 지정하고, 기존 값을 덮어쓰려면 true를 삭제한다

	            if(!list.isEmpty()) {
	            	writeHeader(bw, list.get(0));
	            }
	         // 더미 데이터 추가
	            for (Map<String, Object> data : list) {
	                writeDataLine(bw, data);
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (bw != null) {
	                    bw.flush(); // 남아있는 데이터까지 보내 준다
	                    bw.close(); // 사용한 BufferedWriter를 닫아 준다
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	}
    private static void writeHeader(BufferedWriter writer, Map<String, Object> data) throws IOException {
        // CSV 파일에 헤더 추가
        for (String key : data.keySet()) {
            writer.write(escapeCsvValue(key) + ",");
        }
        writer.write("\n");
    }

    private static void writeDataLine(BufferedWriter writer, Map<String, Object> data) throws IOException {
        // CSV 파일에 데이터 행 추가
        for (Object value : data.values()) {
            writer.write(escapeCsvValue(value.toString()) + ",");
        }
        writer.write("\n");
    }

	private static String escapeCsvValue(String value) {
        // 값에 쉼표가 포함되어 있다면 값을 큰따옴표로 감싸주기
        if (value.contains(",")) {
            return "\"" + value + "\"";
        } else {
            return value;
        }
    }


    private static Map<String, Object> createUserData(int userId, String userName, int age, String startDate) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("user_id", userId);
        userData.put("user_name", userName);
        userData.put("age", age);
        userData.put("start_date", startDate);
        return userData;
    }

}
