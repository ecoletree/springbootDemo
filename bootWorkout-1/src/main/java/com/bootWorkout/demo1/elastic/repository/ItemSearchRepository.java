/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 6. 20.
 * File Name : ItemSearchRepository.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bootWorkout.demo1.elastic.document.ItemDocument;

public interface ItemSearchRepository extends ElasticsearchRepository<ItemDocument, Long>{

	public List<ItemDocument> findByName(String keyword);

}
