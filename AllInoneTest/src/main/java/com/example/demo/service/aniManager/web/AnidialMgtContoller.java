package com.example.demo.service.aniManager.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import com.example.demo.service.aniManager.service.AnidialMgtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/aniManager/anidialMgt")
public class AnidialMgtContoller extends ETBaseController {
	
	private static final String JSP_PATH = ".service.body";
	public static final String TMR_ID_KEY = "session_tmr_id";
	
	@Value("{SCHEMA_EN_KEY}")
	private String schema_en_key;
	
	@Autowired
	AnidialMgtService service;
	
	/** 리스트 관리로 이동
	 * @param mav
	 * @return
	 */
	@Auth
	@RequestMapping("")
	public ModelAndView open(ModelAndView mav, Map<String, Object> params) {
		mav.setViewName(JSP_PATH + ".anidialMgt");
		mav.addObject(ETCommonConst.INIT_DATA, JSONObject.fromObject(service.getCodeList(params)));
		return mav;
	}

	/** 애니다이얼 리스트 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/getAnidialerList")
	public @ResponseBody Map<String, Object> getAnidialList(HttpServletRequest request) throws Exception{
		Map<String, Object> params = getParamToMap(request);
		return service.getAniDialerList(params);
	}
	
	/**애니다이얼 수정,추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/setAnidialerList")
	public @ResponseBody Map<String, Object> setAnidialList(@RequestBody Map<String, Object> param) throws Exception{
		int i = service.setAnidialerList(param);
		return ResultUtil.getResultMap(0 < i,i);
	}
	
	/** 애니다이얼리스트 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/delAnidialerList")
	public @ResponseBody Map<String, Object> delAnidialList(@RequestBody Map<String, Object> param) throws Exception{
		return ResultUtil.getResultMap(0 < service.deleteAnidialerList(param));
	}
	
	/** 애니다이얼 리스트 엑셀 다운로드
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth
	@RequestMapping("/excelDownload")
	public @ResponseBody Map<String, Object> excelDownloadAnidialList(@RequestBody Map<String, Object> param, HttpServletRequest req) throws IOException, ParseException{
		Map<String, Object> result = service.excelDownloadAnidialList(param);
		return result;
	}
    /**
     * 엑셀 파일에 있는 내용을 추가한다.
     *
     * @param request
     * @return
     */
    @Auth
    @RequestMapping("/excelUpload")
    public @ResponseBody Map<String, Object> excelUploadAnidialList(final MultipartHttpServletRequest request) {
        try {
            final boolean result = service.excelUploadAnidialList(request);
            return ResultUtil.getResultMap(result, null, result ? "success" : "fail");
        } catch (Exception e) {
            logError(e.getMessage(), e);
            return ResultUtil.getResultMap(false, null, "fail");
        }
    }
}
