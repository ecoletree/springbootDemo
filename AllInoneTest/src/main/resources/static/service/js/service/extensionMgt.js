/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 04. 22.
 * @DESC : [내선관리] 내선관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "extensionMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "extensionMgt";
	ctrl.path = "/pbx/extensionMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.hunuGroupList = null;
	ctrl.groupList = null;
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
	
	ctrl.setView = function(){
		var self = et.vc;
		$("#selNHuntGroup,#selHuntGroup").select2({
        	maximumSelectionLength: 1
	    });
	    var groupList = self.groupList;
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");  
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selNGroup");
		//헌트그룹 리스트

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
				if (data[name] !== undefined && name === "tenant_name") {
					self.selectUGroupHandler($(this).data("huntgroup"));
					
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
			return value.length === 5;
		});		
		
		ETValidate.addMethod("ipPortValidate",function(value, element, params) {
		    var ip = value.split(".");
		    return ip.length == 4 && ip.every(function (segment) {
		        return self.validateNum(segment, 0, 255);
		    });
		});
		
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("tenant_id",editValidate.REQUIRED,et.message("validate.required", {name:"센터"}));
		editValidate.validateRules("phone",editValidate.REQUIRED,et.message("validate.required", {name:"내선번호"}));
		editValidate.validateRules("phone",editValidate.NUMBERONLY,et.message("validate.only_num", {name:"내선번호"}));
		editValidate.validateRules("phone","fourNumber",et.message("validate.to_num", {name:"내선번호", num:"5자리"}));
		editValidate.validateRules("password",editValidate.REQUIRED,et.message("validate.required", {name:"전화기 인증암호"}));
		editValidate.validateRules("gateway",editValidate.REQUIRED,et.message("validate.required", {name:"발신 Gateway"}));
		editValidate.validateRules("station_ip",editValidate.REQUIRED,et.message("validate.required", {name:"내선 IP"}));
		editValidate.validateRules("station_ip","ipPortValidate",et.message("validate.ipaddress", {name:"내선 IP"}));
		editValidate.apply();
