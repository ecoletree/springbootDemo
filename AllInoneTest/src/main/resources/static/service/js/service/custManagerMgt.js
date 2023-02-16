/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2022. 05. 04
 * @DESC : [관리자웹]  고객사 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "custManagerMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "custManagerMgt";
	ctrl.path = "/tenant/custManagerMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.pwFormId = "#pwForm";
	ctrl.tableId = "#tbList";
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
		self.setValidate();
		self.addEventListener();
		self.groupList = initData.groupList;
		
		et.makeSelectOption(initData.groupList, {value:"group_cd", text:"view_group_name"}, "#selGroupList", "고객사전체");  // 고객사 선택
		et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, "#selCenterList", "센터전체");
		et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, "#selTeamList", "팀전체");
	};
	
	/**
	 * viewData Setting
	 * formId 를 넘겨서 해당 form 만 설정
	 * data의 값은 dataTables row 값이나 또는 디폴트 값
	 */
	ctrl.setViewData = function (formId,data) {
		var self = et.vc;
		self.rowData = data;
		//이벤트를 해줄 대상
		var triggerElement = [];
		$(formId).find("input,select").each(function(index, elemt){
			var name = $(elemt).prop("name");
			if ($(elemt).prop("localName") === "select") {
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				} else {
					$(elemt).val(data[name]);
				}
				if ($(elemt).val() === null) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				}
				
			} else if ($(elemt).prop("type") === "checkbox") {
				if (data[name] === "Y") {
					$(formId+" input[name='"+name+"']").prop("checked",true);
				} else {
					$(formId+" input[name='"+name+"']").prop('checked', false);
				}
				triggerElement.push(elemt);
			} else {
				if (-1 < name.indexOf("user_ip")) {
					var ipNum = Number(name.replace("user_ip",""));
					$(elemt).val(data.user_ip?.split(".")[ipNum-1]);
				} else {
					$(elemt).val(data[name] === undefined ? "" :data[name]);
				}
			}
			triggerElement.push(elemt);
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
		editValidate.validateRules("group_cd",editValidate.REQUIRED,et.message("validate.required", {name:"고객사,센터,팀"}));
		editValidate.validateRules("user_id",editValidate.REQUIRED,et.message("validate.required", {name:"ID"}));
		editValidate.validateRules("user_name",editValidate.REQUIRED,et.message("validate.required", {name:"담당자명"}));
		editValidate.validateRules("phone",editValidate.NUMBERONLY,et.message("validate.only_num", {name:"연락처"}));
		editValidate.validateRules("email",editValidate.EMAIL,et.message("validate.email", {name:"이메일"}));
		editValidate.validateRules("user_ip1",editValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		editValidate.validateRules("user_ip2",editValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		editValidate.validateRules("user_ip3",editValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		editValidate.validateRules("user_ip4",editValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		editValidate.apply();
		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_cd",addValidate.REQUIRED,et.message("validate.required", {name:"고객사,센터,팀"}));
		addValidate.validateRules("user_id",addValidate.REQUIRED,et.message("validate.required", {name:"ID"}));
		addValidate.validateRules("user_pw",addValidate.REQUIRED,et.message("validate.required", {name:"비밀번호"}));
		addValidate.validateRules("user_name",addValidate.REQUIRED,et.message("validate.required", {name:"담당자명"}));
		addValidate.validateRules("phone",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"연락처"}));
		addValidate.validateRules("email",addValidate.EMAIL,et.message("validate.email", {name:"이메일"}));
		addValidate.validateRules("user_ip1",addValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		addValidate.validateRules("user_ip2",addValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		addValidate.validateRules("user_ip3",addValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		addValidate.validateRules("user_ip4",addValidate.REQUIRED,et.message("validate.required", {name:"접속 IP"}));
		addValidate.apply();
		
		var pwValidate = new ETValidate(self.pwFormId).setSubmitHandler(self.pwSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		pwValidate.validateRules("user_pw",pwValidate.REQUIRED,et.message("validate.required", {name:"비밀번호"}));
		pwValidate.apply();
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
		$("#btnPWChangeOpen").click(_.debounce(self.pwChangeOpenHandler,500));
		$("#btnPWChange").click(_.debounce(self.pwChangeHandler,500));
		
		et.setEnterKeyDownEvent("#ipxSearchName", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		$("#selGroupType").change(self.selUserTypeChangeHandler);
		$("#selNGroupType").change(self.selUserTypeChangeHandler);
		
		et.setNumberKeyPressEvent("#iptNPhone");
		et.setNumberKeyPressEvent("#iptPhone");
		et.setNumberKeyPressEvent(self.editFormId+" input[name='user_ip1']");
		et.setNumberKeyPressEvent(self.editFormId+" input[name='user_ip2']");
		et.setNumberKeyPressEvent(self.editFormId+" input[name='user_ip3']");
		et.setNumberKeyPressEvent(self.editFormId+" input[name='user_ip4']");
		et.setNumberKeyPressEvent(self.addFormId+" input[name='user_ip1']");
		et.setNumberKeyPressEvent(self.addFormId+" input[name='user_ip2']");
		et.setNumberKeyPressEvent(self.addFormId+" input[name='user_ip3']");
		et.setNumberKeyPressEvent(self.addFormId+" input[name='user_ip4']");
		
		$("#btnSearch").trigger("click");
		
		
		$("#selSearchGroupType").change(self.selSearchGroupTypeChangeHandler);
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selCenterList").change(self.selGroupListChangeHandler);
		$("#selTeamList").change(self.selGroupListChangeHandler);
		
		$("#selGroupCd").change(self.editGroupCdChangeHandler);
		$("#selCenterCd").change(self.editGroupCdChangeHandler);
		$("#selTeamCd").change(self.editGroupCdChangeHandler);
		$("#selNGroupCd").change(self.editGroupCdChangeHandler);
		$("#selNCenterCd").change(self.editGroupCdChangeHandler);
		$("#selNTeamCd").change(self.editGroupCdChangeHandler);
	}
	
	// ============================== 이벤트 리스너 ==============================
	
	/**
	 * 분류 검색 체인지 이벤트 리스너
	 */
	ctrl.selSearchGroupTypeChangeHandler = function () {
		var self = et.vc;
		var val = $(this).val();
		$("#selCenterList").empty();
		$("#selTeamList").empty();
		if (val === "") {
			$("#selCenterList").show();
			$("#selTeamList").show();
		} else if (val === "group") {
			$("#selCenterList").hide();
			$("#selTeamList").hide();
		} else if (val === "center") {
			$("#selCenterList").show();
			$("#selTeamList").hide();
		} else if (val === "team") {
			$("#selCenterList").show();
			$("#selTeamList").show();
		}
		$("#selGroupList").val("").trigger("change");
	};
	
	/**
	 * 검색 조건에서 고객사,센터,팀을 변경 했을때 발생하는 이벤트
	 */
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data().children;
		if ($(this).prop("id") === "selGroupList") {
			et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, "#selCenterList", "센터전체");
			et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, "#selTeamList", "팀전체");
		} else if ($(this).prop("id") === "selCenterList") {
			et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, "#selTeamList", "팀전체");
		} else if ($(this).prop("id") === "selTeamList") {
			
		}
	}
	
	/*
	 * 수정 추가 분류 변경했을때 발생하는 이벤트
	 * */ 
	ctrl.selUserTypeChangeHandler = function (){
		var self = et.vc;
		var val = $(this).val();
		var elemId = $(this).prop("id");
		var prefix = "";
		if (elemId !== "selGroupType") {
			prefix = "N";
		}
		
		et.makeSelectOption(self.groupList, {value:"group_cd", text:"view_group_name"}, "#sel"+prefix+"GroupCd","",self.rowData.group_cd);  // 고객사 선택
		
		if (val === "") {
			$("#sel"+prefix+"CenterCd").parent().parent().show();
			$("#sel"+prefix+"TeamCd").parent().parent().show();
		} else if (val === "group") {
			$("#sel"+prefix+"CenterCd").parent().parent().hide();
			$("#sel"+prefix+"TeamCd").parent().parent().hide();
		} else if (val === "center") {
			$("#sel"+prefix+"CenterCd").parent().parent().show();
			$("#sel"+prefix+"TeamCd").parent().parent().hide();
		} else if (val === "team") {
			$("#sel"+prefix+"CenterCd").parent().parent().show();
			$("#sel"+prefix+"TeamCd").parent().parent().show();
		}
		$("#sel"+prefix+"GroupCd").trigger("change");
	}
	
	/**
	 * 수정시 고객사,센터,팀을 변경 했을때 발생하는 이벤트
	 */
	ctrl.editGroupCdChangeHandler = function () {
		var self = et.vc;
		var selectedData = $(this).find("option:selected").data();
		if (selectedData !== undefined && selectedData.children !== undefined) {
			var dataList = selectedData.children;
			var prefix = "";
			if (-1 < $(this).prop("id").indexOf("selN")) {
				prefix = "N";
			}
			if ($(this).prop("id") === "sel"+prefix+"GroupCd") {
				et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, "#sel"+prefix+"CenterCd","", self.rowData.center_cd);
				et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, "#sel"+prefix+"TeamCd", "팀전체");
				$("#sel"+prefix+"CenterCd").trigger("change");
			} else if ($(this).prop("id") === "sel"+prefix+"CenterCd") {
				et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, "#sel"+prefix+"TeamCd", "",self.rowData.team_cd);
				$("#sel"+prefix+"TeamCd").trigger("change");
			} else if ($(this).prop("id") === "sel"+prefix+"TeamCd") {
				
			}
		}
		
	}
	
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.group_cd = $("#selGroupList").val();
			params.center_cd = $("#selCenterList").val();
			params.team_cd = $("#selTeamList").val();
			params.user_name = $("#ipxSearchName").val();
			params.user_type = $("#selSearchGroupType").val();
			self.createDatatable(params);
		}
	}
	
	/*
	 * 고객사 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		
		self.setViewData(self.addFormId,{user_type:"group"});
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
		params.user_id = self.rowData.user_id;
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delCustManager", params);
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
	
	/*
	 * 비밀 번호 변경시 띄울 팝업
	 */
	ctrl.pwChangeOpenHandler = function () {
		var self = et.vc;
		$("#iptCPassword").val("");
		$("#divPwChange").modal("show");
	}
	
	/*
	 * 비밀 번호 변경하기 버튼 클릭 핸들러
	 */
	ctrl.pwChangeHandler = function () {
		var self = et.vc;
		$(self.pwFormId).submit();
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
				data : "group_name",
			},{
				data : "center_name",
			},{
				data : "team_name",
			},{
				data : "user_id",
			},{
				data : "user_name",
			},{
				data : "phone",
				className : "txtCenter",
			},{
				data : "email",
			}
			];
		
		var table = et.createDataTableSettings(columns, self.path + "/getCustManagerList", params, self.dataTableDrawCallback);
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
    };
	
	// ============================== Form 리스너 ==============================
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.DBTYPE = "I";
		formData.user_ip = formData.user_ip1+"."+formData.user_ip2+"."+formData.user_ip3+"."+formData.user_ip4
		formData.user_pw = btoa(formData.user_pw);
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setCustManager", formData);
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var msg = "";
		if (formData.user_type === "center") {
			if (formData.center_cd === undefined || formData.center_cd === null || formData.center_cd === "") {
				msg = et.message("validate.required", {name:"고객사,센터,팀"});
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", msg);
				return;
			}
		} else if (formData.user_type === "team"){
			if (formData.center_cd === undefined || formData.center_cd === null || formData.center_cd === ""
				|| formData.team_cd === undefined || formData.team_cd === null || formData.team_cd === "") {
				msg = et.message("validate.required", {name:"고객사,센터,팀"});
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", msg);
				return;
			}
		}
		formData.user_id = self.rowData.user_id;
		formData.user_ip = formData.user_ip1+"."+formData.user_ip2+"."+formData.user_ip3+"."+formData.user_ip4
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setCustManager", formData);
	}
	
	/**
	 * 비밀번호 변경 핸들러
	 * 비밀번호 변경하기 눌렀을 경우 Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.pwSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.user_id = self.rowData.user_id;
		formData.user_pw = btoa(formData.user_pw);
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setPWCustManagerChange", formData);
	}
	
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.addSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#btnSearch").trigger("click");
			$("#divPwChange").modal("hide");
		} else {
			if (result.data === 0) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
			} else {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"ID"}));
			}
		}
	}
	
	
	return ctrl;
}));