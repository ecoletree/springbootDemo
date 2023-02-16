package com.example.demo.service.recordMgt.service.Impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.Utility;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import com.example.demo.service.recordMgt.mapper.RecordSendMgtMapper;
import com.example.demo.service.recordMgt.service.RecordSendMgtService;
import com.example.demo.util.SFTPConnection;
@Service
public class RecordSendMgtServiceImpl extends ETBaseService implements RecordSendMgtService {

	
	//preed (START_TIME_optional13_optional11_optional18_파일명.wav)
	private List<String> supplierNamesCU = Arrays.asList("user_data_13","user_data_11","user_data_18");
	
	@Value("${FILE_PATH}")
	private String sourcePath;
	
	private String downloadPath_skmns = "/ecoletree/skmn/";
	private String downloadPath_preed = "/ecoletree/preedlife5/";
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	RecordSendMgtMapper mapper;

	@Autowired
	GroupMgtService groupMgtService;

	@Override
	public Map<String, Object> getCodeList(Map<String, Object> params) {
		Map<String, Object> resultMap =	MapBuilder.<String, Object>of(
				"groupList", groupMgtService.getGroupList(params)
		);
		return resultMap;
	}

	@Override
	public Map<String, Object> getRecordSendList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectRecordSendList(params);
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
	public int sendRecordList(Map<String, Object> params) {
		
		int i = 0;
		Map<String, Object> fileSendMap = seperateCenterList(params);
		boolean result = fileUpDownIndexCreate(fileSendMap,params.get("ftp_type").toString());
		
		i = result == true ? 1 :0;
		return i;
	}

	/** 파일다운로드업로드인덱스생성
	 * @param fileSendMap
	 * @param string
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean fileUpDownIndexCreate(Map<String, Object> center, String ftpType) {
		
			boolean result = false;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date start_stamp = new Date();
			String today = sdf.format(start_stamp);
			
			Map<String, Object> ftpmap = (Map<String, Object>) center.get("ftpData");
			List<Map<String, Object>> records = (List<Map<String, Object>>) center.get("recData");
			
			if(records.size()!=0) {
			
			// down_up
			int downSuccess = 0;
			int upSuccess = 0;
			for(Map<String, Object> map : records) {
			   Map<String, Object> down = downloadRec(map,ftpType);
			   Map<String, Object> up = new HashMap<String, Object>();
			   if(down.containsKey("filePath")) {
					downSuccess += (int)down.get("successCnt");
					String filePath = down.get("filePath").toString();
					
					up = uploadDeleteFile(filePath,ftpmap,today); // 업로드 & 파일삭제
					
					String is_trans = (int)up.get("successCnt") != 0 ? "Y" : "N"; // 업로드 성공여부 is_trans
					String trans_status = (int)up.get("successCnt") != 0 ? "trans" : "error"; // 전송성공시 trans 실패시 wait
					map.put("is_trans", is_trans);
					map.put("trans_status", trans_status);
					upSuccess += (int)up.get("successCnt");
					//5. update query
					mapper.updateRecordState(map);// 레코드 상태 업데이트
					if(((List<Map<String,Object>>)map.get("certData")).size() != 0) {
						mapper.updateRecordCertificationState(map); // 증서 업데이트
					}; 
			   }
			}//for
			
			int upfailCnt = records.size() - upSuccess;
			Date end_stamp = new Date();
			String ip = Utility.getIP();
			ftpmap.put("send_ip", ip);
			ftpmap.put("record_total", records.size());
			ftpmap.put("download_success", downSuccess);
			ftpmap.put("upload_success", upSuccess);
			ftpmap.put("upload_fail", upfailCnt);
			ftpmap.put("work_start", start_stamp);
			ftpmap.put("work_end", end_stamp);
			
			mapper.updateRecordSendList(ftpmap);// TBL_RECORD_SEND 업데이트
			result = true;
			//logger
			resultLogger(ftpmap,ftpType);
			   
		   }//list.size!=0
	   
		return result;
	}

	private Map<String, Object> seperateCenterList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> sendList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> recList = new ArrayList<Map<String,Object>>();
		String ftp_type = params.get("ftp_type").toString();
		String group_cd = params.get("group_cd").toString();
		int duration_cnt = 0;
		if(ftp_type.equals("CUST")) {
			sendList = mapper.selectPreedSendRecordList(params);
		}
		for(Map<String, Object> vo1 : sendList) {
			if(group_cd.equals(vo1.get("group_cd").toString())) {
				int dur_cnt = (int) vo1.get("duration");
				if(dur_cnt >= 2) {
					recList.add(vo1);
				}else {
					duration_cnt += 1;
				}
			}
		}
		params.put("duration", duration_cnt);
		resultMap.put("ftpData", params);
		resultMap.put("recData", recList);
		
		return resultMap;
	}

	/** 업로드
	 * @param filePath
	 * @param ftpmap
	 * @param today 
	 * @return
	 */
	private Map<String, Object> uploadDeleteFile(String filePath, Map<String, Object> ftpmap, String today) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int successCnt = 0;
		String url = (String) ftpmap.get("ftp_ip"); 
		int port = (int) ftpmap.get("ftp_port");
		String user = (String) ftpmap.get("ftp_user");
		String pwd = (String) ftpmap.get("ftp_pw");
		String dir = (String)ftpmap.get("ftp_dir");
		
		SFTPConnection conn = new SFTPConnection();
		
