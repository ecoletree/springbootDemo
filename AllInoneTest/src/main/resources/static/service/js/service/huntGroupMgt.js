/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 04. 22.
 * @DESC : [내선관리] 헌트그룹관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "huntGroupMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "huntGroupMgt";
	ctrl.path = "/pbx/huntGroupMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.groupList = null;
	ctrl.rowData=null;
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
		self.setView(initData);
		self.addEventListener();
		$("#btnSearch").trigger("click");
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
				var iptData = "";
				if(name === "noanswer_timeout"){
					iptData = data[name] ?? et.message("default.pbx");
				}else{
					iptData = data[name] ?? "";
				}
				$(elemt).val(iptData);
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
		
		ETValidate.addMethod("fourNumber",function(value, element, params) {
			return value.length === 4;
		});
		ETValidate.addMethod("prohibitedNum",function(value, element, params) {
			return value.substr(0,1) !== "9";
		});
		
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("tenant_id",editValidate.REQUIRED,et.message("validate.required", {name:"고객"}));
		editValidate.validateRules("tenant_id",editValidate.NUMBERONLY,et.message("validate.only_num", {name:"고객"}));
		editValidate.validateRules("grp_name",editValidate.REQUIRED,et.message("validate.required", {name:"헌트그룹명"}));
		editValidate.validateRules("noanswer_timeout",editValidate.REQUIRED,et.message("validate.required", {name:"타임아웃"}));
		editValidate.validateRules("noanswer_timeout",editValidate.NUMBERONLY,et.message("validate.required", {name:"타임아웃"}));
		editValidate.apply();
//		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객사"}));
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"센터"}));
		addValidate.validateRules("tenant_id",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"센터"}));
		addValidate.validateRules("grp_id",addValidate.REQUIRED,et.message("validate.required", {name:"헌트그룹ID"}));
		addValidate.validateRules("grp_id","prohibitedNum",et.message("validate.prohibit_num", {name:"헌트그룹ID",num:"9"}));
		addValidate.validateRules("grp_id",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"헌트그룹ID"}));
		addValidate.validateRules("grp_id","fourNumber",et.message("validate.to_num", {name:"헌트그룹ID", num:"4자리"}));
		addValidate.validateRules("grp_name",addValidate.REQUIRED,et.message("validate.required", {name:"헌트그룹명"}));
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
				
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);

		et.setNumberKeyPressEvent("#iptGroupId");
		et.setNumberKeyPressEvent("#iptNoanswer_timeout");
		et.setNumberKeyPressEvent("#iptNGroupId");
		et.setNumberKeyPressEvent("#iptNNoanswer_timeout");
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
			params.tenant_id = $("#selGroupCenter").val();
			params.group_id = $("#selGroup").val();
			self.createDatatable(params);
		}
	}
	/*
	 * 헌트그룹 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		self.setViewData(self.addFormId,{});
		$("#bxWrite").show();
		$("#selNGroup").trigger("change");
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
		params.grp_id = $("#iptGroupId").val();
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delHuntGroup", params);
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
	// ============================== DataTables 생성, 이벤트들 ==============================
	/**
	 * 테이블 조회
	 */
	ctrl.createDatatable = function(postData){
		var self = et.vc;
		var params = postData || {};
		var columns = [
				{data: "group_name"} 
				,{data:"tenant_name"}
				,{data: "grp_id",className:"txtCenter"} 
				,{data: "grp_name"} 
				,{data: "noanswer_timeout",className:"txtRight"} 
				,{data: "station_phone"} //소속내선 쿼리 다시짜기
				
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getHuntGroupList", params, self.dataTableDrawCallback);
		table.paging = false;
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
        
        $("#ulStationList").empty();
        
        var phone = rowData.station_phone;//소속내선 ul
		if(phone !== null && phone !== ""){
			var phoneList = phone.split(",");
	        self.setUlData(phoneList,"#ulStationList");
		}
		        
        
         
    };
    /**
     * ul 리스트 생성
     * @param {Object} list li 생성할 리스트
     * @param {String} id   ul 아이디 "#ul"
     */
	ctrl.setUlData = function(list,id){
		var self = et.vc;
		if(list.length > 0 ){
			list.forEach(elem =>{
				var li = $('<li>', {
					text : elem
				});
		        $(id.charAt(0) !== "#"? "#"+id : id).append(li);
			});
		}else{
			return false;
		}
	}
	// ============================== Form 리스너 ==============================
	
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var params = formData;
		params.DBTYPE = "I";
		if(params.grp_id.substr(0,1) !== "2" ){
			et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "", et.message("validate.frontNumCheck", {name:"헌트 그룹 ID", num :"2000"}), ["확인", "창닫기"], function(e) {
				new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setHuntGroup", params);
			});
			
		}else{
			new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setHuntGroup", params);
		}
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.tenant_id = self.rowData.tenant_id;
		formData.grp_id = $("#iptGroupId").val();
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setHuntGroup", formData);
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
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"헌트 그룹 ID"}));
			}
		}
	}
	
	return ctrl;
}));