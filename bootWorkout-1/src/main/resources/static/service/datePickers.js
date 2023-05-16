(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "datePickers") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "datePickers";
	ctrl.path = "/";
	ctrl.groupList= null;
	ctrl.optionList = {};
	
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		
//		1. Date Range Picker
		self.setDateRangePicker();
		$("#btnSetDatePickerDate").click(self.btnSetDatePickerDateHandler);
		$('#divDatePickerRange').on('apply.daterangepicker', function(ev, picker) {
	    	$("#iptStartDate").val(picker.startDate.format('YYYY-MM-DD'));
			$("#iptEndDate").val(picker.endDate.format('YYYY-MM-DD'));
		});
		$('#divDatePickerRange').on('cancel.daterangepicker', function(ev, picker) {
	    	$(this).val('');
		});
//		2. Litepicker

//		3. Hotel Datepicker


//		4. jQuery date range picker
		self.setJQueryDateRangePicker();
		$("#btnJqOpen").click(self.btnJqOpenHandler);
		$("#btnJqClose").click(self.btnJqCloseHandler);
		$("#btnJqSetRangeDate").click(self.btnJqSetRangeDateHandler);
		$("#btnJqClearSection").click(self.btnJqClearSectionHandler);
		$("#btnJqDestroy").click(self.btnJqDestroyHandler);
		$("#btnJqResetMonth").click(self.btnJqResetMonthHandler);
		
