package com.bootWorkout.demo1.common.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice // 해당 객체가 스프링의 컨트롤러에서 발생하는 예외를 처리하는 존재임을 명시.
public class ControllerAdviceHandler implements ErrorController{

	private static String ERROR_VIEW = "errors";
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(Exception.class)
	public String errorHandler(HttpServletRequest request) {
		logger.info("errorHandler");
		String view = "";

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if(status != null) {
			int errorCode = Integer.valueOf(status.toString());
			
			logger.info("error_code:"+errorCode);
			
			if(errorCode == HttpStatus.NOT_FOUND.value()) {
				logger.info("error_404");
//				view = "redirect:/error/404";
			}
			
			if(errorCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				logger.info("error_500");
//				view =  ERROR_VIEW + "/500";
			}
		}
		return view;
	}
	
	
	
	
}
