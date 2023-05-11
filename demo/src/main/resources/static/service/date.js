/*******************************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * @Author : ysJang
 * @CreateDate : 2019. 03. 04.
 * @DESC : [관리자웹] 로그인
 ******************************************************************************/
(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "home") {
			et.vc= ctrl(et);
		}
		var inter = setInterval(function(){
			et.promiseInit().then(function(){
				et.vc.init();
				clearInterval(inter);
			}, function (){})
		},300);
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
	
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function() {
		var self = et.vc;
		
		
//		$.fn.daterangepicker.defaultOptions = {}
//		$.fn.daterangepicker.defaultOptions.locale = {
//              format: 'YYYY.MM.DD HH:mm',
//              separator: ' ~ ',
//              applyLabel: 'Apply',
//              cancelLabel: 'Cancel',
//              fromLabel: 'From',
//              toLabel: 'To',
//              customRangeLabel: 'Custom',
//              daysOfWeek: $.fn.datepicker.dates.ko.daysMin,
//              monthNames: $.fn.datepicker.dates.ko.months,
//              firstDay: 1
//            };
		$("#btn1").click(self.btnClickHandler);
		$("#btn2").click(self.btnClickHandler2);
		$("#btn3").click(self.btnClickHandler3);
		self.createUI();
	};
	
	ctrl.btnClickHandler = function () {
		var self = et.vc;
		var op = {};
		debugger;
		var a = op?.single;
		var b = op?.single??false;
		alert(a + " | " +b);
		
		//$('#config-demo').data('daterangepicker').setStartDate(moment());
		//alert(eee[0] + " | " +eee[1]);
	}
	
	ctrl.btnClickHandler2 = function () {
		var self = et.vc;
		var aFun = function(ev, picker) {
			debugger;
			var val = picker.startDate.format(picker.locale.format) + picker.locale.separator +
			picker.endDate.format(picker.locale.format);  
		  //do something, like clearing an input
		  $('#reportrange span').html(val);
		};
		
		var cFun = function(ev, picker) {
			debugger;
			var val = "";
			//do something, like clearing an input
			$('#reportrange span').html(val);
		};
		et.initializeDateRangePicker("#reportrange",
		{dateLimit:{months:1},timePicker:true}
		,aFun,cFun);
	}
	
	ctrl.btnClickHandler3 = function () {
		var self = et.vc;
		et.initializeDateRangePicker("#iptDR");
	}
	
	ctrl.createUI = function () {
		var self = et.vc;
		
		$('#config-text').keyup(function() {
          eval($(this).val());
        });
        
        $('.configurator input, .configurator select').change(function() {
          updateConfig();
        });

        $('.demo i').click(function() {
          $(this).parent().find('input').click();
        });

        $('#startDate').daterangepicker({
          singleDatePicker: true,
          startDate: moment().subtract(6, 'days')
        });

        $('#endDate').daterangepicker({
          singleDatePicker: true,
          startDate: moment()
        });

        //updateConfig();

        function updateConfig() {
          var options = {};

          if ($('#singleDatePicker').is(':checked'))
            options.singleDatePicker = true;
          
          if ($('#showDropdowns').is(':checked'))
            options.showDropdowns = true;

          if ($('#showWeekNumbers').is(':checked'))
            options.showWeekNumbers = true;

          if ($('#showISOWeekNumbers').is(':checked'))
            options.showISOWeekNumbers = true;

          if ($('#timePicker').is(':checked'))
            options.timePicker = true;
          
          if ($('#timePicker24Hour').is(':checked'))
            options.timePicker24Hour = true;

          if ($('#timePickerIncrement').val().length && $('#timePickerIncrement').val() != 1)
            options.timePickerIncrement = parseInt($('#timePickerIncrement').val(), 10);

          if ($('#timePickerSeconds').is(':checked'))
            options.timePickerSeconds = true;
          
          if ($('#autoApply').is(':checked'))
            options.autoApply = true;

          if ($('#dateLimit').is(':checked'))
            options.dateLimit = { days: 7 };

          if ($('#ranges').is(':checked')) {
            options.ranges = {
              'Today': [moment(), moment()],
              'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
              'Last 7 Days': [moment().subtract(6, 'days'), moment()],
              'Last 30 Days': [moment().subtract(29, 'days'), moment()],
              'This Month': [moment().startOf('month'), moment().endOf('month')],
              'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            };
          }

          if ($('#locale').is(':checked')) {
            $('#rtl-wrap').show();
            options.locale = {
              direction: $('#rtl').is(':checked') ? 'rtl' : 'ltr',
              format: 'YYYY/MM/DD',
              separator: ' ~ ',
              applyLabel: 'Apply',
              cancelLabel: 'Cancel',
              fromLabel: 'From',
              toLabel: 'To',
              customRangeLabel: 'Custom',
              daysOfWeek: ['일', '월', '화', '수', '목', '금','토'],
              monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
              firstDay: 1
            };
          } else {
            $('#rtl-wrap').hide();
          }

          if (!$('#linkedCalendars').is(':checked'))
            options.linkedCalendars = false;

          if (!$('#autoUpdateInput').is(':checked'))
            options.autoUpdateInput = false;

          if (!$('#showCustomRangeLabel').is(':checked'))
            options.showCustomRangeLabel = false;

          if ($('#alwaysShowCalendars').is(':checked'))
            options.alwaysShowCalendars = true;

          if ($('#parentEl').val().length)
            options.parentEl = $('#parentEl').val();

          if ($('#startDate').val().length) 
            options.startDate = $('#startDate').val();

          if ($('#endDate').val().length)
            options.endDate = $('#endDate').val();
          
          if ($('#minDate').val().length)
            options.minDate = $('#minDate').val();

          if ($('#maxDate').val().length)
            options.maxDate = $('#maxDate').val();

          if ($('#opens').val().length && $('#opens').val() != 'right')
            options.opens = $('#opens').val();

          if ($('#drops').val().length && $('#drops').val() != 'down')
            options.drops = $('#drops').val();

          if ($('#buttonClasses').val().length && $('#buttonClasses').val() != 'btn btn-sm')
            options.buttonClasses = $('#buttonClasses').val();

          if ($('#applyClass').val().length && $('#applyClass').val() != 'btn-success')
            options.applyClass = $('#applyClass').val();

          if ($('#cancelClass').val().length && $('#cancelClass').val() != 'btn-default')
            options.cancelClass = $('#cancelClass').val();
            
          if ($('#iptFormat').val().length && $('#iptFormat').val() != 'YYYY.MM.DD')
            $.fn.daterangepicker.defaultOptions.locale = {format : $('#iptFormat').val() };

          $('#config-text').val("$('#demo').daterangepicker(" + JSON.stringify(options, null, '    ') + ", function(start, end, label) {\n  console.log(\"New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')\");\n});");

        self.JWT =   $('#config-demo').daterangepicker(options, function(start, end, label) { console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')'); }).click();;
          
        }
	}
	
	
	// ============================== 동작 컨트롤 ==============================
	
	return ctrl;
}));