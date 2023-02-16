/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2023. 01. 25.
 * @DESC : [녹취 관리] 녹취전송 FTP 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "ftpMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "ftpMgt";
	ctrl.path = "/record/ftpMgt";
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
				, is_upload : "#btnAdd_list" 
	}
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		et.common.btnRuleClassSetting(self.rullBtnSetting);
		et.common.btnRuleSetting();
		self.groupList = initData.groupList;
		self.setValidate();
		self.addEventListener();
		self.setView();
		$("#btnSearch").trigger("click");
	};
	
	ctrl.setView = function(initData){
		var self = et.vc;
		$('.select2').select2({
        	maximumSelectionLength: 1
	    }); 
	    
		var groupList = self.groupList;
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selUGroup");  
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selNGroup");
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
				
			}  else if($(elemt).prop("localName") === "select"){
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				} else {
					$(elemt).val(data[name]);
				}
				triggerElement.push(elemt);
			}  else {
				var iptVal = data[name];
				if (name === "ftp_ip") {
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
		editValidate.validateRules("group_id",editValidate.REQUIRED,et.message("validate.required", {name:"고객사"}));
		editValidate.validateRules("center_id",editValidate.REQUIRED,et.message("validate.required", {name:"센터"}));
		editValidate.validateRules("ftp_ip","ip_validation",et.message("validate.required", {name:"IP"}));
		editValidate.validateRules("ftp_port",editValidate.REQUIRED,et.message("validate.required", {name:"FTP Port"}));
		editValidate.validateRules("ftp_user",editValidate.REQUIRED,et.message("validate.required", {name:"FTP User"}));
		editValidate.validateRules("ftp_pw",editValidate.REQUIRED,et.message("validate.required", {name:"FTP Password"}));
		editValidate.validateRules("ftp_dir",editValidate.REQUIRED,et.message("validate.required", {name:"FTP Dir"}));
		editValidate.apply();
////		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객사"}));
		addValidate.validateRules("center_id",addValidate.REQUIRED,et.message("validate.required", {name:"센터"}));
		addValidate.validateRules("ftp_ip","ip_validation",et.message("validate.required", {name:"IP"}));
		addValidate.validateRules("ftp_port",addValidate.REQUIRED,et.message("validate.required", {name:"FTP Port"}));
		addValidate.validateRules("ftp_user",addValidate.REQUIRED,et.message("validate.required", {name:"FTP User"}));
		addValidate.validateRules("ftp_pw",addValidate.REQUIRED,et.message("validate.required", {name:"FTP Password"}));
		addValidate.validateRules("ftp_dir",addValidate.REQUIRED,et.message("validate.required", {name:"FTP Dir"}));
		addValidate.apply();
		
	};
		
	ctrl.addEventListener = function () {
		var self = et.vc;
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnAdd").click(_.debounce(self.btnAddHandler,500));
		$("#btnSave").click(_.debounce(self.btnSaveHandler,500));
		$("#btnSaveCancel").click(_.debounce(self.btnSaveCancelHandler,500));
		$("#btnEdit").click(_.debounce(self.btnEditHandler,500));
		$("#btnDelete").click(_.debounce(self.btnDeleteHandler,500));
		
		
		$("#selGroup").trigger("change");
		$("#selGroup").change(self.selGroupListChangeHandler);
		$("#selNGroup").change(self.selNGroupIdChangeHandler);
		
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		et.setNumberKeyPressEvent("input[name='ftp_ip'],input[name='ftp_port']");

	}
		
	// ============================== 이벤트 리스너 ==============================
	ctrl.btnOpenSkillModalHandler = function(){
		var self = et.vc;
		if($(this).is("#btnOpenSkillModal")){
			var groupList = self.groupList;
			et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroupSkill", "고객사 전체",$("#selNGroup").val());
			$("#iptSkillName").val("");
			$("#iptSkillId").val("");
			$("#modalSkillList").modal();
			$("#btnSearchSkill").trigger("click");
		}else{
			$("#modalSkillList").hide();
		}
	}
	
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
		$("#selGroupCenter").trigger("change");
	}
	ctrl.selNGroupIdChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selNGroupCenter");
		$("#selNGroupCenter").trigger("change");
	}
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.group_id = $("#selGroup").val();
			params.center_id = $("#selGroupCenter").val();
			params.ftp_type = $("#selFtpType").val();
			self.createDatatable(params);
		}
		
	}
	/*
	 * 추가 모달 버튼 클릭 핸들러
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
	 * 추가 모달 취소 버튼 클릭 핸들러
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
		var params = {};
		params.ftp_id = rowData.ftp_id;
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delFtpInfo", params);
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
				,{data: "view_center_name"}
				,{data: "ftp_type"}
				,{data: "ftp_ip",className:"txtCenter"} 
				,{data: "ftp_port",className:"txtCenter"}
				,{data: "ftp_user"} 
				,{data: "ftp_pw"} 
				,{data: "ftp_dir"} 
				
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getFtpList", params, self.dataTableDrawCallback);
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
		var ftp_ip = self.dialerIpText(form);
		formData.ftp_ip = ftp_ip;
		formData.DBTYPE = "I";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setFtpInfo", formData);
		
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var rowData = self.rowData;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var ftp_ip = self.dialerIpText(form);
		formData.ftp_ip = ftp_ip;
		formData.ftp_id = rowData.ftp_id;
		formData.group_id = rowData.group_id;
		formData.center_id = rowData.center_id;
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setFtpInfo", formData);
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
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"FTP_TYPE"}));
			}
		}
	}
	/**
	 * dialer_ip 
	 */	
	ctrl.dialerIpText = function(form){
		var dialer_ip="";
		var ip_form = $(form).find("input[name = 'ftp_ip']");
		for(var i=0; i < ip_form.length ; i++){
			var ip = ip_form.eq(i).val();
			dialer_ip += dialer_ip.length < 1 ? ip : "."+ip;
		}
		return dialer_ip;
	}	
	return ctrl;
}));