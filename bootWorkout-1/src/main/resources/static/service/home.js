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
	ctrl.path = "/";
	ctrl.groupList= null;
	ctrl.optionList = {};
	ctrl.linkData = null;
	ctrl.linkColumns = [
				{
					data : "status",
					headerText:"status",
				},{
					data : "url",
					headerText:"url",
				},{
					data : "parent",
					headerText:"parent",
				},{
					data : "source",
					headerText:"source",
				}
			
			];
	
	// ============================== 화면 컨트롤 ==============================
	
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		
		$("#btnSelectGroupSample").click(self.btnSelectGroupSampleHandler);
		$("#btnCompareDateRangePickers").click(self.btnCompareDateRangePickersHandler);
		$("#btnJqueryDateRangePickers").click(self.btnJqueryDateRangePickersHandler);
		$("#btnLogin").click(self.btnLoginHandler);
		$("#btnWebtoApp").click(self.btnWebtoAppHandler);
		$("#btnCheckLink").click(self.btnCheckLinkHandler);
		
		
		$("#btnSendPushAlert").click(self.btnSendPushAlertHandler);
		$("#btnExcelDown").click(self.btnExcelDownHandler);
//		self.fromAppToWeb(msg);
		
//		4. jQuery date range picker

		// default
//		new DateRangePicker("#divJQueryRange0").setPicker();
		// single
//		new DateRangePicker("#divJQueryRange0",true).setInitDate('2023.05.25').setPicker(); // single
		// range
