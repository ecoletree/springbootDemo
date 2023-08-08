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

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor  //dependency injection
@RestController
@RequestMapping("/mongo")
@Slf4j
public class BoardController extends ETBaseController{


	private final BoardRepository boardRepository; //dependency injection
	
	/** 하나씩 insert
	 * { "title":"title_test", "content":"content_test"} 
	 * @param param
	 * @return
	 */
	@RequestMapping("/insertOne")
	public  Map<String, Object> insertOne(@RequestBody Map<String,Object> param) {
		BoardSaveDto dto = new BoardSaveDto();
		Board board = dto.toEntity();
		board.setTitle(param.get("title").toString());
		board.setContent(param.get("content").toString());
		boardRepository.save(board); // save 함수는 같은 아이디면 수정한다.
		return ResultUtil.getResultMap(true, param);
	}
	/** 하나씩 delete
	 * @param dto
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public Map<String, Object> deleteById(@RequestBody BoardSaveDto dto, @PathVariable String id){
		boardRepository.deleteById(id);
		return ResultUtil.getResultMap(true, dto);
	}
	
	/** 검색어 포함하는 것 찾아보기 
	 * @param title
	 * @return
	 */
	@RequestMapping("/likeText/{title}")
	public List<Board> findLikeByText(@PathVariable String title) {
		List<Board> list = boardRepository.findLikeByText(title);
		return list;
	}	
//	@RequestMapping("/likeMap")
//	public List<Board> findLikeByMap(@RequestBody Map<String,Object> param){
//		List<Board> list = boardRepository.findLikeByMap(param);
//		return list;
//	}
}
