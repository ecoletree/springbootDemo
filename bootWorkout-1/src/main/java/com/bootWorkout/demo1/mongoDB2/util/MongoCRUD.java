//package com.bootWorkout.demo1.mongoDB2.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Component;
//
//import com.bootWorkout.demo1.mongoDB2.mapper.UserCollections;
//import com.mongodb.client.result.DeleteResult;
//import com.mongodb.client.result.UpdateResult;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class MongoCRUD<T> {
//	
//	@Autowired
//	MongoTemplate mongoTemplate;
//	
//	// 이부분 어떻게 하는게 좋을지..?
//	private static String COLLECTION_NAME = "secondCol";
//	private static Class<?> COLLECTION_CLASS = UserCollections.class;
//	
//	/** update one
//	 * @param findKey : primary key 
//	 * @param param
//	 * @return
//	 */
//	public Map<String, Object> update(String findKey, Map<String, Object> param) {
//		
//		Map<String, Object> result = new HashMap<>();
//		
//		MongoQuery.setParam(param);
//		Query query = new Query(MongoQuery.is(findKey));
//		Update update = new Update();
//		
//		param.forEach((key,value)->{
//			update.set(key,value);
//		});
//		
//		UpdateResult updateResult = mongoTemplate.updateFirst(query,update,COLLECTION_CLASS);
//		
//		result.put("result", updateResult.wasAcknowledged());
//		
//		return result;
//	}
//	/** update bulk
//	 * @param findKey : primary key 
//	 * @param param
//	 * @return
//	 */
//	public Map<String, Object> update(String findKey, List<Map<String, Object>> paramList) {
//		
//		Map<String, Object> result = new HashMap<>();
//		
//		for(Map<String, Object> param : paramList) {
//		
//			result = update("_id",param);
//		
//		}
//		
//		return result;
//	}
//
//	/** insert one
//	 * @param param
//	 * @return
//	 */
//	public Map<String, Object> save(Map<String,Object> param) {
//		Map<String,Object> result = mongoTemplate.insert(param,COLLECTION_NAME);
//		return result;
//	}
//	
//	/** insert bulk
//	 * @param paramList
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> save(List<Map<String, Object>> paramList) {
//		Map<String,Object> result = (Map<String, Object>) mongoTemplate.insert(paramList, COLLECTION_NAME);
//		return result;
//	}
//	
//	/** find
//	 * @param query
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<T> find(Query query) {
//		
//		List<T> userList = (List<T>) mongoTemplate.find(query, COLLECTION_CLASS, COLLECTION_NAME);
//		
//		return userList;
//	}
//	
//	/** delete one
//	 * @param findKey
//	 * @param param
//	 * @return
//	 */
//	public Map<String, Object> delete(String findKey, Map<String, Object> param) {
//		Map<String, Object> result = new HashMap<>();
//
//		MongoQuery.setParam(param);
//		Query query = new Query(MongoQuery.is(findKey));
//		
//		DeleteResult delete = mongoTemplate.remove(query, COLLECTION_CLASS, COLLECTION_NAME);
//		
//		result.put("deleteResult", delete.wasAcknowledged());
//		
//		return result;
//	}
//	
//	/** delete bulk
//	 * @param findKey
//	 * @param paramList
//	 * @return
//	 */
//	public Map<String, Object> delete(String findKey, List<Map<String, Object>> paramList) {
//		
//		Map<String, Object> result = new HashMap<>();
//		
//		List<String> removeList = new ArrayList<>();
//		
//		for(Map<String,Object> param : paramList) {
//			removeList.add(param.get(findKey).toString());
//		}
//		Query query = new Query(MongoQuery.in(findKey,removeList));
//		
//		DeleteResult delete = mongoTemplate.remove(query, COLLECTION_CLASS, COLLECTION_NAME);
//		
//		result.put("deleteResult", delete.wasAcknowledged());
//		
//		return result;
//	}
//
//	
//	
//	
//	
//}                
