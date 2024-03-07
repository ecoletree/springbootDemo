/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 7.
 * File Name : YmlPropertyUtil.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.common.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YmlPropertyUtil {

	private final Environment environment;


    public String getProperty(String name){
        return environment.getProperty(name);
    }

}