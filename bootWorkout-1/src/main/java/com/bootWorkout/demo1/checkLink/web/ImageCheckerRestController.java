/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 1. 17.
 * File Name : ImageCheckerRestController.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.checkLink.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootWorkout.demo1.checkLink.service.ImageCheckerRestService;

@RestController
@RequestMapping("/image")
public class ImageCheckerRestController {

	private final ImageCheckerRestService service;
	public ImageCheckerRestController(ImageCheckerRestService service) {
		this.service = service;

	}
	@SuppressWarnings("unchecked")
	@PostMapping(value = "", consumes = {APPLICATION_JSON_VALUE})
    public Map<String, Object> check(@RequestBody final Map options)  {
        return service.getMetaDataList(options);
    }
}
