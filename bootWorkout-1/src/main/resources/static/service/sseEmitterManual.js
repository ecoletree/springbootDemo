(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "sseEmitterManual") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	"use strict";

	var ctrl = {};

	ctrl.name = "sseEmitterManual";
	ctrl.path = "/";
	ctrl.groupList= null;
	ctrl.optionList = {};


	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		$("#btnRemove").click(self.btnRemoveHandler);
	};


	ctrl.btnRemoveHandler = function(){
		var self = et.vc;
		var param ={};
		param.id = $("#iptSseId").val();
		new ETService().setSuccessFunction(self.btnRemoveSuccessHandler).callService("/sseManual/remove",param);
	}

	ctrl.btnRemoveSuccessHandler = function(result){
		var self = et.vc;
		alert("remove "+result.message+"!!!");
	}

	// ======================================================================


	return ctrl;
}));