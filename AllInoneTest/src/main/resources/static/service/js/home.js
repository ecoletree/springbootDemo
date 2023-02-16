/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2019. 03. 04.
 * @DESC : [관리자웹] 로그인
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "home") {
			et.vc= ctrl(et);
		}
		var inter = setInterval(function(){
			et.promiseInit().then(function(){
				et.vc.init();
				clearInterval(inter);
			}, function (){})
		},300);
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "home";
	ctrl.path = "/login";
	ctrl.formId = "#loginForm";
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function() {
		var self = et.vc;
		self.setValidate();
		
		self.addEventListener();
	};
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;
		
		var validate = new ETValidate(self.formId).setSubmitHandler(self.submitCallbackHandler).setShowErrors(et.setErrorFunction());
		validate.validateRules("user_id",validate.REQUIRED,et.message("validate.required", {name:"아이디"}));
	    validate.validateRules("user_pw",validate.REQUIRED,et.message("validate.required", {name:"비밀번호"}));
	    validate.apply();
	};
	
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnLogin").click(self.btnLoginHandler);
		et.setEnterKeyDownEvent("#ipxUserPW", self.btnLoginHandler);
	}
	
	// ============================== 이벤트 리스너 ==============================
	/*
	 * 로그인 버튼 클릭 핸들러
	 */
	ctrl.btnLoginHandler = function () {
		var self = et.vc;
		$(self.formId).submit();
	}
	
	// ============================== Form 리스너 ==============================
	/**
	 * Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.submitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.user_pw = btoa(formData.user_pw);
		var params = {cryptoInfo:et.cryptoAES(formData)};
		new ETService().setSuccessFunction(self.loginCallSucceed).callService(self.path + "/doLogin", params);
	}
	
	/**
	 * 로그인 api 가 정상적으로 발생되었을 경우 발생
	 */
	ctrl.loginCallSucceed = function (result) {
		var self = et.vc;
		if (result.resultMsg === ETCONST.SUCCESS) {
			// 로그인 성공
			//new ETService().callView("/tenant/custMgt", null);
			new ETService().callView(result.data.first_menu_url, null);
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message(result.resultMsg));
		}
	}
	
	return ctrl;
}));