/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 2. 9.
 * File Name : ExceptionDetail.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.common.error;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/*
 * 예외가 발생했을 때 해당 예외 내용을 클라이언트에 전달할 수 있도록 예외 내용을 저장할 수 있는 클래스부터 만든다.
 */
public class ExceptionDetail {
	private String message;
	private int code;
	private Date timestamp;
	
	public ExceptionDetail(Exception e, int code) {
		this.code = code;
		this.timestamp = new Date();
		
		StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        this.message = errors.toString();
		
	}
	
	
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	

}
