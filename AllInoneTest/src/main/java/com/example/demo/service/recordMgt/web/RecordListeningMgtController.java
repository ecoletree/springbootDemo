/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 
 * Create Date : 2022. 10. 05.
 * File Name : RecordListeningMgtController.java
 * DESC :  녹음 청취 조회 관리
*****************************************************************/
package com.example.demo.service.recordMgt.web;

import static kr.co.ecoletree.common.ETCommonConst.ETFileConst.NewName;
import static kr.co.ecoletree.common.ETCommonConst.ETFileConst.OrgName;
import static kr.co.ecoletree.common.ETCommonConst.ETFileConst.Path;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.code.service.CodeService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.recordMgt.service.RecordListeningMgtService;
import com.example.demo.util.AllInoneLogUtil;
import com.example.demo.util.FileCryptoUtil;
import com.example.demo.util.AllInoneLogUtil.LOG_MESSAGE;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record/listeningMgt")
public class RecordListeningMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Value("${REC_SOURCE_PATH}")
	private String sourcePath;
	
	@Autowired
	RecordListeningMgtService service;
	
	@Autowired
	AllInoneLogUtil logUtil;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	@Autowired
	CodeService codeService;
	
	/** 녹음 청취로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".recordListening");
		Map<String, Object> params = new HashMap<String, Object>();
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				, "codeList", codeService.getCodeList(params)
				, "columnList", service.getColumnList(params)
				, "searchList", service.getSearchList(params)
				))
		);
		return mav;
	}
	
	/** 녹취 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRecordList")
	public @ResponseBody Map<String, Object> getRecordList(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParamToMap(request);
		return service.getRecordList(params);
	}
	
	/** 증서번호 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getCertificateList")
	public @ResponseBody Map<String, Object> getCertificateList(@RequestBody Map<String, Object> params) throws Exception {
		return ResultUtil.getResultMap(true,service.getCertificateList(params));
	}
	
	/** 녹취 전송 하기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/sendRecordFileTrans")
	public @ResponseBody Map<String, Object> sendRecordFileTrans(@RequestBody Map<String, Object> params) throws Exception {
		return ResultUtil.getResultMap(0 < service.sendRecordFileTrans(params));
	}
	
	/**
	 * html 오디오 파일 동적으로 src 가져오기 
	 * @param id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRecord/{filePath}")
	public void getAudio(@PathVariable String filePath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// mp3 경로
		String filePaths = filePath.replaceAll("§", Matcher.quoteReplacement(File.separator));
		File initFile = new File(filePaths+".aes");
		
		String outputFilePath = initFile.getParent()+File.separator+initFile.getName().substring(0,initFile.getName().lastIndexOf("."))+".mp3";
		boolean bb = FileCryptoUtil.decrypto(initFile.getPath(), outputFilePath);
		File outputFile = null;
		logInfo("INPUT PATH : "+bb + " / " + filePaths);
		logInfo("OUTPUT PATH : "+bb + " / "  + outputFilePath);
		
		if (bb) {
			outputFile = new File(outputFilePath);
		}
		if (outputFile != null) {
			Long startRange = 0l;
	        Long endRange = 0l;
	        Boolean isPartialRequest = false;
	        
	     // 파일 다운로드 이름 생성
	        String downloadName = outputFile.getName();
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
	                    + endRange + "/" + outputFile.length());
	        } else {
	            response.setHeader("Content-Length", outputFile.length() + "");
	            response.setHeader("Content-Range", "bytes 0-"
	                    + outputFile.length() + "/" + outputFile.length());
	            startRange = 0l;
	            endRange = outputFile.length();
	        }

	        // 랜덤 액세스 파일을 이용해 mp3 파일을 범위로 읽기
	        try(
	        	RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "r");
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
	            String msg = LOG_MESSAGE.RECORD_PLAY;
	            msg = msg.replaceAll("[$]data", outputFilePath.substring(outputFilePath.lastIndexOf(File.separator)+1,outputFilePath.lastIndexOf(".")));
	            logUtil.setLog(msg, ETSessionHelper.getUserId());
	            sos.flush();
	        }
		} else {
			throw new Exception();
		}
		
		
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/getRecordFile") //파일 다운로드
	private ModelAndView fileDownload (final ModelAndView mav, final HttpServletRequest request) throws Exception {
		final Map<String, Object> params = getParamToMap(request);
		mav.addObject(OrgName, Objects.requireNonNull(params.get("org_file")));
		mav.addObject(NewName, Objects.requireNonNull(params.get("new_file")));
		mav.addObject(Path, Objects.requireNonNull(params.get("path")));
		boolean b = false;
		if (params.get("is_enc").equals("Y")) { // 암호화 풀어야함
			String outFilePath = params.get("path") +""+params.get("new_file");
			b = FileCryptoUtil.decrypto((String)params.get("rec_file"), outFilePath);
		} else {
			//mav.addObject(NewName, Objects.requireNonNull(params.get("is_enc")));
		}
		String msg = LOG_MESSAGE.RECORD_DOWNLOAD;
		msg = msg.replaceAll("[$]data", (String)params.get("org_file"));
		logUtil.setLog(msg, ETSessionHelper.getUserId());
		mav.setViewName("ETFileDownloadView");
		return mav;
	}
	
	
}
