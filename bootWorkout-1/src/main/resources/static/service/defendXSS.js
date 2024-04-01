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
//		var test = DOMPurify.sanitize("test");
//		var test = DOMPurify.sanitize('<img src="x" onerror="alert(\'XSS 공격 예제\')" />');
//		var test = DOMPurify.text('<script>alert("XSS 공격 예제");</script>');
//		var test = DOMPurify.sanitizeWithoutDOM('<script>alert("test!!!");</script>');
//		console.log(test);
	};

	ctrl.btnSubmitHandler = function(){
		var self = et.vc;
		$("#xssForm").submit();
	}


	ctrl.setValidation = function(){
		var self = et.vc;

		ETValidate.addMethod("sanitize_script",function(value, element, params) {
			var xss = DOMPurify.sanitize(value);
			return xss.length !== 0;
		});

		var validation = new ETValidate("#xssForm").setSubmitHandler(self.setSuccessSubmitHandler).setShowErrors(self.setErrorFunction);
		validation.validateRules("xss_test",validation.XSS,"스크립트는 기입할 수 없습니다.");
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