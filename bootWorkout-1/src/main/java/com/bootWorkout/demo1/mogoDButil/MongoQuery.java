package com.bootWorkout.demo1.mogoDButil;

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
	
	/** 
	 * @param key
	 * @return
	 */
	public static Criteria is(String key) {
		log.info("is 검색");
		Criteria criteria = Criteria.where(key).is(PARAM.get(key));
		return criteria;
	}
	
	/** 
	 * @param key
	 * @return
	 */
	public static Criteria isNot(String key) {
		log.info("is not 검색");
		Criteria criteria = Criteria.where(key).ne(PARAM.get(key));
		return criteria;
	}

	/** 
	 * @param key
	 * @return
	 */
	public static Criteria like(String key) {
		log.info("like 검색");
		Criteria criteria = Criteria.where(key).regex(PARAM.get(key).toString());
		return criteria;
	}
	
	/** 
	 * @param key
	 * @return
	 */
	public static Criteria notLike(String key) {
		log.info("not like 검색");
		Criteria criteria = Criteria.where(key).not().regex(PARAM.get(key).toString());
		return criteria;
	}
	/**
	 * @param key
	 * @param list
	 * @return
	 */
	public static Criteria in(String key, List<String> list) {
		log.info("in 검색");
		Criteria criteria = Criteria.where(key).in(list);
		return criteria;
	}
	
	/** 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Criteria dateBetween(String key, String start, String end) {
		log.info("date between 검색");
			
		Date startDate = dateTime(PARAM.get(start).toString());
		Date endDate = dateTime(PARAM.get(end).toString()); // 초과로 해야
			
		Criteria criteria = Criteria.where(key).gt(startDate).lte(endDate);
		return criteria;
	}

	/** or 
	 * @param criteriaList
	 * @return
	 */
	public static Criteria or(List<Criteria> criteriaList) {
		log.info("or 검색");
		Criteria criteria = Criteria.where("").orOperator(criteriaList);
		return criteria;
	}
	/** and
	 * @param criteriaList
	 * @return
	 */
	public static Criteria and(List<Criteria> criteriaList) {
		log.info("and 검색");
		Criteria criteria = Criteria.where("").andOperator(criteriaList);
		return criteria;
	}
	
	/** mongodb에 date 타입으로 넣도록 변경 
	 * @param dateStr
	 * @return
	 */
	public static Date dateTime(String dateStr) {
		log.info("String to Date 변경");
		sdf.setTimeZone(TimeZone.getTimeZone("KST"));
		Date date = new Date();;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			log.info("String to Date 변경 실패"+e.getMessage());
		}
		return date;
	}



}
