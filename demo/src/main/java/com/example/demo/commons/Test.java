/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2023. 4. 20
 * File Name : Test.java
 * DESC : 
*****************************************************************/
package com.example.demo.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;

public class Test {

	   public static void main(String[] args) {
		      try {
//		         String url = "http://hsitx:bluebay@118.67.154.170:8089/webapi/lua?start_stt.lua";
		         String url = "http://118.67.154.170:8089/webapi/lua?start_stt.lua";
//		         String space = "%20";
		         String space = " ";
		         String agentId = "test002";
		         String globalId = "CA8BFC24-E400-45A5-B45A-9379659459D6";
		         String phoneNumber = "01086546675";
		         String tenantId = "kcdc.go.kr";
		         String encodedAuth = Base64.getEncoder().encodeToString("hsitx:bluebay".getBytes("UTF-8")); 
		         
		         String fullUrl = url+URLEncoder.encode(space+phoneNumber+space+agentId+space+tenantId+space+globalId, "UTF-8");
//		         String fullUrl = url+space+phoneNumber+space+agentId+space+tenantId+space+globalId;
//		         String fullUrl = URLEncoder.encode(url+space+phoneNumber+space+agentId+space+tenantId+space+globalId, "UTF-8");
		         
		         URL obj = new URL(fullUrl);
		         
		         HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		         con.setConnectTimeout(10000);
		         con.setReadTimeout(10000);
		         con.setUseCaches(false);
		         con.setDoInput(true);
		         con.setRequestMethod("GET");
		         con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		         con.setRequestProperty("Authorization", "Basic "+ encodedAuth);
		         
		         int responseCode = con.getResponseCode();
		         BufferedReader in = null;
		         String inputLine;
		         StringBuffer response = new StringBuffer();
		         if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
		            Charset cs = Charset.forName("UTF-8");
		            in = new BufferedReader(new InputStreamReader(con.getInputStream(), cs));
		               while ((inputLine = in.readLine()) != null) {
		                   response.append(inputLine);
		               }
		               
		               in.close();
		         } else {
		            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		               while ((inputLine = in.readLine()) != null) {
		                   response.append(inputLine);
		               }
		               in.close();
		         }
		         
		         System.out.println(" METHOD : GET, URL : "+fullUrl + ", RETURN : "+response.toString());
		      } catch (IOException e) {
		         System.out.println(e.getMessage());
		      }
		   }

		}