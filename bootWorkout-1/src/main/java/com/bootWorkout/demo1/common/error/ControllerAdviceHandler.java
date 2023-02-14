package com.bootWorkout.demo1.common.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bootWorkout.demo1.common.exceptin.CustomException;



@ControllerAdvice // 해당 객체가 스프링의 컨트롤러에서 발생하는 예외를 처리하는 존재임을 명시.
public class ControllerAdviceHandler implements ErrorController{

	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/** Exception 발생에 예외처리
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)// 예외를 명시
	public ModelAndView handleException(Exception e) {
		
		if (e instanceof CustomException) {
			((CustomException) e).getExceptionDetail().getCode();
		}
		logger.info("error:::"+e.getCause());
		
		ModelAndView view=new ModelAndView("errors/404");
		view.addObject("exception", e.getMessage());
		
		return view;
	}	
	
	
	
	
}
