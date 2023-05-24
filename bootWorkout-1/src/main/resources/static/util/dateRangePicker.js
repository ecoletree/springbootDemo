//if(!$){
//	console.error("jQuery is null or jQuery's namespace is crashed. This Util need jQuery plugin.");
//} else if (!_) { 
//	console.error("lodash util plugin is null or '_' overwrite other value. This Util need lodash plugin.");
//} else {
	"use strict";
			/**
		 *  
		 *  jquery date range picker
		 * @param {String} rangeDiv  div id
		 * @param {Boolean} isSingle single 선택 여부
		 */
	function DateRangePicker(rangeDiv,isSingle){
		if (!(this instanceof DateRangePicker)) {
			return new DateRangePicker(rangeDiv,isSingle);
		}
	this.__proto__.init(rangeDiv,isSingle);
	}
	
	DateRangePicker.constructor = DateRangePicker;
	
	DateRangePicker.prototype = {
		
		constructor : DateRangePicker,
		$rangeDiv : "",
		options : {},
		
		
		
		init : function(rangeDiv,isSingle) {

			if (rangeDiv.charAt(0) !== "#") {
				rangeDiv = "#" + rangeDiv;
			}
			
			this.$rangeDiv = $(rangeDiv);
			
			
			
			this.options = {
				autoClose: false,
				format: 'YYYY-MM-DD',
				separator: ' ~ ',
				language:'ko',
				startOfWeek: 'sunday',
				startDate: false,
				endDate: false,
				time: { enabled: true },
				minDays: 0, 
				maxDays: 0, 
				showShortcuts: true,  
				shortcuts: 
				{
					//'prev-days': [1,3,5,7],
					//'next-days': [3,5,7],
					//'prev' : ['week','month','year'],
					//'next' : ['week','month','year']
				},
				customShortcuts : [
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
						{
							name: 'a week from today',
							dates : function()
							{
								var start = moment().toDate();
								var end = moment().add(6,'days').toDate();
								return [start,end];
							}
						},
					], 
				getValue: function() { 
					var div =  $(this).find("input");
					var result = "";
					if(!!div.length){
						result = $('#'+div[0]?.id).val() && $('#'+div[1]?.id).val() 
								? $('#'+div[0]?.id).val() + ' ~ ' + $('#'+div[1]?.id).val() 
								: $('#'+div[0]?.id).val();
					} 						
					return result;
				},
				setValue: function(s){
					var div =  $(this).find("input");
					
					var values = [];
					values.push(s);
					
					if(s.includes('~')) values = s.split('~');
					
					for(var i in values){
						$('#'+div[i]?.id).val(values[i]);
					}
					
					
				},
	 			inline:false, // true 면 화면에 계속 표시
				container:'body',
				alwaysOpen:false,
				
				lookBehind: false,
				batchMode: false,
				duration: 200,
				stickyMonths: true, // true면 달을 넘길때 붙어있는 달이 페어로 넘어간다 (날짜 선택에 지장 없음)
				dayDivAttrs: [],
				dayTdAttrs: [],
				applyBtnClass: '',
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
				 
			},
			this.options.singleDate = !isSingle ? false : true , // true 면 하루 선택만 해도 setValue function 실행  *********************
			this.options.singleMonth = !isSingle ? 'auto' : true  // true 면 한달씩 나옴 
			
			
		},
		
		
		/**
		 * date range picker 적용
		 * 
		 * @returns {DateRangePicker}
		 */
		setPicker : function(){
			var self = this;
			this.$rangeDiv.dateRangePicker(this.options);
			return self;
		},
		
		/**
		 * 최대 선택일 설정 
		 * (ex. new DateRangePicker("#divJQueryRange").setMaxDate(7))
		 * @param {String} days
		 * @returns {DateRangePicker}
		 */
		setMaxDate : function(days){
			var self = this;
			self.options.maxDays = days+1;	
			return self;
		},
		
		/**
		 * 초기 날짜 설정  
		 * 시작일로 부터 몇일, 혹은 선택 날짜 
		 * (ex. new DateRangePicker("#divJQueryRange").setInitDate(시작일, 일수)
		 * (ex. new DateRangePicker("#divJQueryRange").setInitDate(선택일)
		 * @param {String} startDate
		 * @param {String} days
		 * @returns {DateRangePicker}
		 */
		setInitDate : function(startDate,days){
			var self = this;
			var div =  $(self.$rangeDiv).find("input");
			var format = self.options.format;
			var start = moment(startDate).toDate();

			$('#'+div[0]?.id).val(moment(start).format(format)); 
			if(div.length >= 2){
				var end = moment(startDate).add(days,'days').toDate();
				$('#'+div[1]?.id).val(moment(end).format(format));	
			}

			return self;
		}
		
		
		
		
		
	};
//}