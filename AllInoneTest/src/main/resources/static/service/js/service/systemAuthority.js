/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : mk jang
 * @CreateDate : 2022. 10. 20.
 * @DESC : [환경설정] 시스템 관리자 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "systemauth") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "systemauth";
	ctrl.path = "/settings/systemauth";
	ctrl.editFormId = "#editForm";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.rullBtnSetting = {
				is_update : "#btnEdit" 
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
		self.createDatatable(initData.userTypeList);
	};
	
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.apply();
	};
	
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnEdit").click(_.debounce(self.btnEditHandler,500));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
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
			params.id = $("#ipxSearchId").val();
			self.createDatatable(params);
		}
	}
	
	/*
	 * 수정 버튼 클릭 핸들러
	 */
	ctrl.btnEditHandler = function () {
		var self = et.vc;
		$(self.editFormId).submit();
	}
	
	// ============================== DataTables 생성, 이벤트들 ==============================
	/*
	 * 데이터 테이블즈 생성
	 */
	ctrl.createDatatable = function(dataSet) {
		var self = et.vc;
		var columns = [
			{
				data : "user_type",
				render : function (data) {
					var str = "";
					if (data === "system"){
						str = "시스템 관리자";
					} else if (data === "group") {
						str = "고객사 관리자";
					} else if (data === "center") {
						str = "센터 관리자";
					} else {
						str = "팀 관리자";
					}
					return str;
				}
			},{
				data : "create_user",
			},{
				data : "view_create_dttm",
				className : "txtCenter",
			}
			];
		
		var table = et.createDataTableSettings(columns, null, {}, self.dataTableDrawCallback,"",false,dataSet);
		table.paging = false;
		$(self.tableId).DataTable(table);
	};
	
	/**
	 * 테이블 생성후 화면에 표시할때 콜
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		if (0 < settings.aoData.length) {
			$(self.tableId + ' tbody tr:nth-child(1) td:nth-child(1)').trigger('click');
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
        new ETService().setSuccessFunction(self.getAuthListSuccessHandler).callService(self.path + "/getAuthList", rowData);
    };
    
    /**
     * 권한 리스트를 가져온다
     */
    ctrl.getAuthListSuccessHandler = function(result) {
    	var self = et.vc;
    	if (result.message === ETCONST.SUCCESS) {
			self.authoritySettings(result.data);
		} else {
			if (result.data === 0) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
			} else {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"ID"}));
			}
		}
    }
    
    /**
     * 리스트를 가져와서 input name 과 확인후 체크 박스 설정
     */
    ctrl.authoritySettings = function (list) {
    	var self = et.vc;
    	self.rowData.list = list;
    	$.each(list, function (i, data){
    		$.each(data,function(key, value){
    			var ele = $("#"+data.menu_cd).find("input[name='"+key+"']");
    			if (0 < ele.length) {
    				if (value === "Y") {
    					ele.prop("checked",true);
    				} else {
    					ele.prop("checked",false);
    				}
    			}
    		});
    	});
    }
	
	// ============================== Form 리스너 ==============================
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = self.rowData;
		var list = formData.list;
		$.each(list, function (i, data){
    		$.each(data,function(key, value){
    			var ele = $("#"+data.menu_cd).find("input[name='"+key+"']");
    			if (0 < ele.length) {
  					data[key] = ele.is(":checked") ? "Y" : "N";    			
  				}
    		});
    	});
		
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAuthList", formData);
	}
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.addSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			new ETService().setSuccessFunction(self.getAuthListSuccessHandler).callService(self.path + "/getAuthList", self.rowData);
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