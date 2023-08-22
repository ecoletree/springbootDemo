package com.bootWorkout.demo1.flutterTest.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootWorkout.demo1.flutterTest.mapper.FCMNotificationRequestDto;
import com.bootWorkout.demo1.flutterTest.mapper.FlutterTestCollections;
import com.bootWorkout.demo1.flutterTest.service.FCMSendService;
import com.bootWorkout.demo1.mogoDButil.MongoCRUD;
import com.bootWorkout.demo1.mogoDButil.MongoQuery;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import kr.co.ecoletree.common.base.service.ETBaseService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FCMSendServiceImpl extends ETBaseService implements FCMSendService {
	
	@Autowired	
	MongoCRUD<FlutterTestCollections> mongoUtil;

	private static String COLLECTION_NAME = "flutterAppToken";
	private static Class<?> COLLECTION_CLASS = FlutterTestCollections.class;
	
	private final FirebaseMessaging firebaseMessaging;
    
	@Override
	public Map<String, Object> sendMessage(Map<String, Object> param) {
		
		Map<String, Object> resultMap = new HashMap<>();
		String resultMsg = "";
		Query query = new Query();
		query.addCriteria(Criteria.where(""));
		
		// or and 쿼리 만들기
		List<FlutterTestCollections> list = mongoUtil.find(query,COLLECTION_NAME,COLLECTION_CLASS);
		
		logInfo("list::"+list.size());
		
		if(list.size()>0) {
			for(FlutterTestCollections user : list) {
				if(user.getfcmToken() != null) {
					Notification notification = Notification.builder()
	                        .setTitle(param.get("message_title").toString())
	                        .setBody(param.get("message_text").toString())
	                        // .setImage(requestDto.getImage())
	                        .build();
					Message message = Message.builder()
							.setToken(user.getfcmToken().toString())
							.setNotification(notification)
							.build();
					try {
						firebaseMessaging.send(message);
						resultMsg = "알림 성공";
					} catch (FirebaseMessagingException e) {
						e.printStackTrace();
						resultMsg = "알림 성공";
						
					}
				}else {
					resultMsg = "토큰 존재하지 않음";
				}
			}
		}else {

		}
		resultMap.put("resultMsg", resultMsg);
		
		return resultMap;
	}
	
	

}
