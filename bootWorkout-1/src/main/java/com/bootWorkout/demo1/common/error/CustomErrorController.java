//package com.bootWorkout.demo1.common.error;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/error")
//public class CustomErrorController implements ErrorController{
//
//	private static String ERROR_VIEW = "errors";
//	
//	Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@RequestMapping("")
//	public String errorHandler(HttpServletRequest request) {
//		logger.info("errorHandler");
//		String view = "";
//
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		if(status != null) {
//			int errorCode = Integer.valueOf(status.toString());
//			
//			logger.info("error_code:"+errorCode);
//			
//			if(errorCode == HttpStatus.NOT_FOUND.value()) {
//				view = "redirect:/error/404";
//			}
//			
//			if(errorCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//				view =  ERROR_VIEW + "/500";
//			}
//		}
//		return view;
//	}
//	
//	
//	@RequestMapping("/404")
//	public String errorPage404(HttpServletRequest request) {
//		logger.info("open 404 page");
//		return ERROR_VIEW + "/404";
//	}
//	
//	@RequestMapping("/500")
//	public String errorPage500(HttpServletRequest request) {
//		logger.info("open 500 page");
//		return ERROR_VIEW + "/500";
//	}
//	
//	
//}