//		new DateRangePicker("#divJQueryRange1").setMaxDate(7).setInitDate('2023.05.21','2023.05.28').setPicker();
//		new DateRangePicker("#divJQueryRange2").setMaxDate(7).setInitDate(new Date(),5).setPicker();
//		new DateRangePicker("#divJQueryRange3").setExtendsCalendar(true).setDisableSelectForward().setPicker();
//		new DateRangePicker("#divJQueryRange4").setExtendsCalendar(true).setDisableSelectBackward().setPicker();
		
	};
	// ==================================================================================
	/*broken link handler*/
	ctrl.btnCheckLinkHandler = function(){
		
//		var param = {};
//		param.url = $("#iptSiteUrl").val();
//		new ETService().setSuccessFunction(self.checkLinkSuccessHandler).callService("/checkLink", param);

		$.ajax({
			url:"http://localhost:3000/main",
			data:{url:$("#iptSiteUrl").val()},
			method:"GET",
			dataType:"json"
			
		})
		.done(function(json){
			var self = et.vc;
			if(json.resultMsg === "success"){
				var links = json.data;
				self.createDataTable(links);
			}else{
				console.log(json.resultMsg);
			}
		})
		.fail(function(xhr,status,errorthrown){
			console.log("fail");
		});
	}
	ctrl.createDataTable = function(postData){
		var self = et.vc;
		self.linkData = postData;
		var columns = [
				{
					data : "status",
				},{
					data : "url",
				},{
					data : "parent",
				},{
					data : "source",
				}
			
			];
		
		var table = et.createDataTableSettings(columns, null, postData, self.dataTableDrawCallback,"",false,postData);
		table.paging = false;
		$("#tbList").DataTable(table);
	}
	ctrl.btnExcelDownHandler = function(){
		var self = et.vc;
		var columns = self.linkColumns;
		
		$.etExcelUtil.excelDownload(columns,self.linkData,"broken link list",false,false);
	}
	ctrl.dataTableDrawCallback = function(settings){
		
	}
	
	
	/*flutter 모바일-웹 화면 전환*/
	ctrl.fromAppToWeb = function(msg){
		
		var self = et.vc;
		console.log('test');
		document.querySelector("#flutterMessageTitle").innerText = msg;
	}
	ctrl.btnWebtoAppHandler = function(){
		var self= et.vc;
		var msg = "web to app";
		toApp.postMessage(msg);
		
	}
	
	/* firebaseMessage 보내기*/
	ctrl.btnSendPushAlertHandler = function(){
		var self = et.vc;
		var param = {};
		param.message_title = $("#iptTitle").val(); 
		param.message_text = $("#iptText").val(); 
		param.message_name = $("#iptName").val(); 
		new ETService().setSuccessFunction(self.sendMsgSuccessHandler).callService("/flutter/sendMsg", param);
		
	}
	ctrl.sendMsgSuccessHandler = function(result){
		var self= et.vc;
		console.log(result.data);
		debugger;
	}
	ctrl.btnLoginHandler = function(){
		var self = et.vc;
		var param = {};
		param.pwd = btoa($("#iptPwd").val()); 
		new ETService().setSuccessFunction(self.loginSuccessHandler).callService(self.path + "loginChk", param);
		
	}
	ctrl.loginSuccessHandler = function(result){
		var self = et.vc;
		
		console.log(result.data);
		debugger;
	}
	
	// ==================== 4. jQuery Date Range Picker ======================
	
	ctrl.defaultJQueryDateRangePicker = function(id){
		var self = et.vc;
		
		var picker = {
			autoClose: false,
			format: 'YYYY-MM-DD',
			separator: ' 부터 ', // 캘린더 상단에 라벨에 들어가는 구분자
			language: 'ko', // ko로 설정해야함 
			startOfWeek: 'sunday',// or monday
			getValue: function() // --> 이거뭔데
			{
				debugger;
//				return $(this).val();
				if ($('#iptJQueryRange1').val() && $('#iptJQueryRange2').val() ){
//					debugger;
//					console.log($('#iptJQueryRange1').val() + ' to ' + $('#iptJQueryRange2').val());
					return $('#iptJQueryRange1').val() + ' 부터 ' + $('#iptJQueryRange2').val();
				}
				else{
					return '';
				}
			},
			setValue: function(s,s1,s2) //--> 캘린더에서 날자 선택하면 셋팅되는거
			{
				// single_ date일 때 function이 있어야 함
				console.log("S"+s); // 범위일때는 **부터 ** 이렇게 나옴
				$('#iptJQueryRange1').val(s1);
				$('#iptJQueryRange2').val(s2);
//				if(!$(this).attr('readonly') && !$(this).is(':disabled') && s != $(this).val())
//				{
//					$(this).val(s);
//				}
			},
			startDate: false,
			endDate: false,
			time: {
				enabled: true // true/false 시간 선택 가능 <-- 슬라이드 바 말고 다른형태로 가능한지 *********************
			},
			minDays: 0, // 최소 선택 일자
			maxDays: 0, // 최대 선택일자
			showShortcuts: true, // true/false : true면 보여 
			shortcuts: // shortCut 설정 가능 <-- custom 시에 null *********************
			{
				//'prev-days': [1,3,5,7],
				//'next-days': [3,5,7],
				//'prev' : ['week','month','year'],
				//'next' : ['week','month','year']
			},
			customShortcuts : [
//				//if return an array of two dates, it will select the date range between the two dates
				{
					name: 'this week',
					dates : function()
					{
						var start = moment().day(0).toDate();
						var end = moment().day(6).toDate();
						return [start,end];
					}
				},
				{
					name: 'this month',
					dates : function()
					{
						var start = moment().startOf("month").toDate();
						var end = moment().endOf('month').toDate();
						return [start,end];
					}
				},
//				//if only return an array of one date, it will display the month which containing the date. and it will not select any date range
//				{
//					name: 'Oct 2014',
//					dates : function()
//					{
//						//move calendars to show this date's month and next month
//						var movetodate = moment('2014-10','YYYY-MM').toDate();
//						return [movetodate];
//					}
//				}
				
			], // shortCut 설정 가능 *********************
 			inline:false, // true 면 화면에 계속 표시
			container:'body',
			alwaysOpen:false,
			singleDate:false , // true 면 하루 선택만 해도 setValue function 실행  *********************
			lookBehind: false,
			batchMode: false,
			duration: 200,
			stickyMonths: true, // true면 달을 넘길때 붙어있는 달이 페어로 넘어간다 (날짜 선택에 지장 없음)
			dayDivAttrs: [],
			dayTdAttrs: [],
			applyBtnClass: '',
			singleMonth: 'auto', // true 면 한달씩 나옴 
			hoveringTooltip: function(days, startTime, hoveringTime)
			{
				return days > 1 ? days + ' ' + /* lang('days')*/ "일" : '';
			}, // 마우스 hover일때 일 수 세줌 
			showTopbar: true, // 상단에 선택된 날짜 나타냄 (ex.기간:2023-05-02 부터 2023-05-04 (3일간))
			swapTime: false,
			selectForward: true, // true 일 경우에 전에 선택한 날짜 이후의 날짜만 선택 가능하다
			selectBackward: false,
			showWeekNumbers: false,
			getWeekNumber: function(date) //date will be the first day of a week
			{
				return moment(date).format('w');
			},
			monthSelect: true, // 월 드롭박스로 별도 선택 *********************
			yearSelect: true // 년도 드롭박스로 별도 선택 *********************
		}; 
		
//		picker = $.extend(picker,options)

		$(id).dateRangePicker(picker);
		
	}
	
	
	
	
	// ======================================================================
	/**
	 * 셀렉트 화면으로 이동
	 */
	ctrl.btnSelectGroupSampleHandler = function(){
		var self = et.vc;
		new ETService().callView(self.path + "select", {});
	}
	/**
	 * 데이트 피커 비교 화면으로 이동
	 */
	ctrl.btnCompareDateRangePickersHandler = function(){
		var self = et.vc;
		new ETService().callView(self.path + "datePickers", {});
	}
	/**
	 * 데이트 피커 비교 화면으로 이동
	 */
	ctrl.btnJqueryDateRangePickersHandler = function(){
		var self = et.vc;
		new ETService().callView(self.path + "jquerydatePicker", {});
	}
	
	return ctrl;
}));