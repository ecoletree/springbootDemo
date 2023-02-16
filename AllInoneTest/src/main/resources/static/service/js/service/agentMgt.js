/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 04. 22.
 * @DESC : [상담원관리] 상담원관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "agentMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "agentMgt";
	ctrl.path = "/agent/agentMgt";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.pwFormId = "#pwForm";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.selectList = null;
	ctrl.skillData = null;
	ctrl.rullBtnSetting = {
				is_write : "#btnAdd"
				, is_delete : "#btnDelete" 
				, is_update : "#btnEdit" 
				, is_search : "#btnSearch" 
				, is_upload : "#btnAdd_list" 
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
		self.setValidate();
		self.addEventListener();
		self.setView();
		$("#btnSearch").trigger("click");
	};
	
	ctrl.setView = function(initData){
		var self = et.vc;
		$('.select2').select2({
        	maximumSelectionLength: 1
	    }); 
	    
		var groupList = self.groupList;
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroup", "고객사 전체");
		et.makeSelectOption([], {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");  
		et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, "#selTeamList", "팀전체");
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selNGroup");
		var param = {};
//		new ETService().setSuccessFunction(self.selectGroupSuccessHandler).callService(self.path + "/getSkillGroupList", param);
//		new ETService().setSuccessFunction(self.selectTeamSuccessHandler).callService(self.path + "/getTeamList", param);
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
				if(name === "io_flag"){
					var arr = ['1','2'];
					if (data[name] !== undefined) {
						arr = data[name] === '3' ? ['1','2'] :[data[name]];
					}
					 for (var nArrCnt in arr) {
	                    $("input[name=io_flag][value="+arr[nArrCnt]+"]").prop("checked",true);
			    	 }
					triggerElement.push(elemt);
				}
				
			} else if($(elemt).prop("localName") === "select"){
				if (data[name] === undefined) {
					$(elemt).children("option:eq(0)").prop("selected", true);
				} else {
					$(elemt).val(data[name]);
				}
				triggerElement.push(elemt);
			} else {
				$(elemt).val(data[name] === undefined ? "" :data[name]);
				if (data[name] !== undefined && name === "tenant_name") {
					self.selectUTeamHandler($(this).data("teamlist"));
					
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
 
		ETValidate.addMethod("fourNumber",function(value, element, params) {
			return value.length > 5;
		});		
		var editValidate = new ETValidate(self.editFormId).setSubmitHandler(self.editSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		editValidate.validateRules("tenant_id",editValidate.REQUIRED,et.message("validate.required", {name:"고객"}));
		editValidate.validateRules("agent_id",editValidate.REQUIRED,et.message("validate.required", {name:"상담원 ID"}));
		editValidate.validateRules("agent_id",editValidate.NUMBERONLY,et.message("validate.only_num", {name:"상담원 ID"}));
		editValidate.validateRules("agent_id","fourNumber",et.message("validate.to_num", {name:"상담원 ID", num:"6자리"}));
		editValidate.validateRules("agent_name",editValidate.REQUIRED,et.message("validate.required", {name:"상담원명"}));
		editValidate.apply();
////		
		var addValidate = new ETValidate(self.addFormId).setSubmitHandler(self.addSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		addValidate.validateRules("tenant_id",addValidate.REQUIRED,et.message("validate.required", {name:"고객"}));
		addValidate.validateRules("agent_id",addValidate.REQUIRED,et.message("validate.required", {name:"상담원 ID"}));
		addValidate.validateRules("agent_id",addValidate.NUMBERONLY,et.message("validate.only_num", {name:"상담원 ID"}));
		addValidate.validateRules("agent_id","fourNumber",et.message("validate.to_num", {name:"상담원 ID", num:"6자리"}));
		addValidate.validateRules("agent_pw",addValidate.REQUIRED,et.message("validate.required", {name:"비밀번호"}));
		addValidate.validateRules("agent_name",addValidate.REQUIRED,et.message("validate.required", {name:"상담원명"}))
		addValidate.validateRules("file",addValidate.FILE_EXTENSION,et.message("validate.extension", {name:"csv"}), "csv"); // csv file
		addValidate.apply();
		
		var pwValidate = new ETValidate(self.pwFormId).setSubmitHandler(self.pwSubmitCallbackHandler).setShowErrors(et.setErrorFunction());
		pwValidate.validateRules("agent_pw",pwValidate.REQUIRED,et.message("validate.required", {name:"비밀번호"}));
		pwValidate.apply();
	};
		
	ctrl.addEventListener = function () {
		var self = et.vc;
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnAdd").click(_.debounce(self.btnAddHandler,500));
		$("#btnAdd_list").click(_.debounce(self.btnAdd_listHandler,500));
		$("#btnSave").click(_.debounce(self.btnSaveHandler,500));
		$("#btnSaveCancel").click(_.debounce(self.btnSaveCancelHandler,500));
		$("#btnEdit").click(_.debounce(self.btnEditHandler,500));
		$("#btnDelete").click(_.debounce(self.btnDeleteHandler,500));
		$("#btnSaveList").click(_.debounce(self.btnSaveListHandler,500));
		
		$("#btnPWChangeOpen").click(_.debounce(self.pwChangeOpenHandler,500));
		$("#btnPWChange").click(_.debounce(self.pwChangeHandler,500));
		
		/** 추가화면에서 스킬그룹 검색 */
		$("#btnOpenSkillModal").click(_.debounce(self.btnOpenSkillModalHandler,500));
		$("#btnSearchSkill").click(_.debounce(self.btnSearchSkillHandler,500));
		$("#btnSaveSkill").click(_.debounce(self.btnSaveSkillHandler,500));
		
		
		$("#selGroup").trigger("change");
		$("#selGroup").change(self.selGroupListChangeHandler);
		$("#selNGroup").change(self.selNGroupIdChangeHandler);
		$("#selGroupSkill").change(self.selGroupSkillListChangeHandler);
		
		// 고객변경시 그룹 셀렉트 박스 변경
		$("#selNGroupCenter").change(_.debounce(self.selectGroupHandler,500));
		$("#selNGroupCenter").change(_.debounce(self.selectTeamHandler,500));
		$("#selGroupCenter").change(_.debounce(self.selectTeamHandler,500));
		
		et.setEnterKeyDownEvent("#iptSkilGroupNameList,#iptAgentNameList", _.debounce(self.btnSearchHandler,500),$("#btnSearch"));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		et.setDataTableRowSelection("#tbSkillTableList", self.tbSkillTableListRowSelect);
		
		et.setNumberKeyPressEvent("#iptNAgentID,#iptAgentID");

	}
		
	// ============================== 이벤트 리스너 ==============================
	ctrl.btnOpenSkillModalHandler = function(){
		var self = et.vc;
		if($(this).is("#btnOpenSkillModal")){
			var groupList = self.groupList;
			et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroupSkill", "고객사 전체",$("#selNGroup").val());
			$("#iptSkillName").val("");
			$("#iptSkillId").val("");
			$("#modalSkillList").modal();
			$("#btnSearchSkill").trigger("click");
		}else{
			$("#modalSkillList").hide();
		}
	}
	ctrl.btnSearchSkillHandler = function(){
		var self = et.vc;
		var param = {};
		param.group_id = $("#selGroupSkill").val();
		param.tenant_id = $("#selTenantSkill").val();
		param.skill_id = $("#iptSkillId").val();
		param.skill_name = $("#iptSkillName").val();
		self.createSkillDatatable(param);
		
	}
	ctrl.btnSaveSkillHandler = function(){
		var self = et.vc;
		var skillData = self.skillData;
		if(skillData === null) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.agent_skill"));
		}else{
			$("#iptNSkillGroup").val(skillData.name);
			$("#iptNSkillGroup").data(skillData);
			$("#btnCloseSkillModal").trigger("click");
		}
	}
	
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selGroupCenter", "센터전체");
		$("#selGroupCenter").trigger("change");
	}
	ctrl.selNGroupIdChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selNGroupCenter");
		$("#selNGroupCenter").trigger("change");
	}
	ctrl.selGroupSkillListChangeHandler = function () {
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		var tenant_id = Number($("#selNGroupCenter").val());
		et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selTenantSkill","",tenant_id);
	}
	ctrl.selectTeamHandler = function(){
		var dataList = $(this).find("option:selected").data()?.children ?? [];
		var selId = $(this).data("teamlist");
		if(selId === "selTeamList"){
			et.makeSelectOption(dataList, {value:"team_id", text:"view_group_name"}, "#"+selId,"팀전체");
		}else{
			et.makeSelectOption(dataList, {value:"team_id", text:"view_group_name"}, "#"+selId);
		}
		$("#iptNSkillGroup").val("");
		
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
			params.team_id = $("#selTeamList").val();
			params.grp_id = $("#iptSkilGroupNameList").val();
			params.agent_name = $("#iptAgentNameList").val();
			
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
	ctrl.btnAdd_listHandler = function () {
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
		var params = {};
		params.agent_id = $("#iptAgentID").val();
		et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "삭제", et.message("button.delete_confirm"), ["확인", "창닫기"], function(e) {
			new ETService().setSuccessFunction(self.deleteSuccessHandler).callService(self.path + "/delAgentMgt", params);
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
	
	/**
	 * 비밀번호 모달 띄우기 
	 */
	ctrl.pwChangeOpenHandler = function () {
		var self = et.vc;
		$("#iptCPassword").val("");
		$("#divPwChange").modal("show");
	}
	
	/**
	 * 비밀번호 변경 버튼 클릭 
	 */
	ctrl.pwChangeHandler = function () {
		var self = et.vc;
		$(self.pwFormId).submit();
	}
	
	/*
	 * csv 등록 핸들러
	 */
	ctrl.btnSaveListHandler = function(){
		var self = et.vc;
		
		var csv = $("#uploadByDec")[0];
		var json = null;
		var reader = new FileReader();
		
	    reader.onload = function(){
	        var fileData = reader.result;
	        var wb = XLSX.read(fileData, {type : 'binary'});
	        var json = null;
	        wb.SheetNames.forEach(function(sheetName){
		        json =XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);
	        });
	        
	        var headers = [
			{headerText:"고객사 ID", col_name:"group_id"}
			,{headerText:"센터 ID", col_name:"tenant_id"}
			,{headerText:"팀 ID", col_name:"team_id"}
			,{headerText:"스킬 그룹 ID", col_name:"grp_id"}
			,{headerText:"상담원 ID", col_name:"agent_id"}
			,{headerText:"비밀번호", col_name:"agent_pw"}
			,{headerText:"상담원 명", col_name:"agent_name"}
			,{headerText:"In/Out (작성 예) In: I , Out : O , In/Out : I/O", col_name:"io_flag"}
			];
			//header change
			for(var i in headers){
				json = JSON.parse(JSON.stringify(json).split(headers[i].headerText).join(headers[i].col_name));
			}
			json.forEach(function(e){
				e.agent_pw = btoa(e.agent_pw);
			});
	        var params = {};
			    params.agentList = json;
			    params.DBTYPE = "I";
				if(params.agentList.length > 0){
					new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAgentMgt", params);
				}else{
					et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.fail_csv_file"));
				}
	        
	    };//onload function
		reader.readAsText(csv.files[0]);
	    
	}
	/**
	 * tenant_id 별 스킬그룹 selectbox 생성 - 추가화면
	 */ 
	ctrl.selectGroupHandler=function(){
		var self = et.vc;
		var tenantId = $(this).val() === "" ? "all" : $(this).val()+"";
		var selectID = $(this).data("skillgroup");
		if(self.selectList !== null){
			et.makeSelectOption(self.selectList[tenantId], {value:"grp_id", text:"view_grp_name"}, "#"+selectID);  // 그룹선택
			if(self.selectList[tenantId] !== undefined && self.selectList[tenantId] !== null){
				$("#"+selectID).children("option:eq(0)").prop("selected", true);
			}
			
		}
	}
	/**
	 * tenant_id 별 스킬그룹 selectbox 생성 - 수정화면
	 */ 
	ctrl.selectUGroupHandler = function(selectID){
		var self = et.vc;
		var rowData = self.rowData;
		var tenantId = rowData.tenant_id;
		if(self.selectList !== null){
				et.makeSelectOption(self.selectList[tenantId], {value:"grp_id", text:"view_grp_name"}, "#"+selectID);  // 그룹선택
		}
	}

	/**
	 * tenant_id 별 팀 selectbox 생성 - 수정화면
	 */ 
	ctrl.selectUTeamHandler = function(selectID){
		var self = et.vc;
		var rowData = self.rowData;
		var groupList = self.groupList;
		var teamList = null;
		for(var i in groupList){
			var centerList = null;
			if(groupList[i].group_id === rowData.group_id){
				centerList = groupList[i].children;
				if(centerList !== null && centerList !== undefined){
					for(var j in centerList){
						if(centerList[j].tenant_id === rowData.tenant_id){
							teamList = centerList[j].children;
						}
					}
				}
			}
		}
		if(teamList !== null){
			et.makeSelectOption(teamList, {value:"team_id", text:"view_group_name"}, "#"+selectID);  // 그룹선택
		}
		
	}
	/**
	 * 그룹 리스트 조회 successHandler
	 */
	ctrl.selectGroupSuccessHandler = function(result){
		var self = et.vc;
		if(result.message === ETCONST.SUCCESS){
			self.selectList = result.data;
		}
	}
	/**
	 * 그룹 리스트 조회 successHandler
	 */
	ctrl.selectTeamSuccessHandler = function(result){
		var self = et.vc;
		if(result.message === ETCONST.SUCCESS){
			self.teamList = result.data;
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
				{data: "group_name"}
				,{data: "tenant_name"}
				,{data: "team_name"}
				,{data: "grp_id",className:"txtCenter"} 
				,{data: "grp_name"}
				,{data: "agent_id"} 
				,{data: "agent_name"} 
				,{data: "io_flag",className:"txtCenter",
					render: function ( data, type, row ) {
						var flag = "";
						if(data !== null){
							flag = "I"
							if(data === "2"){flag = "O"
							}else if(data === "3"){
							 	flag = "I/O"
							} 
						}
						
					    return flag;
				}} 
				,{data: "last_login",className:"txtCenter"} 
				,{data: "last_logout",className:"txtCenter"} 
				,{data: "phone",className:"txtCenter"} 
				
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getAgentList", params, self.dataTableDrawCallback);
		table.paging=false;
		
		$(self.tableId).DataTable(table);
	}
	/**
	 * 테이블 콜백
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
	 * 테이블 조회
	 */
	ctrl.createSkillDatatable = function(postData){
		var self = et.vc;
		var params = postData || {};
		var columns = [
				{data: "grp_id",className:"txtCenter",
					render: function ( data, type, row ) {
						return '<label class="ecoleCheck"><input value='+data+' name="soundsourceId" type="checkbox" ><i></i></label>';
				}}
				,{data: "group_name"}
				,{data: "view_tenant_name"}
				,{data: "grp_id",className:"txtCenter"} 
				,{data: "name"}
				,{data: "use_cnt",className:"txtCenter",
					render: function ( data, type, row ) {
						var useYn = data > 0 ? "o" : "x";
					    return useYn;
				}} 
				
			];
			
		var table = et.createDataTableSettings(columns, self.path + "/getSkillGroupList", params, self.skillgroupDataTableDrawCallback);
		table.paging=false;
		
		$("#tbSkillTableList").DataTable(table);
	}
		/**
	 * 테이블 콜백
	 */
	ctrl.skillgroupDataTableDrawCallback = function (settings) {
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
        var rowData = et.getRowData(self.tableId, $target.closest("tr"));
        self.rowData = rowData;
        self.setViewData(self.editFormId,rowData);


    }
     ctrl.tbSkillTableListRowSelect = function($target, row, col){
		var self = et.vc;
		var rowData =null;
		if(col === 0){
			var rowData = et.getRowData("#tbSkillTableList", $target.closest("tr"));
		}
		$(tbSkillTableList).find("tbody input").attr("disabled",true);
		if($("#tbSkillTableList").find("tbody input:checked ").length > 0){
			$("#tbSkillTableList").find("tbody input:checked ").attr("disabled",false);
			self.skillData = rowData;
		}else{
			self.skillData = null;
			$(tbSkillTableList).find("tbody input").attr("disabled",false);
		}
	}	
	// ============================== Form 리스너 ==============================
	
	/**
	 * 추가 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.addSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.agent_pw = btoa(formData.agent_pw);
		formData.grp_id = $("#iptNSkillGroup").data()?.grp_id;
		var ioFlag = 0;
		$(ctrl.addFormId+" input[name ='io_flag']:checked").each(function(){  ioFlag += Number($(this).val()); });
		formData.io_flag = ioFlag === 0 ? null : ioFlag;
		var params = {};
		params.DBTYPE = "I";
		params.agentList = [formData]; 
		if(formData.grp_id === ""){
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("validate.required", {name:"스킬 그룹 명"}));
		}else{
			if(params.agentList[0].agent_id.substr(0,1) !== "6" ){
				et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "", et.message("validate.frontNumCheck", {name:"상담원 ID", num :"600000"}), ["확인", "창닫기"], function(e) {
					new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAgentMgt", params);
				});
				
			}else{
				new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAgentMgt", params);
			}
		}
		
	}
	
	/**
	 * 수정 Form Validation이 정상적으로 동작했을 경우 발생
	 */
	ctrl.editSubmitCallbackHandler = function (form) {
		var self = et.vc;
		var rowData = self.rowData;
		var formData = ETValidate.convertFormToObject(form,true,true);
		formData.group_id = rowData.group_id;//그룹id
		formData.tenant_id = rowData.tenant_id; // 센터id
		formData.agent_id = $("#iptAgentID").val(); //상담원id
		formData.grp_id = rowData.grp_id;
		formData.agent_pw = btoa(formData.agent_pw);//비밀번호
		formData.agent_status  =  rowData.agent_status !== null && rowData.agent_status !== "" ? rowData.agent_status : "Available"; 
		formData.state  =  rowData.state !== null && rowData.state !== "" ? rowData.state : "Waiting"; 
		formData.phone  =  rowData.phone !== null && rowData.phone !== "" ? rowData.phone : "";
		var ioFlag = 0;
		$(self.editFormId+" input[name ='io_flag']:checked").each(function(){  ioFlag += Number($(this).val()); });
		formData.io_flag = ioFlag === 0 ? null : ioFlag;
		formData.DBTYPE = "U";
		if(formData.grp_id === undefined){
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("validate.required", {name:"스킬 그룹 명"}));
		}else{
			new ETService().setSuccessFunction(self.addSubmitSuccessHandler).callService(self.path + "/setAgentMgt", formData);
		}
	}
	
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.addSubmitSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#btnSearch").trigger("click");
			$("#divPwChange").modal("hide");
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