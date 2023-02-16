/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 04. 22.
 * @DESC : [국선관리]
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "trunkMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "trunkMgt";
	ctrl.path = "/trunk/trunkMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.selectList = null;
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
		self.setView(initData);
		self.addEventListener();
		
		
	};
	
	ctrl.setView = function(initData){
		var self = et.vc;
		self.groupList = initData.groupList;
		
		$('.select2').select2({
        	maximumSelectionLength: 1
	    });
	    
		et.makeSelectOption(initData.groupList, {value:"tenant_id", text:"view_group_name"}, "#selGroupList", "고객사전체");  // 팀선택
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selCenterList", "센터전체");  // 팀선택
		et.makeSelectOption(initData.groupList, {value:"group_id", text:"view_group_name"}, "#selNGroupId");  // 팀선택


		// 진입시 검색되는 btnSearchHandler ->  selectGroupSuccessHandler 에서 실행
		var param = {};
		new ETService().setSuccessFunction(self.selectGroupSuccessHandler).callService(self.path + "/getGroupList", param);
		 
	}
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

	// ============================== 동작 컨트롤 ==============================
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;

		ETValidate.addMethod("fourNumber",function(value, element, params) {
			return value.length === 4;
		});		
		
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("group_id",editValidate.REQUIRED,et.message("validate.required", {name:"고객사명"}));
		editValidate.validateRules("tenant_id",editValidate.REQUIRED,et.message("validate.required", {name:"센터명"}));
		editValidate.validateRules("trunk",editValidate.REQUIRED,et.message("validate.required", {name:"DNIS(착신) 번호"}));
		editValidate.validateRules("transfer_id",editValidate.REQUIRED,et.message("validate.required", {name:"라우팅 대상"}));
		editValidate.apply();

		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객사명"}));
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"센터명"}));
		addValidate.validateRules("trunk",addValidate.REQUIRED,et.message("validate.required", {name:"DNIS(착신) 번호"}));
		addValidate.validateRules("transfer_id",addValidate.REQUIRED,et.message("validate.required", {name:"라우팅 대상"}));
		addValidate.apply();
	};
		
	ctrl.addEventListener = function () {
		var self = et.vc;
		$("#btnSearch").click(self.btnSearchHandler);
		$("#btnAdd").click(self.btnAddHandler);
		$("#btnSave").click(self.btnSaveHandler);
		$("#btnSaveCancel").click(self.btnSaveCancelHandler);
		$("#btnEdit").click(self.btnEditHandler);
		$("#btnDelete").click(self.btnDeleteHandler);

		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selNGroupId").change(self.selNGroupIdChangeHandler);
		$("#selNCenterId").change(self.selectGroupHandler);
		
		et.setEnterKeyDownEvent("#iptTrunkList,#iptTransferIDList",self.btnSearchHandler,$("#btnSearch"));
		et.setNumberKeyPressEvent("#iptTrunk");
		et.setNumberKeyPressEvent("#iptNTrunk");
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);

		$("input:radio[name=transferSet]").click(self.transferRadioClickHandler);
		$("input:radio[name=transferSetN]").click(self.transferRadioClickHandler);
		

	}
	/**
	 *  라디오 버튼 클릭 이벤트 핸들러
	 */
	ctrl.transferRadioClickHandler = function(event){
		var self = et.vc;
		var targetId = event.currentTarget.id.substr(event.currentTarget.id.length-1,1);
		var tenant_id = targetId === "N" ? $("#selNCenterId").val() : self.rowData.tenant_id; 
		var selectId = targetId === "N" ? "#selNTransferID" : "#selTransferID";
		var list = [];
		if (self.selectList !== null) {
			var transferList = self.selectList[tenant_id];
			
			var name = $(event.currentTarget).val();
			
			list = transferList?.[name]??[];
		}
		et.makeSelectOption(list, {value:"transfer_id", text:"view_transfer_id"}, selectId );
	}
	/**
	 * 상세보기 라우팅 대상 라디오버튼 dataSet
	 */
	ctrl.setTransferRadio = function(){
		var self = et.vc;
		var rowData = self.rowData;
		if(self.selectList != null){
			var tlist = self.selectList[rowData.tenant_id]
			var id = "";
			if(rowData === null){
				id = ""
			}else{
				for(var i in tlist.extension){
					id = Number(tlist.extension[i].transfer_id) === rowData.transfer_id ? "iptExtension" : id;
				}
				for(var i in tlist.hunt){
					id = Number(tlist.hunt[i].transfer_id) === rowData.transfer_id ? "iptHunt" : id;
				}
				for(var i in tlist.skill){
					id = Number(tlist.skill[i].transfer_id) === rowData.transfer_id ? "iptSkill" : id;
				}
				
			}
			$("input:radio[id='"+id+"']").trigger("click");
			
			$("#selTransferID").val(rowData.transfer_id);
			$("#selTransferID").trigger("change")
		}
	}		
	// ============================== 이벤트 리스너 ==============================
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selCenterList", "센터전체");
		
	}
	
	ctrl.selNGroupIdChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selNCenterId");
		$("#selNCenterId").trigger("change");
		
	}
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = _.debounce(function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.group_id = $("#selGroupList").find("option:selected").data().group_id;
			params.tenant_id = $("#selCenterList").find("option:selected").data().tenant_id;
			params.trunk = $("#iptTrunkList").val();
			params.transfer_id = $("#iptTransferIDList").val();
			
			self.createDatatable(params);
		}
	},500)
	/*
	 * 내선번호 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = _.debounce(function () {
		var self = et.vc;
		self.setViewData(self.addFormId,{});
		$("#bxWrite").show();
        $("#selNGroupId").children("option:eq(0)").prop("selected", true);
		$("#selNGroupId").trigger('change');
        
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
		var params = {};
		params.trunk = $("#iptTrunk").val();
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delTrunkMgt", params);
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
	 * tenant_id 별 selectbox 생성
	 */ 
	ctrl.selectGroupHandler= _.debounce(function(){
		var self = et.vc;
		if(self.rowData.transfer_id !== undefined){
			self.setTransferRadio();
		}else{
			$("#iptExtensionN").trigger("click");	
		}
		
	},500)
	/**
	 * 그룹 리스트 조회 successHandler
	 */
	ctrl.selectGroupSuccessHandler = function(result){
		var self = et.vc;
		if(result.message === ETCONST.SUCCESS){
			self.selectList = result.data;
			$("#btnSearch").trigger("click");
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
				,{data: "center_name"}
				,{data: "tenant_id"} 
				,{data: "trunk",className:"txtCenter"} 
				,{data: "transfer_id",className:"txtCenter"} 
				,{data: "memo"} 
				,{data: "register",className:"txtCenter"} 
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getTrunkMgtList", params, self.dataTableDrawCallback);
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
        self.setViewData(self.editFormId,rowData);
        self.selectGroupHandler();
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
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setTrunkMgt", params);
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.trunk = $("#iptTrunk").val();
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setTrunkMgt", formData);
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
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"DNIS(착신) 번호"}));
			}
		}
	}	
	return ctrl;
}));