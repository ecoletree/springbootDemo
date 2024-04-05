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
		var self = et.vc;
		var list = [
				{ name: '홍길동', gender: '남성', phone: '010-1234-5678' ,color:"red"},
		  		{ name: '김영희', gender: '여성', phone: '010-9876-5432' ,color:"black"},
		  		];

		var rendered = self.renderTemplateData('#boxTemplate2',list);

		// DOM에 추가

		$('#container').html(rendered);

		// 각 사람 정보에 클릭 이벤트 추가
		$('.person-info').each(function(index) {
				var self = et.vc;
		    $(this).on('click', function() {
		        // 클릭된 사람 정보 출력
		        console.log('클릭된 사람 정보:',$(this).data("all")); // -> data-all에 담은 데이터 가져옴
				debugger;
		    });
		});

	}


	ctrl.renderTemplateData = function(templateID,list){

		// 배열 데이터를 json형태로 all에 저장
		var newList = [];
		list.forEach(function(obj){
			var jsonString = JSON.stringify(obj);
			var newObj = Object.assign({ all: jsonString }, obj);
        	newList.push(newObj);

		})

		var data = {
			"dataSets":newList,
		};

		// 렌더 해주기 전에 data-all 에 전체 데이터 추가
		$(templateID).children().attr('data-all', "{{all}}");
		var template = $(templateID).html();
		var rendered = Mustache.render(template, data);
		return rendered

	}

// data에 반복 돌려서 하는 방법
//			var data = [
//		  { title: 'Title 1', content: 'Content 1', className: 'class1' },
//		  { title: 'Title 2', content: 'Content 2', className: 'class2' },
//		  // 추가 데이터...
//		];
//
//		// 템플릿 가져오기
//		var template = document.getElementById('template').innerHTML;
//
//		// DOM에 추가
//		var container = document.getElementById('container');
//		data.forEach(function(item) {
//		  // 템플릿 렌더링
//		  var rendered = Mustache.render(template, item);
//		  // DOM에 추가
//		  container.innerHTML += rendered;
//		});
// 각 사람 정보에 클릭 이벤트 추가
//$('.person-info').each(function(index) {
//    $(this).on('click', function() {
//        // 클릭된 사람 정보 출력
//        console.log('클릭된 사람 정보:', data[index]);
//    });
//});
	return ctrl;
}));