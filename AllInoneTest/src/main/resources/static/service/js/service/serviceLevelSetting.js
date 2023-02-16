/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2022. 05. 04
 * @DESC : [관리자웹]  서비스 레벨 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "serviceLevelSetting") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "serviceLevelSetting";
	ctrl.path = "/settings/serviceLevel";
	ctrl.groupList = null;
	ctrl.codeList = null;
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.groupList = initData.groupList;
		et.common.CODE_LIST = initData.codeList; // global
		self.codeList = et.common.getCodeList("SL000"); // 서비스 레벨 코드 
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
			new ETService().setSuccessFunction(self.searchSuccessHandler).callService(self.path + "/getServiceLevelList", params);
		}
	}
	
	/**
	 * 검색이 완료 되었을 때 발생
	 */
	ctrl.searchSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			self.createServiceLevelDIV(result.data);
		}
	}
	
	/**
	 * 저장버튼 클릭 했을 때 발생
	 */
	ctrl.btnSaveHandler = function () {
		var self = et.vc;
		
		var pDIV = $(this).parents(".bxCriticalColWrap");
		pDIV.find(":text").val();
		
		var params = {};
		params.tenant_id = pDIV.data("tenant_id");
		params.tenant_name = pDIV.data("group_name");
		params.service_code = pDIV.find(":radio:checked").val();
		params.service_time = pDIV.find(":text").val();
		
		if (isNaN(Number(params.service_time))) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("validate.only_num", {name:"기준 시간"}));
			return;
		}
		
		new ETService().setSuccessFunction(self.saveSuccessHandler).callService(self.path + "/setServiceLevel", params);
	};
	
	ctrl.saveSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
		}
	}
	
	
	// ============================== 기본 유틸 ==============================
	/*
	 * 서비스 레벨 DIV 을 만들어준다
	 */
	ctrl.createServiceLevelDIV = function (list) {
		var self = et.vc;
		if (0 < list.length) {
			$(".contRightNoData").hide();
		} else {
			$(".contRightNoData").show();
		}
		$("#divServiceLevel").empty();
		
		$.each(list, function(index, item) {
			var pDIV = $('<div class="bxWall bxCriticalColWrap type2"></div>');
			var hCenter = $('<h6></h6>');
			var codeDIV = $('<div class="formWrap"><label class="formLabel">S.L 구현식</label></div>');
			var radioDIV = self.createServiceLevelCode(index);
			
			var timeDIV = $('<div class="formWrap"><label class="formLabel">기준 시간</label><div class="formInput"><div class="formInput"><div class="input-group"></div></div><div></div>');
			var input = $('<input type="text" value="'+item.service_time+'" maxLength="4"><span class="input-group-addon">초</span>');
			
			et.setNumberKeyPressEvent(input.first());
			
			var footerDIV = $('<div class="foot"></div>');
			var saveBtn = $('<button type="button" class="btnMain">저장하기</button>');
			saveBtn.click(self.btnSaveHandler);
			
			codeDIV.append(radioDIV);
			timeDIV.find('.input-group').append(input);
			footerDIV.append(saveBtn);
			
			pDIV.data(item);
			hCenter.text(item.view_group_name);
			codeDIV.find(':radio[name="level'+index+'"][value="'+item.service_code+'"]').prop('checked', true);
			
			pDIV.append(hCenter).append(codeDIV).append(timeDIV).append(footerDIV);
			$("#divServiceLevel").append(pDIV);
			index++;
		});
	};
	
	/**
	 * 라디오 버튼 생성
	 */
	ctrl.createServiceLevelCode = function (radioIndex) {
		var self = et.vc;
		
		var pDIV = $('<div class="formInput"></div>'); 
		$.each(self.codeList, function(index, code) {
			var cDIV = $('<div class="formInput"></div>');
			var radio = $('<label class="ecoleRadio"><input type="radio" name="level'+radioIndex+'" value="'+code.code_cd+'"><i></i>'+code.description+'</label>');
			cDIV.append(radio);
			pDIV.append(cDIV);
		});
		
		return pDIV;
	}
	
	
	return ctrl;
}));