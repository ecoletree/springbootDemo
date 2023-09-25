/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 9. 19.
 * File Name : CheckLinkServiceImpl.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.checkLink.service.Impl;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootWorkout.demo1.checkLink.service.CheckLinkService;

import kr.co.ecoletree.common.base.service.ETBaseService;
//@Service
//@Transactional
public class CheckLinkServiceImpl extends ETBaseService implements CheckLinkService {

	@Override
	public void checkBrokenLink(Map<String, Object> param) throws IOException{
		
		String websiteUrl = "http://www.ecoletree.com/"; // Replace with the URL of the website you want to check
        List<String> brokenLinks = findBrokenLinks(websiteUrl);

        System.out.println("Broken Links:");
        for (String link : brokenLinks) {
            System.out.println(link);
        }

	}

	 public static List<String> findBrokenLinks(String url) throws IOException {
	        List<String> brokenLinks = new ArrayList<>();
	        Document doc = Jsoup.connect(url).get();

	        // Find all links on the page
	        for (Element link : doc.select("a[href]")) {
	            String linkUrl = link.absUrl("href");

	            // Check if the link is valid
	            if (!isValid(linkUrl)) {
	                brokenLinks.add(linkUrl);
	            }
	        }

	        return brokenLinks;
	    }

	    public static boolean isValid(String url) {
	        try {
	            HttpURLConnection.setFollowRedirects(false);
//	            HttpURLConnection connection = (HttpURLConnection) new URL(null,url, new sun.net.www.protocol.https.Handler()).openConnection();
	            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	            connection.setRequestMethod("HEAD");
	            int responseCode = connection.getResponseCode();
	            return (responseCode == HttpURLConnection.HTTP_OK);
	        } catch (IOException e) {
	            return false;
	        }
	    }

}
