package com.example.demo.service.aniManager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnidialMgtMapper {

	public List<Map<String, Object>> selectAniManagerList(Map<String, Object> params);

	public int insertAnidialerList(Map<String, Object> params);

	public int updateAnidialerList(Map<String, Object> params);

	public int deleteAnidialerList(Map<String, Object> params);

	public List<Map<String, Object>> selectDialerList(Map<String, Object> params);

}
