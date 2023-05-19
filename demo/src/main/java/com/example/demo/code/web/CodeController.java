package com.example.demo.code.web;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.code.service.CodeService;
import com.example.demo.util.LoginValidationUtil;
import com.example.demo.util.LoginValidationUtil.LoginParam.LoginParamBuilder;
import com.example.demo.util.LoginValidationUtil.VALIDATION_MSG;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.ETSessionManager;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.FileUtil;
import kr.co.ecoletree.common.util.ResultUtil;
import kr.co.ecoletree.common.vo.ETSessionVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CodeController extends ETBaseController{

	private static final String JSP_PATH = ".service.body";
	
	private static int count  = 0;
	
	@Autowired
	CodeService service;
	
	@Autowired
	LoginValidationUtil tUtil;
	
	@RequestMapping("/")
	public String hello() {
		logDebug("test");
		logError("test");
		logInfo("test");
		return "index";
	}
	
	@RequestMapping("/date")
	public String hello2() {
		logDebug("test");
		logError("test");
		logInfo("test");
		return "date";
	}
	
	@RequestMapping("/agent")
	public String agent() {
		logDebug("test");
		logError("test");
		logInfo("test");
		return "agent";
	}
	
	@RequestMapping("/upload")
	public @ResponseBody Map<String, Object> setKMS(final MultipartHttpServletRequest request) throws Exception {
		final Map<String, Object> params = getParamToMap(request);
		
		MultipartFile multipartFile =  request.getFile("file");
		final String mFileName = multipartFile.getOriginalFilename();
		String ext = null;
		if (-1 == mFileName.lastIndexOf(".")) {
			ext = "";
		} else {
			ext = mFileName.substring(mFileName.lastIndexOf("."));
		}
		FileUtil.createFile(multipartFile, request, mFileName);
		return ResultUtil.getResultMap(0 < 1 ? true : false, params);
	}
	
	@RequestMapping("/tail")
	public ModelAndView open(final ModelAndView mav) {
		mav.setViewName(JSP_PATH + ".tailBody");
		return mav;
	}
	
	
	@RequestMapping("/getList")
	public @ResponseBody Map<String, Object> getList(@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = service.selectList(params);
		
		return ResultUtil.getResultMap(true,list);
	}
	
	@RequestMapping("/getList2")
	public @ResponseBody Map<String, Object> getList2(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		//int list = service.insertCode(params);
		String user_id = "mk";
		String dbPW = "4451596757654153364e5334584671396c6b42466266365170432f42614e4731754e38324f5574585747553d";
		//String dbPW = "4451596757654153364e5334584671396c6b42466266365170432f42614e4731754e38324f5574585747553d";
		ETSessionVO etSessionVO = new ETSessionVO();
		etSessionVO.setUser_id(user_id);
		etSessionVO.setUser_pw(dbPW);
		request.getSession().setAttribute(ETCommonConst.SESSION_VO, etSessionVO);
		ETSessionManager.getInstance().setSession(request.getSession(), user_id);
		
		if (5 < count) {
			count = 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -89);
		
		Calendar PWcal = Calendar.getInstance();
		PWcal.add(Calendar.DATE, -90);
		LoginParamBuilder builder =
				LoginValidationUtil.LoginParam.builder()
				.init_pw("ccc12345!!!")
				.last_login_dttm(cal.getTime())
				.param_pw((String)params.get("password"))
				.login_count(count)
				.user_db_pw(dbPW)
				.last_pw_change_dttm(PWcal.getTime())
				;
		String result = tUtil.validation(builder);
		log.info(result);
		if (result.equals(VALIDATION_MSG.NO_MATCH_DATA)) {
			count++;
		}
		return ResultUtil.getResultMap(true, result);
	}
}
