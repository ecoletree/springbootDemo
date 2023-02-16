/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2022. 05. 04.
 * @DESC : [관리자웹]  고객사 업무시간 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "custTimeMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "custTimeMgt";
	ctrl.path = "/tenant/custTimeMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.groupList = null;
	
	// 요일
	ctrl.days =  [{value:"1", text:"월요일"} 
		,{value:"2", text:"화요일"}
		,{value:"3", text:"수요일"}
		,{value:"4", text:"목요일"}
		,{value:"5", text:"금요일"}
		,{value:"6", text:"토요일"}
		,{value:"7", text:"일요일"}
	];
	
	// 시간
	ctrl.hour = [{value:"00", text:"00"} 
		,{value:"01", text:"01"}
		,{value:"02", text:"02"}
		,{value:"03", text:"03"}
		,{value:"04", text:"04"}
		,{value:"05", text:"05"}
		,{value:"06", text:"06"}
		,{value:"07", text:"07"}
		,{value:"08", text:"08"}
		,{value:"09", text:"09"}
		,{value:"10", text:"10"}
		,{value:"11", text:"11"}
		,{value:"12", text:"12"}
		,{value:"13", text:"13"}
		,{value:"14", text:"14"}
		,{value:"15", text:"15"}
		,{value:"16", text:"16"}
		,{value:"17", text:"17"}
		,{value:"18", text:"18"}
		,{value:"19", text:"19"}
		,{value:"20", text:"20"}
		,{value:"21", text:"21"}
		,{value:"22", text:"22"}
		,{value:"23", text:"23"}
	];
	// 분
	ctrl.minute = [{value:"00", text:"00"} 
		,{value:"05", text:"05"}
		,{value:"10", text:"10"}
		,{value:"15", text:"15"}
		,{value:"20", text:"20"}
		,{value:"25", text:"25"}
		,{value:"30", text:"30"}
		,{value:"35", text:"35"}
		,{value:"40", text:"40"}
		,{value:"45", text:"45"}
		,{value:"50", text:"50"}
		,{value:"55", text:"55"}
	];
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
		self.addEventListener();
		
		self.groupList = initData.groupList;
		
		
		et.makeSelectOption(initData.groupList, {value:"tenant_id", text:"view_group_name"}, "#selGroupList", "고객사전체");  // 팀선택
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selCenterList", "센터전체");  // 팀선택
		et.makeSelectOption(initData.groupList, {value:"group_id", text:"view_group_name"}, "#selNGroupId");  // 팀선택
		et.makeSelectOption([], {value:"group_id", text:"view_group_name"}, "#selNCenterId");  // 팀선택
		
		et.makeSelectOption(self.days, {value:"value", text:"text"}, "#selWSDay");  // 요일
		et.makeSelectOption(self.days, {value:"value", text:"text"}, "#selNWSDay");  // 요일
		
		et.makeSelectOption(self.hour, {value:"value", text:"text"}, "#selWSStartH");  // 시간
		et.makeSelectOption(self.minute, {value:"value", text:"text"}, "#selWSStartM");  // 분
		et.makeSelectOption(self.hour, {value:"value", text:"text"}, "#selWSEndH");  // 시간
		et.makeSelectOption(self.minute, {value:"value", text:"text"}, "#selWSEndM");  // 분
		
		et.makeSelectOption(self.hour, {value:"value", text:"text"}, "#selNWSStartH");  // 시간
		et.makeSelectOption(self.minute, {value:"value", text:"text"}, "#selNWSStartM");  // 분
		et.makeSelectOption(self.hour, {value:"value", text:"text"}, "#selNWSEndH");  // 시간
		et.makeSelectOption(self.minute, {value:"value", text:"text"}, "#selNWSEndM");  // 분
		
	};
	
	/**
	 * viewData Setting
	 * formId 를 넘겨서 해당 form 만 설정
	 * data의 값은 dataTables row 값이나 또는 디폴트 값
	 */
	ctrl.setViewData = function (formId,data) {
		var self = et.vc;
		self.rowData = data;
		
		data.ws_start_h = "00"; 
		data.ws_start_m = "00"; 
		if (data.ws_start != undefined && 4 === data.ws_start.length ) {
			data.ws_start_h = data.ws_start.substring(0,2);
			data.ws_start_m = data.ws_start.substring(2,4);
		}
		
		data.ws_end_h = "00"; 
		data.ws_end_m = "00"; 
		if (data.ws_end != undefined && 4 === data.ws_end.length ) {
			data.ws_end_h = data.ws_end.substring(0,2);
			data.ws_end_m = data.ws_end.substring(2,4);
		}
		
		var triggerElement = [];
		$(formId).find("input,select").each(function(index, elemt){
			var name = $(elemt).prop("name");
			if ($(elemt).prop("localName") !== "select") {
				if ($(elemt).prop("type") !== "radio") {
					$(elemt).val(data[name] === undefined ? "" :data[name]);
				} else {
					if ($(elemt).val() === "1") {
						$(elemt).prop("checked",true);
						triggerElement.push(elemt);
					}
				}
			} else {
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				} else {
					$(elemt).val(data[name]);
				}
				triggerElement.push(elemt);
			}
		});
		/**
		 * 만약 select와 input 이 있으면 click 또는 change 이벤트를 걸어준다
		 */
		$.each(triggerElement,function(index,elemt){
			if ($(elemt).prop("localName") === "select") {
				$(elemt).trigger("change");
			} else {
				if ($(elemt).prop("type") === "radio") {
					$(elemt).trigger("change");
				}
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
		editValidate.validateRules("group_id",editValidate.REQUIRED,et.message("validate.required", {name:"고객사명"}));
		editValidate.validateRules("tenant_id",editValidate.REQUIRED,et.message("validate.required", {name:"센터명"}));
		editValidate.apply();
		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객사명"}));
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"센터명"}));
		addValidate.apply();
	};
	
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnAdd").click(_.debounce(self.btnAddHandler,500));
		$("#btnSave").click(_.debounce(self.btnSaveHandler,500));
		$("#btnSaveCancel").click(_.debounce(self.btnSaveCancelHandler,500));
		$("#btnEdit").click(_.debounce(self.btnEditHandler,500));
		$("#btnDelete").click(_.debounce(self.btnDeleteHandler,500));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selNGroupId").change(self.selNGroupIdChangeHandler);
		
		$("#btnSearch").trigger("click");
		
		$("input[name='raDays']:radio").change(function () {
	        //라디오 버튼 값을 가져온다.
	        var days = $(this).val();
	        
	        if (days === "1") {
	        	$("#divDays").show();
	        } else {
	        	$("#divDays").hide();
	        	
	        }
		});

		
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
	}
	
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.group_id = $("#selGroupList").find("option:selected").data().group_id;
			params.tenant_id = $("#selCenterList").find("option:selected").data().tenant_id;
			self.createDatatable(params);
			
		}
	}
	
	/*
	 * 고객사 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		
		self.setViewData(self.addFormId,{workschedule_id:"-1",ws_day:"1", wa_start:"0000", wa_end:"0000"});
		$("#bxWrite").show();
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
		params.workschedule_id = self.rowData.workschedule_id;
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delCustTime", params);
		});
	}
	
	/*
	 * 삭제 성공시 핸들러
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
	
	// ============================== DataTables 생성, 이벤트들 ==============================
	
	/*
	 * 데이터 테이블즈 생성
	 */
	ctrl.createDatatable = function(postData) {
		var self = et.vc;
		var params = postData || {};
		var columns = [
			{
				data : "group_name",
			},{
				data : "center_name",
			},{
				data : "ws_day",
				className : "txtCenter",
				render : function(data, type, full, meta){
					var str = "";
					$.each(self.days, function(index, item){
						if (data == item.value) {
							str = item.text;
							return;
						}
					});
					return str;
   				},
			},{
				data : "ws_start",
				className : "txtCenter",
				render : function(data, type, full, meta){
					var str = "";
					if (4 === data.length) {
						str = data.substring(0,2)+":"+data.substring(2,4);
					}
					return str;
   				},
			},{
				data : "ws_end",
				className : "txtCenter",
				render : function(data, type, full, meta){
					var str = "";
					if (4 === data.length) {
						str = data.substring(0,2)+":"+data.substring(2,4);
					}
					return str;
   				},
			}
		];
		
		var table = et.createDataTableSettings(columns, self.path + "/getCustTimeList", params, self.dataTableDrawCallback);
		table.paging = false;
		$(self.tableId).DataTable(table);
	};
	
	/**
	 * 테이블 생성후 화면에 표시할때 콜
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
    };
	
	// ============================== Form 리스너 ==============================
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.DBTYPE = "I";
		formData.ws_start = formData.ws_start_h+formData.ws_start_m; 
		formData.ws_end = formData.ws_end_h+formData.ws_end_m;
		
		var wsArray = [];
		var obj = {};
		if (formData.raDays === "1") {
			wsArray.push(Object.assign({}, formData));
		} else {
			$.each(ctrl.days, function(index,item) {
				var dayOfWeek = Number(item.value);
				if (formData.raDays === "2") {
					if (5 < dayOfWeek) {
						return;
					}
					obj = Object.assign({}, formData);
					obj.ws_day = item.value;
					wsArray.push(obj);
				} else if (formData.raDays === "3") {
					if (5 < dayOfWeek) {
						obj = Object.assign({}, formData);
						obj.ws_day = item.value;
						wsArray.push(obj);
					}
				} else {
					obj = Object.assign({}, formData);
					obj.ws_day = item.value;
					wsArray.push(obj);
				}
			});
		}
		
		formData.wsArray = wsArray; 
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setCustTime", formData);
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.workschedule_id = self.rowData.workschedule_id;
		formData.DBTYPE = "U";
		formData.ws_start = formData.ws_start_h+formData.ws_start_m; 
		formData.ws_end = formData.ws_end_h+formData.ws_end_m;
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setCustTime", formData);
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
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.duplicate",{name:"일련번호"}));
			}
		}
	}
	
	
	return ctrl;
}));