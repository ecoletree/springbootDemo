package com.example.demo.service.aniManager.service.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.util.FileUtil;
import kr.co.ecoletree.common.util.LogUtil;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.PropertyUtil;
import com.example.demo.service.aniManager.mapper.AnidialSendMgtMapper;
import com.example.demo.service.aniManager.service.AnidialSendMgtService;
import com.example.demo.service.code.mapper.CodeMapper;
import com.example.demo.service.code.util.CodeUtil;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.util.SFTPConnection;

@Service
public class AnidialSendMgtServiceImpl extends ETBaseService implements AnidialSendMgtService {

	@Autowired
	AnidialSendMgtMapper mapper;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	@Autowired
	CodeMapper codeMapper;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final List<String> HEADERNAMES = Arrays.asList("group_dialer_name", "center_code", "zip_code", "index", "out_no", "broadband_code", "start_date", "end_date");

	@Override
	public Map<String, Object> getCodeList(Map<String, Object> params) {
		Map<String, Object> resultMap =	MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
				,"broadbandCode", codeMapper.selectBroadbandCodeList()
				,"statusCodeList", codeMapper.selectCodeList(CodeUtil.createCodeListParam("ANI000"))
		);
		return resultMap;
	}
	
	
	/**
	 * 전송 리스트 검색
	 */
	@Override
	public Map<String, Object> getSendMgtList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectSendMgtList(params);
			int allCnt = mapper.selectCountSendMgtizpList(params);
			int totalCount = list.size();
			
			resultMap.put("recordsTotal", totalCount);
			resultMap.put("recordsFiltered", totalCount);
			resultMap.put("data", list);
			resultMap.put("allCnt", allCnt);
		} catch (Exception e) {
			logError(e.getMessage(),e);
			throw e;
		}
		
		return resultMap;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> sendAnidialerList(Map<String, Object> param, HttpServletRequest req) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		PropertyUtil util = null;
		try {
			util = PropertyUtil.getInstance("message/messages.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("message property 인스턴스 가져오기 실패");
		}
		if (util != null) {
			String DIALER_AIO = util.getProperties("DIALER_AIOS");
			String DIALER_ETC = util.getProperties("DIALER_ETC");
			String[] AIO = DIALER_AIO.split(",");
			String[] HM = DIALER_ETC.split(",");
			// 타입 나눔 
			List<Map<String,Object>> dataList = (List<Map<String, Object>>) param.get("sendData"); 
			Map<String,Object> insertMap =  devideTypeList(dataList);
			
			//type 이 DIALER_AIO 중 하나일 때, DB등록 후 등록 성공시 csv파일 생성 
			for (String key : AIO) {
				if(insertMap.containsKey(key)) {
					List<Map<String,Object>> insertList = new ArrayList<Map<String,Object>>();
					List<Map<String, Object>> aios_list = (List<Map<String, Object>>) ((Map<String, Object>) insertMap.get(key)).get("sendData");
					
					// 리스트 나누고 리스트 별로 인서트를 한 뒤에 리스트 별 인서트 성공했는지 확인할 수 있어야됨
					insertList = devideCreateCSVInsertList(aios_list,req,key);
					
					resultList.addAll(insertList);
				}
			}
			//type 이 DIALER_ETC 중 하나일 때, CSV파일 생성 후 FTP전송
			for (String key : HM) {
				
				if(insertMap.containsKey(key)) {
					List<Map<String,Object>> ftpResult = new ArrayList<Map<String,Object>>();
					List<Map<String, Object>> hanmac_list = (List<Map<String, Object>>) ((Map<String, Object>) insertMap.get(key)).get("sendData");
					
					List<Map<String, Object>> ftpList = devideCreateCSVInsertList(hanmac_list,req,key);
					
					
					Map<String, Object> ftp = new HashMap<String,Object>();;
					for(Map<String, Object> ftpMap : ftpList) {
						if(ftpMap.get("msg").equals(ETCommonConst.SUCCESS)){
							ftp = ftpSendList(ftpMap);
							ftpResult.add(ftp);
						}else {
							ftpResult.add(ftpMap);
						}
					}
					resultList.addAll(ftpResult);
					
				}
			}
			resultMap.put("resultList", resultList);
		}
		
		return resultMap;
	}


	/** 리스트 나눠서 CSV 파일 생성 후 DB 인서트
	 * @param list
	 * @param req
	 * @param type 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> devideCreateCSVInsertList(List<Map<String, Object>> list, HttpServletRequest req, String type) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		// 1. 입력한 컬럼명 기준으로 리스트 나눠짐 
		Map<String,Object> codeMap =  devideSendList(list, "center_code","dialer_cd");
		List<String> nameList = (List<String>) codeMap.get("names");
		Map<String,Map<String, Object>> dataMap = (Map<String, Map<String, Object>>) codeMap.get("datas");
		
		// 2. 나눠진 리스트 기준으로 csv파일 생성임
		if(!nameList.isEmpty()) {
			for (int j = 0; j < nameList.size(); j++) {
				String key = nameList.get(j);
				int i =0;
				Map<String, Object> csv = createCSVFiles(dataMap.get(key),req);
				
				//csv 생성 성공 시에 
				if(csv.get("msg").equals(ETCommonConst.SUCCESS )) {
					i =  insertSendLists(dataMap.get(key));
					logger.info("csv생성 후 db insert");
				}else {
					logger.info("csv생성 실패 후 db insert 안함");
				}
				
				String msg =  i > 0 ? ETCommonConst.SUCCESS : ETCommonConst.FAILED;
				csv.put("insert_msg", msg);
				
				resultList.add(csv);
			}
		}
		return resultList;
	}


	/** 100건씩 나눠서 insert
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private int insertSendLists(Map<String, Object> params) {
		// db저장은 전부다 (100건씩 나눠서)

		int page = 50;
		ArrayList<Map<String, Object>> arrParam = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("sendData");
		int i = 0;
		try {
			if(!list.isEmpty()) {
				
				int j = 0;
				for(Map<String, Object> map : list) {
					j++;
					arrParam.add(map);
					if (j != 0 && j % page == 0) {
						params.put("sendData", arrParam);
						i += mapper.insertAnidialSend(params);
						arrParam.clear();
					}
				}//for
			}
			if (!arrParam.isEmpty()) {
				params.put("sendData", arrParam);
				i += mapper.insertAnidialSend(params);
			}
			logger.info("DB insert success");
		} catch (Exception e) {
			logger.info("DB insert fail");
			i = -1;
		}
		return i;
	
	}

	/** ftp전송
	 * @param param
	 * @return
	 */
	public Map<String, Object> ftpSendList(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		SFTPConnection conn = new SFTPConnection();
		
		String url = (String) param.get("url"); 
		int port = (int) param.get("port");
		String user = (String) param.get("user");
		String pwd = (String) param.get("pwd");
		String filePath = param.get("filePath").toString() +File.separator+ param.get("fileTitle").toString();
		String dir = param.get("dir").toString().length() == 0 ? "/": param.get("dir").toString();
		String msg =  ETCommonConst.SUCCESS;
		
		File file = new File(filePath); // 서버에 파일 생성할때
		try {
			
			ChannelSftp ss =  conn.ftpConnect(url, port, user, pwd);
			
			if (file.exists()) {
				conn.upload(ss, file, dir);
			} else {
				
				logger.info("파일전송완료여부 : "+conn.isComplate());
			}
			
			conn.disconnect();
			
			
		} catch (JSchException e) {
			logger.info("파일전송실패");
			msg = ETCommonConst.FAILED;
			file.delete();
		} catch (IOException e) {
			logger.info("파일전송실패");
			msg = ETCommonConst.FAILED;
			file.delete();
		} catch (SftpException e) {
			logger.info("파일전송실패");
			msg = ETCommonConst.FAILED;
			file.delete();
		} finally {
			logger.info("전송 완료 후 임시 파일 삭제");
			file.delete();
		}
		resultMap = param;
		resultMap.put("msg", msg);
		resultMap.put("insert_msg", msg);
		
		return resultMap;
	}
	
	/** CSV 파일 생성
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> createCSVFiles(Map<String, Object> param, HttpServletRequest req) {
 
		String folderPath = FileUtil.getTopFolder(req);
		File folder = new File(folderPath+File.separator+"anidialSend"); // .metadata내에 폴더 생성
		logger.info("폴더 저장 경로:"+folder);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("sendData");
		Map<String, Object> returnData = (Map<String,Object>) param.get("ftpDatas"); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // HH 24시간  hh 12시간
		String curTime = sdf.format(System.currentTimeMillis());
		
		String title = (String) returnData.get("title") +"_" + curTime+".csv";
		
		try {
			
			File csv = new File(folder + File.separator + title); // 위에서 만든 폴더 내에 파일 생성
			String os = System.getProperty("os.name").toLowerCase();
			if (os.equals("linux")) {
				Runtime.getRuntime().exec("chmod -R 777 " + folder);
				Runtime.getRuntime().exec("chmod -R 777 " + csv);
			}
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csv), "MS949"));
			
			int i = 0;
			for(Map<String, Object> map : list) {
				i++;
				List<String> valueList = new ArrayList<>();
				for (String key : HEADERNAMES) {
					String objStr = "";							
					objStr= map.get(key) == null ? "": map.get(key).toString();
					if(key.equals("index")) {
						objStr = Integer.toString(i);
					}else if(key.equals("out_no")) {
						objStr = objStr.replaceAll("-","");
					}
					valueList.add(objStr);
				}
				String values = listToString(valueList);
				map.values();
				bw.write(values);
				bw.newLine();
			}
			
			bw.flush();
			bw.close();
			returnData.put("filePath", folder);
			returnData.put("fileTitle", title);
			returnData.put("msg", ETCommonConst.SUCCESS);
			
			return returnData;
		} catch (Exception e) {
			logger.info("CSV파일 생성 실패 : "+LogUtil.getPrintStackTrace(e));
			returnData.put("filePath", folder);
			returnData.put("fileTitle", title);
			returnData.put("msg", ETCommonConst.FAILED);
			
			return returnData;
		}
		
		
	}
	
	/** 입력한 컬럼명 기준으로 csv로 저장될 리스트 나눠짐 
	 * @param list
	 * @param id
	 * @return
	 */
	private Map<String, Object> devideSendList(List<Map<String,Object>> list, String center_id, String dialer_cd) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		List<String> names = new ArrayList<>();

		for(Map<String, Object> vo1 : list) {
			String nowCd = vo1.get(center_id).toString();
			if(!names.contains(nowCd)) {
				names.add(nowCd);
			}
		}
		for(String cd : names) { 
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String,Object>();
			for(Map<String, Object> vo2 : list) {
				String name_cd = vo2.get(center_id).toString();
				if(cd.equals(name_cd)) {
					datas.add(vo2);
				}
			}
			Map<String, Object> ftpDatas = new HashMap<>();
			
			Map<String,Object> ftpData = datas.get(0);
			String group_dialer_name = ftpData.get("group_dialer_name") != null ? ftpData.get("group_dialer_name").toString() + "_" : ""; 
			
			String title = group_dialer_name + cd;
			
			ftpDatas.put("title", title); // 다시 변경  
			ftpDatas.put("url", ftpData.get("dialer_ip")); // 
			ftpDatas.put("port", ftpData.get("dialer_port"));// 
			ftpDatas.put("user", ftpData.get("dialer_user"));// 
			ftpDatas.put("pwd",ftpData.get("dialer_pw"));// 
			ftpDatas.put("dir",ftpData.get("dialer_dir"));// 

			map.put("sendData",datas);
			map.put("ftpDatas",ftpDatas);
			
			dataMap.put(cd, map);
		}
		
		resultMap.put("names", names);
		resultMap.put("datas", dataMap);

		return resultMap;
		
		
	}

	/** 타입별로 리스트 나눔
	 * @param list
	 * @return
	 */
	private Map<String, Object> devideTypeList(List<Map<String, Object>> list) {
		Map<String, Object> dataMap = new HashMap<String,Object>();
		List<String> names = new ArrayList<>();

		for(Map<String, Object> vo1 : list) {
			String nowCd = vo1.get("dialer_type").toString();
			if(!names.contains(nowCd)) {
				names.add(nowCd);
			}
		}
		for(String cd : names) {
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String,Object>();
			for(Map<String, Object> vo2 : list) {
				String name_cd = vo2.get("dialer_type").toString();
				if(cd.equals(name_cd)) {
					datas.add(vo2);
				}
			}

			map.put("sendData",datas);
			
			dataMap.put(cd, map);
		}
		return dataMap;
		
	}

	
	
	/** list를 String 으로 
	 * @param list
	 * @return
	 */
	private String listToString(List<String> list) {

		String resultStr = "";
		for(String str : list) {
			resultStr += (resultStr.length() < 1) ? str : (","+str); 
		}
		return resultStr;
	}


	


	
}
