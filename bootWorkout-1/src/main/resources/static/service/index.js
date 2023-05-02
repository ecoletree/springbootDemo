/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2019. 03. 04.
 * @DESC : [관리자웹] 로그인
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "index") {
			et.vc= ctrl(et);
		}
		var inter = setInterval(function(){
			et.promiseInit().then(function(){
				et.vc.init();
				clearInterval(inter);
			}, function (){})
		},300);
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "home";
	ctrl.path = "/login";
	ctrl.formId = "#loginForm";
	ctrl.JWT = null;
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function() {
		var self = et.vc;
		debugger;
		$("#btn").click(self.clickHandler);
		$("#btn1").click(self.clickHandler1);
		$("#btn2").click(self.clickHandler2);
		$("#btnList").click(self.btnListHandler);
		$("#btnList2").click(self.btnListHandler);
		
		
	};
	
	ctrl.clickHandler = function() {
		et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_faile"));
	}
	
	ctrl.clickHandler1 = function() {
		var self = et.vc;
		var params = {email:$("#iptId").val(), password:$("#iptPw").val()};
		var url = "/members/login";
		new ETService().setSuccessFunction(function(result){
			if (result.message === ETCONST.SUCCESS) {
				self.JWT = result.data.accessToken + "33";
			}
			debugger;
		}).callService(url, params);
	}
	
	ctrl.clickHandler2 = function() {
		var self = et.vc;
		var params = {email:$("#iptId").val(), password:$("#iptPw").val()};
		var url = "/members/test";
		if (self.JWT != null) {
//			new ETService().setSuccessFunction(function(result){
//				debugger;
//			}).callService(url, params, {headers: {Authorization: "Bearer "+self.JWT}});
			
			$.ajax({
				url : "/demo"+url,
				type : "POST",
				data : JSON.stringify(params) ,
				dataType: 'json',
				contentType : "application/json; charset=UTF-8",
				headers: {Authorization: "Bearer "+self.JWT},
				success : function (data) {
					debugger;
				},
				error : function (a,b,c) {
					debugger;
				}
			});  //ajax
			
			
		}
		
	}
	
	ctrl.btnListHandler = function(e) {
		var self = et.vc;
		var code_list = [{code_cd:"ANI001"},{code_cd:"ANI002"},{code_cd:"ANI003"},{code_cd:"ANI004"}];
		var params = {code_list:code_list};
		var url = "/demo/getList";
//		new ETService().setSuccessFunction(function(result){
//		}).callService("/getList", params);
		if ($(this).prop("id") === "btnList") {
			url = "/demo/getList";
		} else {
			url = "/demo/getList2";
		}
		$.ajax({
			url : url,
			type : "POST",
			data : JSON.stringify(params) ,
			dataType: 'text',
			contentType : "application/json; charset=UTF-8",
			success : function (data) {
				debugger;
			},
			error : function (a,b,c) {
				debugger;
			}
		});  //ajax
	}
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * input select에 동적으로 option을 만들어줌
	 * 
	 * @param {array} aDataList option으로 보여질 데이터 리스트
	 * @param {object} oOptionKey option에 value와 text 값에 대응하는 필드명을 담고 있음, value와 text로 고정되어 있음
	 * @param {string} sTargetID option이 들어가게 될 select의 id 값, #selectID 형태
	 * @param {string} sFirstOptionText option 맨 위에 디폴트 값이 있는 경우 보여질 text를 넘김
	 */
	ctrl.makeSelectOption = function(aDataList, oOptionKey, sTargetID, sFirstOptionText, sValue) {
		if (!sTargetID) {
			return;
		}
		debugger;
		$(sTargetID).empty();
		
		if (!!aDataList) {
			var option;

			if (!!sFirstOptionText && sFirstOptionText != "") {// 기본설정값이 있으면 그걸 text로
				option = $('<option>', {
					value : "",
					text : sFirstOptionText,
					selected : ""
				});
				$(sTargetID).append(option);
			}
			
			for (var i = 0; i < aDataList.length; i++) {// 옵션키에 대한거 
				option = $('<option>', {
					value : aDataList[i][oOptionKey.value],
					text : aDataList[i][oOptionKey.text]
				});
				option.data(aDataList[i]);
				$(sTargetID).append(option);
			}
			
			if (sValue) {// 선택된 value가 있을때 (수정화면)
				$(sTargetID).val(sValue);
				$(sTargetID).trigger("change");
			}
		} else {
			if (!!sFirstOptionText) {
				var option = $('<option>', {
					value : "",
					text : sFirstOptionText,
					selected : ""
				});
				$(sTargetID).append(option);
			} 
		}
	};
	return ctrl;
}));