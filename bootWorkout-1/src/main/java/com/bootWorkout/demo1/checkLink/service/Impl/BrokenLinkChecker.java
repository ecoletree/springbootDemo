/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 9. 21.
 * File Name : BrokenLinkChecker.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.checkLink.service.Impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
@Configuration
public class BrokenLinkChecker {
	
	public Logger log = LoggerFactory.getLogger(getClass());
	
	private static WebDriver driver = null;
	
	public List<Map<String,Object>> doLinkChecker(String pageUrl)  {
		List<Map<String,Object>> result = new ArrayList<>();
		
		setWebDriver(pageUrl);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlToBe(pageUrl));//-> 명시적 대기
		
		List<Map<String,Object>> aLink = checkALink(pageUrl,"a","href");
		
		try {
			checkSourceChecker("img","src");
			checkSourceChecker("source","src");
			
		} catch (ClientProtocolException e) {
			log.info(e.getMessage());
		} catch (IOException e) {
			log.info(e.getMessage());
		}finally {
			driver.quit();
		}
		
		log.info("do checker finish");
		
		driver.quit();
		return null;
	}
	/**
	 * @param pageUrl
	 * @return
	 */
	private List<Map<String, Object>> checkALink(String pageUrl,String tagName, String attr) {
		String url = "";
		HttpURLConnection huc = null;
		int respCode = 200;
	        List<WebElement> links = driver.findElements(By.tagName(tagName));
	        Iterator<WebElement> it = links.iterator();
	        
	        while(it.hasNext()){
	            
	            url = it.next().getAttribute(attr);
	            
	            log.info(url);
	            if(url == null || url.isEmpty()){
	            	log.info("URL is either not configured for anchor tag or it is empty");
	                continue;
	            }
	            if(!url.startsWith(pageUrl)){ // pageUrl이 메인이 아닐때 
	            	log.info("URL belongs to another domain, skipping it.");
	                continue;
	            }
	            
	            try {
	                huc = (HttpURLConnection)(new URL(url).openConnection());
	                
	                huc.setRequestMethod("HEAD");
	                
	                huc.connect();
	                
	                respCode = huc.getResponseCode();
	                log.info("status:::"+respCode);
//	                log.info("test:::"+it.next().getTagName()+"/"+attr);
	                if(respCode >= 400){
	                	log.info(url+" is a broken link");
	                }
	                else{
	                	log.info(url+" is a valid link");
	                }
	                    
	            } catch (MalformedURLException e) {
	            	log.info(e.getMessage());
	            } catch (IOException e) {
	            	log.info(e.getMessage());
	            }
	        }
		return null;
	}
	/**
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private List<Map<String, Object>> checkSourceChecker(String tagName, String attr) throws ClientProtocolException, IOException {
		List<WebElement> source_list = driver.findElements(By.tagName(tagName));
        for (WebElement src : source_list)
        {
            if (src != null)
            {
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(src.getAttribute(attr));
                HttpResponse response = client.execute(request);
                /* For valid images, the HttpStatus will be 200 */
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    System.out.println(src.getAttribute("outerHTML") + " is a broken link");
                    
                }
                log.info("status:::"+response.getStatusLine().getStatusCode());
                log.info("outerHTML:::"+src.getAttribute("outerHTML") );
                log.info("test:::"+src.getTagName()+"/"+attr);
            }
        }
		return null;
	}
	
	/** web driver set
	 * @param pageUrl
	 * @return
	 */
	private void setWebDriver(String pageUrl) {
		
		WebDriverManager.chromedriver().setup();
        
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");	
        driver = new ChromeDriver(chromeOptions);
        
        driver.manage().window().maximize();
        log.info("set web driver");
        driver.get(pageUrl);
        
	}

	
		
}


