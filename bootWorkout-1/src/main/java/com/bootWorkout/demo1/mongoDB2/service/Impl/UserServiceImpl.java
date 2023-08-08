package com.bootWorkout.demo1.mongoDB2.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bootWorkout.demo1.mongoDB2.mapper.UserCollections;
import com.bootWorkout.demo1.mongoDB2.service.UserService;
import com.bootWorkout.demo1.mongoDB2.util.MongoCRUD;
import com.bootWorkout.demo1.mongoDB2.util.MongoQuery;
import com.mongodb.client.result.UpdateResult;

import kr.co.ecoletree.common.base.service.ETBaseService;

@Service
public class UserServiceImpl extends ETBaseService implements UserService {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired	
	MongoCRUD<UserCollections> mongoUtil;
	
	private static String COLLECTION_NAME = "secondCol";
	private static Class<?> COLLECTION_CLASS = UserCollections.class;
	
	/**
	 * insert one
	 */
	@Override
	public Map<String, Object> saveOneUser(Map<String, Object> param) throws ParseException {
		String create_time = param.get("createDate").toString();
		param.replace("createDate", MongoQuery.dateTime(create_time));
		Map<String, Object> result =  mongoUtil.save(param);
		return result;
	}

	/**
	 * bulk insert
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveUserList(List<Map<String, Object>> paramList) throws ParseException {
		Map<String, Object> result = new HashMap<>();
		if(paramList.size() != 0) {
			for(Map<String, Object> param : paramList) {
				String create_time = param.get("createDate").toString();
				param.replace("createDate", MongoQuery.dateTime(create_time));
			}
			result =   mongoUtil.save(paramList);
		}
		return result;
	}

	/**
	 * update one
	 */
	@Override
	public Map<String, Object> updateOneUser(Map<String, Object> param) {
		String create_time = param.get("createDate").toString();
		param.replace("createDate", MongoQuery.dateTime(create_time));
		Map<String, Object> result = mongoUtil.update("_id",param);
		
		return result;
	}

	/**
	 * bulk update
	 */
	@Override
	public Map<String, Object> updateUserList(List<Map<String, Object>> paramList) throws ParseException {
		Map<String, Object> result = new HashMap<>();
		for(Map<String, Object> param : paramList) {
			String create_time = param.get("createDate").toString();
			param.replace("createDate", MongoQuery.dateTime(create_time));
		}
		result = mongoUtil.update("_id",paramList);
		return result;
	}

	/**
	 * find
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserCollections> find(Map<String, Object> param) {
		Query query = new Query();
		
		MongoQuery.setParam(param);
		
		Criteria criteria = Criteria.where("");
		
		switch(param.get("type").toString()) {
			case "or": // or + is
				criteria.orOperator(MongoQuery.is("_id","_id")
						,MongoQuery.is("userName","userName"));
				break;
			case "like":
				criteria = MongoQuery.like("userName");
				break;
			case "in":
				List<String> list = (List<String>) param.get("findList");
				criteria = MongoQuery.in("_id",list);
				break;
			case "dateBetween" : 
				criteria = MongoQuery.dateBetween("createDate", "startDate", "endDate");
				break;
			default : 
				criteria = MongoQuery.is("_id","_id");
		}
			
		query.addCriteria(criteria);
		// or and 쿼리 만들기
		// query를 리턴하게 바꾸기
		List<UserCollections> list = mongoUtil.find(query);
		
		return list;
	}
	/**
	 * delete one
	 */
	@Override
	public Map<String, Object> deleteOne(Map<String, Object> param) {
		Map<String, Object> result = mongoUtil.delete("_id",param);
		return result;
	}

	@Override
	public Map<String, Object> deleteList(List<Map<String, Object>> paramList) {
		Map<String, Object> result = mongoUtil.delete("_id",paramList);
		return result;
	}
}
