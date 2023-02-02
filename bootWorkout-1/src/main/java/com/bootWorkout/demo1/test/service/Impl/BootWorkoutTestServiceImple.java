package com.bootWorkout.demo1.test.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootWorkout.demo1.test.mapper.BootWorkoutTestMapper;
import com.bootWorkout.demo1.test.service.BootWorkoutTestService;

@Service
public class BootWorkoutTestServiceImple implements BootWorkoutTestService {

	@Autowired
	BootWorkoutTestMapper mapper;
	
	@Override
	public List<Map<String, Object>> getMenuList(Map<String, Object> param) {
		List<Map<String, Object>> list = mapper.getMenuList(param);
		return list;
	}
	
}
