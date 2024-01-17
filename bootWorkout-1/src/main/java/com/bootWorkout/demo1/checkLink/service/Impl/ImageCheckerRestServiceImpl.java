/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 1. 17.
 * File Name : ImageCheckerRestServiceImpl.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.checkLink.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootWorkout.demo1.checkLink.service.ImageCheckerRestService;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.xmp.XmpDirectory;

import kr.co.ecoletree.common.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ImageCheckerRestServiceImpl implements ImageCheckerRestService {

	/**
	 *
	 */
	@Override
	public Map<String, Object> getMetaDataList(Map<String, Object> params) {

		/**
		 * 이미지링크 하나당 이 리스트가 나옴 -> 이미지 링크 뽑아서
		 */
		List<Map<String, Object>> mdList = getImageMetaData(params.get("url").toString());


		return MapBuilder.of("meta_data_length", mdList.size(),"metaData",mdList);
	}

	/**
	 * @param string
	 * @return
	 */
	private List<Map<String, Object>> getImageMetaData(String url) {
		List<Map<String, Object>>  resultList = new ArrayList<Map<String, Object>> ();
		try {
			// 대상 이미지의 Url
			InputStream input = new URL(url).openStream();

            Metadata metadata = ImageMetadataReader.readMetadata(input);

            for (Directory directory : metadata.getDirectories()) {
	            for (Tag tag : directory.getTags()) {
	            	String dirName = tag.getDirectoryName();
	    			String tagName = tag.getTagName();
					String desc = tag.getDescription();
					resultList.add(Map.of("directory", dirName, "key", tagName, "value", desc));
	            }
	            //XMP 의 경우
	            if (directory instanceof XmpDirectory) {
//	            	  for (XmpDirectory xmpDirectory : metadata.getDirectoriesOfType(XmpDirectory.class)) {
//	            		  XMPMeta xmpMeta = xmpDirectory.getXMPMeta();
//	            		  XMPIterator itr = xmpMeta.iterator();
//	                      // Iterate XMP properties
//	                      while (itr.hasNext()) {
//	                          XMPPropertyInfo property = (XMPPropertyInfo) itr.next();
//	                          // Print details of the property
//	                          System.out.println(property.getPath() + ": " + property.getValue());
//	                      }
//	            	  }
	            	String directoryName = directory.getName();
	                Map<String, String> xmpProperties = ((XmpDirectory)directory).getXmpProperties();
	                for (Map.Entry<String, String> property : xmpProperties.entrySet()) {
	                    String key = property.getKey();
	                    String value = property.getValue();
	                    if (value != null && value.length() > 1024) {
	                        value = value.substring(0, 1024) + "...";
	                    }
	                    resultList.add(Map.of("directory", directoryName, "key", key, "value", value));
	                }
	            }
	            //추출 에러시 처리.
//	            for (String error : directory.getErrors()) {
//	                System.err.println("ERROR: " + error);
//	            }
	        }
        } catch (ImageProcessingException e) {
        	log.info(e.getMessage());
        } catch (MalformedURLException e) {
        	log.info(e.getMessage());
        } catch (IOException e) {
        	log.info(e.getMessage());
		}

		return resultList;
	}

}
