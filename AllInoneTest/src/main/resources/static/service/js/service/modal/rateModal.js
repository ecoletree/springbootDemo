/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : mkjang
 * @CreateDate : 2022. 10. 18.
 * @DESC : [과금관리] 내선별 리포트 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.rateModal || et.rateModal.name !== "rateModal") {
			et.rateModal= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "rateModal";
	ctrl.path = "/chargeMgt/rateMgt";
	ctrl.addFormId = "#formAddRate";
	ctrl.editFormId = "#formEditRate";
	ctrl.tbRateDetail = "#tbRateDetailList";
	ctrl.rowDetailData = null;
	ctrl.initData = null;
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.rateModal;
		self.initData = initData;
		self.setValidate();
		self.addEventListener();
		self.setView(initData);
	};
	
	ctrl.setView = function(initData) {
		var self = et.rateModal;
		
		self.setTime();
		
		$("#divRate").show();
		
		et.makeSelectOption(initData.telecomList, {value:"telecom_code", text:"telecom_name"}, "#selModalTelecom"); //통신사 구분
		et.makeSelectOption(initData.codeList, {value:"code_cd", text:"code_name"}, "#selModalBillType");  // 통화 구분
		et.makeSelectOption(initData.telecomList, {value:"telecom_code", text:"telecom_name"}, "#selModalFormTelecom"); //통신사 구분
		et.makeSelectOption(initData.codeList, {value:"code_cd", text:"code_name"}, "#selModalFormBillType");  // 통화 구분
		et.makeSelectOption(initData.telecomList, {value:"telecom_code", text:"telecom_name"}, "#selModalFormNTelecom"); //통신사 구분
		et.makeSelectOption(initData.codeList, {value:"code_cd", text:"code_name"}, "#selModalFormNBillType");  // 통화 구분
	}
	
	/*
	 * Main 페이지 요율관리 버튼 클릭 했을 경우
	 */
	ctrl.viewInit = function () {
		$("#bxRateDetailWrite").hide();
	}
	
	ctrl.setTime = function () {
		var self = et.vc;
		var hours = [];
		var minutes = [];
		var i = 0;
		for (i = 0; i < 24; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : txt,text : txt}
			hours.push(data);
		}
		for (i = 0; i < 60; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : txt,text : txt}
			minutes.push(data);
		}
		
		et.makeSelectOption(hours, {value:"value", text:"text"}, "#selSHour");  // 수정:시작시간 시
		et.makeSelectOption(hours, {value:"value", text:"text"}, "#selEHour");  // 수정:종료시간 시
		et.makeSelectOption(hours, {value:"value", text:"text"}, "#selNSHour");  // 추가:시작시간 시
		et.makeSelectOption(hours, {value:"value", text:"text"}, "#selNEHour");  // 추가:종료시간 시
		et.makeSelectOption(minutes, {value:"value", text:"text"}, "#selSMinute");  // 수정:시작시간 분
		et.makeSelectOption(minutes, {value:"value", text:"text"}, "#selEMinute");  // 수정:종료시간 분
		et.makeSelectOption(minutes, {value:"value", text:"text"}, "#selNSMinute");  // 추가:시작시간 분
		et.makeSelectOption(minutes, {value:"value", text:"text"}, "#selNEMinute");  // 추가:종료시간 분
	}
	
	/**
	 * viewData Setting
	 * formId 를 넘겨서 해당 form 만 설정
	 * data의 값은 dataTables row 값이나 또는 디폴트 값
	 */
	ctrl.setViewData = function (formId,data) {
		var self = et.rateModal;
		//이벤트를 해줄 대상
		var triggerElement = [];
		$(formId).find("input,select").each(function(index, elemt){
			var name = $(elemt).prop("name");
			if ($(elemt).prop("type") === "radio") {
				if ($(elemt).val() === data[name]) {
					$(elemt).prop("checked",true);
					triggerElement.push(elemt);
				}
				
			} else if($(elemt).prop("localName") === "select"){
				if (name === "shour") {
					if (data.start_time !== null && 4 === data.start_time?.length) {
						$(elemt).val(data.start_time.substring(0,2));
					} else {
						$(elemt).val("00");
					}
				} else if (name === "sminute") {
					if (data.start_time !== null && 4 === data.start_time?.length) {
						$(elemt).val(data.start_time.substring(2,4));
					} else {
						$(elemt).val("00");
					}
				} else if (name === "ehour") {
					if (data.end_time !== null && 4 === data.end_time?.length) {
						$(elemt).val(data.end_time.substring(0,2));
					} else {
						$(elemt).val("00");
					}
				} else if (name === "eminute") {
					if (data.end_time !== null && 4 === data.end_time?.length) {
						$(elemt).val(data.end_time.substring(2,4));
					} else {
						$(elemt).val("00");
					}
				} else {
					if (data[name] === undefined) {
						$(elemt).children("option:eq(0)").prop("selected", true);
					} else {
						$(elemt).val(data[name]);
					}
				}
				triggerElement.push(elemt);
			} else {
				$(elemt).val(data[name] === undefined ? "" :data[name]);
			}
			
		});
		/**
		 * 만약 select와 input 이 있으면 click 또는 change 이벤트를 걸어준다
		 */
		$.each(triggerElement,function(index,elemt){
			if ($(elemt).prop("localName") === "select") {
				$(elemt).trigger("change");
			} else if ($(elemt).prop("localName") === "input") {
				$(elemt).trigger("click");
			}
		});
	}
	
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.rateModal;

		var addRateDetailValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addRateDetailSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addRateDetailValidate.validateRules("time",addRateDetailValidate.REQUIRED,et.message("validate.required", {name:"기준 시간"}));
		addRateDetailValidate.validateRules("rate",addRateDetailValidate.REQUIRED,et.message("validate.required", {name:"시간당 단가"}));
		addRateDetailValidate.apply();
		
		var editRateDetailValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editRateDetailSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editRateDetailValidate.validateRules("time",editRateDetailValidate.REQUIRED,et.message("validate.required", {name:"기준 시간"}));
		editRateDetailValidate.validateRules("rate",editRateDetailValidate.REQUIRED,et.message("validate.required", {name:"시간당 단가"}));
		editRateDetailValidate.apply();
	};
	
	/**
	 * AddEventListener
	 */
	ctrl.addEventListener = function () {
		var self = et.rateModal;
		// 요율 팝업에서 발생하는 컨트롤들
		$("#btnRateAdd").click(self.btnRateDetailAddHandler);
		$("#btnRateSave").click(self.btnRateDetailSaveHandler);
		$("#btnRateDetailCancel").click(self.btnRateDetailCancelHandler);
		$("#btnRateDetailEdit").click(self.btnRateDetailSaveHandler);
		$("#btnRateDetailDel").click(self.btnRateDetailDeleteHandler);
		
		
		$("#btnModalSearch").click(self.btnSearchHandler);
		
		et.setDataTableRowSelection(self.tbRateDetail, self.tbListRowSelect);
		
		et.setNumberKeyPressEvent(self.editFormId+" input[name='bill_range']");
		et.setNumberKeyPressEvent(self.editFormId+" input[name='time']");
		self.decimalKeyPressEvent(self.editFormId+" input[name='rate']");
		et.setNumberKeyPressEvent(self.addFormId+" input[name='bill_range']");
		et.setNumberKeyPressEvent(self.addFormId+" input[name='time']");
		self.decimalKeyPressEvent(self.addFormId+" input[name='rate']");
	}
	
	/**
	 * 숫자와 . 만 입력 가능하도록 수정
	 */
	ctrl.decimalKeyPressEvent = function (target) {
		$(target).keypress(function (evt){
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			var _value = event.srcElement.value;
			
			if (_value == "") {
				_value = "0";
			}
			
			if (evt.keyCode < 48 || evt.keyCode > 57) {
				if (evt.keyCode !== 46) { //숫자와 . 만 입력가능
					return false;
				}
			}
			
			// 두자리 이하의 숫자만 입력가능
	        var _pattern1 = /^\d{3}$/; // 현재 value값이 2자리 숫자이면 . 만 입력가능
	        // {숫자}의 값을 변경하면 자리수를 조정할 수 있다.
	        if (_pattern1.test(_value)) {
	            if (charCode !== 46) {
	                return false;
	            }
	        }

	        // 소수점 둘째자리까지만 입력가능
	        var _pattern2 = /(^\d+$)|(^\d{1,}.\d{0,2}$)/; // 현재 value값이 소수점 둘째짜리 숫자이면 더이상 입력 불가
	        // {숫자}의 값을 변경하면 자리수를 조정할 수 있다.
	        if (!_pattern2.test(_value)) {
	            return false;
	        }     
	        return true;
	        
		});
		
		$(target).keyup(function (evt){
			var objTarget = evt.srcElement || evt.target;
			var _value = event.srcElement.value;
			if (/[ㄱ-ㅎㅏ-ㅡ가-힣]/g.test(_value)){
				objTarget.value = null;
			}
		});
	}
	
	// ============================== 동작 컨트롤 ==============================
		
	// ============================== 이벤트 리스너 ==============================
	
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = _.debounce(function () {
		var self = et.rateModal;
		var params = {};
		params.telecom_code = $("#selModalTelecom").val();
		params.bill_type = $("#selModalBillType").val();
		self.createRateDatatable(params);
	},500)
	
	
	/**
	 * 요율 관리 신규 추가 화면
	 */
	ctrl.btnRateDetailAddHandler = _.debounce(function () {
		var self = et.rateModal;
		self.setViewData(self.addFormId,{bill_range:1, rate:0, time:0});
		$("#bxRateDetailWrite").show();
	},500);
	
	/**
	 * 요율 관리 신규 저장
	 */
	ctrl.btnRateDetailSaveHandler = _.debounce(function (e) {
		var self = et.rateModal;
		if ($(e.currentTarget).prop("id") === "btnRateDetailEdit") {
			$(self.editFormId).submit();
		} else {
			$(self.addFormId).submit();
		}
	},500);
	
	/**
	 * 요율 관리 신규 취소
	 */
	ctrl.btnRateDetailCancelHandler = _.debounce(function () {
		var self = et.rateModal;
		$("#bxRateDetailWrite").hide();
		if (0 < self.initData.rateDetailList.length) {
			$("#divRate").hide();
		} else {
			$("#divRate").show();
		}
	},500);
	
	/**
	 * 요율 관리 신규 삭제
	 */
	ctrl.btnRateDetailDeleteHandler = _.debounce(function () {
		var self = et.rateModal;
		var params = {};
		params.deleteList = [{id : $(self.editFormId+" input[name='id']").val()}];
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteRateDetailSuccessHandler).callService(self.path + "/delRate", params);
		});
	},500);
	
	/**
	 * 요율 관리 신규 삭제 SuccessHandler
	 */
	ctrl.deleteRateDetailSuccessHandler = function (result) {
		var self = et.rateModal;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_success"));
			$("#btnModalSearch").trigger("click");
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_faile"));
		}
	}
	
	// ====================== 요율 관리 모달창에서 발생하는 이벤트들 =========================
	/**
	 * 요율 신규 추가
	 */
	ctrl.btnAddRateHandler = _.debounce(function () {
		var self = et.rateModal;
		$("#bxRateWrite").show();
	}, 500);
	
	/**
	 * 요율 신규 저장
	 */
	ctrl.btnRateSaveHandler = _.debounce(function () {
		var self = et.rateModal;
		$(self.formRate).submit();
	}, 500);
	
	/**
	 * 요율 신규 취소
	 */
	ctrl.btnRateCancelHandler = _.debounce(function () {
		var self = et.rateModal;
		$("#bxRateWrite").hide();
	}, 500);
	
	// ============================== DataTables 생성, 이벤트들 ==============================
	
	/**
	 * 테이블 조회
	 */
	ctrl.createRateDatatable = function(postData){
		var self = et.rateModal;
		var params = postData || {};
		var columns = [
			{data: "telecom_name"}
			, {data: "view_code_name"}
			, {data: "bill_range",
				render : function (data,type,full,meta) {
					 var str = data+"대역";
					 return str;
				 }
			}
			, {data: "holiday",
			 render : function (data,type,full,meta) {
				 var str = data === "Y" ? "평일" : "토/일/공휴일";
				 return str;
				 }
			}
			, {data: "view_start_time", className:"txtCenter"}
			, {data: "view_end_time", className:"txtCenter"}
			, {data: "time", className:"txtRight"}
			, {data: "rate", className:"txtRight"}
			, {data: "rate_name"}
			];
		var table = et.createDataTableSettings(columns, self.path + "/getRateList", params, self.dataTableDrawCallback);
		table.paging = false;
		$(self.tbRateDetail).DataTable(table);
	}
	
	/**
	 * 테이블 콜백
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.rateModal;
		if (settings.sTableId === "tbRateDetailList") {
			if (0 < settings.json.recordsTotal) {
				$("#divRate").hide();
			} else {
				$("#divRate").show();
			}
			$("#tbRateDetailList_paginate").hide();
			$("#"+settings.sTableId + ' tbody tr:nth-child(1) td:nth-child(1)').trigger('click');
		}
		
	}
	
	 /**
     * 테이블 row 선택시
     *
     * @param {jQuery} $target 클릭한 대상
     * @param {number} row 행 인덱스
     * @param {number} col 열 인덱스
     */
    ctrl.tbListRowSelect = function($target, row, col) {
        var self = et.rateModal;
        var tableId = "#"+$($target).parents("table").prop("id");
        var rowData = et.getRowData(tableId, $target.closest("tr"));
        self.setViewData(self.editFormId,rowData);
    };
    
    /**
	 * 요율 삭제 Sucess Handler
	 */
    ctrl.deleteRateSuccessHandler = function (result) {
    	var self = et.rateModal;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_success"));
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_faile"));
		}
    }
	
	// ============================== Form 리스너 ==============================
	
	/**
	 * 요율 정보 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addRateDetailSubmitCallbackHandler = function (form) {
		var self = et.rateModal;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.DBTYPE = "I";
		formData.start_time = formData.shour+formData.sminute;
		formData.end_time = formData.ehour+formData.eminute;
		new ETService().setSuccessFunction(self.addRateDetailSubmitSuccessHandler).callService(self.path + "/setRate", formData);
	}
	
	/**
	 * 요율 정보 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editRateDetailSubmitCallbackHandler = function (form) {
		var self = et.rateModal;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.DBTYPE = "U";
		formData.start_time = formData.shour+formData.sminute;
		formData.end_time = formData.ehour+formData.eminute;
		new ETService().setSuccessFunction(self.addRateDetailSubmitSuccessHandler).callService(self.path + "/setRate", formData);
	}
	
	ctrl.addRateDetailSubmitSuccessHandler = function (result) {
		var self = et.rateModal;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#btnModalSearch").trigger("click");
			$("#selTelecom").trigger("change");
			$("#selNTelecom").trigger("change");
		} else {
			if (result.data === 0) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
			} else {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"요율 코드"}));
			}
		}
	}
		
	return ctrl;
}));