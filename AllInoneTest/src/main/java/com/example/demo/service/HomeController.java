package com.example.demo.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ecoletree.common.auth.Auth;
import kr.co.ecoletree.common.auth.ETSessionManager;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.exception.ETException;
import kr.co.ecoletree.common.helper.ETSessionHelper;
import com.example.demo.service.login.service.LoginService;
import com.example.demo.util.AllInoneLogUtil;
import com.example.demo.util.AllInoneLogUtil.LOG_MESSAGE;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends ETBaseController{
	
	@Autowired
	LoginService service;
	
	@Autowired
	AllInoneLogUtil logUtil;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * 웹훅 테스트
	 */
	@RequestMapping(value = "/")
	public String home(Locale locale, Model model) {
		return ETSessionHelper.isLoginUser() ? "redirect:/home" : "redirect:/login";
	}
	
	/**
	 * 로그아웃 화면 열기
	 * @return
	 */
	@Auth
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ETException {
		logUtil.setLog(LOG_MESSAGE.LOGOUT, ETSessionHelper.getUserId());
		ETSessionManager.getInstance().removeSession(ETSessionHelper.getUserId());
		ETSessionHelper.logout(request);
		try {
			response.sendRedirect(servletContext.getContextPath() + "/login");
		} catch (IOException e) {
			logError(e.getMessage());
			throw new ETException();
		}
	}
	
}
