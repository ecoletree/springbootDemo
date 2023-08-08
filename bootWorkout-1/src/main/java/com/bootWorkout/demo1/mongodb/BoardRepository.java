/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 7. 19.
 * File Name : BoardRepository.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.mongodb;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BoardRepository extends MongoRepository<Board, String> {

	Board findByTitle(String title);
	
	@Query("{ $or:[{'title':{$regex:?0}},{'content':{$regex:?0}}] }")
	List<Board> findLikeByText(String title);

	@Query("{ $or:[{'title':{$regex:?0}},{'content':{$regex:?0}}] }")
	List<Board> findLikeByMap(Map<String, Object> param);
}