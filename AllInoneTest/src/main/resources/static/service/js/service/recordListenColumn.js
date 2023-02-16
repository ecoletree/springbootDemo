/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 11. 15
 * @DESC : [녹취관리]  녹음 청취 조회 컬럼 설정
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "recordListenColumn") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "recordListenColumn";
	ctrl.path = "/record/listenColumn";
	ctrl.groupList = null;
	ctrl.codeList = null;
	ctrl.labelNames = [
			{column_id : 1,    column_name:"시작 시각"}
			,{column_id : 2,   column_name:"종료 시각"}
			,{column_id : 3,   column_name:"통화 시간"}
			,{column_id : 4,   column_name:"In/Out"}
			,{column_id : 5,   column_name:"녹취ID"}
			,{column_id : 6,   column_name:"내선 번호"}
			,{column_id : 7,   column_name:"상태"}
			,{column_id : 8,   column_name:"Logger ID"}
			,{column_id : 9,   column_name:"Optional1"}
			,{column_id : 10 , column_name:"Optional2"}
			,{column_id : 11 , column_name:"Optional3"}
			,{column_id : 12 , column_name:"Optional4"}
			,{column_id : 13 , column_name:"Optional5"}
			,{column_id : 14 , column_name:"Optional6"}
			,{column_id : 15 , column_name:"Optional7"}
			,{column_id : 16 , column_name:"Optional8"}
			,{column_id : 17 , column_name:"Optional9"}
			,{column_id : 18 , column_name:"Optional10"} 
			,{column_id : 19 , column_name:"Optional11"} 
			,{column_id : 20 , column_name:"Optional12"} 
			,{column_id : 21 , column_name:"Optional13"} 
			,{column_id : 22 , column_name:"Optional14"} 
			,{column_id : 23 , column_name:"Optional15"} 
			,{column_id : 24 , column_name:"Optional16"} 
			,{column_id : 25 , column_name:"Optional17"} 
			,{column_id : 26 , column_name:"Optional18"} 
			,{column_id : 27 , column_name:"Optional19"} 
			,{column_id : 28 , column_name:"Optional20"} 
			,{column_id : 29 , column_name:"고객사명"} 
			,{column_id : 30 , column_name:"센터명"} 
			,{column_id : 31 , column_name:"팀명"} 
			,{column_id : 32 , column_name:"상담사명"} 
	]
	
	ctrl.rullBtnSetting = {
				is_update : "#btnSave" 
	}
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		et.common.btnRuleClassSetting(self.rullBtnSetting);
		et.common.btnRuleSetting();
		self.maskingCode = initData.maskingCode;
		self.setView();
		self.addEventListener();

	};
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 초기화 셋팅
	 */
	ctrl.setView = function() {
		var self = et.vc;
		new ETService().setSuccessFunction(self.getRecordColumnSuccessHandler).callService(self.path + "/getRecordColumnList", {});
		
	};
	/**
	 * 
	 */
	ctrl.addEventListener = function() {
		var self = et.vc;
		$("#btnSave").click(_.debounce(self.btnSaveHandler,500));
		
		
	};
	ctrl.maskingTypeChangeHandler = function(){
		var self = et.vc;
		var selectedData = $(this).find("option:selected")?.data() ?? [];
		$(this).parent().find(".note").empty();
		if(Object.keys(selectedData).length > 0){
			$(this).parent().find(".note").append(selectedData.description);
		}
		
	}
	
	/**
	 * 컬럼 리스트 
	 */
	ctrl.getRecordColumnSuccessHandler = function(result){
		var self = et.vc;
		if(result.message === ETCONST.SUCCESS){
			var columns = result.data;
			self.columnList = columns;
			if(columns.length !== 0){
				self.setColumnView(columns);
				$(".masking_type_select").on("change",self.maskingTypeChangeHandler);
				$(".masking_type_select").trigger("change");
			}
		}
	}
	/**
	 * 초기화 셋팅
	 */
	ctrl.setColumnView = function(columns){
		var self = et.vc;
		$('.bxTileWrap').empty();
		var labelNames = self.labelNames;
		var rowWrap = $('<div class="bxCriticalRowWrap"></div>');
		
		for(var i in columns){
			
			var col_id = columns[i].column_id;
			var colWrap = $('<div class="bxWall bxCriticalColWrap type2" id="'+col_id+'"></div>');
			var col_name = "";
			for(var j in labelNames){
				if(col_id === labelNames[j].column_id)col_name = labelNames[j].column_name
			}
			var div_name = $('<div><h6>'+col_name+'</h6></div>');
			var div_content = $('<div></div>');
			var formWrap = $('<div class="formWrap"></div>');
			var header_label = $('<label>컬럼 헤더 명</label>');
			var header_ipt = $('<input type="text" name="col_name" value="'+columns[i].col_name+'" maxLength="32">');
			formWrap.append(header_label).append(header_ipt);
			
			/** user_data */
			var maskingWrap = $('');
			var switchWrap = $('');
			if(columns[i].rec_column.includes('user_data')){
				
				/** 마스킹 타입 */
				maskingWrap = $('<div class="formWrap"></div>');
				var div_select = $('<div></div>');
				var masking_label = $('<label>마스킹 타입</label>');
				var masking_select = $('<select class="masking_type_select" id="masking_'+col_id+'" name="masking_type"><option>마스킹 없음</option></select>');
				var selectId = "masking_"+col_id;
				var masking_span = $('<span class="note"></span>');
				div_select.append(masking_select).append(masking_span);
				maskingWrap.append(masking_label).append(div_select)
				
				/** 사용유무 */
				var switchWrap = $('<div class="formWrap switch"></div>');
				var use_label = $('<label>사용유무</label>');
				var switch_div = $('<div class="custom-switch"></div>');
				var switch_ipt = $('<input name="is_use" type="checkbox" class="custom-control-input" id="swtich_'+col_id+'">');
				var check = columns[i].is_use === "Y" ? true : false
				$(switch_ipt).prop("checked",check);
				var switch_label = $('<label class="custom-control-label" for="swtich_'+col_id+'"></label>');
				switch_div.append(switch_ipt).append(switch_label);
				switchWrap.append(use_label).append(switch_div);
			}
			
			
			div_content.append(formWrap).append(maskingWrap).append(switchWrap);
			colWrap.append(div_name).append(div_content);
			
			$(rowWrap).append(colWrap);
			
			$('.bxTileWrap').append(rowWrap);
			et.makeSelectOption(self.maskingCode, {value:"code_cd", text:"code_name"}, "#"+selectId, "마스킹 없음",columns[i].masking_type);
		}
	}
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSave").click(self.btnSaveHandler);
		
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selCenterList").change(self.selGroupListChangeHandler);
	}
	// ============================== 이벤트 리스너 ==============================
	
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSaveHandler = function () {
		var self = et.vc;
		var total = $(".bxTileWrap").find('.bxCriticalColWrap');
		var list = [];
		
		$.each(total,function(index,element){
			var column = {};
			var masking_type = $(element).find(".masking_type_select").val()||null;
			var col_name = $(element).find("input[name='col_name']").val();
			var is_use = "Y";
			if($(element).find("input[name='is_use']").length != 0){
				var use_chk = $(element).find("input[name='is_use']:checked").val();
				is_use = use_chk === "on" ? "Y" :"N";
			}
			column.column_id = element.id;
			column.col_name = col_name;
			column.masking_type = masking_type;
			column.is_use = is_use;
			list.push(column);
		});
		
		var params = {};
		params.list = list; 
		
		new ETService().setSuccessFunction(self.setRecordColumnListSuccessHandler).callService(self.path + "/setRecordColumnList", params);
	}
	ctrl.setRecordColumnListSuccessHandler = function(result){
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			self.setView();
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
		}
		
	}
	
	
	// ============================== 기본 유틸 ==============================
	
	
	return ctrl;
}));