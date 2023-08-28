package com.bootWorkout.demo1.flutterTest.web;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.flutterTest.service.SaveTokenService;
import com.bootWorkout.demo1.mogoDButil.MongoQuery;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/restFlutter")
@Slf4j
public class SaveTokenController extends ETBaseController{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	SaveTokenService service;
	
	public static MongoQuery queries;
	
	/** insert
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/save", method = RequestMethod.POST) 
	public @ResponseBody Map<String,Object> saveToken(@RequestBody Map<String,Object> param) throws ParseException{
		log.info("save device token");
		Map<String, Object> result = service.saveToken(param);
		return ResultUtil.getResultMap(true, result);
	}
}
