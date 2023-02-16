/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : mkjang
 * @CreateDate : 2022. 10. 18.
 * @DESC : [과금관리] 내선별 리포트 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "rateMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "rateMgt";
	ctrl.path = "/chargeMgt/rateMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.tbRateListId = "#tbRateList";
	ctrl.tbNRateListId = "#tbNRateList";
	ctrl.initData = null;
	ctrl.INFO_TYPE = "NORMAL";
	ctrl.rullBtnSetting = {
				is_write : "#btnAdd"
				, is_delete : "#btnDelete" 
				, is_update : "#btnEdit" 
				, is_search : "#btnSearch" 
	}
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		et.common.btnRuleClassSetting(self.rullBtnSetting);
		et.common.btnRuleSetting();
		self.initData = initData;
		self.setValidate();
		self.addEventListener();
		self.setView(initData);
		et.initializeDatepicker(new Date(), '#divNApplyDttm');
		et.initializeDatepicker(new Date(), '#divApplyDttm');
	};
	
	ctrl.setView = function(initData) {
		var self = et.vc;
		
		et.makeSelectOption(initData.telecomList, {value:"telecom_code", text:"telecom_name"}, "#selTelecom");  // 사업자 선택
		et.makeSelectOption(initData.telecomList, {value:"telecom_code", text:"telecom_name"}, "#selNTelecom");  // 사업자 선택
	}
	
	/**
	 * viewData Setting
	 * formId 를 넘겨서 해당 form 만 설정
	 * data의 값은 dataTables row 값이나 또는 디폴트 값
	 */
	ctrl.setViewData = function (formId,data) {
		var self = et.vc;
		//이벤트를 해줄 대상
		var triggerElement = [];
		$(formId).data(data);
		$(formId).find("input,select").each(function(index, elemt){
			var name = $(elemt).prop("name");
			if ($(elemt).prop("type") === "radio") {
				if ($(elemt).val() === data[name]) {
					$(elemt).prop("checked",true);
					triggerElement.push(elemt);
				}
				
			} else if($(elemt).prop("localName") === "select"){
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				} else {
					$(elemt).val(data[name]);
				}
				if (name !== "rate_code") {
					triggerElement.push(elemt);
				}
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
		var self = et.vc;
		
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("prefix_name",editValidate.REQUIRED,et.message("validate.required", {name:"Prefix 명"}));
		editValidate.validateRules("prefix",editValidate.REQUIRED,et.message("validate.required", {name:"Prefix"}));
		editValidate.validateRules("apply_dttm",editValidate.REQUIRED,et.message("validate.required", {name:"적용일"}));
		editValidate.validateRules("rate_code",editValidate.REQUIRED,et.message("validate.required", {name:"요율명칭"}));
		editValidate.apply();

		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("prefix_name",addValidate.REQUIRED,et.message("validate.required", {name:"Prefix 명"}));
		addValidate.validateRules("prefix",addValidate.REQUIRED,et.message("validate.required", {name:"Prefix"}));
		addValidate.validateRules("apply_dttm",addValidate.REQUIRED,et.message("validate.required", {name:"적용일"}));
		addValidate.validateRules("rate_code",addValidate.REQUIRED,et.message("validate.required", {name:"요율명칭"}));
		addValidate.apply();
	};
	
	ctrl.addEventListener = function () {
		var self = et.vc;
		$("button[name='ratePopup']").click(self.openRateModal);
		
		$("#btnSearch").click(self.btnSearchHandler);
		$("#btnAdd").click(self.btnAddHandler);
		$("#btnSave").click(self.btnSaveHandler);
		$("#btnSaveCancel").click(self.btnSaveCancelHandler);
		$("#btnEdit").click(self.btnEditHandler);
		$("#btnDelete").click(self.btnDeleteHandler);
		
		$("#selRate").change(self.selRateChangeHandler);
		$("#selNRate").change(self.selRateChangeHandler);
		
		$("#selTelecom").change(self.selTeleComChangeHandler);
		$("#selNTelecom").change(self.selTeleComChangeHandler);
		
		et.setEnterKeyDownEvent("#iptPrefixName,#iptPrefix", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		
	}
	
	// ============================== 동작 컨트롤 ==============================
	ctrl.createRateSelectBox = function (rateId, telecomParam) {
		var self = et.vc;
		
		
		//var rateCodeList = self.initData.rateCodeList;
		
	//et.makeSelectOption(rateCodeList[telecomParam.telecom_code] ?? [], {value:"bill_type", text:"code_name"}, rateId);  // 사업자 선택
		//$(rateId).trigger("change");
		
		new ETService().setSuccessFunction(function (result){
			if (result.message === ETCONST.SUCCESS) {
				if (rateId === "#selRate") {
					$(self.tbRateListId).DataTable().destroy().draw();
				} else {
					$(self.tbNRateListId).DataTable().destroy().draw();
				}
				et.makeSelectOption(result.data ?? [], {value:"bill_type", text:"code_name"}, rateId);
				if ($(rateId).parents("form").data("rate_code")) {
					$(rateId).val($(rateId).parents("form").data("rate_code"));
				}
				$(rateId).trigger("change");
			}
		}).callService(self.path + "/getRateGroupList", telecomParam);
	}
	
	// ============================== 이벤트 리스너 ==============================
	/**
	 * 요율 관리 모달창을 띄워 준다
	 */
	ctrl.openRateModal = function () {
		var self = et.vc;
		$("#modalAddList").modal("show");
		et.rateModal.viewInit();
	}
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = _.debounce(function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.prefix_name = $("#iptPrefixName").val();
			params.prefix = $("#iptPrefix").val();
			
			self.createDatatable(params);
		}
	},500)
	/*
	 * 내선번호 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = _.debounce(function () {
		var self = et.vc;
		debugger;
		self.setViewData(self.addFormId,{route:"*"});
		$("#bxWrite").show();
//		if (0 < self.initData.rateDetailList.length) {
//			$("#selNRate").children("option:eq(0)").prop("selected", true);
//			$("#selNRate").trigger('change');
//		}
        
	},500)
/*
	 * 추가 버튼 클릭 핸들러
	 */
	ctrl.btnSaveHandler = _.debounce(function () {
		var self = et.vc;
		$(self.addFormId).submit();
	},500)
	
	/*
	 * 취소 버튼 클릭 핸들러
	 */
	ctrl.btnSaveCancelHandler = _.debounce(function () {
		var self = et.vc;
		$("#bxWrite").hide();
	},500)
	
	/*
	 * 수정 버튼 클릭 핸들러
	 */
	ctrl.btnEditHandler = _.debounce(function () {
		var self = et.vc;
		$(self.editFormId).submit();
	},500)
	/*
	 * 삭제 버튼 클릭 핸들러
	 */
	ctrl.btnDeleteHandler = _.debounce(function () {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(self.editFormId,true,true);
		var params = {};
		params.deleteList = [{info_id:formData.info_id}];
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delRateInfo", params);
		});
	},500)
	
	ctrl.deleteSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_success"));
			$("#btnSearch").trigger("click");
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_faile"));
		}
	}
	
	/**
	 * 요율 명칭 변경시 요율 정보를 가져온다
	 */
	ctrl.selRateChangeHandler = function (e) {
		var self = et.vc;
		var params = $(e.currentTarget).children("option:selected").data();
		if (params !== undefined) {
			var rateList = params.children;
			if ($(e.currentTarget).prop("id") === "selRate") {
				self.createDataRatetable(self.tbRateListId ,rateList);
			} else {
				self.createDataRatetable(self.tbNRateListId ,rateList);
			}
		}
	}
	
	ctrl.selTeleComChangeHandler = function (e) {
		var self = et.vc;
		var params = $(e.currentTarget).children("option:selected").data();
		if ($(e.currentTarget).prop("id") === "selTelecom") {
			self.createRateSelectBox("#selRate" ,params);
		} else {
			self.createRateSelectBox("#selNRate" ,params);
		}
	}
	
	
	// ============================== DataTables 생성, 이벤트들 ==============================
	/**
	 * 테이블 조회
	 */
	ctrl.createDatatable = function(postData){
		var self = et.vc;
		var params = postData || {};
		var columns = [
				{data: "prefix_name"}
				,{data: "prefix"}
				,{data: "telecom_name"} 
				,{data: "rate_name"} 
				,{data: "apply_dttm",className:"txtCenter"} 
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getRateInfoList", params, self.dataTableDrawCallback);
		table.paging = false;
		$(self.tableId).DataTable(table);
	}
	
	/**
	 * 상세 페이지 요율 정보 테이블 조회
	 */
	ctrl.createDataRatetable = function(tableId, rateList){
		var self = et.vc;
		var columns = [
			{data: "bill_range",
				render : function (data,type,full,meta) {
					 var str = data+"대역";
					 return str;
				 }
			}
			,{data: "holiday",
			 render : function (data,type,full,meta) {
				 var str = data === "Y" ? "평일" : "토/일/공휴일";
				 return str;
				 }
			}
			,{data: "view_start_time", className:"txtCenter"}
			,{data: "view_end_time", className:"txtCenter"}
			,{data: "time", className:"txtRight"}
			,{data: "rate", className:"txtRight"}
			,{data: "rate_name"}
			];
		
		var option = et.createDataTableSettings(columns, null, {}, self.dataTableDrawCallback,"",false,rateList);
		option.paging = false;
		$(tableId).DataTable(option);
	}
	
	/**
	 * 테이블 콜백
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		if (settings.sTableId === "tbList") {
			$("#divRateInfo").show();
			$(self.tableId + ' tbody tr:nth-child(1) td:nth-child(2)').trigger('click');
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
        var self = et.vc;
        var tableId = "#"+$($target).parents("table").prop("id");
        var rowData = et.getRowData(tableId, $target.closest("tr"));
        if (tableId === self.tableId) {
        	self.setViewData(self.editFormId,rowData);
        	$("#divRateInfo").hide();
        }
    };
	
	// ============================== Form 리스너 ==============================
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.DBTYPE = "I";
		formData.info_type = self.INFO_TYPE;
		new ETService().setSuccessFunction(self.addRateInfoSubmitSuccessHandler).callService(self.path + "/setRateInfo", formData);
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.DBTYPE = "U";
		formData.info_type = self.INFO_TYPE;
		new ETService().setSuccessFunction(self.addRateInfoSubmitSuccessHandler).callService(self.path + "/setRateInfo", formData);
	}
	
	ctrl.addRateInfoSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#btnSearch").trigger("click");
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