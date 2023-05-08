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
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.groupList = initData.groupList;
		
		self.setOptionsInit();
		
		$("#btnClear,#btnClear2").click(self.btnClearHandler);
		$("#selGroup,#selCenter").change(self.selectChangeHandler);
		$("#selGroup2,#selCenter2").change(self.selectChangeHandler);
		// rowData setting : 상세화면 셋팅할때 
		$("#btnEdit").click(self.btnEditHandler);
	};
	

	/**
	 * 옵션 초기 설정
	 */
	ctrl.setOptionsInit = function(){
		var self = et.vc;
		
		var groupList = self.groupList;
	
		
		var options1 = [];
		var option1 = {value:"group_id", text:"view_group_name", targetID:"#selGroup",default:"그룹전체"};
		var option2 =  {value:"tenant_id", text:"view_group_name", targetID:"#selCenter", default:"센터전체"};
		var option3 = {value:"team_id", text:"view_group_name", targetID:"#selTeam", default:"팀전체"};
		
		options1.push(option1,option2,option3);
		
		
		self.setOptionList("selGroup",options1);
		
		var options2 = [];
		var option4 = {value:"group_id", text:"view_group_name", targetID:"#selGroup2", keyData:"group_id"};
		var option5 =  {value:"tenant_id", text:"view_group_name", targetID:"#selCenter2", keyData:"tenant_id"};
		var option6 = {value:"team_id", text:"view_group_name", targetID:"#selTeam2",default:"팀전체", keyData:"team_id"};
		
		options2.push(option4,option5,option6);
		
		self.setOptionList("selGroup2",options2);
		
		self.makeTreeSelectOption(groupList,options1);
		self.makeTreeSelectOption(groupList,options2);
	}

	// ============================== 동작 컨트롤 ==============================
	/**
	 * 상세화면 (rowData -> select 박스로 나타낼때)
	 */
	ctrl.btnEditHandler = function(){
		var self = et.vc;
		var groupList = self.groupList;
		var groupName = "selGroup2"
		var rowData ={};
		// --> rowData 형태로 만들기
		$('input[data-groupname="iptGroup"]').each(function(index,element){
			if (index === 0) {
			rowData.group_id = element.value;
			} else if (index === 1) {
			rowData.tenant_id = element.value;
			} else {
			rowData.team_id = element.value;
			}
		});
		// <--
		var options = self.setSValue(groupName,rowData);
		self.makeTreeSelectOption(groupList,options);
		
	}	
	/**
	 * 초기화 : 초기 옵션값, 최상위 그룹 리스트 넣으면 초기화
	 */
	ctrl.btnClearHandler = function(){
		var self = et.vc;
		var groupList = self.groupList;
		var groupname = $(this).data("groupname");
		var rowData ={}; 
		var options = self.setSValue(groupname,rowData);
		self.makeTreeSelectOption(groupList,options); 
	}
	/**
	 * select 선택 변경 시에 
	 */
	ctrl.selectChangeHandler = function(){
		var self = et.vc;
		var groupName = $(this).data("groupname");
		var rowData ={};
		var index = $(this).index()-1; // 변경할 옵션 설정할때 필요해
		var options = self.setSValue(groupName,rowData,index);
		var groupList = $(this).find("option:selected").data()?.children ?? [];
		self.makeTreeSelectOption(groupList,options);
	}
	


	// ============================== Utill ==============================
	/**
	 * 옵션 리스트 common 저장
	 */
	ctrl.setOptionList = function(groupname,option){
		var self = et.vc;
		self.optionList[groupname] = option;
	}
	
	/**
	 * 트리 셀렉트
	 */
	ctrl.makeTreeSelectOption = function(aDataList,oOptionList){
		var self = et.vc;
		var targetData = aDataList;
		var options = [];
		oOptionList.forEach((option, i) => {
			var targetID = option.targetID;
			var sValue = option?.sValue ;
			$(targetID).empty();
			var optionValue = option.value; 
			var optionText = option.text;
			var optionDefault = oOptionList[i].default; 
			et.makeSelectOption(targetData,{value:optionValue, text:optionText},targetID,optionDefault,sValue );
			options.push (option);
			targetData = $(targetID).find("option:selected").data()?.children ?? [];
		});
		
	}
	
	/**
	 * 옵션값 변경 (전역에 저장된 option값은 변경 없음)
	 */
	ctrl.setSValue = function(groupname,rowData,index){
		var self = et.vc;
		
		var optionList = self.optionList[groupname];
		var options = [];
		index = index??-1;// index가 undefined면 그냥 optionList return 
		for(var i in optionList){
			if( i > index){
				optionList[i].sValue = rowData?.[optionList[i].keyData]??"";	
				options.push(optionList[i]);
			}
		}
		return options;
	}	
	return ctrl;
}));