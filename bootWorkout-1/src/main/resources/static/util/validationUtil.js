"use strict";

	// ==============================validation Util==============================
function ETValidate(form) {
	if (!(this instanceof ETValidate)) {
			return new ETValidate(formId);
		}
	this.__proto__.init(form);
}

ETValidate.constructor = ETValidate;

ETValidate.prototype = {
	constructor : ETValidate,

	$form : {},
	validate :{},

	/* validateType : ETCONST.VALIDATE 상수 값 */
	REQUIRED : "required",
	NUMBERONLY : "number",
	DATECHECKER : "startEndDateChecker",
	MAXDATE : "maxDate",
	IMGFILESIZE : "imgFileCheck",
	SUMMERNOTEREQUIRED : "summernoteCheck",
	EMAIL : "email",
	FOURNUMBER : "fourNumber",
	FILE_EXTENSION : "extension",
	XSS : "xss_script",

	init : function(formId) {
		if (formId.charAt(0) !== "#") {
			formId = "#" + formId;
		}
		this.$form = $(formId);
		this.validate.onkeyup = false;
		this.validate.ignore = "";
		this.validate.onclick =  false;
		this.validate.onfocusout =  false;
		this.validate.rules =  {};
		this.validate.messages =  {};



	},
	/**
	 * validate option 값 설정
	 *
	 * @param {string} optionName 옵션명.
	 * @param {object} handler 옵션에 적용할 값 또는 함수
	 * @param {boolean} disallowTrue true 값을 적용할 수 없음.
	 */
	setOption : function(optionName, handler, disallowTrue){
			if (!!disallowTrue && handler === true) {
						handler = undefined;
			}

			this.validate[optionName] = handler;
	},
	/**
	 * 밸리데이션 룰, 메시지 적용
	 *
	 * @param {String} elementName 폼안에 들어있는 컴포넌트 네임
	 * @param {String} validateType rule에 설정될 설정값
	 * @param {string} message 해당 rule에 위배될 경우 출력될 메시지
	 * @param {String} param rule에 사용될 param값
	 *
	 * DATECHECKER(시작일이 종료일보다 앞선 날짜) : elementName = 시작일, param ={elem : 종료일name}
	 * MAXDATE(날짜수 초과시 체크) : elementName = 시작일, param ={elem : 종료일name, num: 검색최대일수}
	 *
	 */
	validateRules : function(elementName, validateType, message ,param){
		var validate = this.validate;
		var rule = {};
		var messages = {};

		if(param === null || param === undefined){
			rule[validateType] = true;
		}else if(param !== null || param !== undefined){
//			var params = [];
//			params.push(param);
			rule[validateType] = param;
		}
		messages[validateType] = message;

		if(validate.rules[elementName] === undefined){
			validate.rules[elementName] = rule;
		}else{
			Object.assign(validate.rules[elementName],rule);
		}
		if(validate.messages[elementName] === undefined){
			validate.messages[elementName] = messages;
		}else{
			Object.assign(validate.messages[elementName],messages);
		}

	},

	/**
	 * 변경된 validation 설정 적용
	 *
	 * @returns {ETValidate}
	 */
	apply: function(){
		var self = this;
		this.$form.validate(this.validate);
		return self;
	},

	/**
	 * errorPlacement를 사용하지 않고 handler를 사용함, keyup, click, focusout Event handler 사용 안 함.
	 *
	 * @param handler
	 * @return {ETValidate}
	 */
	setShowErrors: function(handler){
		var self = this;
		self.setOption("errorPlacement", function (error, element) {});
		self.setOption("onkeyup", false);
		self.setOption("onclick", false);
		self.setOption("onfocusout", false);
		self.setOption("showErrors", handler);
		return self;
	},
	/**
	 * submitHandler를 설정합니다. <br>
	 * submitHandler는 form의 submit 버튼을 클릭하여 form 내부 inputs의 유효성을 검사, 모두 ok되면 수행될 함수입니다.
	 *
	 * @param {function(form)} handler
	 * @returns {ETValidate}
	 */
	setSubmitHandler : function(handler) {
		var self = this;
		self.setOption("submitHandler", handler);
		return self;
	},

}//prototype
	/**
	 * 폼 데이터를 object로 변환한다.
	 *
	 * @param {} form
	 * @param {boolean} isTrim form 값 세팅시 trim 처리 여부. (validate 결과와 달라질 수 있으므로 주의 필요.)
	 * @param {boolean} isReplaceEnter "\r\n"값을 "<br>"로 변경
	 * @returns {Object}
	 */
	ETValidate.convertFormToObject = function(form, isTrim, isReplaceEnter) {
		var arr = $(form).serializeArray();
		var result;

		isTrim = isTrim === true;

		result = _.transform(arr, function(output, item) {
			var value = item.value;
			if (isTrim) {
				value = value.trim();
			}
			if (isReplaceEnter) {
				value = value.replace(/(\r)*\n/g, '<br>');
			}
			_.set(output, item.name, value);
		}, {});

		return result;
	};
	/**
	 * 폼 데이터를 FormData로 변환한다.
	 *
	 * @param {} form
	 * @param {boolean} isTrim form 값 세팅시 trim 처리 여부. (validate 결과와 달라질 수 있으므로 주의 필요.)
	 * @param {boolean} isReplaceEnter "\r\n"값을 "<br>"로 변경
	 * @returns {Object}
	 */
	ETValidate.convertFormToFormData = function(form, isTrim, isReplaceEnter) {
		var arr = $(form).serializeArray();
		var result = new FormData();
		isTrim = isTrim === true;
		$.each(arr, function(index, item) {
			var value = item.value;
			if (isTrim) {
				value = value.trim();
			}
			if (isReplaceEnter) {
				value = value.replace(/(\r)*\n/g, '<br>');
			}
			result.append(item.name, value);
		});

		return result;
	};
		/**
	 * validator에 신규 조건 및 validator 함수를 추가
	 *
	 * @param {string} type 신규 조건명
	 * @param {function(value, element, params)} method return false 일때 에러메시지
	 * @param {string} defaultMessage 기본 메시지
	 */
	ETValidate.addMethod = function(type, method, defaultMessage) {
//		defaultMessage = $.validator.format(defaultMessage);
		$.validator.addMethod(type, method, defaultMessage);
	};

	/**
	 * 폼 데이터에 내용을 object로 변환한다.
	 * xss 공격 가능성이 있는 오브젝트 검사 추가
	 *
	 * @param {} form
	 * @param {boolean} isTrim form 값 세팅시 trim 처리 여부. (validate 결과와 달라질 수 있으므로 주의 필요.)
	 * @param {boolean} isReplaceEnter "\r\n"값을 "<br>"로 변경
	 * @returns {Object}
	 */
	ETValidate.convertXSSFormToObject = function(form, isTrim, isReplaceEnter) {
		var arr = $(form).serializeArray();
		var result;

		isTrim = isTrim === true;

		result = _.transform(arr, function(output, item) {
			var value = item.value;
			if (isTrim) {
				value = value.trim();
			}
			if (isReplaceEnter) {
				value = value.replace(/(\r)*\n/g, '<br>');
			}
			value = value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
			_.set(output, item.name, value);
		}, {});

		return result;
	};
