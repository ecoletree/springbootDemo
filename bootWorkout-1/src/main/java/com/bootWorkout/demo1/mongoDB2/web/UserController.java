package com.bootWorkout.demo1.mongoDB2.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.bootWorkout.demo1.mogoDButil.MongoQuery;
import com.bootWorkout.demo1.mongoDB2.mapper.UserCollections;
import com.bootWorkout.demo1.mongoDB2.service.UserService;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/user")
@Slf4j
public class UserController extends ETBaseController{

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	UserService userService;
	
	public static MongoQuery queries;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** insert
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/save", method = RequestMethod.POST) 
	public @ResponseBody Map<String,Object> save(@RequestBody Map<String,Object> param) throws ParseException{
		log.info("create");
		Map<String, Object> result = userService.saveOneUser(param);
		return ResultUtil.getResultMap(true, result);
	}
	
	/** bulk insert
	 * @param param
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/saveList")
	public @ResponseBody Map<String,Object> save(@RequestBody List<Map<String,Object>> paramList) throws ParseException{
		Map<String, Object> result = userService.saveUserList(paramList);
		return ResultUtil.getResultMap(true,result);
	}
	/** update one
	 * @return
	 */
	@RequestMapping("/update") 
	public @ResponseBody Map<String,Object> update(@RequestBody Map<String,Object> param){
		Map<String, Object> result = userService.updateOneUser(param);
		return ResultUtil.getResultMap(true,result); 
	}
	
	/** bulk update
	 * @param paramList
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/updateList") 
	public @ResponseBody Map<String,Object> update(@RequestBody List<Map<String,Object>> paramList) throws ParseException{
		Map<String, Object> result = userService.updateUserList(paramList);
		return ResultUtil.getResultMap(true);
	}
	
	/** select
	 * @param param
	 * @return
	 */
	@RequestMapping("/read")
	public @ResponseBody Map<String,Object> readUserNameis(@RequestBody Map<String,Object> param){
		List<UserCollections> users = userService.find(param);
		return ResultUtil.getResultMap(true,users);
	}
	
	
	/** delete --> 일괄 삭제 가능하도록 메소드 따로 빼기 (범위 삭제 가능한지)
	 * @param param
	 * @return
	 */
	@RequestMapping("/delete")
	public @ResponseBody Map<String, Object> delete(@RequestBody Map<String,Object> param){
		Map<String, Object> result = userService.deleteOne(param);
		return ResultUtil.getResultMap(true,result);
	}
	
	@RequestMapping("/deleteList") 
	public @ResponseBody Map<String,Object> deleteList(@RequestBody List<Map<String,Object>> paramList) throws ParseException{
		Map<String, Object> result = userService.deleteList(paramList);
		return ResultUtil.getResultMap(true);
	}	
	

	
}
