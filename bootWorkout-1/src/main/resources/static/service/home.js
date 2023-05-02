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
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.groupList = initData.groupList;
		self.selectChangeHandler();
		$("#selGroup,#selCenter").change(self.selectChangeHandler);
		
		
	};
	
	ctrl.selectChangeHandler = function(){
		var self = et.vc;
		
		var groupList = self.groupList;
		
		var options = [];
		var option1 = {value:"group_id", text:"view_group_name", targetID:"#selGroup",default:"그룹전체"};
		var option2 =  {value:"tenant_id", text:"view_group_name", targetID:"#selCenter", default:"센터전체"};
		var option3 = {value:"group_cd", text:"view_group_name", targetID:"#selTeam", default:"팀전체"};
		
		options.push(option1,option2,option3);
		
		self.makeTreeSelectOption(groupList,options);
	}
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 트리 구조 셀렉트 박스 생성
	 * 
	 * @param {array} aDataList option으로 보여질 데이터 리스트
	 * @param {array} oOptionList option들에 value와 text 값의 필드명을 각각의 오브젝트로 담은 리스트
	 							, {value: "",text :"", targetID:"#targetID", default:""} 고정
	 * @param {String} ftargetID option이 들어가게 될 최상위 select의 id 값, #selectID 형태
	 */
	ctrl.makeTreeSelectOption = function(aDataList,oOptionList){
		
		var targetData = aDataList;
		var option;
		for(var i in oOptionList){
			var targetID = oOptionList[i].targetID;
			var sValue = $(targetID).val(); // selected value
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