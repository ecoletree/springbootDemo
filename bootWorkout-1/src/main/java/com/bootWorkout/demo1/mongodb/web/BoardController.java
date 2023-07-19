/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 7. 19.
 * File Name : BoardController.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.mongodb.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.mongodb.Board;
import com.bootWorkout.demo1.mongodb.BoardRepository;
import com.bootWorkout.demo1.mongodb.dto.BoardSaveDto;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  //dependency injection
@RestController
@RequestMapping("/mongo")
public class BoardController extends ETBaseController{


	private final BoardRepository boardRepository; //dependency injection
	
	
	/**
	 * insert&update
	 */
	@RequestMapping("/update/{id}")
	public Map<String, Object> update(@RequestBody BoardSaveDto dto, @PathVariable String id) {
		// TODO Auto-generated method stub
		Board board = dto.toEntity();
		board.set_id(id); // save 함수는 같은 아이디면 수정한다.
		boardRepository.save(board);
		return ResultUtil.getResultMap(true, dto);
	}
	
	@RequestMapping("/delete/{id}")
	public Map<String, Object> deleteById(@RequestBody BoardSaveDto dto, @PathVariable String id){
		boardRepository.deleteById(id);
		return ResultUtil.getResultMap(true, dto);
	}
	
	@RequestMapping("/find/{id}")
	public Board findById(@PathVariable String id) {
//		return boardRepository.findById(id).get();
		
		return boardRepository.findByTitleOrContent(id,id);
	}
//	
//	@RequestMapping("/findAll")
//	public List<Board> findAll() {
//		return boardRepository.findAll();
//	}
//	
//	@RequestMapping("/findByName")
//	public Map<String, Object> selectID(String id){
//		BasicDBObject
//		boardRepository.deleteById(id);
//		return null;
//	}
//	
	@RequestMapping("/like/{title}")
	public List<Board> findLikeByText(@PathVariable String title) {
//		
		List<Board> list = boardRepository.findLikeByText(title);
		return list;
	}	
}
