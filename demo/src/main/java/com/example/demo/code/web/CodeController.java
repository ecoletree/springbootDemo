package com.example.demo.code.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CodeController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/")
	public String hello() {
		log.debug("TEST");
		log.info("TEST");
		log.error("TEST");
		log.warn("TTT");
		return "index";
	}
}
