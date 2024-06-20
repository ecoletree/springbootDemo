/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 6. 20.
 * File Name : ItemSearchServiceImpl.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.elastic.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bootWorkout.demo1.elastic.document.ItemDocument;
import com.bootWorkout.demo1.elastic.repository.ItemSearchRepository;
import com.bootWorkout.demo1.elastic.service.ItemSearchService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {

	private final ItemSearchRepository itemSearchRepository;

	public ItemDocument createItem(ItemDocument itemDocument) {

        return itemSearchRepository.save(itemDocument);
    }

	public List<ItemDocument> getItemByName(String keyword) {
        List<ItemDocument> byName = itemSearchRepository.findByName(keyword);
        return byName;
    }
}
