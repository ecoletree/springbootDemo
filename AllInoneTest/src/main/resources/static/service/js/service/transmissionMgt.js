/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 04. 22.
 * @DESC : [애니 매니져] 전송관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "transmissionMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "transmissionMgt";
	ctrl.path = "/aniManager/sendMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.pwFormId = "#pwForm";
	ctrl.tableId = "#tbList";
	ctrl.detailTableId = "#tbListDetail";
	ctrl.rowData = null;
	ctrl.selectList = null;
	ctrl.detailData = null;
	ctrl.status_cd=null;
	ctrl.rullBtnSetting = {
				is_search : "#btnSearch"
				, is_transfer : "#btnSendList,#btnSendDetailList" 
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
		self.status_cd = initData.statusCodeList;
		self.addEventListener();
		self.setView();
		$("#btnSearch").trigger("click");
	};
	
	ctrl.setView = function(){
		var self = et.vc;
	    var initData = self.initData;
		var groupList = initData.groupList;
		var broadList = initData.broadbandCode;
		
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");  
		et.makeSelectOption(broadList, {value:"broadband_code", text:"broadband_name"}, "#selBroadBand","지역 전체");
		
		$("#iptSetAutopreview").prop("checked", true);
		$("#iptSetAutopreview").trigger("change");
		
		
	}

	// ============================== 동작 컨트롤 ==============================
	ctrl.addEventListener = function () {
		var self = et.vc;
		$("#btnSearch").click(self.btnSearchHandler);
		$("#btnSendList").click(self.btnSendListHandler); // 전송하기
		$("#btnSendDetailList").click(self.btnSendListHandler); // 전송하기
		$("#btnCSVtrasnsmission").click(self.btnCSVtrasnsmissionHandler); // 생성및전송하기
		$("#btnClose").click(self.btnCloseHandler); // 전송결과창닫기
		
		
		$("#iptSetAutopreview").change(self.iptCheckBoxHandler);
		$("#iptCustomCheck").change(self.iptCheckBoxHandler);
		
		$(document).on("change","input[name=center_id]",self.iptCenterIdCheckHandler);
	
		$("#selGroup").trigger("change");
		$("#selGroup").change(self.selGroupListChangeHandler);
		
		// 고객변경시 그룹 셀렉트 박스 변경
		$("#selGroupCenter").change(self.selectTeamHandler);
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		

	}
		
	// ============================== 이벤트 리스너 ==============================
	/**
	 * 자동 미리 보기 체크
	 */
	ctrl.iptCheckBoxHandler = function(){
		var self = et.vc;
		var chk = false;
		if($(this).is(':checked')) {
			chk = true;
		}
		$($(this).data("btnname")).prop("checked", chk);	
		$($(this).data("btnname")).trigger('change');	
	}
	/**
	 * 개별 미리보기 체크 
	 */
	ctrl.iptCenterIdCheckHandler = function(){
		var self = et.vc;
		
		$("#divTransmissionMgtDetail").hide();
		var paramArr = self.setDetailList() ??[];
		var checkedcnt = $(self.tableId+" tbody input:checked").length;
		var allcnt = $(self.tableId+" tbody input").length;
		if(allcnt != checkedcnt){
			$("#iptCustomCheck").prop("checked", false);
		}
		if(paramArr.length > 0){
			
			$("#divTransmissionMgtDetail").show();	
			self.createDetailDataTable(paramArr);
			$("#spDetailCnt").html(paramArr.length);
		}
		
	}
	/**
	 * 모달닫기 
	 */
	ctrl.btnCloseHandler = function(){
		var self = et.vc;
		var divResult = document.getElementById("divSendResult"); 	
        divResult.style.display = 'none';
	}
	/**
	 * 리스트 
	 */
	ctrl.btnSendListHandler = function(){
		var self = et.vc;
		var checkedcnt = $(self.tableId+" tbody input:checked").length;
		var paramArr =[];
		if(checkedcnt > 0){
			$("#divSendModal").modal("show");
			$(self.tableId+" tbody input:checked ").each(function(index){
				var chkData = et.getRowData(self.tableId,$(this).parents("tr"));
				paramArr.push(chkData);						
			});
			self.createSendDataTableModal(paramArr);
 		}else{
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.data_select"));
		}
	}
	/**
	 * 전송 
	 */
	ctrl.btnCSVtrasnsmissionHandler = function(){
		var self = et.vc;
		var params = {};
		params.sendData = self.detailData;
		new ETService().setSuccessFunction(self.btnSendListSuccessHandler).callService(self.path+"/sendAnidialerList", params);
		
	}
	/**
	 * 전송 success handler 
	 */
	ctrl.btnSendListSuccessHandler = function(result){
		var self = et.vc;
		var divResult = document.getElementById("divSendResult"); 	
		
		if (result.message === ETCONST.SUCCESS) {
			$("#divSendModal").modal("hide"); 	
			var resultData = result.data.resultList;
			if(resultData.length>0){
		        divResult.style.display = 'block';
		        self.createResultDataTableModal(resultData); 	
			}else{
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.transmission_fail"));
			}
			
			
		}else{
	        divResult.style.display = 'none'; 	
			
		}
		
	}
	
	ctrl.setDetailList= function(table){
		var self = et.vc;
		var paramArr = [];
		var chkData = null;
		var checkedcnt = $(self.tableId+" tbody input:checked").length;
		if(checkedcnt > 0){
			$(self.tableId+" tbody input:checked ").each(function(index){
				
				chkData = et.getRowData(self.tableId,$(this).parents("tr"));
				var broadband = chkData.broadband_codes;
				
				for(var i in broadband){
					var zip = broadband[i].zipData;
					for(var j in zip){
						paramArr.push(zip[j]);						
					}
				}
			});
 		}
		
 		self.detailData = paramArr;
		return paramArr;
	}
	
	
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
		$("#selGroupCenter").trigger("change");
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
			
			self.createDatatable(params);
			
		}
		
	}
	
	
	// ============================== DataTables 생성, 이벤트들 ==============================
	/**
	 * 테이블 조회 - 센터별 지역수 리스트
	 */
	ctrl.createDatatable = function(postData){
		var self = et.vc;
		var params = postData || {};
		var columns = [
				{data: "center_id",render: function ( data, type, row ) {
						return '<label class="ecoleCheck"><input type="checkbox" name="center_id"><i></i></label>';
				}} 
				,{data: "view_group_name"} 
				,{data: "view_center_name"} 
				,{data: "center_cnt",className:"txtCenter"} 
				
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getSendMgtList", params, self.dataTableDrawCallback,"",false);
		table.paging=false;
		
		$(self.tableId).DataTable(table);
	}
	/**
	 * 테이블 콜백
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		if (0 < settings.json.recordsTotal) {
    		$('.contRightNoData').hide();
    		$("#tbList_paginate").show();
    		var allCnt = settings.json.allCnt;
    		$("#spAllCnt").html(allCnt);
    		$("#iptCustomCheck").prop("checked", false);
    		if($("#iptSetAutopreview").is(':checked')){
				$("#iptCustomCheck").prop("checked", true);
			}
    		$("#iptCustomCheck").trigger("change");
    	} else {
    		$('.contRightNoData').show();
    		$("#tbList_paginate").hide();
    	}
	}

	ctrl.createDetailDataTable = function(data){
		var self = et.vc;
		var columns = [
				{data: "register_status",className:"txtCenter"
					,render: function ( data, type, row ) {
						var status = data > 0 ? "등록" : "미등록";
						return status;
				}} 
				,{data: "view_group_name"} 
				,{data: "view_center_name"} 
				,{data: "center_code"} 
				,{data: "view_dialer_name"} 
				,{data: "broadband_name"} 
				,{data: "broadband_code",className:"txtCenter"} 
				,{data: "zip_code",className:"txtCenter"} 
				,{data: "out_no",className:"txtCenter"} 
				,{data: "in_no",className:"txtCenter"} 
				,{data: "status_code",className:"txtCenter",
					render: function ( data, type, row ) {
						var self = et.vc;
						var statusList = self.status_cd;
						var status_name = "";
						for(var i in statusList){
							if(data === statusList[i].code_cd){
								status_name = 	statusList[i].code_name;
							}
						}
						return status_name;
				}} 
				,{data: "view_start_date",className:"txtCenter"} 
				,{data: "view_end_date",className:"txtCenter"} 
				,{data: "view_update_dttm",className:"txtCenter"} 
				
			];
			
		var table = et.createDataTableSettings(columns, null, {}, self.dataDetailTableDrawCallback,true,true,data);
		
		$(self.detailTableId).DataTable(table);
	}
	/**
	 * 테이블 콜백
	 */
	ctrl.dataDetailTableDrawCallback = function (settings) {
		var self = et.vc;
	}
	/**
	 * 테이블 조회
	 */
	ctrl.createSendDataTableModal = function(data){
		var self = et.vc;
		var columns = [
				{data: "view_group_name"} 
				,{data: "view_center_name"} 
				,{data: "center_cnt",className:"text-center"} 
				
			];
			
		var table = et.createDataTableSettings(columns, null, {}, self.dataModalTableDrawCallback,"",false,data);
		table.paging=false;
		
		$("#tbSendTableList").DataTable(table);
	}
	/**
	 * 테이블 콜백
	 */
	ctrl.dataModalTableDrawCallback = function (settings) {
		var self = et.vc;
		
	}
	/**
	 * 테이블 조회
	 */
	ctrl.createResultDataTableModal = function(data){
		var self = et.vc;
		var columns = [
				{data: "fileTitle"} 
				,{data: "insert_msg",className:"txtCenter",
					render: function ( data, type, row ) {
						var status = data === "success" ? "성공" : "실패";
						return status;
				}} 
				
			];
			
		var table = et.createDataTableSettings(columns, null, {}, self.dataResultTableDrawCallback);
		table.paging=false;
		table.data = data;
		
		$("#tbList_result").DataTable(table);
	}
	/**
	 * 테이블 콜백
	 */
	ctrl.dataResultTableDrawCallback = function (settings) {
		var self = et.vc;
		
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
//        var rowData = et.getRowData(self.tableId, $target.closest("tr"));
//        self.rowData = rowData;
        


    }
	// ============================== Form 리스너 ==============================
	
		
	return ctrl;
}));