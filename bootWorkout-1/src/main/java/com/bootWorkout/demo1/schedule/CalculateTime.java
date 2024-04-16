/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : kh201
 * Create Date : 2024. 4. 11.
 * File Name : CalculateTime.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.schedule;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CalculateTime {
    static LocalTime BREAK_STARTTIME = LocalTime.of(11, 30);
	static LocalTime BREAK_ENDTIME = LocalTime.of(13, 0);
	
	static DateTimeFormatter TIME_FORMATTER =  DateTimeFormatter.ofPattern("HHmm");
	public static void main(String[] args) {
		
		// List<Map<String,Object>> weekList / 전체리스트 (work_time)
		//vacation, holiday_code, date, 출퇴근시간, is_next -> weekList -> total_time
		calculateCommuteTimeSample();
		
		
	}

	@SuppressWarnings("unused")
	private static void calculateCommuteTimeSample() { //-
		//work_time 
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format("20240410");
		LocalDate commuteDate = LocalDate.parse(date);
		String is_next = "N";
//		is_next = "Y";
		
        LocalTime sTime = LocalTime.parse("1400", TIME_FORMATTER);
        LocalTime eTime = LocalTime.parse("1830", TIME_FORMATTER);
        
        // LocalDateTime.of(LocalDate date, LocalTime time);
		// 출근시간, 퇴근시간 값 구함 (익일퇴근일땐 하루 더함)
        LocalDateTime startTime = LocalDateTime.of(commuteDate, sTime);
		LocalDateTime endTime = LocalDateTime.of(is_next == "Y" ?commuteDate.plusDays(1):commuteDate, eTime);
		
		LocalDateTime  excludeStartTime = LocalDateTime.of(commuteDate, BREAK_STARTTIME); // 출근일의 11시 30분
		LocalDateTime  excludeEndTime = LocalDateTime.of(commuteDate, BREAK_ENDTIME); // 출근일의 13시 00분
		
		// 출근시간~11시 30분까지
		Duration amDuration = timeBetween(startTime,excludeStartTime);
        
		// 13시~ 퇴근시간까지 ( 13시 이후 출근인 경우 출근시간~퇴근시간)
		Duration pmDuration = timeBetween(startTime.isAfter(excludeEndTime) ?startTime: excludeEndTime, endTime);
        
        // 총합
        Duration totalDuration = amDuration.plus(pmDuration);///-> work_time
        
        String vacation = null;
//		vacation = "VACA001";
//		vacation = "VACA002";
        // 휴가값에 따라 시간 추가
        if(vacation != null) {
        	totalDuration = addVacationtime(vacation,totalDuration);
        }// total_time

        log.info("출근부터 11시30분까지: {}시간 {}분",amDuration.toHours(), amDuration.minusHours(amDuration.toHours()).toMinutes());
        log.info("13시부터 퇴근까지: {}시간 {}분",pmDuration.toHours(), pmDuration.minusHours(pmDuration.toHours()).toMinutes());
        // 결과 출력
        long hours = totalDuration.toHours();
        long minutes = totalDuration.minusHours(hours).toMinutes();

        log.info("총 근무시간: {}시간 {}분",hours, minutes);
		
	}

	/** 사이 시간 계산
	 * 음수는 0으로
	 * @param start
	 * @param end
	 * @return
	 */
	private static Duration timeBetween(LocalDateTime start, LocalDateTime end) {
		Duration duration =Duration.between(start, end);
		duration = (duration.isNegative() ? Duration.ZERO : duration);// 음수로 나왔을 때는 00시간 00분으로 나오도록
		return duration;
	}

	/** 휴가값에 따라 시간 더해서 반환
	 * @param vacation
	 * @param totalDuration
	 * @return
	 */
	private static Duration addVacationtime(String vacation,Duration totalDuration) {
		//
        if(vacation== "VACA002") {
        	totalDuration  = totalDuration.plusHours(3).plusMinutes(30);
        }else if(vacation=="VACA001") {
        	totalDuration  = totalDuration.plusHours(7);
        }
		return totalDuration;
	}

}

