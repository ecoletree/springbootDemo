/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : mkjang
 * @CreateDate : 2022. 10. 18.
 * @DESC : [과금관리] 내선별 리포트 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "payReport") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "payReport";
	ctrl.path = "/chargeMgt/payReport";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	self.codeList = null;
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
		self.groupList = initData.groupList;
		self.setTime();
		self.setView(initData);
		self.addEventListener();
	};
	
	ctrl.setView = function(initData) {
		var self = et.vc;
		
		et.common.CODE_LIST = initData.codeList;
		self.codeList = et.common.getCodeList("CA000"); // 통화 구분 코드
		
		self.createCallTypeCheckBox();
		var d = new Date();
		et.initializeDatepicker(new Date(d.getFullYear(),d.getMonth()-1,d.getDate()), '#divStartDate');
		et.initializeDatepicker(new Date(), '#divEndDate');
		
		var groupList = self.groupList;
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");  
	}
	
	/**
	 * 통화 구분 만들기
	 */
	ctrl.createCallTypeCheckBox = function () {
		var self = et.vc;
		
		$(".row2").empty();
		$.each(self.codeList, function (index, item){
			var checkBox = $('<label class="ecoleCheck"><input type="checkbox" value="'+item.code_cd+'" checked><i></i>'+item.code_name+'</label>');
			$(".row2").append(checkBox);
		});
		
	}
	
	ctrl.setTime = function () {
		var self = et.vc;
		$("#selSHour").empty();
		$("#selEHour").empty();
		$("#selSMinute").empty();
		$("#selSSecond").empty();
		$("#selEMinute").empty();
		$("#selESecond").empty();
		var i = 0;
		for (i = 0; i < 24; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : i,text : txt}
			var option1 = $('<option>', data);
			var option2 = $('<option>', data);
			
			$("#selSHour").append(option1);
			$("#selEHour").append(option2);
		}
		for (i = 0; i < 60; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : i,text : txt}
			var option1 = $('<option>', data);
			var option2 = $('<option>', data);
			var option3 = $('<option>', data);
			var option4 = $('<option>', data);
			$("#selSMinute").append(option1);
			$("#selSSecond").append(option2);
			$("#selEMinute").append(option3);
			$("#selESecond").append(option4);
		}
		
		$("#selEHour").val(23);
		$("#selEMinute").val(59);
		$("#selESecond").val(59);
	}
	
	ctrl.getTime = function (divId) {
		var str = "";
		$.each($(divId+" select option:selected"),function(index,elem){
			str += $(elem).text()+":";
		});
		//str = $(divId+" select option:selected").text()
		str = str.substring(0,str.length-1);
		return str;
	}
	
	// ============================== 동작 컨트롤 ==============================
		
	ctrl.addEventListener = function () {
		var self = et.vc;
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnExcelDownLoad").click(_.debounce(self.btnExcelDownHandler,500));
		$("#selGroup").trigger("change");
		$("#selGroup").change(self.selGroupListChangeHandler);
		et.setNumberKeyPressEvent("#iptSPhone");
		et.setNumberKeyPressEvent("#iptEPhone");
		et.setNumberKeyPressEvent("#iptBiling");
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
	}
	// ============================== 이벤트 리스너 ==============================
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
	}
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			if ($("#iptSPhone").val() !== "") {
				params.sphone = Number($("#iptSPhone").val());
			} 
			if ($("#iptEPhone").val() !== "") {
				params.ephone = Number($("#iptEPhone").val());
				params.sphone = params.sphone === undefined ? 1 : params.sphone;
			}
			params.amount = $("#iptBiling").val() === "0" ? null : Number($("#iptBiling").val());
			params.above = $("#selAbove").val();
			params.sdate = $("#divStartDate input").val()+" "+self.getTime("#divSTime");
			params.edate = $("#divEndDate input").val()+" "+self.getTime("#divETime");
			params.call_type = [];
			params.call_type.push("CA000");
			$.each($(".row2 input"), function (index, elem) {
				if ($(elem).is(":checked")) {
					params.call_type.push($(elem).val());
				}
			});
			params.typeList = params.call_type.toString();
			params.group_id = $("#selGroup").val();
			params.tenant_id = $("#selGroupCenter").val();
			self.createDatatable(params);
		}
	}
	
	/*
	 * 엑셀 다운로드 핸들러
	 */
	ctrl.btnExcelDownHandler = function () {
		var self = et.vc;
		$.etExcelUtil.excelDownloadAjax($(self.tableId).DataTable(),null,"내선별_과금리포트",false,false);
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
				data : "phone",
			},{
				data : "view_tenant_name",
			},{
				data : "ca001",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca002",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca003",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca004",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca005",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca006",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca007",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca008",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca009",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "ca010",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			},{
				data : "casum",
				className : "txtRight",
				render : function (data,type,full,meta) {
					 return data.toLocaleString();
				}
			}
				
			];
		
		var table = et.createDataTableSettings(columns, self.path + "/getReportList", params, self.dataTableDrawCallback);
		table.paging = false;
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
        var rowData = et.getRowData(self.tableId, $target.closest("tr"));
        self.rowData = rowData;
    };
    
	// ============================== Form 리스너 ==============================
	
		
	return ctrl;
}));