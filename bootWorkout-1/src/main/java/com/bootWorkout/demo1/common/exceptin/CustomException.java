/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 2. 9.
 * File Name : CustomExcepion.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.common.exceptin;

import com.bootWorkout.demo1.common.error.ExceptionDetail;

public class CustomException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ExceptionDetail eeee;
    public CustomException() {
        super();
    }

    public CustomException(final String msg) {
        super(msg);
    }

    public CustomException(final Throwable t) {
        super(t);
    }
    
    public CustomException(final Exception t) {
    	super(t);
    	setExceptionDetail(t, 404);
    }

    public CustomException(final String msg, final Throwable t) {
        super(msg, t);
    }
    
    public void setExceptionDetail (Exception t , int code) {
    	eeee = new ExceptionDetail(t, code);
    }
    
    
    public ExceptionDetail getExceptionDetail() {
    	return eeee;
    }
    
    
}
