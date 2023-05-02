package com.bootWorkout.demo1.code.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootWorkout.demo1.code.mapper.CodeListMapper;
import com.bootWorkout.demo1.code.service.CodeListService;
import com.bootWorkout.demo1.common.exceptin.CustomException;

@Service
public class CodeListServiceImple implements CodeListService {

	@Autowired
	CodeListMapper mapper;
	
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
