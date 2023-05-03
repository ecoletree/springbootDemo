(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "home") {
			et.vc= ctrl(et);
		}
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
	ctrl.groupList= null;
	ctrl.optionList = {};
	ctrl.selectedList = {};
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.groupList = initData.groupList;
		
//		self.setOption(["selGroup","selGroup2"]);
		self.selectChangeHandler();
		$("#btnEdit").click(self.btnEditHandler);
		$("#btnClear,#btnClear2").click(self.btnClearHandler);
		$("#selGroup,#selCenter").change(self.selectChangeHandler);
		$("#selGroup2,#selCenter2").change(self.selectChangeHandler);
		
		
	};
	
	
	/**
		1. 초기화
	 */
	ctrl.btnClearHandler = function(){
		var self = et.vc;
		var groupname = $(this).data("groupname");
		var groupList = self.groupList;
		self.selectedList[groupname] = [];
		var options = self.setSValue(groupname);
		self.makeTreeSelectOption(groupList,options);
	}
	/**
		1. 수정 
	 */
	ctrl.btnEditHandler = function(){
		var self = et.vc;
		var groupList = self.groupList;
		var sVal =[];
		var groupname = "selGroup2";
		$('input[data-groupname=iptGroup]').each(function(index,element){
			sVal.push(element.value);
		});
		self.selectedList[groupname] = sVal;
		var options = self.setSValue(groupname);
		
		self.makeTreeSelectOption(groupList,options);
		
	}
	/**
		리스트, 셀렉트 이름으로 옵션에 sValue 설정
	 */
	ctrl.setSValue = function(groupname){
		var self = et.vc;
		var list = self.selectedList[groupname];
		var options = self.optionList[groupname];
		Object.keys(options).forEach(function(v){
			options[v].sValue = list[v]??"";
		})
		return options;
	}
	
	ctrl.selectChangeHandler = function(){
		var self = et.vc;
		
		var groupList = self.groupList;
	
		
		var options = [];
		var option1 = {value:"group_id", text:"view_group_name", targetID:"#selGroup",default:"그룹전체"};
		var option2 =  {value:"tenant_id", text:"view_group_name", targetID:"#selCenter", default:"센터전체"};
		var option3 = {value:"team_id", text:"view_group_name", targetID:"#selTeam", default:"팀전체"};
		
		options.push(option1,option2,option3);
		
		var options2 = [];
		
		var option4 = {value:"group_id", text:"view_group_name", targetID:"#selGroup2",default:"그룹전체"};
		var option5 =  {value:"tenant_id", text:"view_group_name", targetID:"#selCenter2", default:"센터전체"};
		var option6 = {value:"team_id", text:"view_group_name", targetID:"#selTeam2", default:"팀전체"};
		
		options2.push(option4,option5,option6);
		
		self.optionList["selGroup"] = options;
		self.optionList["selGroup2"] = options2;
		
		self.makeTreeSelectOption(groupList,options);
		self.makeTreeSelectOption(groupList,options2);
	}
	
	// ============================== 동작 컨트롤 ==============================
	ctrl.makeTreeSelectOption = function(aDataList,oOptionList){
		var targetData = aDataList;
		var option;
		for(var i in oOptionList){
			var targetID = oOptionList[i].targetID;
			
			var sValue = oOptionList[i]?.sValue ?? $(targetID).val();
			$(targetID).empty();
			
			var optionValue = oOptionList[i].value; 
			var optionText = oOptionList[i].text;
			var optionDefault = oOptionList[i].default; 
			
			/**
			 * default text 설정 
			 */
			if (!!optionDefault && optionDefault != "") {
				option = $('<option>', {
					value : "",
					text : optionDefault,
					selected : ""
				});
				$(targetID).append(option);
			}
			
			/**
			 * 옵션 값 설정
			 */
			var optionValues = [];
			if(!!targetData){
				for(var j in targetData){
					option = $('<option>', {
						value : targetData[j][optionValue],
						text : targetData[j][optionText],
					});
					option.data(targetData[j]);
					$(targetID).append(option);
					optionValues.push(targetData[j][optionValue].toString());
				}
			}
			
			/**
			 * 선택된 value가 옵션 값에 있을 때 
			 */
			if (sValue) {
				
				if(optionValues.includes(sValue)){
					$(targetID).val(sValue);
				}
			}
			
			/**
			 * 선택된 옵션의 데이터 저장 (하위 옵션)
			 */
			targetData = $(targetID).find("option:selected").data()?.children ?? [];
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	return ctrl;
}));