		try {
			
			ChannelSftp ss =  conn.ftpConnect(url, port, user, pwd);
			
			// 서버에 폴더생성
			String path = dir+ (today.length() != 0 ? "/"+today : today);
			SftpATTRS attrs=null;
			try {
				attrs = ss.stat(path);
			} catch (Exception e) {
			}
			if (attrs != null) {
				logger.info("Directory exists IsDir="+attrs.isDir());
			} else {
				ss.mkdir(path);
			}

			File file = new File(filePath); //저장된 파일경로 가져오기
			
			if (file.exists()) {
				conn.upload(ss, file, path);
				successCnt+=1;
				logger.info("파일 업로드 성공");
			}
			conn.disconnect();
			file.delete(); // 파일 삭제
		} catch (JSchException e) {
			logger.info("파일 업로드 실패");
		} catch (IOException e) {
			logger.info("파일 업로드 실패");
		} catch (SftpException e) {
			logger.info("파일 업로드 실패");
		}
		returnMap.put("successCnt", successCnt);
		return returnMap;
	}
	/** 다운로드
	 * @param map
	 * @param ftpType
	 * @return
	 */
	private Map<String, Object> downloadRec(Map<String, Object> rec, String ftpType) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int successCnt = 0;
		String filePath="";
		File downLoadFile = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String downloadPath = ftpType.equals("DB") ? downloadPath_skmns :downloadPath_preed;
		try {
			File recOriFile = new File((String)rec.get("rec_file"));
			
			if (recOriFile.exists()) {
				String name ="";
				if(ftpType.equals("CUST")) {
					String now = sdf.format(date);
					name = now+"_"+setIndexTxt(supplierNamesCU,rec,"_");
				}
				String fileName = name+recOriFile.getName();
				filePath = downloadPath+fileName;
				downLoadFile =defineFile(filePath);
				
				in = new BufferedInputStream(new  FileInputStream(recOriFile));
				out = new BufferedOutputStream(new FileOutputStream(downLoadFile));
				byte[] buffer = new byte[4096];
				int bytesRead = 0;
				while ((bytesRead = in .read(buffer)) != -1) {
	                 out.write(buffer, 0, bytesRead);
	            }
				
				out.flush();
				successCnt += 1;
				returnMap.put("filePath", filePath);
			}
		} catch (Exception e) {
			logger.info("파일 다운로드 실패");
		} finally {
			try {
				if(in != null) in.close();
				if(out != null) out.close();
				logger.info("파일 다운로드 성공");
			} catch (IOException e) {
				logger.info("파일 다운로드 stream close 실패");
			}
		}
		returnMap.put("successCnt", successCnt);
		return returnMap;
	}
	/** 파일생성
	 * @param filePath
	 * @return
	 */
	private File defineFile(String filePath) {
		File dfile = new File(filePath);
		if(!dfile.exists()) {
			try {
				File path = dfile.getParentFile();
				if (!path.exists()) {
					path.mkdirs();
				}
				dfile.createNewFile();
				logger.info("파일 생성");
			} catch (IOException e) {
				logger.info("파일 생성 실패");
			}
		}
			
		return dfile;
	}
	
	
	/** 인덱스 내용, 파일이름 생성
	 * @param supplierNames
	 * @param recMap
	 * @param seperator 
	 * @return
	 */
	private String setIndexTxt(List<String> supplierNames, Map<String, Object> recMap, String seperator) {
		String txt = "";
		logger.info("setIndexTxt");
		for(String name : supplierNames) {
			if(recMap.get(name) != null) {
				if(recMap.get(name).toString().length() != 0) {
					txt+= recMap.get(name)+seperator ;
				}
			}
		}
		return txt;
	}
	
	/** logger
	 * @param logMap
	 * @param ftpType 
	 */
	private void resultLogger(Map<String, Object> logMap, String ftpType) {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		
		Date start = (Date) logMap.get("work_start");
		Date end = (Date) logMap.get("work_end");
		
		// 작업소요시간
		long diff = end.getTime() - start.getTime();
		long hours = (diff / 1000) / 60 / 60 % 24;
		long minutes = (diff / 1000) / 60 % 60;
		long seconds = (diff / 1000) % 60;
		
		// logger
		System.out.println("=========================================================================================================================");
		logger.info("작업 고객사 : " + logMap.get("group_id"));
		logger.info("작업 센터 : " + logMap.get("tenant_id"));
		logger.info("작업 종류 : " + "Manual");
		logger.info("작업 IP : " + logMap.get("send_ip")); 
		logger.info("작업 시작시각 : " + sdf1.format(start));
		logger.info("작업 종료시각 : " + sdf1.format(end));
		logger.info("작업 소요시간 : " + hours+":"+minutes+":"+seconds );
		logger.info("작업 범위 : " + sdf2.format(start));
		logger.info("콜 개수 : " + logMap.get("record_total"));
		logger.info("다운로드 개수 : " + logMap.get("download_success"));
		if(ftpType.equals("CUST")) logger.info("다운로드 실패 개수 : " + ((int)logMap.get("record_total") - (int)logMap.get("download_success")));
		logger.info("업로드 개수 : " + logMap.get("upload_success"));
		if(ftpType.equals("CUST")) logger.info("업로드 실패 개수 : " + logMap.get("upload_fail"));
		logger.info("통화시간 2초 이하 실패 개수 : " + logMap.get("duration"));
		if(ftpType.equals("DB")) {
			logger.info("인덱스 생성 개수 : "+logMap.get("write_index"));
			logger.info("인덱스 업로드 개수 :"+logMap.get("upload_index"));
		}
		String resultStr = (int)logMap.get("record_total") == (int)logMap.get("upload_success") ? "ok" :"fail";
		logger.info("작업 결과 : "+ resultStr);
		
	}
}
