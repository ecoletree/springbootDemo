/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 1. 25.
 * File Name : RecordFtpMgtMapper.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.recordMgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordFtpMgtMapper {

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectFtpList(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public int selectFtpDupCheck(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public int insertFtpInfo(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public int updateFtpInfo(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public int deleteFtpInfo(Map<String, Object> params);

}
