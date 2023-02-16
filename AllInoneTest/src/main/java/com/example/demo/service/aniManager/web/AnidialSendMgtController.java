package com.example.demo.service.aniManager.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.aniManager.service.AnidialSendMgtService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/aniManager/sendMgt")
public class AnidialSendMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	AnidialSendMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 전송 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".transmissionMgt");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(service.getCodeList(params)));
		return mav;
	}
	
	/** 전송 관리 리스트 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getSendMgtList")
	public @ResponseBody Map<String, Object> getSendMgtList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getSendMgtList(params);
	}
	
	
	/** 리스트 전송 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/sendAnidialerList")
	public @ResponseBody Map<String, Object> sendAnidialerList(@RequestBody Map<String, Object> param, HttpServletRequest req) throws Exception{
		Map<String, Object> map = service.sendAnidialerList(param,req);
		return ResultUtil.getResultMap(!map.isEmpty() , map);
	}
}
