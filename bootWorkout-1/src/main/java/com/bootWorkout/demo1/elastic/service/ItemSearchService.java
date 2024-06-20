/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 6. 20.
 * File Name : ItemSearchService.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.elastic.service;

import java.util.List;

import com.bootWorkout.demo1.elastic.document.ItemDocument;

public interface ItemSearchService {

	/**
	 * @param keyword
	 * @return
	 */
	public List<ItemDocument> getItemByName(String keyword);

	/**
	 * @param itemDocument
	 * @return
	 */
	public ItemDocument createItem(ItemDocument itemDocument);

}
