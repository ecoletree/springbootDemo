(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "fullCalendarTest") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	"use strict";
	
	var ctrl = {};
	
	ctrl.name = "fullCalendarTest";
	ctrl.path = "/";
	ctrl.groupList= null;
	ctrl.optionList = {};
	ctrl.calendar = "#calendar";
	ctrl.commonEvents = null;
	ctrl.KYREvents = null;
	ctrl.SYREvents = null;
	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		
		self.setData();
		self.setCalendar();
		
		
	};
	
	ctrl.setCalendar = function(){
		var self = et.vc;
		var cal = document.getElementById("calendar"); 
		//캘린더 헤더 옵션
        const headerToolbar = {
            left: 'prevYear,prev,next,nextYear today',
            center: 'title',
            right: 'dayGridMonth,dayGridWeek,timeGridDay'
        }

        // 캘린더 생성 옵션(참공)
        const calendarOption = {
            height: '700px', // calendar 높이 설정
            expandRows: true, // 화면에 맞게 높이 재설정
            slotMinTime: '09:00', // Day 캘린더 시작 시간
            slotMaxTime: '18:00', // Day 캘린더 종료 시간
            // 맨 위 헤더 지정
            headerToolbar: headerToolbar,
            initialView: 'dayGridMonth',  // default: dayGridMonth 'dayGridWeek', 'timeGridDay', 'listWeek'
            locale: 'kr',        // 언어 설정
            selectable: true,    // 영역 선택
            selectMirror: true,  // 오직 TimeGrid view에만 적용됨, default false
            navLinks: true,      // 날짜,WeekNumber 클릭 여부, default false
            weekNumbers: true,   // WeekNumber 출력여부, default false
            editable: true,      // event(일정) 
            /* 시작일 및 기간 수정가능여부
            eventStartEditable: false,
            eventDurationEditable: true,
            */
            dayMaxEventRows: true,  // Row 높이보다 많으면 +숫자 more 링크 보임!
            /*
            views: {
                dayGridMonth: {
                    dayMaxEventRows: 3
                }
            },
            */
            nowIndicator: true,
            //events:[],
            eventSources: [ // 일정 데이터 리스트
                self.commonEvents,  // Ajax 요청 URL임에 유의!
                self.KYREvents,
                self.SYREvents
            ]
        }

		var calendar = new FullCalendar.Calendar(cal,calendarOption)
		calendar.render();
		
	 	calendar.on("eventAdd", info => console.log("Add:", info));
        calendar.on("eventChange", info => console.log("Change:", info));
        calendar.on("eventRemove", info => console.log("Remove:", info));
        calendar.on("eventClick", info => {
	// 이벤트 클릭시에 여기에 나옴
	debugger;
            console.log("eClick:", info);
            console.log('Event_title: ', info.event.title);
            console.log('Event_comment: ', info.event.extendedProps.comment);
            console.log('Coordinates: ', info.jsEvent);
            console.log('View: ', info.view);
            // 재미로 그냥 보더색 바꾸깅
            info.el.style.borderColor = 'red';
        });
//        calendar.on("eventMouseEnter", info => console.log("eEnter:", info));
//        calendar.on("eventMouseLeave", info => console.log("eLeave:", info));
        calendar.on("dateClick", self.dateClickHandler);
//        calendar.on("select", info => {
//            console.log("선택", info.event);
//
////            mySchStart.value = info.startStr;
////            mySchEnd.value = info.endStr;
//			
//            
//        });
	}
	ctrl.dateClickHandler = function(info){
		console.log("dateClick!!!:", info);
		$("#yrModal").show();
		debugger;
	}
	ctrl.setData = function(){
		var self = et.vc;
		
		self.commonEvents = [
		    {
		        "id": "common001",
		        "title": "추석",
		        "start": "2023-09-28",
		        "end":"2023-10-01",
		        "allDay": true,
		        "backgroundColor":"black",
		        "textColor":"pink",
		        "extendedProps": {
		            "comment": "명절"
		        }
		    },
		    {
		        "id": "common002",
		        "title": "임시휴일",
		        "start": "2023-10-02",
		        "allDay": true,
		        "backgroundColor":"black",
		        "textColor":"pink",
		        "extendedProps": {
		            "comment": "징검다리"
		        }
		    },
		    {
		        "id": "common003",
		        "title": "개천절",
		        "start": "2023-12-03",
		        "display":"background",
		        "allDay": true,
		        "backgroundColor":"green",
		        "textColor":"black",
		        "extendedProps": {
		            "comment": "국가휴일"
		        }
		    }
		];
	//=========================================================
		self.KYREvents = [
//		    {
//		        "id": "kyr001",
//		        "title": "스케줄구현",
//		        "start": "2023-12-06",
//		        "end":"2023-12-09",
//		        "allDay": true,
//		        "backgroundColor":"black",
//		        "textColor":"yellow",
//		        "extendedProps": {
//		            "comment": "FullCal사용"
//		        }
//		    },
		    {
		        "id": "kyr002",
		        "title": "최프착수보고",
		        "start": "2023-12-07T14:00",
		        "end":"2023-12-07T15:00",
		        "backgroundColor":"black",
		        "textColor":"yellow",
		        "extendedProps": {
		            "comment": "최프발표"
		        }
		    },
		     {
		        "id": "syr001",
		        "title": "김경현",
		        "start": "2023-12-06",
		        "end":"2023-12-06",
		        "allDay": true,
		        "backgroundColor":"#ffb3f6",
		        "textColor":"black",
		        "extendedProps": {
		            "comment": "asdf",
		        },
		        
		    },
//		    {
//		        "id": "kyr003",
//		        "title": "모달구현",
//		        "start": "2023-12-06",
//		        "end":"2023-12-07",
//		        "backgroundColor":"black",
//		        "textColor":"yellow",
//		        "allDay": true,
//		        "extendedProps": {
//		            "comment": "스케줄기능확장"
//		        }
//		    }
		];
// 출근 - 본사, 재택, 그외
// 휴가 - 오후/오전 반차 , 유급 휴가
// 미출근 퇴근 

	//=========================================================
		self.SYREvents = [
		    {
		        "id": "syr001",
		        "title": "출근 9명",
		        "start": "2023-12-06",
		        "end":"2023-12-06",
		        "allDay": true,
		        "backgroundColor":"#9dfaa8",
		        "textColor":"black",
		        "extendedProps": {
		            "comment": "asdf",
		            "details":{"본사":["김형석","장윤석","장미혜","곽동숙","김경현","이승미"], "재택":["허윤선","장성우"],"그외":["김민석"]}
		        },
		        
		    },
		    {
		        "id": "syr002",
		        "title": "휴가 3명",
		        "start": "2023-12-06",
		        "end":"2023-12-06",
		        "allDay": true,
		        "backgroundColor":"#78b0ff",
		        "textColor":"black",
		        "extendedProps": {
		            "comment": "asdf",
		            "details":{"휴가":["박은중"], "오전반차":["박한빈"],"오후반차":["김준희"]}
		        },
		    },
		    {
		        "id": "syr003",
		        "title": "퇴근/미출근 2명",
		        "start": "2023-12-06",
		        "end":"2023-12-06",
		        "allDay": true,
		        "backgroundColor":"#e3e3e3",
		        "textColor":"black",
		        "extendedProps": {
		            "comment": "asdf",
		            "details":{"퇴근":["최준혁"],"미출근":["배성균","추원호"]}
		        },
		    },
		];
		
	}
	
	
	
	
	
	return ctrl;
}));