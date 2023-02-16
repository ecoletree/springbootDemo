package com.example.demo.service.chargeMgt.web;

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
import com.example.demo.service.chargeMgt.service.RateMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/chargeMgt/rateMgt")
public class RateMgtController extends ETBaseController{
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	RateMgtService service;
	
	/** 일반 요율 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".rateMgt");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(service.getCodeList()));
		return mav;
	}
	
	/** 일반요율 관리 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRateInfoList")
	public @ResponseBody Map<String, Object> getRateInfoList(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParamToMap(request);
		return service.getRateInfoList(params);
	}
	
	/** 일반요율 관리 저장 하기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setRateInfo")
	public @ResponseBody Map<String, Object> setRateInfo(@RequestBody Map<String, Object> params) throws Exception {
		int i = service.setRateInfo(params);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 일반요율 관리 삭제 하기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delRateInfo")
	public @ResponseBody Map<String, Object> delRateInfo(@RequestBody Map<String, Object> params) throws Exception {
		return ResultUtil.getResultMap(0 < service.delRateInfo(params));
	}
	
	/** 요율 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRateGroupList")
	public @ResponseBody Map<String, Object> getRateGroupList(@RequestBody Map<String, Object> params) throws Exception {
		return service.getRateGroupList(params);
	}
	
	/** 요율 리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getRateList")
	public @ResponseBody Map<String, Object> getRateList(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParamToMap(request);
		return service.getRateList(params);
	}
	
	/** 요율 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setRate")
	public @ResponseBody Map<String, Object> setRate(@RequestBody Map<String, Object> param)throws Exception {
		int i = service.setRate(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 요율 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delRate")
	public @ResponseBody Map<String, Object> delRate(@RequestBody Map<String, Object> param)throws Exception {
		return ResultUtil.getResultMap(0 < service.delRate(param));
	}
}
