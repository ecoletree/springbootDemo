/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 10. 27.
 * @DESC : [녹취관리] 녹취 전송 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "recordSendMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "recordSendMgt";
	ctrl.path = "/record/sendMgt";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.mediaPlayer = null;
	ctrl.filePath = "/usr/local/freeswitch/sounds";
	ctrl.tables= null;
	ctrl.fail_cnt = null;
	ctrl.rullBtnSetting = {
				is_search : "#btnSearch" 
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
		et.setEnterKeyDownEvent("#iptFtpIP", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		$("#selGroupList").trigger("change");
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selCenterList").change(self.selGroupListChangeHandler);
		
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
			var fail_cnt = $("input:checkbox[id='iptFailCntCheck']").is(":checked") ?'Y': 'N';
			
			params.group_id = $("#selGroupList option:selected").data("group_id"); 
			params.tenant_id = $("#selCenterList option:selected").data("tenant_id");
			params.ftp_ip = $("#iptFtpIP").val();
			params.fail_cnt = fail_cnt;
			self.createDatatable(params);
		}
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
			,{data : "ftp_ip"}				
			,{data : "ftp_port"}				
			,{data : "ftp_id"}				
			,{data : "ftp_pw"}				
			,{data : "ftp_dir"}				
			,{data : "faile_count"}				
			,{data : "record_send_id",className : "txtCenter"
				,render : function (data, type, full, meta) {
					var div ='<button type="button"class="btnWhite '+et.common.RULL_CLASS.IS_TRANSFER+'" disabled="disabled">수동전송</button>';
					if(full.faile_count > 0){
						div ='<button type="button" name="manual_send" class="btnWhite '+et.common.RULL_CLASS.IS_TRANSFER+'" >수동전송</button>';
					}
					return div;
				}}				
			];
		
		var option = et.createDataTableSettings(columns, self.path + "/getRecordSendList", params, self.dataTableDrawCallback);
		option.paging = false;
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
        if($target.is('[name="manual_send"]')){
        	var rowData = et.getRowData(self.tableId, $target.closest("tr"));
        	var message = rowData.faile_count+"건을 전송 하시겠습니까?";
        	et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "수동전송", message, ["확인", "창닫기"], function(e) {
    			new ETService().setSuccessFunction(self.sendRecordListSuccessHandler).callService(self.path + "/sendRecordList", rowData);
    		});
	
		}
    };
    
	ctrl.sendRecordListSuccessHandler = function(result){
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.manual_send_success"));
		} else{
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.manual_send_fail"));
		}
		$("#btnSearch").trigger("click");
	}
	// ============================== Form 리스너 ==============================
	
	return ctrl;
}));