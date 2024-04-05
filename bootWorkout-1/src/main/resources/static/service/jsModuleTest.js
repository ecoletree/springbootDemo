(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "jsModuleTest") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	"use strict";

	var ctrl = {};

	ctrl.name = "jsModuleTest";
	ctrl.path = "/";
	ctrl.groupList= null;
	ctrl.optionList = {};
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.boxTemplate();

	};

	ctrl.boxTemplate = function(){
		// 데이터
		var data = {
			"dataSets":[
				{ name: '네이버', gender: '여성', phone: '010-1234-5678' ,color:"blue"},
		  		{ name: '카카오', gender: '남성', phone: '010-9876-5432' ,color:"green"},
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