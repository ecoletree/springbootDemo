/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : kkh
 * @CreateDate : 2022. 07. 14.
 * @DESC : [환경설정]  임계점 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "criticalSetting") {
			et.vc= ctrl(et);
		}
		
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "criticalSetting";
	ctrl.path = "/settings/critical";
	ctrl.editFormId = "#editForm";
	ctrl.addFormId = "#addForm";
	ctrl.tableId = "#tbList";
	ctrl.groupType = "center";
	ctrl.rowData = null;
	ctrl.groupList = null;
	ctrl.colorList = null;
	ctrl.timeList = null;
	ctrl.search_center ="";				
	ctrl.rullBtnSetting = {
				is_update : "#btnSave" 
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
		self.userInfo = initData.userInfo;
		et.common.CODE_LIST = initData.codeList;
		self.colorList = et.common.getCodeList("SC000"); // 서비스 레벨 코드
		self.addEventListener();
		self.setView();
		
		$("#btnSearch").trigger("click");
	};
	
	ctrl.setView = function(){
		var self = et.vc;
		var groupList = self.groupList;
		self.timeList = self.setTimeList();
		et.makeSelectOption(groupList, {value:"group_id", text:"view_group_name"}, "#selGroupList", "");
		et.makeSelectOption(self.timeList, {value:"time", text:"time_view"}, ".seltimeList", "");
		
		$("#selGroupList").trigger("change");
	}
	


	// ============================== 동작 컨트롤 ==============================
	/**
	 * 폼에 validator 세팅
	 */
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnSave").click(_.debounce(self.btnSaveHandler,500));
		$("#selGroupList").change(self.selGroupListChangeHandler);
		$("#selCenterList").change(self.selGroupListChangeHandler);
		
		
	}
	
	// ============================== 이벤트 리스너 ==============================

	/**
	 * 검색 조건에서 고객사,센터,팀을 변경 했을때 발생하는 이벤트
	 */
	ctrl.selGroupListChangeHandler = function () {
		var self = et.vc;
		if ($(this).prop("id") === "selGroupList") {
			var dataList = $(this).find("option:selected").data()?.children ?? [];
			et.makeSelectOption(dataList, {value:"tenant_id", text:"view_group_name"}, "#selCenterList", "");
			$("#selCenterList").trigger('change');
		}else{
			if($("#selCenterList").val() === null){
				$(".contRightNoData").show();
			}else{
				$(".contRightNoData").hide();
			}
		}
		
	}
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			var tenant_id = $("#selCenterList").val();
			self.search_center = $("#selCenterList option:checked").data();
			if(tenant_id !== null){
				var params = {};
				params.tenant_id = tenant_id ;
				$('#divProcess').show();
				new ETService().setSuccessFunction(self.getCriticalSuccessHandler).callService(self.path + "/getCriticalStatus", params);
							
			}
		}
	}
	
	ctrl.getCriticalSuccessHandler = function(result){
		var self = et.vc;
		if(result.message === ETCONST.SUCCESS){
			$(".contRightNoData").hide();
			$('#divProcess').hide();
			$("#pCenterName").empty();
			var center = self.search_center; 
			var center_name = '조회 된 센터: <span>'+center.group_name+'</span>(<span>'+center.tenant_id+'</span>)';
			$("#pCenterName").append(center_name);
			
			var status = result.data;
			self.createFormwrap(status);
			$(".selEndMin, .selEndSec").change(self.startEndChangeHandler);
			$(".iptEndCount").on("change",self.countRateChangeHandler);
			$(".bxWUnset").on("change",self.colorSampleChangeHandler);
			$(".highlighCheck").on("change",self.highlightChangeHandler);
			$(".iptEndCount").trigger("change");
			$(".selEndMin").trigger("change");
			$(".bxWUnset").trigger("change");
			
			
		}
	}
	ctrl.countRateChangeHandler = function(){
		var self = et.vc;
		var codecd = $(this).data("code");
		var cnt = $(this).val();
		$("#iptStartCount"+codecd).val(Number(cnt)+1);
	}
	/*
	 * 색상표 하이라이트 여부 
	 */	
	ctrl.highlightChangeHandler = function(e){
		var code = $(this).data('code');
		var id = '#divColorPic'+code;
		var chk = $(this).is(':checked');
		
		$(id).removeClass('highlight');
		if($(this).is(':checked') === true){
			$(id).addClass('highlight');
		}
		
	}
	/*
	 * 색상표 변경
	 */
	ctrl.colorSampleChangeHandler = function(e){
		var color = $(this).val();
		var code = $(this).data('code');
		var id = '#divColorPic'+code;
		$(id).attr('class','bxColorSample '+color);
		$("#chkhighlight"+code).attr("disabled",false);
		if(color === null || color === ""){
			$("#chkhighlight"+code).attr("disabled",true);
			$("#chkhighlight"+code).prop("checked",false);
		}
		$(".highlighCheck").trigger("change");
	}
	/*
	 * 시간 변경 
	 */
	ctrl.startEndChangeHandler = function (e){
		var codecd = $(this).data("code");
		var min = Number($("#selEndMin"+codecd).val()); 
		var sec = Number($("#selEndSec"+codecd).val());
		var tot = (min * 60) + sec+1;
		var new_min =Math.trunc( tot / 60);
		var new_sec = tot % 60;
		 
		var sel = $(this).parents('.bxCriticalColWrap').find('select[name='+codecd+']');
		
		$.each(sel,function(index,elem){
			if (index === 0) {
				$(elem).val(new_min);
			} else {
				$(elem).val(new_sec);
			}
		});
	}
	/*
	 * formwrap 생성
	 */
	ctrl.createFormwrap = function(data){
		var self = et.vc;
		var commonsp1 = '<span>이상</span>';
		var commonsp2 = '<span>~</span>';
		for(var i in data){
			var p_code = data[i].code_cd;
			var type = data[i].status_type;
			var colWrap = "#div"+p_code;
			$(colWrap).empty();
			$(colWrap).append('<h6>'+data[i].code_name+'</h6>'); 
			var child = data[i].children;
			var preVal = 0;
			var preCd = '';
			
			
			for(var j in child){
				var nowVal = child[j].code_value;
				var nowCd = child[j].code_cd;
				var formWrap = $('<div class="formWrap"></div>');
				formWrap.data('code',nowCd)
				formWrap.data('type',type)
				var sMin = "selStartMin";
				var sSec = "selStartSec";
				var eMin = "selEndMin";
				var eSec = "selEndSec";
				var selectDiv = $('<div class="formInput bxMinW302"></div>');
				
				if(type === "time"){
					var sDiv = $('<div class="input-group"></div>');
					var sSp = $('<span class="input-group-addon">:</span>');
					var sSel1 = $('<select disabled="disabled"></select>');
					var sSel2 = $('<select disabled="disabled"></select>');
					sSel1.prop("class",sMin);
					sSel1.prop("name",preCd);
					sSel1.data("code",preCd);
					sSel2.prop("class",sSec);
					sSel2.prop("name",preCd);
					sSel2.data("code",preCd);
					
					sDiv.append(sSel1).append(sSp).append(sSel2);
					
					var sp1 = $(commonsp1);
					
					var sp2 = $(commonsp2);

					
					if(nowVal !== null){
						
						var end_tm = Math.trunc( nowVal / 60);
						var end_min = nowVal % 60;
						
						var eDiv = $('<div class="input-group"></div>');
						var eSp = $('<span class="input-group-addon">:</span>');
						var eSel1 = $('<select></select>');
						var eSel2 = $('<select></select>');
						
						eSel1.prop("class",eMin);
						eSel1.prop("id",eMin + nowCd);
						eSel1.data("code",nowCd);
						eSel2.prop("class",eSec);
						eSel2.prop("id",eSec + nowCd);
						eSel2.data("code",nowCd);
						
						eDiv.append(eSel1).append(eSp).append(eSel2);
						selectDiv.append(sDiv).append(sp2).append(eDiv);
						
						
					}else{
						selectDiv.append(sDiv).append(sp1);
					}
					
				}else{
					var sptst = '<span class="input-group-addon">'+(type === "count" ?'호':'%')+'</span>';
					var div1 = $('<div class="input-group"></div>');
					var ipt1 = $('<input type="text" disabled="disabled" value="0">');
					ipt1.prop('id',"iptStartCount"+preCd);
					var sp1 = $(sptst);
					div1.append(ipt1).append(sp1);
					
					
					var spc1 =  $(commonsp1);
					var spc2 =  $(commonsp2);
					if(nowVal !== null && nowVal !== ""){
						var div2 = $('<div class="input-group"></div>');
						var ipt2 = $('<input type="text" maxLength="3" >');
						ipt2.prop('class',"iptEndCount");
						ipt2.prop('id',"iptEndCount"+nowCd);
						ipt2.data('code',nowCd);
						ipt2.val(nowVal);
						var sp2 = $(sptst);
						div2.append(ipt2).append(sp2);
						selectDiv.append(div1).append(spc2).append(div2);
					}else{
						selectDiv.append(div1).append(spc1);
						
					}
					
				}
				var colorDiv = self.colorStatusSetting(child[j]);
				
				formWrap.append(selectDiv).append(colorDiv);
				
				$(colWrap).append(formWrap);
				
				et.makeSelectOption(self.timeList, {value:"time", text:"time_view"}, "."+sMin ,"");
			 	et.makeSelectOption(self.timeList, {value:"time", text:"time_view"}, "."+sSec ,"");
		 		et.makeSelectOption(self.timeList, {value:"time", text:"time_view"}, "#"+eMin+nowCd,"",end_tm);
				et.makeSelectOption(self.timeList, {value:"time", text:"time_view"}, "#"+eSec+nowCd,"",end_min);
		 		et.makeSelectOption(self.colorList, {value:"code_value", text:"code_name"}, "#selColor"+nowCd, "색없음",child[j].color);

				preVal = nowVal;
				preCd = nowCd;
			}
		}
	}
	/*
	 * formwrap - color div 생성
	 */
	ctrl.colorStatusSetting = function(data){
		var nowCd = data.code_cd;
		
		var colorDiv = $('<div class="formInput bxMinW320"></div>');
		var color_pic = $('<div class="bxColorSample" id ="divColorPic'+nowCd+'"><div></div></div>');
		var color_sel = $('<select class="bxWUnset"></select>');
			color_sel.prop("id","selColor"+nowCd);
			color_sel.data("code",nowCd);
		
		var highlight = data.is_highlight === "Y" ? true : false;
		
		var label = $('<label class="ecoleCheck"><input type="checkbox" ><i></i>색강조 사용하기</label>');
		$(label).children('input[type="checkbox"]').prop('class',"highlighCheck");
		$(label).children('input[type="checkbox"]').prop('checked',highlight);
		$(label).children('input[type="checkbox"]').data('code',nowCd);
		$(label).children('input[type="checkbox"]').prop('id',"chkhighlight"+nowCd);

		colorDiv.append(color_pic).append(color_sel).append(label);
		
		return colorDiv;
	}
	/*
	 * 고객사 추가 버튼 클릭 핸들러
	 */
	ctrl.btnAddHandler = function () {
		var self = et.vc;
		
		self.setViewData(self.addFormId,{});
		$("#bxWrite").show();
	}
	
	/*
	 * 추가 버튼 클릭 핸들러
	 */
	ctrl.btnSaveHandler = function () {
		var self = et.vc;
//		$(self.addFormId).submit();
		
		var total = $(".bxTileWrap").find('.bxCriticalColWrap');
		var list = [];
		var fromto = 0;
		var blank = 0;
		var val_msg = "";
		$.each(total,function(index,element){
			if(element.id !== ""){
//				var text = $(element).find('h6').text();
				var child = $(element).find('.formwrap');
				var pre_val = 0;
				$.each(child,function(index,element){
					var code = $(element).data('code');
					var type = $(element).data('type');
					var value = null;
					if(type === "time"){
						var min = $("#selEndMin"+code).val();
						var sec = $("#selEndSec"+code).val();
						value = (Number(min)*60)+Number(sec)||null;	
					}else{
						var cnt = $("#iptEndCount"+code).val();
							if(cnt === ""){
								blank += 1; 	
							}
						value = Number($("#iptEndCount"+code).val())||null;
						
					}
					var row = {};
					row.tenant_id = $("#selCenterList").val();
					row.status_cd = code;
					
					row.set_value = value;
					row.color = $("#selColor"+code).val();
					row.is_highlight = !!$("#chkhighlight"+code).is(":checked") ? "Y" :"N";
					row.order_num = index;
					list.push(row);
					if(value!== null && value <= pre_val){
						fromto += 1;
					}
					pre_val = value;
				});
			}
			
		});
		var val_msg = "";
		if(fromto+blank > 0){
			val_msg += fromto > 0 ? et.message("validate.critical_fromto") :""; 
			val_msg += blank > 0? et.message("validate.critical_required"):"" ;
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", val_msg);
		}else{
			var params = {};
			params.list = list; 
			new ETService().setSuccessFunction(self.saveSuccessHandler).callService(self.path + "/setCriticalSettingMgt", params);
		}
		
	}

	// ============================== DataTables 생성, 이벤트들 ==============================
	// ============================== Form 리스너 ==============================
	
	
	/**
	 * 추가 및 수정완료시 발생하는 SucessHandler
	 */
	ctrl.saveSuccessHandler = function (result) {
		var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.success"));
			$("#btnSearch").trigger("click");
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
		}
	}
	/**
	 * 분,초 0~59
	 */
	ctrl.setTimeList = function(){
		var list = [];
		for(var i = 0; i <= 59 ; i++){
		var map = {};
			map.time = i;
			map.time_view = i <= 9 ? "0"+i : i.toString();
		list.push(map);
		}
		return list;
	}
	
	
	return ctrl;
}));