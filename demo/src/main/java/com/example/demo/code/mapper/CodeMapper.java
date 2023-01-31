package com.example.demo.code.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeMapper {

	public List<Map<String, Object>> selectList (Map<String, Object> params);
}
