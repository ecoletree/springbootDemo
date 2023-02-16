/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 10. 21.
 * @DESC : [조직관리] 다이얼러관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "dialerMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "dialerMgt";
	ctrl.path = "/tenant/dialerMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.pwFormId = "#pwForm";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.selectList = null;
	ctrl.skillData = null;
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
		self.setView();
//		
		$("#btnSearch").trigger("click");
		
	};
	
	ctrl.setView = function(){
		var self = et.vc;
		
	    var initData = self.initData;
		var groupList = initData.groupList;
		
		
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption(groupList, {value:"group_cd", text:"view_group_name"}, "#selNGroup");
		et.makeSelectOption(groupList, {value:"group_cd", text:"view_group_name"}, "#selUGroup");
		
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
		$(formId).find("input,select").each(function(index, elemt){
			var name = $(elemt).prop("name");
			if ($(elemt).prop("type") === "radio") {
				if ($(elemt).val() === data[name]) {
					$(elemt).prop("checked",true);
					triggerElement.push(elemt);
				}
			}else if ($(elemt).prop("type") === "checkbox") {
				$("input").prop('checked', false);
				
			} else if($(elemt).prop("localName") === "select"){
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				} else {
					$(elemt).val(data[name]);
				}
				triggerElement.push(elemt);
			}  else {
				var iptVal = data[name];
				if (name === "dialer_ip") {
					if(data[name] !== undefined){
						var ipList = data[name].split(".");
						var index = $(elemt).index() < 1 ? $(elemt).index() : $(elemt).index() /2 ;
						iptVal = ipList[index];
					}
				}
				$(elemt).val(iptVal);
				
				triggerElement.push(elemt);
			}
		});
		/**
		 * 만약 select와 input 이 있으면 click 또는 change 이벤트를 걸어준다
		 */
		$.each(triggerElement,function(index,elemt){
			if ($(elemt).prop("localName") === "select") {
				$(elemt).trigger("change");
			}else if ($(elemt).prop("type") === "checkbox") {
			} 
			else if ($(elemt).prop("localName") === "input") {
				$(elemt).trigger("click");
			}
		});
	}

	// ============================== 동작 컨트롤 ==============================
