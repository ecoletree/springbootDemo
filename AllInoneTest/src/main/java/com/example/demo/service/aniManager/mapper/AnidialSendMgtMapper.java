package com.example.demo.service.aniManager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnidialSendMgtMapper {

	public List<Map<String, Object>> selectSendMgtList(Map<String, Object> params);

	public List<Map<String, Object>> selectSendMgtZipList(Map<String, Object> params);
	
	public List<Map<String, Object>> selectBroadbandCodeLists(Map<String, Object> params);

	public int insertAnidialSend(Map<String, Object> param);

	public int selectCountSendMgtizpList(Map<String, Object> params);

}
