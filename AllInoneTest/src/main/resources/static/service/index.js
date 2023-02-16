/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2019. 03. 04.
 * @DESC : [관리자웹] 로그인
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "home") {
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
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function() {
		var self = et.vc;
		$("#btn").click(self.clickHandler);
		$("#btnList").click(self.btnListHandler);
		$("#btnList2").click(self.btnListHandler);
		
		
	};
	
	ctrl.clickHandler = function() {
		et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("button.delete_faile"));
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
	
	return ctrl;
}));