/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 7. 19.
 * File Name : BoardSaveDto.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.mongodb.dto;

import com.bootWorkout.demo1.mongodb.Board;

import lombok.Data;

@Data
public class BoardSaveDto {

	private String title;
	private String content;
	
	public Board toEntity() {
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		return board;
	}
}
