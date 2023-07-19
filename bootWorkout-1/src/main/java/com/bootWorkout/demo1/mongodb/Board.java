/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 7. 19.
 * File Name : EventDoc.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Document(collection="firstcol")
@Getter
@Setter
public class Board {
	
	@Id
	private String _id;
	private String title;
	private String content;

}
