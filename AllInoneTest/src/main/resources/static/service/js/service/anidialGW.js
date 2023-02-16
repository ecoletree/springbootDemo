/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2022. 05. 04.
 * @DESC : [관리자웹]  AniDial 게이트웨이 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "anidialGW") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "anidialGW";
	ctrl.path = "/gateway/anidialGW";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
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
		ETValidate.addMethod("ipPortValidate",function(value, element, params) {
			var parts = value.split(":");
		    var ip = parts[0].split(".");
		    var port = parts[1];
		    return self.validateNum(port, 1, 65535) && ip.length == 4 && ip.every(function (segment) {
		        return self.validateNum(segment, 0, 255);
		    });
		});
		 
		self.getGwGroupList();
		self.setValidate();
		self.addEventListener();
	};
	
	/**
	 * viewData Setting
	 * formId 를 넘겨서 해당 form 만 설정
	 * data의 값은 dataTables row 값이나 또는 디폴트 값
	 */
	ctrl.setViewData = function (formId,data) {
		var self = et.vc;
		var triggerElement = [];
		$(formId).find("input,select").each(function(index, elemt){
			var name = $(elemt).prop("name");
			if ($(elemt).prop("localName") !== "select") {
				$(elemt).val(data[name] === undefined ? "" :data[name]);
			} else {
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				} else {
					$(elemt).val(data[name]);
				}
			}
		});
		/**
		 * 만약 select와 input 이 있으면 click 또는 change 이벤트를 걸어준다
		 */
		$.each(triggerElement,function(index,elemt){
			if ($(elemt).prop("localName") === "select") {
				$(elemt).trigger("change");
			}
		});
	}
	
	/**
	 * ip:port validation
	 */
	ctrl.validateNum = function (input, min, max) {
		var num = +input;
	    return num >= min && num <= max && input === num.toString();
	}
		ctrl.getGwGroupList = function(){
		var self = et.vc;
		new ETService().setSuccessFunction(self.getGWGroupSuccessHandler).callService(self.path + "/getGWGroup", {});
	}
	ctrl.getGWGroupSuccessHandler = function(result){
		var self = et.vc;
		self.gwGroupList = [];
		if (result.message === ETCONST.SUCCESS) {
			self.gwGroupList = result.data;
		}
		et.makeSelectOption(self.gwGroupList, {value:"group_gw_id", text:"view_group_gw_name"}, "#selGWGroupList", "그룹 전체");  // 조회 gw group	
		  // 조회 gw group	
		
	}
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("prefix4",editValidate.REQUIRED,et.message("validate.required", {name:"PREFIX"}));
		editValidate.validateRules("gateway","ipPortValidate",et.message("validate.ipaddress", {name:"GATEWAY"}));
		editValidate.validateRules("group_gw_id",editValidate.REQUIRED,et.message("validate.required", {name:"그룹 ID"}));
		editValidate.validateRules("group_gw_name",editValidate.REQUIRED,et.message("validate.required", {name:"그룹 명"}));
		editValidate.apply();
		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("prefix4",addValidate.REQUIRED,et.message("validate.required", {name:"PREFIX"}));
		addValidate.validateRules("gateway","ipPortValidate",et.message("validate.ipaddress", {name:"GATEWAY"}));
		addValidate.validateRules("group_gw_id",addValidate.REQUIRED,et.message("validate.required", {name:"그룹 ID"}));
		addValidate.validateRules("group_gw_name",addValidate.REQUIRED,et.message("validate.required", {name:"그룹 명"}));
		addValidate.apply();
	};
	
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnAdd").click(_.debounce(self.btnAddHandler,500));
		$("#btnSave").click(_.debounce(self.btnSaveHandler,500));
		$("#btnSaveCancel").click(_.debounce(self.btnSaveCancelHandler,500));
		$("#btnEdit").click(_.debounce(self.btnEditHandler,500));
		$("#btnDelete").click(_.debounce(self.btnDeleteHandler,500));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		et.setNumberKeyPressEvent("#iptNPrefix4");
		et.setNumberKeyPressEvent("#iptNGWPrefix");
		et.setNumberKeyPressEvent("#iptPrefix4");
		et.setNumberKeyPressEvent("#iptGWPrefix");
		
		$("#selUGWGroupList").change(self.selGWGroupListChangeHandler);
		$("#selNGWGroupList").change(self.selGWGroupListChangeHandler);
			
		$("#btnSearch").trigger("click");
		
	}
	
	// ============================== 이벤트 리스너 ==============================
	ctrl.selGWGroupListChangeHandler = function(){
		var self = et.vc;
		var dataList = $(this).find("option:selected").data() ?? {};
		
		var id = $(this).data().groupid;
		var name = $(this).data().groupname;
		$("#"+id).attr("disabled",true);
		
		if(Object.keys(dataList).length === 0){
			$("#"+id).attr("disabled",false);
		}
		$("#"+id).val(dataList.group_gw_id ?? "");
		$("#"+name).val(dataList.group_gw_name ?? "");
	}
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			self.createDatatable(params);
		}
	}
	
	/*
	 * 고객사 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		
		self.setViewData(self.addFormId,{});
		et.makeSelectOption(self.gwGroupList, {value:"group_gw_id", text:"view_group_gw_name"}, "#selNGWGroupList", "새로운 그룹");
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
		var params = {};
		params.prefix4 = $("#iptPrefix4").val();
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delAnidialGW", params);
		});
	}
	
	/*
	 * 삭제 버튼 클릭 핸들러
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
	/*
	 * 데이터 테이블즈 생성
	 */
	ctrl.createDatatable = function(postData) {
		var self = et.vc;
		var params = postData || {};
		var columns = [
			{
				data : "group_gw_name",
			},{
				data : "prefix4",
				className : "txtCenter",
			},{
				data : "gw_id",
				className : "txtCenter",
			},{
				data : "gateway",
				className : "txtCenter",
			},{
				data : "gateway_prefix",
				className : "txtCenter",
			}
		];
		
		var table = et.createDataTableSettings(columns, self.path + "/getAnidialGWList", params, self.dataTableDrawCallback);
		table.paging = false;
		$(self.tableId).DataTable(table);
	};

	/**
	 * 테이블 생성후 화면에 표시할때 콜
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
        self.setViewData(self.editFormId,rowData);
        et.makeSelectOption(self.gwGroupList, {value:"group_gw_id", text:"view_group_gw_name"}, "#selUGWGroupList", "새로운 그룹",rowData.group_gw_id);  // 조회 gw group1
    };
	
	// ============================== Form 리스너 ==============================
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var gwIsNew = $("#selNGWGroupList").val() === "" ?"Y" :"N";
		formData.gwIsNew = gwIsNew;
		formData.group_gw_id = $("#iptNGWGroupId").val();
		formData.isTrunk = "N";
		formData.DBTYPE = "I";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAnidialGW", formData);
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var gwIsNew = $("#selUGWGroupList").val() === "" ?"Y" :"N";
		formData.gwIsNew = gwIsNew;
		formData.isTrunk = "N";
		formData.group_gw_id = $("#iptUGWGroupId").val();
		formData.prefix4 = $("#iptPrefix4").val();
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAnidialGW", formData);
	}
	
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.addSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			self.getGwGroupList();
			$("#btnSearch").trigger("click");
		} else {
			if (result.data === 0) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
			} else if (result.data === -2) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"그룹 ID"}));
			}else {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"PREFIX"}));
			}
		}
	}
	
	
	return ctrl;
}));