/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;
 
 		ETValidate.addMethod("ip_validation",function(value, element, params) {
			var name = $(element).prop("name");
			var list = [];
			var ipElements = $(element).parent().find("input[name='"+name+"']"); 
			ipElements.each(function (i) {
		        var value = ipElements.eq(i).val();
		        if(value.length !== 0 ){
					list.push(value);
				}
		    });
		    var ip_validation = list.length < 4 ? false : true;
		    
			return ip_validation;
		});
		
				
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("dialer_cd",editValidate.REQUIRED,et.message("validate.required", {name:"다이얼러 코드"}));
		editValidate.validateRules("dialer_name",editValidate.REQUIRED,et.message("validate.required", {name:"다이얼러 명"}));
		editValidate.validateRules("dialer_type",editValidate.REQUIRED,et.message("validate.required", {name:"다이얼러 타입"}));
		editValidate.validateRules("dialer_ip","ip_validation",et.message("validate.required", {name:"IP"}));
		editValidate.validateRules("dialer_port",editValidate.REQUIRED,et.message("validate.required", {name:"Port"}));
		editValidate.validateRules("dialer_user",editValidate.REQUIRED,et.message("validate.required", {name:"User ID"}));
		editValidate.validateRules("dialer_pw",editValidate.REQUIRED,et.message("validate.required", {name:"Password"}));
		editValidate.apply();
		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("dialer_cd",addValidate.REQUIRED,et.message("validate.required", {name:"다이얼러 코드"}));
		addValidate.validateRules("dialer_name",addValidate.REQUIRED,et.message("validate.required", {name:"다이얼러 명"}));
		addValidate.validateRules("dialer_type",addValidate.REQUIRED,et.message("validate.required", {name:"다이얼러 타입"}));
		addValidate.validateRules("dialer_ip","ip_validation",et.message("validate.required", {name:"IP"}));
		addValidate.validateRules("dialer_port",addValidate.REQUIRED,et.message("validate.required", {name:"Port"}));
		addValidate.validateRules("dialer_user",addValidate.REQUIRED,et.message("validate.required", {name:"User ID"}));
		addValidate.validateRules("dialer_pw",addValidate.REQUIRED,et.message("validate.required", {name:"Password"}));
		addValidate.apply();
		
	};
		
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnSearch").click(self.btnSearchHandler);
		$("#btnAdd").click(self.btnAddHandler);
		$("#btnSave").click(self.btnSaveHandler);
		$("#btnSaveCancel").click(self.btnSaveCancelHandler);
		$("#btnEdit").click(self.btnEditHandler);
		$("#btnDelete").click(self.btnDeleteHandler);
		$("#btnSaveList").click(self.btnSaveListHandler); // 엑셀 등록
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		et.setEnterKeyDownEvent("#iptDialerName,#iptDialerType", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setNumberKeyPressEvent("input[name='dialer_ip'],input[name='dialer_port']");
		
	}
		
	// ============================== 이벤트 리스너 ==============================
	
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.group_id = $("#selGroup").val();
			params.dialer_name = $("#iptDialerName").val();
			params.dialer_type = $("#iptDialerType").val();
			
			self.createDatatable(params);
			
		}
		
	}
	/*
	/*
	 *  추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		self.setViewData(self.addFormId,{});
		$("#bxWrite").show();
	}
	/*
	 * 추가 버튼 클릭 핸들러
	 */
	ctrl.btnSaveHandler = function () {
		var self = et.vc;
		$(self.addFormId).submit();
	}
	
	/*
	 * 취소 버튼 클릭 핸들러
	 */
	ctrl.btnSaveCancelHandler = function () {
		var self = et.vc;
		$("#bxWrite").hide();
	}
	
	/*
	 * 수정 버튼 클릭 핸들러
	 */
	ctrl.btnEditHandler = function () {
		var self = et.vc;
		$(self.editFormId).submit();
	}
	/*
	 * 삭제 버튼 클릭 핸들러
	 */
	ctrl.btnDeleteHandler = function () {
		var self = et.vc;
		var rowData = self.rowData;
		var delData = {};
		delData.dialer_cd = rowData.dialer_cd;
		var params = {};
		params.dialerList = [delData];
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delDialer", params);
		});
	}
	
	/**
	 * 삭제 완료 후 Sucess Handler
	 */
	ctrl.deleteSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_success"));
			$("#btnSearch").trigger("click");
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_faile"));
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
				{data: "view_group_name"}
				,{data: "dialer_cd"}
				,{data: "dialer_name"} 
				,{data: "dialer_type"}
				,{data: "dialer_ip",className:"txtCenter"} 
				,{data: "dialer_port",className:"txtCenter"} 
				,{data: "is_use",className:"txtCenter",
					render: function ( data, type, row ) {
						return data === "Y" ? "사용" : "사용안함";
				}} 
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getDialerList", params, self.dataTableDrawCallback);
		table.paging=false;
		
		$(self.tableId).DataTable(table);
	}
	/**
	 * 테이블 콜백
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		if (0 < settings.json.recordsTotal) {
			$(self.tableId + ' tbody tr:nth-child(1) td:nth-child(2)').trigger('click');
    		$('.contRightNoData').hide();
    		$("#tbList_paginate").show();
    	} else {
    		$('.contRightNoData').show();
    		$("#tbList_paginate").hide();
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
        var rowData = et.getRowData(self.tableId, $target.closest("tr"));
        self.rowData = rowData;
        self.setViewData(self.editFormId,rowData);


    }
	// ============================== Form 리스너 ==============================
	
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var dialer_ip = self.dialerIpText(form);
		formData.dialer_ip = dialer_ip;
		formData.DBTYPE = "I";
		
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setDialer", formData);

		
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var dialer_ip = self.dialerIpText(form);
		formData.dialer_ip = dialer_ip;
		formData.dialer_cd = $("#iptDialerCode").val();
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setDialer", formData);
	}

	
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.addSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#btnSearch").trigger("click");
		} else {
			if (result.data === 0) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
			} else {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"다이얼러 코드"}));
			}
		}
	}

	/**
	 * dialer_ip 
	 */	
	ctrl.dialerIpText = function(form){
		var dialer_ip="";
		var ip_form = $(form).find("input[name = 'dialer_ip']");
		for(var i=0; i < ip_form.length ; i++){
			var ip = ip_form.eq(i).val();
			dialer_ip += dialer_ip.length < 1 ? ip : "."+ip;
		}
		return dialer_ip;
	}
		
	return ctrl;
}));