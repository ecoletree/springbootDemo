/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : mk jang
 * @CreateDate : 2022. 10. 20.
 * @DESC : [환경설정] 시스템 로그 관리
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "recordListening") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "recordListening";
	ctrl.path = "/record/listeningMgt";
	ctrl.tableId = "#tbList";
	ctrl.rowData = null;
	ctrl.mediaPlayer = null;
	ctrl.filePath = "";
	ctrl.codeList = null;
	ctrl.transList = null;
	ctrl.initData = null;
	
	ctrl.rullBtnSetting = {
			is_write : "#btnAdd"
				, is_delete : "#btnDelete" 
				, is_update : "#btnEdit" 
				, is_search : "#btnSearch" 
				, is_download : "#btnExcel" 
				, is_rec_download : "#btnDownload"
				, is_encrypt_download : "#btnEncDownload"
	}
	
	ctrl.transStatus = [
			{value:"wait", text:"대기"}
			, {value:"trans", text:"전송"}
			, {value:"error", text:"미전송"}
			];
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.initData = initData;
		et.common.btnRuleClassSetting(self.rullBtnSetting);
		et.common.btnRuleSetting();
		
		self.setSearchList(initData.searchList);
		self.setColumnList(initData.columnList);
		self.setMediaElementPlayer();
		self.setTime();
		self.setView(initData);
		self.addEventListener();
		
		$(".bxPlayerWrap").draggable({cancel:'.bxPlayer'});
	};
	
	ctrl.setView = function(initData) {
		var self = et.vc;
		et.common.CODE_LIST = initData.codeList;
		self.codeList = et.common.getCodeList("TR000"); // 녹취 구분 코드
		et.makeSelectOption(self.codeList, {value:"code_cd", text:"code_name"}, "#selGubun0");  
		et.makeSelectOption(self.codeList, {value:"code_cd", text:"code_name"}, "#selGubun1");  
		et.makeSelectOption(self.codeList, {value:"code_cd", text:"code_name"}, "#selGubun2");  
		et.makeSelectOption(self.codeList, {value:"code_cd", text:"code_name"}, "#selGubun3");  
		
		et.initializeDatepicker(new Date(), '#divStartDate');
		et.initializeDatepicker(new Date(), '#divEndDate');
	}
	
	ctrl.setMediaElementPlayer = function () {
		var self = et.vc;
		var features = ['playpause', 'current', 'progress', 'duration', 'volume', 'skipback', 'jumpforward', 'speed', 'fullscreen'];
		self.mediaPlayer = new MediaElementPlayer(document.getElementById("ecolePlayer"), {
			features : features,
			iconSprite : sessionStorage.getItem("contextpath")+'/resources/plugin/mediaElements_5.1/build/mejs-controls.svg'
		});
	}
	
	ctrl.setTime = function () {
		var self = et.vc;
		$("#selSHour").empty();
		$("#selEHour").empty();
		$("#selSMinute").empty();
		$("#selSSecond").empty();
		$("#selEMinute").empty();
		$("#selESecond").empty();
		$("#selSDuration").empty();
		$("#selEDuration").empty();
		var optionSDuration = $('<option>', {value : "",text : "전체"});
		var optionEDuration = $('<option>', {value : "",text : "전체"});
		$("#selSDuration").append(optionSDuration);
		$("#selEDuration").append(optionEDuration);
		var i = 0;
		for (i = 0; i < 24; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : i,text : txt}
			var option1 = $('<option>', data);
			var option2 = $('<option>', data);
			
			$("#selSHour").append(option1);
			$("#selEHour").append(option2);
		}
		for (i = 0; i < 60; i++) {
			var txt = i < 10 ? "0"+i : i+"";
			var data = {value : i,text : txt}
			var data2 = {value : i,text : txt+"분"}
			var option1 = $('<option>', data);
			var option2 = $('<option>', data);
			var option3 = $('<option>', data);
			var option4 = $('<option>', data);
			optionSDuration = $('<option>', data2);
			optionEDuration = $('<option>', data2);
			$("#selSMinute").append(option1);
			$("#selSSecond").append(option2);
			$("#selEMinute").append(option3);
			$("#selESecond").append(option4);
			$("#selSDuration").append(optionSDuration);
			$("#selEDuration").append(optionEDuration);
		}
		
		$("#selEHour").val(23);
		$("#selEMinute").val(59);
		$("#selESecond").val(59);
	}
	
	ctrl.setSearchList = function (list) {
		var self = et.vc;
		$("#divSearch").empty();
		$.each (list, function (index, data) {
			var element = null;
			if (data.description === "select") {
				element = $('<select name="'+data.code_value+'"></select>');
				if (data.code_value === "status") {
					et.makeSelectOption(self.transStatus, {value:"value", text:"text"}, element, "전송상태전체");
				} else if (data.code_value === "group_id") {
					et.makeSelectOption(self.initData.groupList, {value:"group_cd", text:"view_group_name"}, element, "고객사전체");
				} else if (data.code_value === "tenant_id") {
					et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, element, "센터전체");  
				} else if (data.code_value === "team_id") {
					et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, element, "팀전체"); 
				}
				element.change(self.searchControlChangeHandler);
			} else {
				element = $('<input type="text" name="'+data.code_value+'" placeholder="'+data.code_name+'">');
			}
			$("#divSearch").append(element);
		});
	}
	
	ctrl.setColumnList = function (list) {
		var self = et.vc;
		$("#tbList thead tr").empty();
		$.each (list, function (index, data) {
			var element = $('<th title="'+data.col_name+'"><div>'+data.col_name+'</div></th>');;
			$("#tbList thead tr").append(element);
		});
		$("#tbList thead tr").append($('<th style="width: 4%" title=""><div>&nbsp;</div></th>'));
		$("#tbList thead tr").append($('<th style="width: 6%" title=""><div>&nbsp;</div></th>'));
	} 
	
	// ============================== 동작 컨트롤 ==============================
	/**
	 * 화면에서 사용하는 컨트롤들의 Event들을 add 시켜준다
	 */
	ctrl.addEventListener = function () {
		var self = et.vc;
		
		$("#btnSearch").click(_.debounce(self.btnSearchHandler,500));
		$("#btnExcel").click(_.debounce(self.btnExcelDownHandler,500));
		$("#btnDownload").click(_.debounce(self.btnFileDownHandler,500));
		$("#btnEncDownload").click(_.debounce(self.btnFileDownHandler,500));
		$("#btnTrans").click(_.debounce(self.btnTransHandler,500));
		$("#btnPlayerClose").click(_.debounce(function(){
			self.mediaPlayer.setSrc("");
			self.mediaPlayer.pause();
			$(".popPlayer").hide(); 
		}));
		
		et.setDataTableRowSelection(self.tableId, self.tbListRowSelect);
		
		$("#btnSearch").trigger("click");
	}
	
	ctrl.getTime = function (divId) {
		var str = "";
		$.each($(divId+" select option:selected"),function(index,elem){
			str += $(elem).text()+":";
		});
		//str = $(divId+" select option:selected").text()
		str = str.substring(0,str.length-1);
		return str;
	}
	
	// ============================== 이벤트 리스너 ==============================
	/**
	 * 동적 생성된 검색 옵션에서 select 이벤트 핸들러
	 */
	ctrl.searchControlChangeHandler = function (e) {
		var self = et.vc;
		if ($(e.currentTarget).prop("name") !== "status") {
			self.selGroupListChangeHandler(e.currentTarget);
		}
	}
	
	/*
	 * 검색 버튼 클릭 핸들러
	 */
	ctrl.btnSearchHandler = function (e) {
		var self = et.vc;
		var target = e.target ?? e;
		if ($(target).is(":visible")) {
			debugger;
			var params = {};
			params.sdate = $("#divStartDate input").val()+" "+self.getTime("#divSTime");
			params.edate = $("#divEndDate input").val()+" "+self.getTime("#divETime");
			if ($("#chkIn").is(":checked") && $("#chkOut").is(":checked")) {
				params.call_type = "ALL";
			} else if ($("#chkIn").is(":checked") && !$("#chkOut").is(":checked")) {
				params.call_type = "INBOUND";
			} else if (!$("#chkIn").is(":checked") && $("#chkOut").is(":checked")) {
				params.call_type = "OUTBOUND";
			} else {
				params.call_type = "";
			}
			
			if ($("#selSDuration").val() !== "" && $("#selEDuration").val() !== "") {
				params.sDuration = Number($("#selSDuration").val()) * 60;
				params.eDuration = Number($("#selEDuration").val()) * 60;
			} else if ($("#selSDuration").val() !== "" && $("#selEDuration").val() === "") {
				params.sDuration = Number($("#selSDuration").val()) * 60;
			} else if ($("#selSDuration").val() === "" && $("#selEDuration").val() !== "") {
				params.eDuration = Number($("#selEDuration").val()) * 60;
			}
			
			$.each($("#divSearch").children(),function (i,element){
				var name = $(element).prop("name");
				var val = ""
				if (name === "group_id") {
					val = $(element).find("option:selected").data().group_id; 
				} else if (name === "tenant_id") {
					val = $(element).find("option:selected").data().tenant_id;
				} else if (name === "team_id") {
					val = $(element).find("option:selected").data().team_id;
				} else {
					val = $(element).val();
				}
				params[name] = val;
			});
			
			self.createDatatable(params);
		}
	}
	
	/*
	 * 엑셀 다운로드 핸들러
	 */
	ctrl.btnExcelDownHandler = function () {
		var self = et.vc;
		$.etExcelUtil.excelDownloadAjax($(self.tableId).DataTable(),null,"녹취_청취",false,false);
	}
	
	/**
	 * 검색 조건에서 고객사,센터,팀을 변경 했을때 발생하는 이벤트
	 */
	ctrl.selGroupListChangeHandler = function (target) {
		var self = et.vc;
		var dataList = $(target).find("option:selected").data().children;
		var selTenant = $(target).parent().find("select[name='tenant_id']");
		var selTeam = $(target).parent().find("select[name='team_id']");
		if ($(target).prop("name") === "group_id") {
			if (0 < selTenant.length) {
				et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, selTenant, "센터전체");
			} 
			if (0 < selTeam.length) {
				et.makeSelectOption([], {value:"group_cd", text:"view_group_name"}, selTeam, "팀전체");
			}
		} else if ($(target).prop("name") === "tenant_id") {
			if (0 < selTeam.length) {
				et.makeSelectOption(dataList, {value:"group_cd", text:"view_group_name"}, selTeam, "팀전체");
			}
		} else if ($(target).prop("name") === "tream_id") {
			
		}
	}
	
	/**
	 * 파일 다운로드
	 */
	ctrl.btnFileDownHandler = function (e) {
		var self = et.vc;
		var params = self.rowData;
		params.path = params.rec_file.substring(0,params.rec_file.indexOf(params.uuid));
		if ($(e.currentTarget).prop("id") === "btnDownload") {
			params.org_file = params.uuid + ".mp3";
			params.new_file = params.uuid + ".mp3";
			params.is_enc = "Y"; 
		} else {
			params.org_file = params.uuid + ".aes";
			params.new_file = params.uuid + ".aes";
			params.is_enc = "N";
		}
    	new ETService().callView(self.path + "/getRecordFile", params);
	}
	
	/**
	 * 전송하기 버튼 클릭시
	 */
	ctrl.btnTransHandler = function () {
		var self = et.vc;
		var params = null;
		
		if (self.transList !== null) {
			params = {};
			params.list = [];
			for (var i=0; i < 4; i++) {
				var data = {};
				var certData = $("#txtCert"+i).data();
				var certificate_num = $("#txtCert"+i).val();
				var gubun = $("#selGubun"+i).val();
				var isTrans = $("#selTrans"+i).val();
				var isStatus = isTrans === "Y" ? "wait" : "error";
				var isChanged = "N";
				if (certData.certificate_cd !== undefined) {
					data.certificate_cd =  certData.certificate_cd;
					if (i < self.transList.length) {
						if (certificate_num !== self.transList[i].certificate_num) {
							data.ori_certificate_num = self.transList[i].certificate_num;
							isChanged = "Y"; // 증서번호가 변경되면 로그에 쌓는다;
						}
					}
				}
				
					
				if (isTrans === "Y" && certificate_num === "") {
					et.alert.show(ETCONST.ALERT_TYPE_INFO, "", "증서 번호를 필수 입력 사항입니다.");
					params = null;
					return;
				}
				
				data.certificate_num = certificate_num;
				if (data.certificate_num !== "") {
					data.gubun = gubun;
					data.is_trans = isTrans;
					data.trans_status = isStatus;
					data.is_changed = isChanged;
					data.rec_file = self.rowData.rec_file;
					data.id = self.rowData.id;
					params.list.push(data);
				}
			}
		}
		if (params != null) {
			params.id = self.rowData.id;
			params.client_name = $("#txtClientName").val();
			et.alert.showCustomBtn(ETCONST.ALERT_TYPE_CONFIRM, "전송하기", "선택한 녹취를 전송하시겠습니까?", ["전송하기", "창닫기"], function(e) {
				new ETService().setSuccessFunction(self.sendRecordFileTransSuccessHandler).callService(self.path + "/sendRecordFileTrans", params);
			});
		}
	}
	
	 ctrl.sendRecordFileTransSuccessHandler = function (result) {
	    	var self = et.vc;
			if (result.message === ETCONST.SUCCESS) {
				et.alert.show(ETCONST.ALERT_TYPE_INFO, "", "전송예약 되었습니다.");
				$('#divTrans').modal('hide');
				self.btnSearchHandler();
			} else {
				if (result.data === 0) {
					et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
				}
			}
	    }
	
	// ============================== DataTables 생성, 이벤트들 ==============================
	/*
	 * 데이터 테이블즈 생성
	 */
	ctrl.createDatatable = function(postData) {
		var self = et.vc;
		var params = postData || {};
		var columns = [];
		$.each (self.initData.columnList, function (index, data) {
			var column = {};
			column.data = data.rec_column;
			if (data.class_name !== null) {
				column.className = data.class_name; 
			}
			if (data.rec_column === "trans_status") {
				column.render = function (data, type, full, meta) {
					var str = "";
					if (data === null) {
						str = "";
					} else {
						str = data.replace(/wait/gi, "대기");
						str = str.replace(/trans/gi, "전송");
						str = str.replace(/error/gi, "미전송");
					}
					return str;
				}
			} else {
				if (data.masking_type !== null) {
					column.render = function (data, type, full, meta) {
						var str = "";
						if (data !== null) {
							var maskingType = meta.settings.aoColumns[meta.col].maskingType;
							if (maskingType === "MK001") {
								str = $.etStringUtil.maskingPhone(data);
							} else if (maskingType === "MK002") {
								str = $.etStringUtil.maskingPhone(data,true);
							} else if (maskingType === "MK003") {
								str = $.etStringUtil.maskingEmail(data);
							} else if (maskingType === "MK004") {
								str = $.etStringUtil.maskingRRN(data);
							} else if (maskingType === "MK005") {
								str = $.etStringUtil.maskingName(data);
							} else if (maskingType === "MK006") {
								str = $.etStringUtil.maskingName(data,true);
							} else {
								str = data;
							}
						}
						return str;
					}
					column.maskingType = data.masking_type;
					
				}
			} 
			
			columns.push (column);
		});
		columns.push ({
			data : "rec_id",
			className : "txtCenter",
			render : function (data, type, full, meta) {
				var styleName = "btnReady";
				
				var status = full.trans_status;
				if (status !== null) {
					var arr = status.split("|");
					var isTrans = true;
					$.each(arr,function (index, item){
						if (item == "wait" || item == "error" ) {
							isTrans = false;
							return;
						}
					});
					if (isTrans) {
						styleName = "btnTrans";
					}
				}
				var str = '<button type="button" name="send" class="btnWhite '+styleName+' '+et.common.RULL_CLASS.IS_TRANSFER+'" title="전송하기"></button>';
				return str;
			},
			meta : function (data,full) {
				return "";
			}
		});
		columns.push ({
			data : "rec_file",
			className : "txtCenter",
			render : function (data, type, full, meta) {
				var str = '<button type="button" name="listen" class="btnWhite btnListen '+et.common.RULL_CLASS.IS_PLAY+'" title="듣기"></button><button type="button" name="download" class="btnWhite btnDown" title="다운로드"></button>';
				return str;
			},
			meta : function (data,full) {
				return "";
			}
		});
		var table = et.createDataTableSettings(columns, self.path + "/getRecordList", params, self.dataTableDrawCallback);
		$(self.tableId).DataTable(table);
	};
	
	/**
	 * 테이블 생성후 화면에 표시할때 콜
	 */
	ctrl.dataTableDrawCallback = function (settings) {
		var self = et.vc;
		et.common.btnRuleSetting();
		if (0 < settings.json.recordsTotal) {
    		$('.contRightNoData').hide();
    		$("#tbList_paginate").show();
    	} else {
    		$('.contRightNoData').show();
    		$("#tbList_paginate").hide();
    	}
	}
	
	 /**
     * 테이블 row 선택시
     *
     * @param {jQuery} $target 클릭한 대상
     * @param {number} row 행 인덱스
     * @param {number} col 열 인덱스
     */
    ctrl.tbListRowSelect = function($target, row, col) {
        var self = et.vc;
        var rowData = et.getRowData(self.tableId, $target.closest("tr"));
        self.rowData = rowData;
        if ($target.is("button")) {
        	if ($target.is(".btnReady") || $target.is(".btnTrans")) {
        		self.showDivTrans(rowData);
            } else if ($target.is(".btnlisten")) {
            	$("#pStartStamp").text(rowData.start_stamp);
            	$("#pPhone").text(rowData.phone);
            	$("#pAgentName").text(rowData.view_agent_name);
            	$(".popPlayer").show();
            	var path = rowData.rec_file;
    			path = path.replace(self.filePath,"");
    			path = path.replace(/\\/gi, "§");
    			path = path.replace(/\//gi, "§");
    			var url = sessionStorage.getItem("contextpath")+self.path+"/getRecord/"+path;
    			
            	self.mediaPlayer.setSrc(url);
            	self.mediaPlayer.load();
            	var playPromise = self.mediaPlayer.play();
            	if (playPromise !== undefined) {
        		  playPromise.then(function() {
        		  }).catch(function(error) {
        			  et.alert.show(ETCONST.ALERT_TYPE_INFO, "", "파일이 없습니다.");
        			  $(".popPlayer").hide();
        		  });
        		}
            } else if ($target.is(".btnDown")) {
            	$("#divDownload").modal("show");
            }
    	}
    };
    
    ctrl.showDivTrans = function (data) {
    	var self = et.vc;
    	self.transList = null;
    	$.each($("#divTrans").find("input,select"),function(index,elem){
    		if ($(elem).is("input")) {
    			$(elem).val("");
    		} else if ($(elem).is("select")) {
    			$(elem).find("option:eq(0)").prop("selected","selected");
    		}
    	})
    	$("#txtClientName").val(data.client_name);
    	new ETService().setSuccessFunction(self.getCertificateListSuccessHandler).callService(self.path + "/getCertificateList", data);
    	
    }
    
    ctrl.getCertificateListSuccessHandler = function (result) {
    	var self = et.vc;
		if (result.message === ETCONST.SUCCESS) {
			var list = result.data;
			self.transList = list;
			$.each(list,function (index, data) {
				$("#txtCert"+index).data(data);
				$("#txtCert"+index).val(data.certificate_num);
				$("#selGubun"+index).val(data.gubun);
				$("#selTrans"+index).val(data.is_trans);
				if (data.trans_status  === "wait") {
					$("#txtStatus"+index).val("대기");
				} else if (data.trans_status  === "trans") {
					$("#txtStatus"+index).val("완료");
				} else {
					$("#txtStatus"+index).val("미전송");
				}
			});
			$("#divTrans").modal("show");
		} else {
			et.alert.show(ETCONST.ALERT_TYPE_INFO, "", et.message("common.faile"));
		}
    }
	
	// ============================== Form 리스너 ==============================
	
	return ctrl;
}));