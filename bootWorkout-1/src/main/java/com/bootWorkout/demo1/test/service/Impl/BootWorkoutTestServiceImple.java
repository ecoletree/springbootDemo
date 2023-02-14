package com.bootWorkout.demo1.test.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootWorkout.demo1.common.exceptin.CustomException;
import com.bootWorkout.demo1.test.mapper.BootWorkoutTestMapper;
import com.bootWorkout.demo1.test.service.BootWorkoutTestService;

@Service
public class BootWorkoutTestServiceImple implements BootWorkoutTestService {

	@Autowired
	BootWorkoutTestMapper mapper;
	
	@Override
	public List<Map<String, Object>> getMenuList(Map<String, Object> param) throws CustomException {
		List<Map<String, Object>> list = null;
		try{
			list = mapper.getMenuList(param);
		}catch(Exception e){
			System.out.println("메뉴리스트에러");
			throw new CustomException(e);
		}
		return list;
	}
	
}
