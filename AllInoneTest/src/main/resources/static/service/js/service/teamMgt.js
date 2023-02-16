/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2022. 05. 04.
 * @DESC : [관리자웹]  고객사 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "teamMgt") {
			et.vc= ctrl(et);
		}
		
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "teamMgt";
	ctrl.path = "/tenant/teamMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.groupType = "team";
	ctrl.rowData = null;
	ctrl.groupList = null;
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
		self.groupList = initData.groupList;
		self.setValidate();
		self.setView();
		self.addEventListener();
	};
	
	ctrl.setView = function(){
		var self = et.vc;
		var groupList = self.groupList;
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");  
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
			if ($(elemt).prop("localName") !== "select") {
				$(elemt).val(data[name] === undefined ? "" :data[name]);
			} else {
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
					$(elemt).data("data",data);
				} else {
					$(elemt).val(data[name]);
					$(elemt).data("data",data);
				}
				triggerElement.push(elemt);
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
	

	// ============================== 동작 컨트롤 ==============================
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("group_name",editValidate.REQUIRED,et.message("validate.required", {name:"팀명"}));
		editValidate.validateRules("group_master",editValidate.REQUIRED,et.message("validate.required", {name:"담당자명"}));
		editValidate.validateRules("phone",editValidate.REQUIRED,et.message("validate.required", {name:"연락처"}));
		editValidate.validateRules("phone",editValidate.NUMBERONLY,et.message("validate.only_num", {name:"연락처"}));
		editValidate.apply();
		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객사"}));
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"센터"}));
		addValidate.validateRules("team_id",addValidate.REQUIRED,et.message("validate.required", {name:"팀 일련번호"}));
		addValidate.validateRules("team_id",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"팀 일련번호"}));
		addValidate.validateRules("group_name",addValidate.REQUIRED,et.message("validate.required", {name:"팀명"}));
		addValidate.validateRules("group_master",addValidate.REQUIRED,et.message("validate.required", {name:"담당자명"}));
		addValidate.validateRules("phone",addValidate.REQUIRED,et.message("validate.required", {name:"연락처"}));
		addValidate.validateRules("phone",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"연락처"}));
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
		
		$("#selGroup").trigger("change");
		$("#selGroup").change(self.selGroupListChangeHandler);
		$("#selNGroup").change(self.selNGroupIdChangeHandler);
		et.setEnterKeyDownEvent("#iptTeamName", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setEnterKeyDownEvent("#iptSearchTenantMaster", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		// 라디오 버튼 이벤트 핸들러
		$("input:radio[name=raNSessionTime]").click(function(event){
			if ($(event.currentTarget).val() === "0") {
				$("#iptNMaxSesstionTime").val("0");
				$("#iptNMaxSesstionTime").attr("disabled",true);
			} else {
				$("#iptNMaxSesstionTime").removeAttr("disabled");
			}
		});
		
		// 라디오 버튼 이벤트 핸들러
		$("input:radio[name=raSessionTime]").click(function(event){
			if ($(event.currentTarget).val() === "0") {
				$("#iptMaxSesstionTime").val("0");
				$("#iptMaxSesstionTime").attr("disabled",true);
			} else {
				$("#iptMaxSesstionTime").removeAttr("disabled");
			}
		});
		
		et.setNumberKeyPressEvent("#iptUteamId");
		et.setNumberKeyPressEvent("#iptPhone");
		et.setNumberKeyPressEvent("#iptNteamId");
		et.setNumberKeyPressEvent("#iptNPhone");
		
		$("#btnSearch").trigger("click");
	}
	
	// ============================== 이벤트 리스너 ==============================
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
	}
	
	ctrl.selNGroupIdChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selNGroupCenter");
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
			params.tenant_id = $("#selGroupCenter").val();
			params.group_name = $("#iptTeamName").val();
			params.group_master = $("#iptSearchTenantMaster").val();
			params.group_type = self.groupType;
			self.createDatatable(params);
		}
	}
	
	/*
	 * 고객사 추가 버튼 클릭 핸들러
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
		var params = {};
		params.group_cd = self.rowData.group_cd;
		params.type = 'team';
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delTeamMgt", params);
		});
	}
	
	/*
	 * 삭제 성공시 핸들러
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
				data : "view_cust_name",
			},{
				data : "view_center_name",
			},{
				data : "group_name",// 팀
			},{
				data : "team_id",// 팀
			},{
				data : "group_master",
			},{
				data : "address",
			},{
				data : "phone",
			}
			];
		
		var table = et.createDataTableSettings(columns, self.path + "/getTableList", params, self.dataTableDrawCallback);
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
        self.rowData = rowData;
        self.setViewData(self.editFormId,rowData);
    };
	
	// ============================== Form 리스너 ==============================
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.DBTYPE = "I";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setTeamMgt", formData);
	}
	
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.group_cd = self.rowData.group_cd;
		formData.DBTYPE = "U";
		formData.group_id = self.rowData.group_id;
		formData.tenant_id = self.rowData.tenant_id;
		formData.team_id = self.rowData.team_id;
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setTeamMgt", formData);
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
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"일련번호"}));
			}
		}
	}
	
	
	return ctrl;
}));