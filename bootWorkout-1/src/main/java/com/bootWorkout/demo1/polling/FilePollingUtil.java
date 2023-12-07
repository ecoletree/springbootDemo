/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 12. 7.
 * File Name : FilePollingUtil.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.polling;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class FilePollingUtil {
	
	/** 파일 변경 감지
	 * @param dir
	 */
	public void watchFiles(String dir) {
		log.info("변경 감지 유틸");
		log.info("변경 감지할 디렉토리::"+dir);
		try {
			WatchService watch = FileSystems.getDefault().newWatchService();
			//어떤 디렉토리의 변경 감지 할지 지정
			Path path = Paths.get(dir);
			// 어떤 이벤트 감지할 지 지정
			path.register(watch, StandardWatchEventKinds.ENTRY_CREATE
								, StandardWatchEventKinds.ENTRY_MODIFY
								, StandardWatchEventKinds.ENTRY_DELETE);
			
			while(true) {
				WatchKey key = watch.take();
				List<WatchEvent<?>> list = key.pollEvents(); //이벤트를 받을 때까지 대기
				for(WatchEvent<?> event : list) {
					Kind<?> kind = event.kind();
					Path pth = (Path) event.context();
					if(kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
						//파일이 생성되었을 때 실행되는 코드
						log.info("파일 변경 감지 - 생성 : " + pth.getFileName());
					} else if(kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
						//파일이 삭제되었을 때 실행되는 코드
						log.info("파일 변경 감지 - 삭제 : " + pth.getFileName());
					} else if(kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
						//파일이 수정되었을 때 실행되는 코드
						log.info("파일 변경 감지 - 수정 : " + pth.getFileName());
					} else if(kind.equals(StandardWatchEventKinds.OVERFLOW)) {
						//운영체제에서 이벤트가 소실되었거나 버려질 경우 실행되는 코드
						log.info("OVERFLOW");
					}
				}
				if(!key.reset()) break; //키 리셋
			}
			watch.close();
			
		} catch (Exception e) {
			log.info("변경 감지 에러");
			log.info(e.getMessage());
		}
	}
	

}
