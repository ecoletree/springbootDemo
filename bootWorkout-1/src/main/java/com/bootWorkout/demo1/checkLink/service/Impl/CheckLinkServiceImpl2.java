/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 9. 19.
 * File Name : CheckLinkServiceImpl.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.checkLink.service.Impl;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootWorkout.demo1.checkLink.service.CheckLinkService;

import kr.co.ecoletree.common.base.service.ETBaseService;
import lombok.RequiredArgsConstructor;
@Service
@Transactional
@RequiredArgsConstructor 
public class CheckLinkServiceImpl2 extends ETBaseService implements CheckLinkService {

	private final BrokenLinkChecker brokenLinkChecker;
	
	@Override
	public void checkBrokenLink(Map<String, Object> param) throws IOException{
		brokenLinkChecker.doLinkChecker(param.get("url").toString());
	}



}
