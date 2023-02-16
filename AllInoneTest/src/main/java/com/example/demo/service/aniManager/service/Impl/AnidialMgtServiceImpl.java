package com.example.demo.service.aniManager.service.Impl;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.exception.ETRuntimeException;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.FileUtil;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.StringUtil;
import com.example.demo.service.aniManager.mapper.AnidialMgtMapper;
import com.example.demo.service.aniManager.mapper.DiallerMgtMapper;
import com.example.demo.service.aniManager.service.AnidialMgtService;
import com.example.demo.service.code.mapper.CodeMapper;
import com.example.demo.service.code.util.CodeUtil;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.util.ExcelUtil;

@Service
public class AnidialMgtServiceImpl extends ETBaseService implements AnidialMgtService {

	@Autowired
	AnidialMgtMapper mapper;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	@Autowired
	CodeMapper codeMapper;
	
	@Autowired
	DiallerMgtMapper dialerMapper;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final Map<String, String> EXCEL_HEADER_MAPPER = MapBuilder.of(
            "spon_cd", "group_id", "센터", "center_id", "cntr_cd", "center_code"
            , "ad_cd", "dialer_cd", "지역", "broadband_name", "lcl_no", "broadband_code"
            , "out_no", "out_no", "in_no", "in_no", "from_dt", "start_date"
            ,"to_dt","end_date"
            
    );
	
	@Override
	public Map<String, Object> getCodeList(Map<String, Object> params) {
		Map<String, Object> resultMap =	MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				,"statusCodeList", codeMapper.selectCodeList(CodeUtil.createCodeListParam("ANI000"))
				,"broadbandCode", codeMapper.selectBroadbandCodeList()
				,"dialerList",setDialerHierarchy(mapper.selectDialerList(params),"group_id")
		);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getAniDialerList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectAniManagerList(params);
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

	@SuppressWarnings("unchecked")
	@Override
	public int setAnidialerList(Map<String, Object> params) {
		// DBTYPE = INSERT 할 건지 UPDATE 할것인지 체크
		final String user_id = ETSessionHelper.getUserId();
		params.put("user_id", user_id);
		int i=0;
		
		if (params.get("DBTYPE").equals(CommonConst.DataBaseConst.INSERT)) {
			int page = 100;
			ArrayList<Map<String, Object>> arrParam = new ArrayList<Map<String, Object>>();
			Map<String, Object> param = setPrimaryId(params);
			List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("anidialList");
			try {
				if(!list.isEmpty()) {
					
					int j = 0;
					for(Map<String, Object> map : list) {
						j++;
						arrParam.add(map);
						if (j != 0 && j % page == 0) {
							params.put("anidialList", arrParam);
							i += mapper.insertAnidialerList(params);
							arrParam.clear();
						}
					}//for
				}
				if (!arrParam.isEmpty()) {
					params.put("anidialList", arrParam);
					i += mapper.insertAnidialerList(params);
				}
				return i;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				i = -1;
				return i;
			}
		} else {
			i = mapper.updateAnidialerList(params);
		}
		
		return i;
	}

	/** 배열 안에 있는 정보 수정 및 추가
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> setPrimaryId(Map<String, Object> params) {
		Map<String, Object> resultMap = params;
		List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("anidialList") ;
		for(Map<String, Object> map : list) {
			map.put("animanger_cd", StringUtil.getUUID("ANI"));
			if(map.get("is_use") == null) {
				map.put("is_use", "Y");
			}
		}
		resultMap.put("anidialList", list);
		return resultMap;
	}

	@Override
	public int deleteAnidialerList(Map<String, Object> params) {
		return mapper.deleteAnidialerList(params);
	}

	@Override
	public Map<String, Object> excelDownloadAnidialList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectAniManagerList(params);
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
	public boolean excelUploadAnidialList(MultipartHttpServletRequest request) {
        final File excelFile = getTempExcelFile(request);
        List<Map<String, Object>> anidialList = setExceltoInsertData(excelFile);
        
        Map<String, Object> param = new HashMap<>();
        param.put("DBTYPE", CommonConst.DataBaseConst.INSERT);
        param.put("anidialList", anidialList);
        int i = setAnidialerList(param);
        
        final boolean isSuccess = i > 0;
        
        return isSuccess;
	}

	private List<Map<String, Object>> setExceltoInsertData(File excelFile) {
		final List<Map<String, Object>> anidialList;
		try {
        	anidialList = ExcelUtil.getXlsxDataWithHeader(excelFile, 0).get(0).stream()
                  .map(row -> {
                  final Map<String, Object> map = new HashMap<String, Object>();
                  row.forEach((header, value) -> map.put(EXCEL_HEADER_MAPPER.get(header), value));
                  return map;
              })
              .collect(Collectors.toList());
        	logger.info("엑셀 리스트로 변환");
        } catch (Exception e) {
        	logger.info("엑셀 리스트로 변환 실패");
            throw new ETRuntimeException(e);
        } finally {
            excelFile.delete();
            logger.info("엑셀 파일 삭제");
        }
		return anidialList;
	}

	private File getTempExcelFile(final MultipartHttpServletRequest request) {
        final MultipartFile excelMultipartFile = request.getFile("anidial_file");
        final String tempFileName = "anidialList" + System.currentTimeMillis() + ".xlsx";
        try {
            final String excelFileName = FileUtil.createFile(excelMultipartFile, request, "animanager" + File.separator + tempFileName);
            return Paths.get(FileUtil.getCustomerFile(request, excelFileName)).toFile();
        } catch (IOException | ETException e) {
            throw new ETRuntimeException(e);
        }
    }
	
	/** String cd가 key인 리스트 map 생성
	 * @param dialerList
	 * @param cd
	 * @return
	 */
	public static Map<String, Object> setDialerHierarchy(List<Map<String, Object>> dialerList, String cd) {
		List<Map<String, Object>> list = dialerList;
		List<Map<String, Object>> child = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String preTenantId = null;
		for (Map<String, Object> map : list) {
			String tenantId = map.get(cd).toString();
			if (preTenantId == null) {
				child = new ArrayList<Map<String,Object>>();
			} else if (!preTenantId.equals(tenantId) ) {
				returnMap.put(preTenantId, child);
				child = new ArrayList<Map<String,Object>>();
			}
			child.add(map);
			preTenantId = tenantId;
		}
		
		if (child != null && !child.isEmpty()) {
			returnMap.put(preTenantId, child);
		}
		returnMap.put("all", list);
		
		return returnMap;
	}
}
