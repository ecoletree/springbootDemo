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
	
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		
		$("#btnSelectGroupSample").click(self.btnSelectGroupSampleHandler);
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
	
	ctrl.setJQueryDateRangePicker = function(){
		var self = et.vc;
		var picker = {
			format : 'YYYY-MM-DD',
			separator : '-',
			language:'ko',
			minDays: 1,
			maxDays: 7,
//			startOfWeek: 'monday',
//			singleMonth: true, // single month mode -->
//			showShortcuts: false,
//			showTopbar: false // <--
			selectForward: true, 
			getValue: function()
			{
				if ($('#iptJQueryRange1').val() && $('#iptJQueryRange2').val() ){
					debugger;
					console.log($('#iptJQueryRange1').val() + ' to ' + $('#iptJQueryRange2').val());
					return $('#iptJQueryRange1').val() + ' to ' + $('#iptJQueryRange2').val();
				}
				else{
					return '';
				}
			},
			setValue: function(s,s1,s2)
			{
				$('#iptJQueryRange1').val(s1);
				$('#iptJQueryRange2').val(s2);
			}
		}
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
	/**
	 * 셀렉트 화면으로 이동
	 */
	ctrl.btnSelectGroupSampleHandler = function(){
		var self = et.vc;
		new ETService().callView(self.path + "select", {});
	}
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