/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 6. 20.
 * File Name : ElasticsearchConfig.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

	@Value("${spring.data.elasticsearch.url}")
    private String ELASTIC_URL;

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
                .connectedTo(ELASTIC_URL)
                .build();
	}

}
