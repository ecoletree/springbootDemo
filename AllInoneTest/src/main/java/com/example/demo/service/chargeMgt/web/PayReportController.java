package com.example.demo.service.chargeMgt.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.MapBuilder;
import com.example.demo.service.chargeMgt.service.PayReportService;
import com.example.demo.service.code.service.CodeService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/chargeMgt/payReport")
public class PayReportController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	PayReportService service;
	
	@Autowired
	CodeService codeService;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 내선별 리포트 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".payReport");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
		MapBuilder.<String, Object>of(
				"codeList", codeService.getCodeList(params)
				,"groupList", groupMgtService.getGroupList(params)
				))
		);
		return mav;
	} 
	
	/** 내선별 리포트 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getReportList")
	public @ResponseBody Map<String, Object> getReportList(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParamToMap(request);
		params.put("call_type", ((String)params.get("typeList")).split(","));
		if (params.containsKey("sphone")) {
			params.put("sphone", Integer.parseInt((String)params.get("sphone")));
		}
		if (params.containsKey("ephone")) {
			params.put("ephone", Integer.parseInt((String)params.get("ephone")));
		}
		return service.getReportList(params);
	}
}
