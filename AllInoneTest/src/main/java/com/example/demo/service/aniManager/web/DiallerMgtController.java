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
import kr.co.ecoletree.common.util.MapBuilder;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.aniManager.service.DialerMgtService;
import com.example.demo.service.groupMgt.service.GroupMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/tenant/dialerMgt")
public class DiallerMgtController extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	DialerMgtService service;
	
	@Autowired
	GroupMgtService groupMgtService;
	
	/** 다이얼러 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".dialerMgt");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(
				MapBuilder.<String, Object>of(
						"groupList", groupMgtService.getGroupList(params) // 그룹 센터 
						))
				);
		return mav;
	}


	/** 다이얼러 리스트 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getDialerList")
	public @ResponseBody Map<String, Object> getDialerList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getDialerList(params);
	}
	
	/**다이얼러 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setDialer")
	public @ResponseBody Map<String, Object> setDialer(@RequestBody Map<String, Object> param) throws Exception{
		int i = service.setDialer(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 다이얼러 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delDialer")
	public @ResponseBody Map<String, Object> delDialer(@RequestBody Map<String, Object> param) throws Exception{
		return ResultUtil.getResultMap(0 < service.delDialer(param));
	}
	
}