////		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객사"}));
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"센터"}));
		addValidate.validateRules("phone",addValidate.REQUIRED,et.message("validate.required", {name:"내선번호"}));
		addValidate.validateRules("phone",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"내선번호"}));
		addValidate.validateRules("phone","fourNumber",et.message("validate.to_num", {name:"내선번호", num:"5자리"}));
		addValidate.validateRules("password",addValidate.REQUIRED,et.message("validate.required", {name:"전화기 인증암호"}));
		addValidate.validateRules("gateway",addValidate.REQUIRED,et.message("validate.required", {name:"발신 Gateway"}));
		addValidate.validateRules("station_ip",addValidate.REQUIRED,et.message("validate.required", {name:"내선 IP"}));
		addValidate.validateRules("station_ip","ipPortValidate",et.message("validate.ipaddress", {name:"내선 IP"}));
		addValidate.validateRules("file",addValidate.FILE_EXTENSION,et.message("validate.extension", {name:"csv"}), "csv"); // csv file
		addValidate.apply();
	};
	
	/**
	 * ip:port validation
	 */
	ctrl.validateNum = function (input, min, max) {
		var num = +input;
	    return num >= min && num <= max && input === num.toString();
	}
		
	ctrl.addEventListener = function () {
		var self = et.vc;
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnAdd").click(_.debounce(self.btnAddHandler,500));
		$("#btnAdd_list").click(_.debounce(self.btnAdd_listHandler,500));
		$("#btnSave").click(_.debounce(self.btnSaveHandler,500));
		$("#btnSaveCancel").click(_.debounce(self.btnSaveCancelHandler,500));
		$("#btnEdit").click(_.debounce(self.btnEditHandler,500));
		$("#btnDelete").click(_.debounce(self.btnDeleteHandler,500));
		$("#btnSaveList").click(_.debounce(self.btnSaveListHandler,500));
		$("#btnDownLoadTemplate").click(_.debounce(self.btnDownLoadTemplateHandler,500));
		
		// 고객변경시 그룹 셀렉트 박스 변경
		$("#selGroup").trigger("change");
		$("#selGroup").change(self.selGroupListChangeHandler);
		$("#selNGroup").change(self.selNGroupIdChangeHandler);
		$("#selGroupCenter").change(self.selectGroupHandler);
		$("#selNGroupCenter").change(self.selectGroupHandler);
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		et.setEnterKeyDownEvent("#ipxSearchNum,#ipxSearchDisplayNumGW,#ipxSearchDisplayNum", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setNumberKeyPressEvent("#ipxSearchNum");
		et.setNumberKeyPressEvent("#ipxSearchDisplayNum");
		et.setNumberKeyPressEvent("#selNTenantId");
		et.setNumberKeyPressEvent("#iptNPhone");
		et.setNumberKeyPressEvent("#iptDisplayNum");
		et.setNumberKeyPressEvent("#iptNDisplayNum");

	}
		
	// ============================== 이벤트 리스너 ==============================
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
		$("#selGroupCenter").trigger("change");
	}
	
	ctrl.selNGroupIdChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selNGroupCenter");
		$("#selNGroupCenter").trigger('change');
	}
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.group_id =  $("#selGroup").val();
			params.tenant_id = $("#selGroupCenter").val();
			params.phone = $("#ipxSearchNum").val();
			params.gateway = $("#ipxSearchDisplayNumGW").val();
			params.display_num = $("#ipxSearchDisplayNum").val();
			params.station_grp = $("#selHuntGroupList").val();
			
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
		
	}
	/*
	 * 내선번호일괄 등록 버튼 클릭 핸들러
	 */
	ctrl.btnAdd_listHandler = function () {
		var self = et.vc;
		$("#fileDel").trigger("click");
		$("#modalAddList").modal();
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
		params.phone = self.rowData.phone;
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delExtensionMgt", params);
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
	
	/*
	 * 내선번호 csv 등록 핸들러
	 */
	ctrl.btnSaveListHandler = function(){
		var self = et.vc;
		
		var csv = $("#uploadByDec")[0];
		var json = null;
		var reader = new FileReader();
		
	    reader.onload = function(){
	        var fileData = reader.result;
	        var wb = XLSX.read(fileData, {type : 'binary'});
	        var json = null;
	        wb.SheetNames.forEach(function(sheetName){
		        json =XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);
	        });
	        var headers = [
			{headerText:"고객사 ID", col_name:"group_id"}
			,{headerText:"센터 ID", col_name:"tenant_id"}
			,{headerText:"내선 번호", col_name:"phone"}
			,{headerText:"전화기 인증암호", col_name:"password"}
			,{headerText:"발신 Gateway", col_name:"gateway"}
			,{headerText:"발신 번호", col_name:"display_num"}
			,{headerText:"헌트 그룹 ID", col_name:"grp_id"}
			,{headerText:"내선 IP", col_name:"station_ip"}
			];
			//header change
			for(var i in headers){
				json = JSON.parse(JSON.stringify(json).split(headers[i].headerText).join(headers[i].col_name));				
			}
	        var params = {};
			    params.stationList = json;
			    params.DBTYPE = "I";
			    if(params.stationList.length > 0){
					new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setExtensionMgt", params);
				}else{
					et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.fail_csv_file"));
				}
	        
	    };//onload function
	    reader.readAsText(csv.files[0]);
	    
	}
	/**
	 * 템플릿 내려받기
	 */
	 ctrl.btnDownLoadTemplateHandler = function(){
		var self = et.vc;
		
	}
	/**
	 * tenant_id 별 selectbox 생성
	 */ 
	ctrl.selectGroupHandler=function(){
		var self = et.vc;
		var tenantId = $(this).val() === "" ? "all" : $(this).val()+"";
		var huntGroup =  $(this).data("huntgroup");
		if (self.hunuGroupList !== null) {
			if (huntGroup === "selHuntGroupList") {
				et.makeSelectOption(self.hunuGroupList[tenantId], {value:"grp_id", text:"grp_name"}, "#"+huntGroup,"헌트 그룹 전체");  // 그룹선택
			} else {
				et.makeSelectOption(self.hunuGroupList[tenantId], {value:"grp_id", text:"grp_name"}, "#"+huntGroup);  // 그룹선택
			}
		}
		
	}
	ctrl.selectUGroupHandler = function(huntgroup){
		var self = et.vc;
		var rowData = self.rowData;
		if (self.hunuGroupList !== null) {
			et.makeSelectOption(self.hunuGroupList[rowData.tenant_id], {value:"grp_id", text:"view_grp_name"}, "#"+huntgroup);  // 그룹선택
			if (rowData !== null && rowData.grp_id !== undefined) {
				$("#"+huntGroup).val(rowData.grp_id);
				$("#"+huntGroup).trigger("change");
			}
		}
		
	}
	/**
	 * 그룹 리스트 조회 successHandler
	 */
	ctrl.selectGroupSuccessHandler = function(result){
		var self = et.vc;
		if(result.message === ETCONST.SUCCESS){
			self.hunuGroupList = result.data;
			$("#selGroupCenter").trigger("change");
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
			
				{data: "group_name"} //name으로 바꾸기
				,{data: "tenant_name"} //name으로 바꾸기
				,{data: "phone",className:"txtCenter"} 
				,{data: "password"} 
				,{data: "gateway",className:"txtCenter"} 
				,{data: "display_num",className:"txtCenter"} 
				,{data: "station_grp",className:"txtCenter"} 
				,{data: "grp_name"} //헌트그룹명
				,{data: "pstate",className:"txtCenter"} 
				,{data: "pstate_tm",className:"txtCenter"} 
				,{data: "station_ip",className:"txtCenter"} 
				
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getExtensionMgtList", params, self.dataTableDrawCallback);
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

    };	
	// ============================== Form 리스너 ==============================
	
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var params = {};
		params.DBTYPE = "I";
		formData.grp_id = formData.grp_id ?? null; 
		params.stationList = [formData];
		if(params.stationList[0].phone.substr(0,1) !== "5" ){
			et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "", et.message("validate.frontNumCheck", {name:"내선번호", num :"50000"}), ["확인", "창닫기"], function(e) {
				new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setExtensionMgt", params);
			});
			
		}else{
			new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setExtensionMgt", params);
		}
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.tenant_id = self.rowData.tenant_id;
		formData.phone = $("#iptPhone").val();
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setExtensionMgt", formData);
		
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
			if (result.data === "common.duplicate") {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"내선 번호"}));
			} else {
				var msg = et.message("common.fail_csv");
				if (result.data === "common.fail_csv_file") {
					msg += et.message(result.data);
				} else {
					var arr = result.data.split(";");
					$.each(arr,function(index,item){
						msg += et.message(item);
					})
				}
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", msg);
			}
			
		}
	}
	
	return ctrl;
}));