// ==============================validator addMethod==============================
/** datechecker
 * 시작일이 종료일보다 앞선 날짜
 * elementName: 시작일
 * param : 종료일
 */
$.validator.addMethod("startEndDateChecker", function(value, element, params){
	var paramsVal = $(element.form).find("[name="+params.elem+"]").val();
	if(paramsVal !== ""){
		return new Date(value) <= new Date(paramsVal);
	}else{
		return true;
	}
});
/** maxdate
 * 날짜수 초과시 체크
 * elementName: 종료일
 * param : 시작일
 */
$.validator.addMethod("maxDate", function(value, element, params){
	var paramsVal = $(element.form).find("[name="+params.elem+"]").val();
	if(value !== null && paramsVal !== ""){
		var sdate = new Date(value);
		var edate = new Date(paramsVal);
		var time = edate.getTime() - sdate.getTime();
		var date = time / (1000*60*60*24);
		return date < Number(params.num);
	}else{
		return true;
	}
});
/**IMGFILESIZE
 * 이미지파일크기
 */
$.validator.addMethod("imgFileCheck", function(value, element, params){
	if(value !== ""){
		 var maxSize  = 1024*1024*params.num;//7mb
		 var size = document.getElementById(element.id).files[0].size;
		return size < maxSize;
	}else{
		return true;
	}
});
/**SUMMERNOTEREQUIRED
 * summernote 필수입력체크
 */
$.validator.addMethod("summernoteCheck", function(value, element, params){
	if ($(element).summernote('isEmpty')){
		return false;
	}else{
		return true;
	}

});
/**FOURNUMBER
 * 4자리 체크
 */
$.validator.addMethod("fourNumber", function(value, element, params){
	return value.length === 4;
});

/**
 * 파일 확장자 체크
 * @param value
 * @param element
 * @param params
 * @returns
 */
$.validator.addMethod("extension",function(value, element, params) {
	params = typeof params === "string" ? params.replace(/,/g, '|') : params;
    return this.optional(element) || value.match(new RegExp(".(" + params + ")$", "i"));
});

$.validator.addMethod("xss_script",function(value, element, params) {
	var xss = DOMPurify.sanitize(value);
	return xss.length !== 0;
});

