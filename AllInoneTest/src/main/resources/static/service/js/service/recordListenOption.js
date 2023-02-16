/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : mk jang
 * @CreateDate : 2022. 10. 20.
 * @DESC : [환경설정] 시스템 관리자 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "recordListenOption") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "recordListenOption";
	ctrl.path = "/record/recordListenOption";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.rullBtnSetting = {
				is_write : "#btnColumnSave"
				,is_update:"#btnSearchSave"
	}
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		et.common.btnRuleClassSetting(self.rullBtnSetting);
		et.common.btnRuleSetting();
		self.addEventListener();
		self.createDatatable(initData.userTypeList);
	};
	
	
	// ============================== 동작 컨트롤 ==============================
	
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearchSave").click(_.debounce(self.btnEditHandler,500));
		$("#btnColumnSave").click(_.debounce(self.btnEditHandler,500));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		$("#tbColumnList").on("change","input", function(e){
			var index = Number($(e.currentTarget).prop("id").replace("customSwitch01-",""));
			var data = $("#tbColumnList").DataTable().data()[index];
			data.is_view = $(this).is(':checked') ? "Y" : "N";
		});
		
		$("#tbSearchList").on("change","input", function(e){
			var index = Number($(e.currentTarget).prop("id").replace("customSwitch02-",""));
			var data = $("#tbSearchList").DataTable().data()[index];
			data.is_view = $(this).is(':checked') ? "Y" : "N";
		});
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
	ctrl.btnEditHandler = function (e) {
		var self = et.vc;
		var dataList = null;
		var isColumn = true;
		if ($(this).prop("id") === "btnColumnSave") {
			dataList = $("#tbColumnList").DataTable().data();
			isColumn = true;
		} else {
			dataList = $("#tbSearchList").DataTable().data()
			isColumn = false;
		}
		var list = [];
		$.each(dataList, function (index,item){
			if (item.is_view === null) {
				item.is_view = "N";
			}
			list.push(item);
		});
		
		self.editSubmitCallbackHandler(list, isColumn);
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
				data : "view_update_user",
			},{
				data : "view_update_dttm",
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
        new ETService().setSuccessFunction(self.getAuthListSuccessHandler).callService(self.path + "/getColumnSearchList", rowData);
    };
    
    /**
     * 권한 리스트를 가져온다
     */
    ctrl.getAuthListSuccessHandler = function(result) {
    	var self = et.vc;
    	if (result.message === ETCONST.SUCCESS) {
			self.columnCreateDataTable(result.data.columnList);
			self.searchCreateDataTable(result.data.optionList);
		} else {
			if (result.data === 0) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
			} else {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"ID"}));
			}
		}
    }
    
    /**
     * 리스트를 가져와서 column dataTable 셋팅
     */
    ctrl.columnCreateDataTable = function (list) {
    	var self = et.vc;
    	var columns = [
			{
				data : "col_name",
			},{
				data : "is_view",
				render : function (data,type,full,meta) {
					var checked = data === "Y" ? 'checked="checked"' : '';
					var div = $('<div class="custom-switch"></div>');
					var input = $('<input type="checkbox" class="custom-control-input" '+checked+' id="customSwitch01-'+meta.row+'">')
					var label = $('<label class="custom-control-label" for="customSwitch01-'+meta.row+'"></label>');
					div.append(input);
					div.append(label);
					return div.prop("outerHTML");
				}
			}
		];
		
		var table = et.createDataTableSettings(columns, null, {}, self.optionDataTableDrawCallback,"",false,list);
		table.paging = false;
		$("#tbColumnList").DataTable(table);
    }
    
    /**
     * 리스트를 가져와서 검색 dataTable 셋팅
     */
    ctrl.searchCreateDataTable = function (list) {
    	var self = et.vc;
    	var columns = [
			{
				data : "code_name",
			},{
				data : "is_view",
				render : function (data,type,full,meta) {
					var checked = data === "Y" ? 'checked="checked"' : '';
					var div = $('<div class="custom-switch"></div>');
					var input = $('<input type="checkbox" class="custom-control-input" '+checked+' id="customSwitch02-'+meta.row+'"><label class="custom-control-label" for="customSwitch02-'+meta.row+'"></label>');
					div.append(input);
					return div.prop("outerHTML");
				}
			}
		];
		
		var table = et.createDataTableSettings(columns, null, {}, self.optionDataTableDrawCallback,"",false,list);
		table.paging = false;
		$("#tbSearchList").DataTable(table);
    }
    
    /**
	 * 테이블 생성후 화면에 표시할때 콜
	 */
	ctrl.optionDataTableDrawCallback = function (settings) {
		var self = et.vc;
		if (0 < settings.aoData.length) {
    		$('.contRightNoData').hide();
    		$("#tbList_paginate").show();
    	} else {
    		$('.contRightNoData').show();
    		$("#tbList_paginate").hide();
    	}
	}
	
	// ============================== Form 리스너 ==============================
	/**
	 * 수정 버튼이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (list, isColumn) {
		var self = et.vc;
		var formData = self.rowData;
		formData.list = list;
		var url = isColumn ? "/setColumnList" : "/setOptionList";
		
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + url, formData);
	}
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.addSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
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