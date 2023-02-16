/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 04. 22.
 * @DESC : [상담원관리] 스킬그룹관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "skilGroupMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "skilGroupMgt";
	ctrl.path = "/agent/skilGroupMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.selectList = null;
	ctrl.groupLst = null;
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
		$("#btnSearch").trigger("click");
		
	};
	
	ctrl.setView = function(initData){
		var self = et.vc;
		$('.select2').select2({
        	maximumSelectionLength: 1
	    });
		var groupList =self.groupList;
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
				triggerElement.push(elemt);
			} else {
				var iptData = "";
				if(name === "noanswer_timeout"){
					iptData = data[name] ?? et.message("default.skill");
				}else{
					iptData = data[name] ?? "";
				}
				$(elemt).val(iptData);
				if (data[name] !== undefined && name === "tenant_name") {
					self.selectUSoundHandler($(this).data("soundlist"));
					
				}
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
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
	}
	
	ctrl.selNGroupIdChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selNGroupCenter");
		$("#selNGroupCenter").trigger("change");
	}
/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;

		ETValidate.addMethod("fourNumber",function(value, element, params) {
			return value.length === 4;
		});		
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("tenant_id",editValidate.REQUIRED,et.message("validate.required", {name:"고객"}));
		editValidate.validateRules("grp_id",editValidate.REQUIRED,et.message("validate.required", {name:"스킬 그룹"}));
		editValidate.validateRules("grp_id","fourNumber",et.message("validate.to_num", {name:"스킬 그룹", num:"4자리"}));
		editValidate.validateRules("name",editValidate.REQUIRED,et.message("validate.required", {name:"상담 그룹 명"}));
		editValidate.validateRules("noanswer_timeout",editValidate.REQUIRED,et.message("validate.required", {name:"타임아웃"}));
		editValidate.validateRules("noanswer_timeout",editValidate.NUMBERONLY,et.message("validate.only_num", {name:"타임아웃"}));
		editValidate.apply();

		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객"}));
		addValidate.validateRules("grp_id",addValidate.REQUIRED,et.message("validate.required", {name:"스킬 그룹"}));
		addValidate.validateRules("grp_id","fourNumber",et.message("validate.to_num", {name:"스킬 그룹", num:"4자리"}));
		addValidate.validateRules("name",addValidate.REQUIRED,et.message("validate.required", {name:"상담 그룹 명"}));
		addValidate.validateRules("noanswer_timeout",addValidate.REQUIRED,et.message("validate.required", {name:"타임아웃"}));
		addValidate.validateRules("noanswer_timeout",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"타임아웃"}));
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
		
		
		$("#selNTenantId").change(_.debounce(self.selectSoundHandler,500));
		$("#selNGroupCenter").change(_.debounce(self.selectSoundHandler,500));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		et.setEnterKeyDownEvent("#iptSkilGroupList,#iptSkilGroupNameList", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setNumberKeyPressEvent("#selNTenantId");
		et.setNumberKeyPressEvent("#iptNSkilGroup");
		et.setNumberKeyPressEvent("#iptTimeOut");
		et.setNumberKeyPressEvent("#iptNTimeOut");

		var param = {};
		new ETService().setSuccessFunction(self.selectSoundSuccessHandler).callService(self.path + "/getSoundList", param);
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
			params.tenant_id = $("#selGroupCenter").val();
			params.grp_id = $("#iptSkilGroupList").val();
			params.name = $("#iptSkilGroupNameList").val();
			
			self.createDatatable(params);
		}
	}
	/*
	 * 내선번호 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		self.setViewData(self.addFormId,{});
		$("#bxWrite").show();
		$("#selNTenantId").trigger("change");
		
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
		params.tenant_id = self.rowData.tenant_id;
		params.grp_id = self.rowData.grp_id;
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delSkillGroup", params);
		});
	}
	
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
	 * tenant_id 별 selectbox 생성
	 */
	ctrl.selectSoundHandler=function(){
		var self = et.vc;
		var param = {};
		var selectID = $(this).data("soundlist").split(",");
		if(self.selectList !== null){
			var tenantList = self.selectList[$("#selNGroup").val()];
			var soundList = tenantList !== undefined ? tenantList[ $("#selNGroupCenter").val()] : [];
			
			for(var i in selectID){
				et.makeSelectOption(soundList,{value:"file_name", text:"file_name"}, "#"+selectID[i]);  // 그룹선택
			}
		}

		
	}
	ctrl.selectUSoundHandler = function(list){
		var self = et.vc;
		var rowData = self.rowData;
		var id = rowData.tenant_id;
		var selectID = list.split(",");
		if(self.selectList !== null){
			var tenantList = self.selectList[rowData.group_id];
			var soundList = tenantList !== undefined ? tenantList[id] : [];
			for(var i in selectID){
				et.makeSelectOption(soundList,{value:"file_name", text:"file_name"}, "#"+selectID[i]);  // 그룹선택
			}
		}
		if(rowData !== undefined){
			$("#selEnterAnnounce").val(rowData.enter_announce)
	        $("#selReenterAnnounce").val(rowData.reenter_announce);
	        $("#selConnectAnnounce").val(rowData.connect_announce);
		}
	}
	
	/**
	 * 그룹 리스트 조회 successHandler
	 */
	ctrl.selectSoundSuccessHandler = function(result){
		var self = et.vc;
		if(result.message === ETCONST.SUCCESS){
			console.log(result.data);
			self.selectList = result.data;
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
				{data: "group_name"}
				,{data: "tenant_name"}
				,{data: "grp_id",className:"txtCenter"} 
				,{data: "name"} 
				,{data: "noanswer_timeout",className:"txtRight"} 
				,{data: "enter_announce"} 
				,{data: "reenter_announce"} 
				,{data: "connect_announce"} 
				,{data: "recording",className:"txtCenter",render: function(data,type,row,meta){
					return data === 0 ? "X" :"O"
				}} 
				,{data: "acd",className:"txtCenter"} 
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getSkillGroupList", params, self.dataTableDrawCallback);
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
        
        

    };	
	// ============================== Form 리스너 ==============================
	
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var params = formData;
		params.DBTYPE = "I";
		if(params.grp_id.substr(0,1) !== "3" ){
			et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "", et.message("validate.frontNumCheck", {name:"스킬 그룹", num :"3000"}), ["확인", "창닫기"], function(e) {
				new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setSkillGroup", params);
			});
			
		}else{
			new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setSkillGroup", params);
		}
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.tenant_id = self.rowData.tenant_id;
		formData.grp_id = self.rowData.grp_id;
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setSkillGroup", formData);
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
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"스킬 그룹"}));
			}
		}
	}	
	return ctrl;
}));