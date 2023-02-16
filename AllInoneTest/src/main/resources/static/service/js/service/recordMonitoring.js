/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 10. 27.
 * @DESC : [녹취관리] 통화 모니터링
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "recordMonitoring") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "recordMonitoring";
	ctrl.path = "/record/monitoring";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.mediaPlayer = null;
	ctrl.filePath = "/usr/local/freeswitch/sounds";
	ctrl.tables= null;
	ctrl.rullBtnSetting = {
				is_search : "#btnSearch" 
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
		self.setView(initData);
		self.addEventListener();
		$("#btnSearch").trigger("click");
	};
	
	ctrl.setView = function(initData) {
		var self = et.vc;
		var initData = self.initData;
		et.makeSelectOption(initData.groupList, {value:"group_cd", text:"view_group_name"}, "#selGroupList", "고객사 전체");  // 고객사 선택
	}
	
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnExcel").click(_.debounce(self.btnExcelDownHandler,500));
		$("#btnPlayerClose").click(_.debounce(function(){
			self.mediaPlayer.pause();
			$(".popPlayer").hide(); 
		}));
		
//		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		$("#selGroupList").trigger("change");
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selCenterList").change(self.selGroupListChangeHandler);
		$("#selTeamList").change(self.selGroupListChangeHandler);
		
//		$("#btnSearch").trigger("click");
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
			params.group_id = $("#selGroupList option:selected").data("group_id"); 
			params.tenant_id = $("#selCenterList option:selected").data("tenant_id");
			params.team_id = $("#selTeamList option:selected").data("team_id");
			
			self.createDatatable(params);
		}
	}
	
	/*
	 * 엑셀 다운로드 핸들러
	 */
	ctrl.btnExcelDownHandler = function () {
		var self = et.vc;
		var columns = [
				{headerText : "고객사", data : "view_group_name"}
				,{headerText : "센터",data : "view_center_name"}				
				,{headerText : "상담원",data : "view_agent_name"}				
				,{headerText : "In/Out",data : "calltype"}				
				,{headerText : "내선번호",data : "phone"}				
				,{headerText : "전화번호",data : "other"}				
				,{headerText : "통화 시간",data : "now_call"}				
				,{headerText : "오늘 통화 시간",data : "today_call"}				
				,{headerText : "어제통화 시간",data : "yesterday_call"}	
			]
		
		$.etExcelUtil.excelDownloadAjax($(self.tableId).DataTable(),columns,"녹취_통화모니터링",false,false);
	}
	
	/**
	 * 검색 조건에서 고객사,센터,팀을 변경 했을때 발생하는 이벤트
	 */
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data().children;
		if ($(this).prop("id") === "selGroupList") {
			et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, "#selCenterList", "센터 전체");
			et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, "#selTeamList", "팀 전체");
		} else if ($(this).prop("id") === "selCenterList") {
			et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, "#selTeamList", "팀 전체");
		} else if ($(this).prop("id") === "selTeamList") {
			
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
			{data : "view_group_name"}
			,{data : "view_center_name"}				
			,{data : "view_agent_name"}				
			,{data : "pstate",className : "txtCenter"
				,render : function (data, type, full, meta) {
					var div ='';
					if(data !== null){
						if(data === "answer"){
							div = '<img src="../resources/ecoletree/img/icon_call.png" alt="통화중">';
						}
					}
					return div;
				}}				
			,{data : "calltype",className : "txtCenter"
				,render : function (data, type, full, meta) {
					var type ="";
					if(data === "I"){
						type = "In";
					}else if(data === "O"){
						type = "Out";
					}
					return type;
				}}				
			,{data : "phone",className : "txtCenter"}				
			,{data : "other",className : "txtCenter"}				
			,{data : "now_call",className : "txtCenter"
				,render : function (data, type, full, meta) {
					return data;
				}}				
			,{data : "today_call",className : "txtCenter"}				
			,{data : "yesterday_call",className : "txtCenter"}				
//			,{data : "pstate",className : "txtCenter"
//				,render : function (data, type, full, meta) {
//					var div ='';
//					if(data !== null){
//						div = '<button type="button" class="btnWhite btnListen '+et.common.RULL_CLASS.IS_PLAY+'" title="듣기" disabled="disabled"></button>';
//					}
//					return div;
//				}}				
			];
		
		var option = et.createDataTableSettings(columns, self.path + "/getRecordMgtList", params, self.dataTableDrawCallback);
		var table =  $(self.tableId).DataTable(option);
		self.tables = table;
	};
	
	/**
	 * 테이블 생성후 화면에 표시할때 콜
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		et.common.btnRuleSetting();
		if (0 < settings.json.recordsTotal) {
    		$('.contRightNoData').hide();
    		$("#tbList_paginate").show();
    		var dataLength = self.tables.data().length;
			var dataArr = self.tables.data();
			if (0< dataLength) {
				for (var i = 0; i < dataLength; i++) {
					var status = dataArr[i].status;
					if (status !== "LoggedOut") { 
						$("#tbList tr:eq("+(i+1)+")").addClass("login");
					}
				}
			}
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
    };
    
	
	// ============================== Form 리스너 ==============================
	
	return ctrl;
}));