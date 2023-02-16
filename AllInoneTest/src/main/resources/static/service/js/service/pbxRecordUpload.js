/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2022. 05. 04.
 * @DESC : [관리자웹]  pbx 음원관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "pbxRecordUpload") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "pbxRecordUpload";
	ctrl.path = "/sound";
	ctrl.formId = "#formUpload";
	ctrl.tableId = "#tbList";
	ctrl.sourceType = "pbx";
	ctrl.filePath = "/usr/local/freeswitch/sounds";
	ctrl.rowData = null;
	ctrl.groupList = null;
	
	ctrl.maxFileSize = 10*1024*1024; //10mb
	ctrl.rullBtnSetting = {
				is_write : "#btnAdd"
				, is_delete : "#btnDelete" 
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
		self.groupList = initData.groupList;
		ETValidate.addMethod("fileNameMaxLengthAsByte",function(value, element, params) {
			return (value || "").replace(/[\0-\x7f]|([0-\u07ff]|(.))/g,"$&$1$2").length <= ($(element).data("maxNameLength") || $(element).data("max-name-length") || 0);
		});
		
		et.makeSelectOption(initData.groupList, {value:"group_id", text:"view_group_name"}, "#selGroupList", "고객사전체");  // 팀선택
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selCenterList", "센터전체");  // 팀선택
		et.makeSelectOption(initData.groupList, {value:"group_id", text:"view_group_name"}, "#selGroupId");  // 팀선택
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selCenterId");  // 팀선택
		
		self.setValidate();
		self.addEventListener();
	};
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;
		
		var addValidate = new ETValidate(self.formId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("group_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객사"}));		
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"센터"}));		
		
		addValidate.validateRules("file",addValidate.IMGFILESIZE,et.message("validate.filesize", {num:10}),{num:10});
		addValidate.validateRules("file","fileNameMaxLengthAsByte",et.message("validate.filename", {num:100}));
		addValidate.validateRules("file",addValidate.REQUIRED,et.message("validate.required", {name:"음원"}));
		addValidate.validateRules("file",addValidate.FILE_EXTENSION,et.message("validate.extension", {name:"wav"}), "wav");
		
		
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
		$("#btnDelete").click(_.debounce(self.btnDeleteHandler,500));
		$("#chkAllCheck").click (function () {
			var chk=$(this).is(":checked")
			$(self.tableId).find('input[name="soundsourceId"]').each(function(){
				$(this).prop('checked', chk);
			})
		});
		
		
		et.setEnterKeyDownEvent("#ipxSearchFileName", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		$('.fileinput').on('change.bs.fileinput clear.bs.fileinput', self.file_changeEventListener);
		
		// 파일이 취소 되었을때
		$("#uploadByDec").change(function(){
			if ($(this).val() === "") {
				$("#srcSound").attr("src", "");
	    		$("#audioSound").hide();
	    		document.getElementById("audioSound").load();
			}
		});
		
		$("#btnSearch").trigger("click");
		
		$('#divSound').on('hidden.bs.modal', function () {
			$("#audioSound")[0].pause();
			$("#srcSound").attr("src", "");
			$("#uploadByDec").val("");
		});
		
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selGroupId").change(self.selGroupIdChangeHandler);
	}
	
	// ============================== 이벤트 리스너 ==============================
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selCenterList", "센터전체");
	}
	
	ctrl.selGroupIdChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		var sValue = null;
		if (self.rowData != null) {
			sValue = self.rowData.tenant_id; 
		}
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selCenterId","",sValue);
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
			params.file_name = $("#ipxSearchFileName").val();
			params.source_type = self.sourceType;
			self.createDatatable(params);
		}
	}
	
	 /**
     * 첨부파일 변경 이벤트 핸들러
     * @param e
     */
    ctrl.file_changeEventListener = function (e) {
        var self = et.vc;
        if (e.type === 'change') {
        	 self.fileinputChanged(this);
        }
    };
    
    /**
     * 첨부파일이 선택되었을 때 수행
     * @param element
     */
    ctrl.fileinputChanged = function (element) {
    	var self = et.vc;
    	var objectURL;
        
    	if (objectURL) {
    		URL.revokeObjectURL(objectURL);  
    	}
    	var file = $("#uploadByDec")[0].files[0];
    	if(file !== undefined){
    		if (file.name.match(new RegExp(".(wav)$", "i"))) {
    			objectURL = URL.createObjectURL(file);
        		$("#srcSound").attr("src", objectURL);
        		$("#audioSound").show();
        		
        	} else {
        		$("#srcSound").attr("src", "");
        		$("#audioSound").hide();
        	}
    	}else{
    		$("#srcSound").attr("src", "");
    		$("#audioSound").hide();
    	}
    	document.getElementById("audioSound").load();
    };
	
	/*
	 * 음원 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		self.rowData = null;
		$("#audioSound").hide();
		$("#uploadByDec").val("");
		$("#selGroupId").children("option:eq(0)").prop("selected", true);
		$("#selGroupId").trigger("change");
		$(".fileinput-filename").text("");
		$("#divSound").modal("show");
		$("#divSound").data("data",{});
		
	}
	
	/*
	 * 추가 버튼 클릭 핸들러
	 */
	ctrl.btnSaveHandler = function () {
		var self = et.vc;
		$(self.formId).submit();
	}
	
	/*
	 * 삭제 버튼 클릭 핸들러
	 */
	ctrl.btnDeleteHandler = function () {
		var self = et.vc;
		var params = {};
		var sources = [];
		
		$(self.tableId).find("tbody input:checked ").each(function(index){
			var chkData = et.getRowData(self.tableId,$(this).parents("tr"));
			sources.push(chkData);
		});
		if (0 === sources.length){
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_not_exist"));
		} else {
			params.sources = sources;
			et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
				new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delRecord", params);
			});
		}
	}
	
	/*
	 * 삭제 버튼 클릭 핸들러
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
				data : "soundsource_id",
				className : "txtCenter",
				render : function (data, type, row, meta){
	   				return '<label class="ecoleCheck"><input value='+data+' name="soundsourceId" type="checkbox" ><i></i></label>';
	   			}
			},{
				data : "group_name",
			},{
				data : "center_name",
			},{
				data : "file_name",
			},{
				data : "file_path",
				className : "txtCenter",
				render : function (data, type, full, meta){
					var path = full.file_path;
					path = path.replace(self.filePath,"");
					path = path.replace(/\\/gi, "§");
					path = path.replace(/\//gi, "§");
					var url = sessionStorage.getItem("contextpath")+self.path+"/getAudio/"+path;
					var str = "<audio controls>";
					str += "<source src='"+url+"'>";
					str += "이 브라우저로는 음원을 재생할 수 없습니다.<br>다른 브라우저를 이용해 주세요.";
					str += "</audio>";
	   				return str;
	   			}
			}
			];
		var table = et.createDataTableSettings(columns, self.path + "/getRecordList", params, self.dataTableDrawCallback);
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
        if (0 < col && col < 4) {
        	var rowData = et.getRowData(self.tableId, $target.closest("tr"));
        	self.rowData = rowData;
        	var path = rowData.file_path;
			path = path.replace(self.filePath,"");
			path = path.replace(/\\/gi, "§");
			path = path.replace(/\//gi, "§");
			var url = sessionStorage.getItem("contextpath")+self.path+"/getAudio/"+path;
    		$("#srcSound").attr("src", url);
        	document.getElementById("audioSound").load();
        	$("#audioSound").show();
        	
    		$("#selGroupId").val(rowData.tenant_id);
    		$("#selGroupId").trigger("change");
    		$(".fileinput-filename").text(rowData.file_name);
    		$("#divSound").modal("show");
    		$("#divSound").data("data",rowData);
        }
    };
	
	// ============================== Form 리스너 ==============================
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		
		$("#audioSound")[0].pause();
		var formData = ETValidate.convertFormToFormData(form,true,true);
		if ($("#divSound").data("data").soundsource_id === undefined) {
			formData.append("DBTYPE","I");
		} else {
			formData.append("DBTYPE","U");
			formData.append("soundsource_id",$("#divSound").data("data").soundsource_id);
			formData.append("ori_file_path",$("#divSound").data("data").ori_file_path);
			formData.append("soundsource_cd",$("#divSound").data("data").soundsource_cd);
		}
		var files = $(form).find('[name="file"]').prop('files');
		if (!(_.isNil(files) || _.isEmpty(files))){
            formData.append("sound_file", files[0]);
        } else {
            formData.append('sound_file', null);
        }
		formData.append('source_type', self.sourceType);
		var ajaxOption = {
            contentType: false,
            processData: false
        };
		new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setRecord", formData, ajaxOption);
	}
	
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.addSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#divSound").modal("hide");
			$("#btnSearch").trigger("click");
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