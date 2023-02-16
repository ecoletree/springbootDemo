/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : RecordUploadController.java
 * DESC : 음원업로드
*****************************************************************/
package com.example.demo.service.recordUpload.web;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.recordUpload.service.RecordUploadService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sound")
public class RecordUploadController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Value("${SOUND_SOURCE_PATH}")
	private String sourcePath;
	
	@Autowired
	RecordUploadService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** PBX 음원업로드 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("/pbxRecordUpload")
	public ModelAndView openPBX(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".pbxRecordUpload");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	}
	
	/** 
	 * IVR 음원업로드 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("/ivrRecordUpload")
	public ModelAndView openIVR(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".ivrRecordUpload");
		return mav;
	}
	
	/** 음원리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRecordList")
	public @ResponseBody Map<String, Object> getRecordList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getRecordUploadList(params);
	}
	
	/** 음원리스트 업로드
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setRecord")
	public @ResponseBody Map<String, Object> setRecordList(final MultipartHttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return ResultUtil.getResultMap(0 < service.setRecordUpload(params, request));
	}
	
	/** 음원리스트 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delRecord")
	public @ResponseBody Map<String, Object> delRecordList(@RequestBody Map<String, Object> param) throws Exception{
		return ResultUtil.getResultMap(0 < service.delRecordUpload(param));
	}
	
	/**
	 * html 오디오 파일 동적으로 src 가져오기 
	 * @param id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getAudio/{filePath}")
	public void getAudio(@PathVariable String filePath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// mp3 경로
		String filePaths = filePath.replaceAll("§", Matcher.quoteReplacement(File.separator));
		File initFile = new File(sourcePath+filePaths+".wav");
		Long startRange = 0l;
        Long endRange = 0l;
        Boolean isPartialRequest = false;
        
     // 파일 다운로드 이름 생성
        String downloadName = initFile.getName() + ".wav";
        String browser = request.getHeader("User-Agent");

        // 파일 인코딩
        if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
            // 브라우저 확인 파일명 encode
            downloadName = URLEncoder.encode(downloadName, "UTF-8").replaceAll("\\+", "%20");
        } else {
            downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
        }

        // 리스폰스 헤더 설정
        response.setHeader("Content-Disposition", "filename=\"" + downloadName +"\"");
        response.setContentType("audio/mpeg");
        response.setHeader("Accept-Ranges", "bytes"); // 크롬 구간문제 해결용
        response.setHeader("Content-Transfer-Encoding", "binary;");

        // 부분 범위 리퀘스트인지, 전체 범위 리퀘스트인지에 따라 Content-Range 값을 다르게
        if(isPartialRequest) {
            response.setHeader("Content-Range", "bytes " + startRange + "-"
                    + endRange + "/" + initFile.length());
        } else {
            response.setHeader("Content-Length", initFile.length() + "");
            response.setHeader("Content-Range", "bytes 0-"
                    + initFile.length() + "/" + initFile.length());
            startRange = 0l;
            endRange = initFile.length();
        }

        // 랜덤 액세스 파일을 이용해 mp3 파일을 범위로 읽기
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(initFile, "r");
            ServletOutputStream sos = response.getOutputStream();	){

            Integer bufferSize = 1024, data = 0;
            byte[] b = new byte[bufferSize];
            Long count = startRange;
            Long requestSize = endRange - startRange + 1;

            // startRange에서 출발
            randomAccessFile.seek(startRange);

            while(true) {
                // 버퍼 사이즈 (1024) 보다 범위가 작으면
                if(requestSize <= 2) {
                    // Range byte 0-1은 아래 의미가 아님.
                    // data = randomAccessFile.read(b, 0, requestSize.intValue());
                    // sos.write(b, 0, data);

                    // ** write 없이 바로 flush ** //
                    sos.flush();
                    break;
                }

                // 나머지는 일반적으로 진행
                data = randomAccessFile.read(b, 0, b.length);

                // count가 endRange 이상이면 요청 범위를 넘어선 것이므로 종료
                if(count <= endRange) {
                    sos.write(b, 0, data);
                    count += bufferSize;
                    randomAccessFile.seek(count);
                } else {
                    break;
                }

            }
            sos.flush();
        }
		
	}
	
	
}
