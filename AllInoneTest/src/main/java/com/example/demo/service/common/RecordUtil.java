/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Jang Yoon Seok
 * Create Date : 2022. 4. 26.
 * File Name : RecordUtil.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.ecoletree.common.exception.ETException;

public class RecordUtil {

	private static final Logger logger = LoggerFactory.getLogger(RecordUtil.class);
	
	/**
	 * 파일생성
	 * @param fileData
	 * @param request
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String createFile(MultipartFile fileData, MultipartHttpServletRequest request, String fileName) throws IOException, ETException {
		String folderPath = fileName.substring(0,fileName.lastIndexOf(File.separator));
		File folder = new File(folderPath);
		
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		FileOutputStream fos = null;
		try {
			byte fileDataArr[] = fileData.getBytes();
			File newFile = new File(fileName);
			fos = new FileOutputStream(newFile);
			fos.write(fileDataArr);
		} catch (IOException e) {
			fileName = null;
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (fos != null)
				fos.close();
		}
		
		
		return fileName;
	}
}

