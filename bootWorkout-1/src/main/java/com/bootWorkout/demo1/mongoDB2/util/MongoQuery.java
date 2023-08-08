package com.bootWorkout.demo1.mongoDB2.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MongoQuery {
	
	private static Map<String,Object> PARAM;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void setParam(Map<String, Object> param) {
		PARAM = param;
	}
	
	public static Criteria is(String key,String valKey) {
		Criteria criteria = Criteria.where(key).is(PARAM.get(valKey));
		return criteria;
	}

	public static Criteria like(String key) {
		Criteria criteria = Criteria.where(key).regex(PARAM.get(key).toString());
		return criteria;
	}

	public static Criteria in(String key, List<String> list) {
		Criteria criteria = Criteria.where(key).in(list);
		return criteria;
	}
	
	public static Criteria dateBetween(String key, String start, String end) {
			
		Date startDate = dateTime(PARAM.get(start).toString());
		Date endDate = dateTime(PARAM.get(end).toString()); // 초과로 해야
			
		Criteria criteria = Criteria.where(key).gt(startDate).lte(endDate);
		return criteria;
	}

	
	/** mongodb에 date 타입으로 넣도록 변경 
	 * @param dateStr
	 * @return
	 */
	public static Date dateTime(String dateStr) {
		sdf.setTimeZone(TimeZone.getTimeZone("KST"));
		Date date = new Date();;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

}
