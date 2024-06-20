/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 6. 20.
 * File Name : ItemSearchController.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.elastic.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.elastic.document.ItemDocument;
import com.bootWorkout.demo1.elastic.service.ItemSearchService;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemSearchController extends ETBaseController {

	private final ItemSearchService itemSearchService;

	@PostMapping("/search")
	public @ResponseBody Map<String, Object> search(@RequestBody Map<String, Object> param){
		String keyword = (String)param.get("name");
		List<ItemDocument> list = itemSearchService.getItemByName(keyword);
		return ResultUtil.getResultMap(list.size()>0, list);
	}

	@PostMapping("/create")
	public @ResponseBody Map<String, Object> create(@RequestBody ItemDocument itemDocument){

		ItemDocument doc = itemSearchService.createItem(itemDocument);
		return  ResultUtil.getResultMap(true, doc);
	}
}
