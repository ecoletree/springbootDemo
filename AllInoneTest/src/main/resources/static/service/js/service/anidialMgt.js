/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 04. 22.
 * @DESC : [상담원관리] 상담원관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "anidialMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "anidialMgt";
	ctrl.path = "/aniManager/anidialMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.pwFormId = "#pwForm";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.selectList = null;
	ctrl.skillData = null;
	ctrl.searchData = null;
	ctrl.excelHeaders = null;
	ctrl.dialerList =null;
	ctrl.rullBtnSetting = {
				is_write : "#btnAdd"
				, is_delete : "#btnDelete" 
				, is_update : "#btnEdit" 
				, is_search : "#btnSearch" 
				, is_upload : "#btnExcelUpLoad,#btnExcelTemplateDownload" 
				, is_download : "#btnExcelDownLoad" 
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
		
		$("#btnSearch").trigger("click");
		
	};
	
	ctrl.setView = function(){
		var self = et.vc;
		
	    var initData = self.initData;
		var groupList = initData.groupList;
		var broadList = initData.broadbandCode;
		var statusList = initData.statusCodeList;
		self.dialerList = initData.dialerList;
		
		
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");  
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selNGroup");
		
		et.makeSelectOption(broadList, {value:"broadband_code", text:"broadband_name"}, "#selBroadBand","지역 전체");
		et.makeSelectOption(broadList, {value:"broadband_code", text:"view_broadband_name"}, "#selUBroadBandCode");
		et.makeSelectOption(broadList, {value:"broadband_code", text:"view_broadband_name"}, "#selNBroadBandCode");
		
		et.makeSelectOption(statusList, {value:"code_cd", text:"code_name"}, "#selStatusCode","상태 전체");
		et.makeSelectOption(statusList, {value:"code_cd", text:"code_name"}, "#selUStatusCode");
		et.makeSelectOption(statusList, {value:"code_cd", text:"code_name"}, "#selNStatusCode");
		
		
//		et.makeSelectOption(dialerList, {value:"dialer_cd", text:"view_dialer_name"}, "#selUDialerCode");
//		et.makeSelectOption(dialerList, {value:"dialer_cd", text:"view_dialer_name"}, "#selNDialerCode");
		
		
		
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
			} else {
				
				$(elemt).val(data[name] === undefined ? "" :data[name]);
				if(name === "start_date" ||name === "end_date" ){
					var id = "#"+ $(elemt).parent().prop("id");
					var date = data[name] === undefined ? new Date() : data[name] ;
					et.initializeDatepicker(date, id);
				}
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
 
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("out_no",editValidate.REQUIRED,et.message("validate.required", {name:"발신번호"}));
		editValidate.validateRules("in_no",editValidate.REQUIRED,et.message("validate.required", {name:"착신번호"}));
		editValidate.validateRules("start_date",editValidate.REQUIRED,et.message("validate.required", {name:"사용 시작일"}));
		editValidate.validateRules("end_date",editValidate.REQUIRED,et.message("validate.required", {name:"사용 종료일"}));
		editValidate.apply();
		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("out_no",addValidate.REQUIRED,et.message("validate.required", {name:"발신번호"}));
		addValidate.validateRules("in_no",addValidate.REQUIRED,et.message("validate.required", {name:"착신번호"}));
		addValidate.validateRules("start_date",addValidate.REQUIRED,et.message("validate.required", {name:"사용 시작일"}))
		addValidate.validateRules("end_date",addValidate.REQUIRED,et.message("validate.required", {name:"사용 종료일"}))
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
		$("#btnSaveList").click(_.debounce(self.btnSaveListHandler,500)); // 엑셀 등록
		$("#btnExcelDownLoad").click(_.debounce(self.btnExcelDownLoadHandler,500)); 
		$("#btnExcelUpLoad").click(_.debounce(self.btnExcelUpLoadHandler,500));
		
		$("#selGroup").trigger("change");
		$("#selGroup").change(self.selGroupListChangeHandler);
		$("#selNGroup").change(self.selNGroupIdChangeHandler);
		
		// 센터변경시 다이얼코드 변경
		$("#selNGroupCenter").change(_.debounce(self.setDialCode,500));
		
		
		et.setEnterKeyDownEvent("#iptOutNo", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		et.setNumberKeyPressEvent("#iptOutNo");
		et.setNumberKeyPressEvent("#iptUOutNo");
		et.setNumberKeyPressEvent("#iptUInNo");
		et.setNumberKeyPressEvent("#iptNOutNo");
		et.setNumberKeyPressEvent("#iptNInNo");
		
	}
		
	// ============================== 이벤트 리스너 ==============================
	
	
	
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
		$("#selGroupCenter").trigger("change");
	}
	ctrl.selNGroupIdChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selNGroupCenter");
		$("#selNGroupCenter").trigger("change");
		var dialerList = self.dialerList[$(this).val()]?? [];
		et.makeSelectOption(dialerList, {value:"dialer_cd", text:"view_dialer_name"}, "#selNDialerCode");
	}
	ctrl.setDialCode = function(){
		var self = et.vc;
		var dataList = $(this).find("option:selected").data() ?? [];
		var center_code = dataList.dialer_center_code !== null ? dataList.dialer_center_code : "";
		$("#iptNdialerCenterCode").val(center_code);
		
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
			params.broadband_code = $("#selBroadBand").val();
			params.status_code = $("#selStatusCode").val();
			params.is_use = $("#selIsUse").val();
			params.out_no = $("#iptOutNo").val();
			self.searchData = params;
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
	ctrl.btnExcelUpLoadHandler = function () {
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
		var rowData = self.rowData;
		var delData = {};
		delData.animanger_cd = rowData.animanger_cd;
		var params = {};
		params.anidialList = [delData];
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delAnidialerList", params);
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
	
	/*
	 * 엑셀 다운로드 
	 */
	ctrl.btnExcelDownLoadHandler = function(){
		var self = et.vc;
		var param = self.searchData;
		new ETService().setSuccessFunction(self.excelDownloadSuccessHandler).callService(self.path+"/excelDownload", param);
		
	}
	/*
	 * 엑셀 헤더 생성
	 */
	ctrl.makeExcelHeaderList = function(){
		var self = et.vc;
		var headerSize = $("#tbList").DataTable().columns().header().length;
		var headers = [];
		var headerTexts = ["spon_cd" ,"센터" ,"cntr_cd","ad_cd","지역","lcl_no","out_no","in_no","from_dt","to_dt"];
		var headerColData = [];
		for(var i=0; i < headerSize; ++i) {//header 추가
			var col_data = $("#tbList").DataTable().columns().dataSrc()[i];
			if(col_data !== "is_use" &&col_data !== "status_code"&&col_data !== "view_update_dttm"){
				headerColData.push(col_data);
			}
		}
		for(var j=0; j < headerTexts.length; ++j){
			var obj = {};
			obj.headerText = headerTexts[j];
			obj.col_data = headerColData[j];
			headers.push(obj);
		}
		self.excelHeaders = headers;
//		return headers;
	}
	
	ctrl.excelDownloadSuccessHandler = function(result){
		var self = et.vc;
		var wb = XLSX.utils.book_new();
		
		var headers = self.excelHeaders;
		
		var dataSize = result.data.length;//row 추가
		
		var datas = [];
		for(var i=0; i < dataSize; ++i) {
			var data = result.data[i];
			var excelData = {};
			$.each(headers, function (index,item){
				excelData[item.headerText] = data[item.col_data];
			});
			datas.push(excelData);
		}
		
		var newWorksheet  = XLSX.utils.json_to_sheet(datas,{headers : headers});
		XLSX.utils.book_append_sheet(wb, newWorksheet, "anidialer_list");
		var now = new Date();
		var time = now.getFullYear()+now.getMonth()+now.getDate()+now.toTimeString().split(" ")[0].replaceAll(":","");
		var file_name = 'ANIDIALER_LIST_'+time+'.xlsx';
		XLSX.writeFile(wb, (file_name));
		
	}
	
	/*
	 * 엑셀 등록 핸들러
	 */
	ctrl.btnSaveListHandler = function(){
		var self = et.vc;
		
		var files = $("#uploadByDec").prop('files');
		if (!(_.isNil(files) || _.isEmpty(files))){
            var formData = new FormData(); 
            formData.append("anidial_file", files[0]);
            new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/excelUpload", formData, {contentType: false, processData: false});
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
				{data: "group_id",
					render: function ( data, type, row ) {
						return row.view_group_name;
				}}
				,{data: "center_id",
					render: function ( data, type, row ) {
						return row.view_center_name;
				}}
				,{data: "center_code"}// 다이얼러센터코드
				,{data: "dialer_cd"} 
				,{data: "broadband_name"}
				,{data: "broadband_code",className:"txtCenter"} 
				,{data: "out_no",className:"txtCenter"} 
				,{data: "in_no",className:"txtCenter"} 
				,{data: "status_code",className:"txtCenter",
					render: function ( data, type, row ) {
						var self = et.vc;
						var statusList = self.initData.statusCodeList;
						var status_name = "";
						for(var i in statusList){
							if(data === statusList[i].code_cd){
								status_name = 	statusList[i].code_name;
							}
						}
						return status_name;
				}} 
				,{data: "is_use",className:"txtCenter",
					render: function ( data, type, row ) {
						return data === "Y" ? "사용" : "사용안함";
				}} 
				,{data: "start_date",className:"txtCenter"} 
				,{data: "end_date",className:"txtCenter"} 
				,{data: "view_update_dttm",className:"txtCenter"} 
				
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getAnidialerList", params, self.dataTableDrawCallback);
		table.paging=false;
		
		$(self.tableId).DataTable(table);
	}
	/**
	 * 테이블 콜백
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		self.makeExcelHeaderList();
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
        var dialerList = self.dialerList;
		et.makeSelectOption(dialerList[rowData.group_id], {value:"dialer_cd", text:"view_dialer_name"}, "#selUDialerCode");
        self.setViewData(self.editFormId,rowData);
        

    }
	// ============================== Form 리스너 ==============================
	
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.group_name = $("#selNGroup").find("option:selected").data().group_name;
		formData.center_name = $("#selNGroupCenter").find("option:selected").data().group_name;
		formData.center_code = $("#iptNdialerCenterCode").val();
		formData.broadband_name = $("#selNBroadBandCode").find("option:selected").data().broadband_name;
		formData.start_date =  $("#iptNstartDate").val();
		formData.end_date =  $("#iptNendDate").val();

		var params = {};
		params.DBTYPE = "I";
		params.anidialList = [formData];
		
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAnidialerList", params);

		
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var rowData = self.rowData;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.animanger_cd = rowData.animanger_cd;
		formData.group_id = rowData.group_id;
		formData.center_id = rowData.center_id;
		formData.group_name = rowData.group_name;
		formData.center_name = rowData.center_name;
		formData.center_code = rowData.center_code;
		formData.broadband_name = $("#selUBroadBandCode option:selected").data().broadband_name;
		formData.start_date = $("#iptUstartDate").val();
		formData.end_date = $("#iptUendDate").val();
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAnidialerList", formData);
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
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"상담원 ID"}));
			} else {
				var msg = et.message("common.fail_csv");
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", msg);
			}
		}
	}
	/**
	 * 비밀번호 수정 완료시 발생하는 SucessHandler
	 */
	ctrl.pwSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		var rowData = self.rowData;
		formData.grp_id = rowData.grp_id;
		formData.agent_id = rowData.agent_id;
		formData.agent_pw = btoa(formData.agent_pw);
		formData.DBTYPE = "U";
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setPWAgentChange", formData);
	}
		
	return ctrl;
}));