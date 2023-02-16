package com.example.demo.service.recordMgt.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import kr.co.ecoletree.common.util.StringUtil;
import com.example.demo.service.common.CommonConst;
import com.example.demo.service.recordMgt.mapper.RecordListeningMgtMapper;
import com.example.demo.service.recordMgt.service.RecordListeningMgtService;
import com.example.demo.util.AllInoneLogUtil;
import com.example.demo.util.AllInoneLogUtil.LOG_MESSAGE;
@Service
public class RecordListeningMgtServiceImpl extends ETBaseService implements RecordListeningMgtService {

	@Autowired
	RecordListeningMgtMapper mapper;
	
	@Autowired
	AllInoneLogUtil logUtil;

	@Override
	public Map<String, Object> getRecordList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = mapper.selectRecordList(params);
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
	public List<Map<String, Object>> getCertificateList (Map<String, Object> params) {
		List<Map<String, Object>> list = mapper.selectRECTransList(params);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int sendRecordFileTrans (Map<String, Object> params) {
		List<Map<String, Object>> list = (List<Map<String, Object>>)params.get("list");
		int i = 0;
		int index = 1;
		for (Map<String, Object> map : list) {
			map.put("session_user_id", ETSessionHelper.getUserId());
			if (!map.containsKey("certificate_cd")) {
				map.put("certificate_cd", StringUtil.getUUID(CommonConst.CodePrefixConst.CERTIFICATE));
				map.put("internal_index", index);
			}
			i = mapper.insertRECTrans(map);
			
			if (map.get("is_changed").equals("Y")) {
				 String msg = LOG_MESSAGE.CERTIFICATE_UPDATE; 
				 msg = msg.replace("[$]data", map.get("ori_certificate_num") + " > " + map.get("certificate_num"));
		         logUtil.setLog(msg, ETSessionHelper.getUserId());
			}
			index++;
		}
		i = mapper.updateRECTrans(params);
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getColumnList(Map<String, Object> params) {
		Map<String, Object> userInfo =  (Map<String, Object>)ETSessionHelper.getSessionVO().getUser_info();
		params.put("user_type", userInfo.get("user_type"));
		return mapper.selectRecordColumnList(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSearchList(Map<String, Object> params) {
		Map<String, Object> userInfo =  (Map<String, Object>)ETSessionHelper.getSessionVO().getUser_info();
		params.put("user_type", userInfo.get("user_type"));
		return mapper.selectRecordSearchList(params);
	} 
}
