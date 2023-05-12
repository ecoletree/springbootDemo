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
	ctrl.JWT = null;
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function() {
		var self = et.vc;
		$("#btn").click(self.clickHandler);
		$("#btn1").click(self.clickHandler1);
		$("#btn2").click(self.clickHandler2);
		$("#btnList").click(self.btnListHandler);
		$("#btnList2").click(self.btnListHandler);
		$("#btnFile").click(self.fileUpload);
		
		
         debugger;
		self.createDataGrid();
	};
	
	ctrl.fileUpload = function(){
		var fileInput = document.querySelector("#txtFile");
		 var ajaxOption = {
                    contentType: false,
                    processData: false
                };
         var formData = new FormData();
         formData.append("file",fileInput.files[0]);
		 new ETService().setSuccessFunction(function(result){
			 debugger;
		 }).callService("/upload", formData, ajaxOption);
	}
	
	ctrl.createDataGrid = function(){
		var grid = canvasDatagrid();
		$("#divGrid").append(grid);
		grid.data = [
		  { col1: 'row 1 column 1', col2: 'row 1 column 2', col3: 'row 1 column 3' },
		  { col1: 'row 2 column 1', col2: 'row 2 column 2', col3: 'row 2 column 3' },
		];
	}
	
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
		var params = {code_list:code_list, password:btoa("ccc12345!!")};
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
				alert(data);
			},
			error : function (a,b,c) {
				debugger;
			}
		});  //ajax
	}
	
	// ============================== 동작 컨트롤 ==============================
	
	return ctrl;
}));