//		5. LightPicker
		self.setLightPicker();
		$("#btnSetLightPick").click(self.btnSetLightPickHandler);
		
		
	};
	// ======================= 1. Date Range Picker ===========================
	ctrl.setDateRangePicker = function(){
		var self = et.vc;
		$('#divDatePickerRange').daterangepicker(
			{
			    opens: 'left',
			    autoUpdateInput: false,
//			    showDropdowns: true, // 년도, 월 드롭다운으로 선택
			    minYear: 1901,
			    maxYear: parseInt(moment().format('YYYY'),10),
			    maxSpan:{
					"days": 7 // 최대 선택일 관련 
				},
			    locale: {
			        "format": 'YYYY-MM-DD',
			        "separator": " ~ ",
			        "applyLabel": "Apply",
			        "cancelLabel": "Clear",
			        "fromLabel": "From",
			        "toLabel": "To",
			        "customRangeLabel": "Custom",
			        "weekLabel": "W",
			        "daysOfWeek": self.ko_week,
			        "monthNames": self.ko_month,
			        "firstDay": 1
			    }
		    
		 	} 
		 	,function(start, end, label) { // apply 이후에 반영되는 function
				$("#iptStartDate").val(start.format('YYYY-MM-DD'));
				$("#iptEndDate").val(end.format('YYYY-MM-DD'));
		 	}
		 );
		 
		 
	}
	ctrl.btnSetDatePickerDateHandler = function(){ // rowData로 들어오는 날짜 설정할때
		var sdate = $("#iptStartDate").val();
		var edate = $("#iptEndDate").val();
		$("#divDatePickerRange").data('daterangepicker').setStartDate(sdate);
		$("#divDatePickerRange").data('daterangepicker').setEndDate(edate);
		
	}
	// =========================== 2. Litepicker ==============================
	
	
	// ======================= 3. Hotel Datepicker ===========================
	// ==================== 4. jQuery Date Range Picker ======================
	
	ctrl.setJQueryDateRangePicker = function(options){
		/*
		options = {
			format : 'YYYY.MM.DD',
			separator : '                 ~                         ',
			single : true
		}
		*/
		var self = et.vc;
		var picker = {
			autoClose: false,
			format: 'YYYY-MM-DD',
			separator: ' 부터 ', // 캘린더 상단에 라벨에 들어가는 구분자
			language: 'ko', // ko로 설정해야함 
			startOfWeek: 'sunday',// or monday
			getValue: function() // --> 이거뭔데
			{
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
			showShortcuts: false, // true/false : true면 보여 
			shortcuts: // shortCut 설정 가능 <-- custom 시에 null *********************
			{
				//'prev-days': [1,3,5,7],
				//'next-days': [3,5,7],
				//'prev' : ['week','month','year'],
				//'next' : ['week','month','year']
			},
			customShortcuts : [
//				//if return an array of two dates, it will select the date range between the two dates
//				{
//					name: 'this week',
//					dates : function()
//					{
//						var start = moment().day(0).toDate();
//						var end = moment().day(6).toDate();
//						return [start,end];
//					}
//				},
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
		}
		//picker = $.extend(picker, options);
		
		$('#divJQueryRange')
			.dateRangePicker(picker)
			.bind('datepicker-change', function(e, obj) {
			});
	} 	

	
	ctrl.btnJqOpenHandler = function(e){
		e.stopPropagation();
		$('#divJQueryRange').data('dateRangePicker').open();
	}
	ctrl.btnJqCloseHandler = function(e){
		e.stopPropagation();
		$('#divJQueryRange').data('dateRangePicker').close();
	}
	ctrl.btnJqSetRangeDateHandler = function(e)
	{
		e.stopPropagation();
		var sdate = $('#iptSetJQueryRange1').val();
		var edate = $('#iptSetJQueryRange2').val();
		$('#divJQueryRange').data('dateRangePicker').open();
		$('#divJQueryRange').data('dateRangePicker').setDateRange(sdate,edate);
	}
	
	
	ctrl.btnJqClearSectionHandler =function(e)
	{
		e.stopPropagation();
		$('#divJQueryRange').data('dateRangePicker').clear();
	}
	
	ctrl.btnJqDestroyHandler = function(e)
	{
		e.stopPropagation();
		$('#divJQueryRange').data('dateRangePicker');
	}
	
	ctrl.btnJqResetMonthHandler=function(e)
	{
		e.stopPropagation();
		$('#divJQueryRange').data('dateRangePicker').resetMonthsView();
	}
	// ============================== 5. LightPicker ==============================
	/**
	 * lightpick.js 에서 defaults format 수정
	 */
	ctrl.setLightPicker = function(){
		var picker = new Lightpick({
		    field: document.getElementById('iptLightPick1'),
		    secondField: document.getElementById('iptLightPick2'),
		    singleDate: false,
//		    lang:"ru", // auto, system language 외에 러시아어
		    minDays: 3, // 최소선택
    		maxDays: 7, // 최다선택
		    format: "YYYY/MM/DD",
		    numberOfMonths: 2, //Number of visible months.
		    onSelect: function(start, end){
		        var str = '';
		        str += start ? start.format('YYYY/MM/DD') + ' 부터 ' : '';
		        str += end ? end.format('YYYY/MM/DD') +' 까지' : '';
		        document.getElementById('result-3').innerHTML = str;
		    },
		    selectForward : false, // Select second date after the first selected date. default : false
		    footer : true, 
		    locale :{
				  buttons: {
				    prev: '←',
				    next: '→',
				    close: '×',
				    reset: 'Reset',
				    apply: 'Apply'
				  },
				  tooltip: {
				    one: '일',
				    other: '일'
				  },
				  tooltipOnDisabled: null,
				  pluralize: function(i, locale){
				    if (typeof i === "string") i = parseInt(i, 10);
				
				    if (i === 1 && 'one' in locale) return locale.one;
				    if ('other' in locale) return locale.other;
				
				    return '';
				  }
				}
			
		});
		return picker;
//		

	}
	ctrl.btnSetLightPickHandler = function(){
		var self = et.vc;
		
		var picker = self.setLightPicker();
		var start_date = $("#demo-3_1").val();
		var end_date = $("#demo-3_2").val();
		picker.setStartDate(new Date(start_date)); // 시작일 셋팅
		picker.setEndDate(new Date(end_date)); // 종료일 셋팅
	}

	
	// ======================================================================
	ctrl.en_month = ["January",
			         "February",
			         "March",
			         "April",
			         "May",
			         "June",
			         "July",
			         "August",
			         "September",
			         "October",
			         "November",
			         "December"]
     ctrl.en_week = ["Su",
			         "Mo",
			         "Tu",
			         "We",
			         "Th",
			         "Fr",
			         "Sa"]
     ctrl.ko_month = ["1월",
			         "1월",
			         "2월",
			         "3월",
			         "4월",
			         "5월",
			         "6월",
			         "7월",
			         "8월",
			         "9월",
			         "10월",
			         "11월",
			         "12월",
			         ]
     ctrl.ko_week = ["일",
			         "월",
			         "화",
			         "수",
			         "목",
			         "금",
			         "토"]
	
	return ctrl;
}));