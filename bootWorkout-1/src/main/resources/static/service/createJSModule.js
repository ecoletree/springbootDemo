(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "createJSModule") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	"use strict";

	var ctrl = {};

	ctrl.name = "createJSModule";
	ctrl.path = "/";
	ctrl.groupList= null;
	ctrl.optionList = {};
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.template1();
		self.template2();
		self.boxTemplate();
		$("#btnJSModuleTest").click(self.btnJSModuleTestHandler);

	};
	/**
	 * sse emitter 수동 편집 화면으로 이동
	 */
	ctrl.btnJSModuleTestHandler = function(){
		var self = et.vc;
		new ETService().callView(self.path + "jsModuleTest", {});
	}
	ctrl.template1 = function(){
		var self = et.vc;
		var template = $('#templete1').html();
		Mustache.parse(template);

		var data = {
			name: "Luke",
			age: 15
		};

		var rendered = Mustache.render(template, data);
		$('#result1').html(rendered);
	}

	ctrl.template2 = function(){
		var template = $('#templete2').html();
		Mustache.parse(template);

		var data =
		{
		  "peoples": [
			{
			  "name": "Moe",
			  "age":13
			},
			{
			  "name": "Larry",
			  "age":16

			},
			{
			  "name": "Curly",
			  "age":21
			},
			{
			  "name": "Jusun",
			  "age":36
			}
		  ]
		};

		var rendered = Mustache.render(template, data);
		$('#result2').html(rendered);
	}

	ctrl.boxTemplate = function(){
		// 데이터
		var data = {
			"dataSets":[
				{ name: '홍길동', gender: '남성', phone: '010-1234-5678' ,color:"red"},
		  		{ name: '김영희', gender: '여성', phone: '010-9876-5432' ,color:"black"},
		  		]
		};

		// 템플릿 가져오기
		var template = $('#boxTemplate2').html();

		// DOM에 추가
		var rendered = Mustache.render(template, data);
		$('#container').html(rendered);
	}

	return ctrl;
}));