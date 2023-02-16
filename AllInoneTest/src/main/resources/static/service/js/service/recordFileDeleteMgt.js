/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2022. 05. 04
 * @DESC : [관리자웹]  서비스 레벨 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "recordFileDeleteMgt") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "recordFileDeleteMgt";
	ctrl.path = "/record/fileDeleteMgt";
	ctrl.groupList = null;
	ctrl.codeList = null;
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
		self.groupList = initData.groupList;
		self.setView();
		self.setValidate();
		self.addEventListener();
		
		
	};
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 초기화 셋팅
	 */
	ctrl.setView = function() {
		var self = et.vc;
		
		et.makeSelectOption(self.groupList, {value:"group_cd", text:"view_group_name"}, "#selGroupList", "고객사전체");  // 고객사 선택
		et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, "#selCenterList", "센터전체");
	};
	
	/**
	 * 폼에 validator 세팅
	 */
	ctrl.setValidate = function() {
		var self = et.vc;
	};
	
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selCenterList").change(self.selGroupListChangeHandler);
		
		$("#btnSearch").trigger("click");
	}
	
	// ============================== 이벤트 리스너 ==============================
	/**
	 * 검색 조건에서 고객사,센터,팀을 변경 했을때 발생하는 이벤트
	 */
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		var dataList = $(this).find("option:selected").data().children;
		if ($(this).prop("id") === "selGroupList") {
			et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, "#selCenterList", "센터전체");
		}
	}
	
	
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var params = {};
			params.group_id = $("#selGroupList").find("option:selected").data("group_id");
			params.tenant_id = $("#selCenterList").find("option:selected").data("tenant_id");
			new ETService().setSuccessFunction(self.searchSuccessHandler).callService(self.path + "/getRecordRemoveList", params);
		}
	}
	
	/**
	 * 검색이 완료 되었을 때 발생
	 */
	ctrl.searchSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			self.createRecordRemoveList(result.data);
		}
	}
	
	/**
	 * 저장버튼 클릭 했을 때 발생
	 */
	ctrl.btnSaveHandler = function () {
		var self = et.vc;
		
		var pDIV = $(this).parents(".bxCriticalColWrap");
		
		var params = {};
		params.tenant_id = pDIV.data("tenant_id");
		params.run_time = pDIV.find("select option:selected").text();
		params.period_month = pDIV.find(":text")[0].value;
		params.period_day = pDIV.find(":text")[1].value;
		params.is_use = pDIV.find(":radio:checked").val();
		new ETService().setSuccessFunction(self.saveSuccessHandler).callService(self.path + "/setRecordRemove", params);
	};
	
	ctrl.saveSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#btnSearch").trigger("click");
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
		}
	}
	
	
	// ============================== 기본 유틸 ==============================
	/*
	 * 서비스 레벨 DIV 을 만들어준다
	 */
	ctrl.createRecordRemoveList = function (list) {
		var self = et.vc;
		if (0 < list.length) {
			$(".contRightNoData").hide();
		} else {
			$(".contRightNoData").show();
		}
		$("#divRecordRemove").empty();
		
		$.each(list, function(index, item) {
			var pDIV = $('<div class="bxWall bxCriticalColWrap type3"></div>');
			var hCenter = $('<h6>'+item.view_group_name+'</h6>');
//			var runTimeDIV = $('<div class="formWrap"><label class="formLabel">실행 시간</label></div>');
//			var selectDIV = self.createRunTime(index, item);
			
			var periodDIV = $('<div class="formWrap"><label class="formLabel">보관 기간</label><div class="formInput"><div class="input-group"></div></div><div>');
			var input1 = $('<input type="text" value="'+item.period_month+'" maxLength="3"><span class="input-group-addon">개월</span>');
			var input2 = $('<input type="text" value="'+item.period_day+'" maxLength="2"><span class="input-group-addon">일</span>');
			
			var checkedY = "";
			var checkedN = "";
			if (item.is_use === "Y") {
				checkedY = 'checked="checked"';
			} else {
				checkedN = 'checked="checked"';
			}
			var useDIV = $('<div class="formWrap"><label class="formLabel">사용 유무</label><div class="formInput"><div class="formInput"><div class="formInput-inline"></div></div><div></div>');
			var ra1 = $('<label class="ecoleRadio"><input type="radio" value="Y" name="radio'+index+'" '+checkedY+'><i></i>사용함</label>');
			var ra2 = $('<label class="ecoleRadio"><input type="radio" value="N" name="radio'+index+'" '+checkedN+'><i></i>사용 안함</label>');
			
			var footerDIV = $('<div class="foot"></div>');
			var saveBtn = $('<button type="button" class="btnMain '+et.common.RULL_CLASS.IS_UPDATE+'">저장하기</button>');
			saveBtn.click(self.btnSaveHandler);
			
//			runTimeDIV.append(selectDIV);
			periodDIV.find('.input-group').append(input1).append(input2);
			useDIV.find('.formInput-inline').append(ra1).append(ra2);
			footerDIV.append(saveBtn);
			
			pDIV.data(item);
			
			pDIV.append(hCenter).append(periodDIV).append(useDIV).append(footerDIV);
//			pDIV.append(hCenter).append(runTimeDIV).append(periodDIV).append(useDIV).append(footerDIV);
			$("#divRecordRemove").append(pDIV);
		});
		
		et.common.btnRuleSetting();
	};
	
	/**
	 * 라디오 버튼 생성
	 */
	ctrl.createRunTime = function (radioIndex, item) {
		var self = et.vc;
		
		var pDIV = $('<div class="formInput"><div class="input-group"></div></div>');
		var hour = $("<select></select>");
		var month = $("<select></select>");
		var minute = $("<select></select>");
		
		var i = 0;
		for (i = 0; i < 24; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : i,text : txt}
			var option = $('<option>', data);
			hour.append(option);
		}
		for (i = 0; i < 60; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : i,text : txt}
			var option1 = $('<option>', data);
			var option2 = $('<option>', data);
			month.append(option1);
			minute.append(option2);
		}
		
		pDIV.find(".input-group").append(hour);
		pDIV.find(".input-group").append('<span class="input-group-addon">:</span>');
		pDIV.find(".input-group").append(month);
		
		if (item.run_time !== null) {
			hour.val(Number(item.run_time.substring(0,2)));
			month.val(Number(item.run_time.substring(2,4)));
		}
		
		return pDIV;
	}
	
	
	return ctrl;
}));