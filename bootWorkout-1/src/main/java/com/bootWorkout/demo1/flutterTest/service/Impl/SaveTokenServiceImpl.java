package com.bootWorkout.demo1.flutterTest.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootWorkout.demo1.flutterTest.mapper.FlutterBoardCollections;
import com.bootWorkout.demo1.flutterTest.mapper.FlutterTestCollections;
import com.bootWorkout.demo1.flutterTest.service.SaveTokenService;
import com.bootWorkout.demo1.mogoDButil.MongoCRUD;
import com.bootWorkout.demo1.mogoDButil.MongoQuery;

import kr.co.ecoletree.common.base.service.ETBaseService;

@Service
@Transactional
public class SaveTokenServiceImpl extends ETBaseService implements SaveTokenService {

	@Autowired	
	MongoCRUD<FlutterTestCollections> mongoUtil;

	private static String COLLECTION_NAME = "flutterAppToken";
	private static Class<?> COLLECTION_CLASS = FlutterTestCollections.class;
	
	/**
	 * 단말기 토큰 저장
	 */
	@Override
	public Map<String, Object> saveToken(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		String create_time = param.get("createDate").toString();
		param.replace("createDate", MongoQuery.dateTime(create_time));
		
		MongoQuery.setParam(param);
		Criteria criteria = Criteria.where("");
		criteria = MongoQuery.is("userId");
		Query query = new Query();
		query.addCriteria(criteria);
		// or and 쿼리 만들기
		List<FlutterTestCollections> list = mongoUtil.find(query,COLLECTION_NAME,COLLECTION_CLASS);
		if(list.size() > 0) {
			result = mongoUtil.update("userId",param,COLLECTION_CLASS);
		}else {
			result = mongoUtil.save(param,COLLECTION_NAME);
		}
		
		return result;
	}

	@Override
	public Map<String, Object> saveBoardData(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		String create_time = param.get("createDate").toString();
		param.replace("createDate", MongoQuery.dateTime(create_time));
		String update_time = param.get("updateDate").toString();
		param.replace("updateDate", MongoQuery.dateTime(update_time));
		
		MongoQuery.setParam(param);
		Criteria criteria = Criteria.where("");
		criteria = MongoQuery.is("id");
		Query query = new Query();
		query.addCriteria(criteria);
		// or and 쿼리 만들기
		List<FlutterTestCollections> list = mongoUtil.find(query,"flutterBoard",FlutterBoardCollections.class);
		if(list.size() > 0) {
			result = mongoUtil.update("userId",param,FlutterBoardCollections.class);
		}else {
			result = mongoUtil.save(param,"flutterBoard");
		}
		
		return result;
	}
	

}
