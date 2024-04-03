(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "defendXSS") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	"use strict";

	var ctrl = {};

	ctrl.name = "defendXSS";
	ctrl.path = "/";
	ctrl.groupList= null;
	ctrl.optionList = {};
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.setValidation();
		$("#btnSubmit").click(self.btnSubmitHandler);

		self.xssEscapingTest();

	};

	/**
	 * he.js 사용
	 */
	ctrl.xssEscapingTest = function(){
		var test = he.escape('<script>alert("test!!!");</script>');
		var test22 = he.unescape(test);
		console.log("he:"+test);
		console.log("adsf:"+test22);
	}

	ctrl.btnSubmitHandler = function(){
		var self = et.vc;
		$("#xssForm").submit();
	}

	/**
	 * xss 방어
	 */
	ctrl.setValidation = function(){
		var self = et.vc;

		var validation = new ETValidate("#xssForm").setSubmitHandler(self.setSuccessSubmitHandler).setShowErrors(self.setErrorFunction);

		/*
		 * rule - remove
		 * https://jqueryvalidation.org/rules/
		 */
//		validation.defenseXSS(); // -> xss 체크 input,textarea (폼 룰 추가 후에 삭제할 룰만 기입)
		validation.defenseXSS("스크립트는 기입할 수 없습니다.").xssExcludeRules(["xss_test2","xss_test3"]); // -> xss 체크 input,textarea (폼 룰 추가 후에 삭제할 룰만 기입)
		validation.validateRules("xss_test",validation.REQUIRED,"필수입니다.");
		validation.validateRules("xss_test2",validation.REQUIRED,"필수입니다.");
		validation.validateRules("xss_test3",validation.REQUIRED,"필수입니다.");


		validation.apply();
	};

	ctrl.setSuccessSubmitHandler = function(form){
		var self =et.vc;
		var formData = ETValidate.convertXSSFormToObject("#xssForm",true,true);
		console.log(formData);
		var result = $("#divResult");
		result.append('<br><p>sanitize : '+formData.xss_test+'</p><p>convert : '+formData.xss_test2+'</p><br>');

	};

	ctrl.setErrorFunction = function(message){
		if(!_.isEmpty(message)){
			var keys = Object.keys(message);
			var html = "";
			_.forEach(keys,function(err){
				html += err+":"+ message[err]+"\n";
			})
			alert(html);
		}
	};

	return ctrl;
}));