/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : mk jang
 * @CreateDate : 2022. 10. 20.
 * @DESC : [환경설정] 시스템 로그 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "systemLog") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "systemLog";
	ctrl.path = "/settings/systemLog";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
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
		self.setView(initData);
		self.addEventListener();
	};
	
	ctrl.setView = function(initData) {
		var self = et.vc;
		
		et.initializeDatepicker(new Date(), '#divStartDate');
		et.initializeDatepicker(new Date(), '#divEndDate');
	}
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnExcelDownLoad").click(_.debounce(self.btnExcelDownHandler,500));
		
		et.setEnterKeyDownEvent("#ipxSearchId", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		$("#btnSearch").trigger("click");
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
			params.agent_id = $("#iptAgentId").val();
			params.sdate = $("#divStartDate input").val();
			params.edate = $("#divEndDate input").val();
			self.createDatatable(params);
		}
	}
	
	/*
	 * 엑셀 다운로드 핸들러
	 */
	ctrl.btnExcelDownHandler = function () {
		var self = et.vc;
		$.etExcelUtil.excelDownloadAjax($(self.tableId).DataTable(),null,"시스템_로그",false,false);
		
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
				data : "log_dttm",
				className : "txtCenter",
			},{
				data : "agent_id",
			},{
				data : "log_msg",
			},{
				data : "login_ip",
				className : "txtCenter",
			}
			];
		
		var table = et.createDataTableSettings(columns, self.path + "/getSystemLogList", params, self.dataTableDrawCallback);
		$(self.tableId).DataTable(table);
	};
	
	/**
	 * 테이블 생성후 화면에 표시할때 콜
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		if (0 < settings.json.recordsTotal) {
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
    };
	
	// ============================== Form 리스너 ==============================
	
	return ctrl;
}));