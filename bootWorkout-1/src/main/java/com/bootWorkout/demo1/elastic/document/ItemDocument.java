/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 6. 20.
 * File Name : ItemDocument.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.elastic.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Document(indexName = "item")
@Mapping(mappingPath = "static/elastic-mapping.json")
@Setting(settingPath = "static/elastic-token.json",replicas = 0 )
public class ItemDocument {

	@Id
    @Field(name = "id", type = FieldType.Keyword)
    private String itemId;

    @Field(type = FieldType.Keyword)
    private String user;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Integer)
    private int price;

    @Field(type = FieldType.Text)
    private String itemImage;

}
