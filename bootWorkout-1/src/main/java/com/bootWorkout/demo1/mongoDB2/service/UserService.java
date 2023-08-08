package com.bootWorkout.demo1.mongoDB2.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.bootWorkout.demo1.mongoDB2.mapper.UserCollections;

public interface UserService {

	public Map<String,Object> saveOneUser(Map<String, Object> param) throws ParseException;

	public Map<String, Object> saveUserList(List<Map<String, Object>> paramList) throws ParseException;

	public Map<String,Object> updateOneUser(Map<String, Object> param) ;

	public Map<String, Object> updateUserList(List<Map<String, Object>> paramList) throws ParseException;

	public List<UserCollections> find(Map<String, Object> param);

	public Map<String, Object> deleteOne(Map<String, Object> param);

	public Map<String, Object> deleteList(List<Map<String, Object>> paramList);
}
