/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2022. 4. 11.
 * File Name : RecordUploadServiceImpl.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.recordUpload.service.Impl;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.exception.ETRuntimeException;
import kr.co.ecoletree.common.util.FileUtil;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.StringUtil;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.common.RecordUtil;
import com.example.demo.service.recordUpload.mapper.RecordUploadMapper;
import com.example.demo.service.recordUpload.service.RecordUploadService;
@Service
@Transactional
public class RecordUploadServiceImpl extends ETBaseService implements RecordUploadService {

	@Value("${SOUND_SOURCE_PATH}")
	private String sourcePath;
	
	@Autowired
	RecordUploadMapper mapper;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Map<String, Object> getRecordUploadList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectRecordUploadList(params);
			int totalCount = list.size();
			
			resultMap.put("recordsTotal", totalCount);
			resultMap.put("recordsFiltered", totalCount);
			resultMap.put("data", list);
		} catch (Exception e) {
			logError(e.getMessage(),e);
			throw e;
		}
		
		return resultMap;
	}
	
	@Override
	@Transactional
	public int setRecordUpload(Map<String, Object> params, MultipartHttpServletRequest request) {
		List<File> oldFileList = new ArrayList<File>();
		int i=0;
		
		List<Map<String, Object>> fileInfoList = null;
		if (params.get("source_type").equals("pbx")) {
			fileInfoList = getMultiPartFiles(request, generateSaveFileDir(sourcePath, (String)params.get("source_type"), (String)params.get("group_id"),(String)params.get("tenant_id")), generateFileName((String)params.get("source_type")), params, "sound_file");
		} else {
			fileInfoList = getMultiPartFiles(request, generateSaveFileDir(sourcePath, (String)params.get("source_type")), generateFileName((String)params.get("source_type")), params, "sound_file");
		}
			
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.UPDATE)) {
			if (!fileInfoList.isEmpty()) {
				String fileDir = (String)params.get("ori_file_path");
				File oldFile = new File(fileDir);
				if (oldFile.exists()) {
					oldFileList.add(oldFile);
				}
			}
		} else {
			params.put("soundsource_cd", StringUtil.getUUID("SOUND"));
		}
		try {
			if (bulkCreateFiles(fileInfoList, request) && mapper.upsertRecordUpload(rejectInvalidFileInfo(params)) < 1) {
				// 파일 업로드 혹은 공지사항 저장 실패
				i = 0;
				throw new ETRuntimeException();
			} else {
				i = 1;
			}
		} catch (Exception e) {
			// 수정 이전 기존 파일 삭제
			final List<File> delFileList = new ArrayList<File>();
			if (!fileInfoList.isEmpty()) {
				for (Map<String, Object> map : fileInfoList) {
					delFileList.add((File)map.get("file"));
				}
			}
			delFileList.forEach(FileSystemUtils::deleteRecursively);
			i = 0;
			throw e;
		}
		// 수정 이전 기존 파일 삭제
		oldFileList.forEach(FileSystemUtils::deleteRecursively);
		return i;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public int delRecordUpload(Map<String, Object> params) {
		List<File> oldFileList = new ArrayList<File>();
		List<Map<String, Object>> list = (List<Map<String, Object>>)params.get("sources");
			
		int i=mapper.deleteRecordUpload(params);
		if (0 < i) {
			for (Map<String, Object> map : list) {
				File file = new File((String)map.get("file_path"));
				if (file.exists()) {
					oldFileList.add(file);
				}
			}
		} 
		// 수정 이전 기존 파일 삭제
		oldFileList.forEach(FileSystemUtils::deleteRecursively);
		return i;
	}
	
	
	/**
	 * 업로드할 파일 정보 리스트 생성
	 *
	 * @param req 파일 정보가 들어있는 MultipartHttpServletRequest 객체
	 * @param saveDir 저장할 디렉터리명
	 * @param fileName 저장할 파일명
	 * @param target 생성된 업로드 파일 정보가 담길 맵 객체
	 * @param multipartFileKeys MultipartHttpServletRequest에서 파일에 접근할 수 있는 키
	 * @return
	 */
	private static List<Map<String, Object>> getMultiPartFiles(final MultipartHttpServletRequest req, final Supplier<String> saveDir, final Supplier<String> fileName, final Map<String, Object> target, final String... multipartFileKeys) {
		final Consumer<Map<String, Object>> consumer = map -> Arrays.asList("file_path", "file_name", "file_size").forEach(key -> target.put(key, map.get(key)));
		return getMultiPartFiles(req, saveDir, fileName, consumer, multipartFileKeys);
	}
	
	/**
	 * 업로드할 파일 정보 리스트 생성
	 *
	 * @param req 파일 정보가 들어있는 MultipartHttpServletRequest 객체
	 * @param saveDir 저장할 디렉터리명
	 * @param fileName 저장할 파일명
	 * @param consumer 생성된 업로드 파일 정보 처리 함수
	 * @param multipartFileKeys MultipartHttpServletRequest에서 파일에 접근할 수 있는 키
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<Map<String, Object>> getMultiPartFiles(final MultipartHttpServletRequest req, final Supplier<String> saveDir, final Supplier<String> fileName, final Consumer<Map<String, Object>> consumer, final String... multipartFileKeys) {
		final Stream<Map<String, Object>> stream = Stream.of(multipartFileKeys)
				.map(req::getFiles)
				.filter(multipartFiles -> !multipartFiles.isEmpty())
				.flatMap(List::stream)
				.map(multipartFile -> {
					final String mFileName = multipartFile.getOriginalFilename();
					String ext = null;
					if (-1 == mFileName.lastIndexOf(".")) {
						ext = "";
					} else {
						ext = mFileName.substring(mFileName.lastIndexOf("."));
					}
					final String path = saveDir.get() + File.separator + multipartFile.getOriginalFilename();

					return MapBuilder.of(
						"file", multipartFile
						, "file_path", path
						, "file_name", multipartFile.getOriginalFilename()
						, "file_size", multipartFile.getSize()
					);
				});

			if (Objects.nonNull(consumer))
				return stream.peek(consumer).collect(toList());

			return stream.collect(toList());
	}
	
	/**
	 * 업로드 폴더 경로 생성
	 *
	 * @param root 루트 디렉터리명
	 * @param steps 루트로부터 하위 디렉터리명
	 * @return
	 */
	private static Supplier<String> generateSaveFileDir(final String root, final String... steps) {
		if (steps.length > 0) {
			return () -> root + File.separator + String.join(File.separator, steps);
		}
		return () -> root;
	}
	
	/**
	 * 업로드 파일명 생성
	 * @return
	 */
	private static Supplier<String> generateFileName(String sourtType) {
		return () -> sourtType+Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * 복수개 파일 업로드 수행.
	 * 실패한 파일이 있을 경우 기존 업로드 성공한 파일 삭제
	 *
	 * @param fileInfos
	 * @param req
	 * @return
	 */
	private boolean bulkCreateFiles(final List<Map<String, Object>> fileInfos, final MultipartHttpServletRequest req) {
		
		final List<String> succeedList = fileInfos.stream()
				.map(map -> {
					try {
						return RecordUtil.createFile((MultipartFile) map.get("file"), req, String.valueOf(map.get("file_path")));
					} catch (IOException | ETException e) {
						logger.info("bulkCreateFiles : 복수 파일 업로드 실패");
					}
					return null;
				})
				.filter(Objects::nonNull)
				.collect(toList());

		final boolean result = fileInfos.size() == succeedList.size();
		if (!result && !succeedList.isEmpty()) {
			// 파일 생성 중 exception 발생하여 이미 생성된 파일 삭제
			succeedList.stream().allMatch(name -> FileUtil.deleteFile(req, name));
		}

		return result;
	}
	
	
	/**
	 * 첨부파일 정보가 하기와 같이 유효하지 않을 경우 맵에서 제거한다.
	 *
	 * file_size: java.Long으로 변경 불가능한 경우
	 * file_name: empty string 인 경우
	 * file_path: empty string 인 경우
	 *
	 * @param params
	 * @return
	 */
	private static Map<String, Object> rejectInvalidFileInfo(final Map<String, Object> params) {
		try {
			params.replace("file_size", Long.parseLong(String.valueOf(params.get("file_size"))));
		} catch (Exception e) {
			params.remove("file_size");
		}

		if ("".equals(params.get("file_name"))) {
			params.remove("file_name");
		}
		if ("".equals(params.get("file_path"))) {
			params.remove("file_path");
		}
		
		if (params.containsKey("soundsource_id")) {
			params.put("soundsource_id", Integer.parseInt((String) params.get("soundsource_id")));
		}

		return params;
	}

}
