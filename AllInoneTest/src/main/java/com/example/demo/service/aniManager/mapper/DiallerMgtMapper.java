package com.example.demo.service.aniManager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiallerMgtMapper {

	public List<Map<String, Object>> selectDialerList(Map<String, Object> params);

	public int insertDialerList(Map<String, Object> params);

	public int updateDialerList(Map<String, Object> params);

	public int deleteDialerList(Map<String, Object> params);

	public int selectDialerCheck(Map<String, Object> params);

}
