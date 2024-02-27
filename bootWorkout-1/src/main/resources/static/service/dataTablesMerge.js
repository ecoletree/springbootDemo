(function(et, ctrl) {
	if (_.isObject(et) && et.name === ETCONST.PROJECT_NAME) {
		if (!et.vc || et.vc.name !== "dataTablesMerge") {
			et.vc= ctrl(et);
		}
	} else {
		console.error("ecoletree OR ETCONST is not valid. please check that common.js file was imported.");
	}
}(this.ecoletree, function(et) {
	"use strict";

	var ctrl = {};

	ctrl.name = "dataTablesMerge";
	ctrl.path = "/";
	ctrl.table = null;
	ctrl.dummyData = [
	  { start_date: '2023-04-20', user_name: 'Bob Johnson', position_cd: 'P007', position: 'Analyst', age: 28, salary: 55000 },
	  { start_date: '2023-04-20', user_name: 'Mia Harris', position_cd: 'P007', position: 'Analyst', age: 25, salary: 48000 },
	  { start_date: '2023-04-20', user_name: 'David Lee', position_cd: 'P007', position: 'Analyst', age: 26, salary: 50000 },
	  { start_date: '2023-04-20', user_name: 'Grace Turner', position_cd: 'P007', position: 'Analyst', age: 29, salary: 58000 },
	  { start_date: '소계', user_name: '소계', position_cd: '소계', position: '소계', age: "소계", salary: 53000 },
	  { start_date: '2023-04-28', user_name: 'John Doe', position_cd: 'P008', position: 'Engineer', age: 30, salary: 60000 },
	  { start_date: '2023-04-28', user_name: 'Alice Brown', position_cd: 'P009', position: 'Developer', age: 32, salary: 65000 },
	  { start_date: '2023-04-28', user_name: 'Emma White', position_cd: 'P009', position: 'Developer', age: 28, salary: 62000 },
	  { start_date: '2023-04-28', user_name: 'Henry Harris', position_cd: 'P009', position: 'Developer', age: 31, salary: 70000 },
	  { start_date: '2023-04-28', user_name: 'Kate Miller', position_cd: 'P009', position: 'Developer', age: 34, salary: 75000 },
	  { start_date: '소계', user_name: '소계', position_cd: '소계', position: '소계', age: "소계", salary: 53000 },
	  { start_date: '2023-05-05', user_name: 'Jane Smith', position_cd: 'P006', position: 'Manager', age: 35, salary: 80000 },
	  { start_date: '2023-05-05', user_name: 'Charlie Wilson', position_cd: 'P006', position: 'Manager', age: 40, salary: 90000 },
	  { start_date: '2023-05-05', user_name: 'Frank Taylor', position_cd: 'P006', position: 'Manager', age: 33, salary: 85000 },
	  { start_date: '2023-05-05', user_name: 'Isabel Walker', position_cd: 'P006', position: 'Manager', age: 36, salary: 85000 },
	  { start_date: '2023-05-05', user_name: 'Leo Turner', position_cd: 'P006', position: 'Manager', age: 38, salary: 88000 },
	  { start_date: '소계', user_name: '소계', position_cd: '소계', position: '소계', age: "소계", salary: 53000 },
	];

	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;

		self.createDataTables();
		$("#btnExcelDown").click(self.btnExcelDownHandler);
	};
	// ==================================================================================
	ctrl.createDataTables = function(){
		var self = et.vc;
		var testData = self.dummyData;
		var processedData = testData;
//		var processedData = testData.map(function (item, index, array) {
//			var rowspan = item.start_date === (array[index + 1]?.start_date || '') ? 'Y' : 'N';
//			return { ...item, rowspan: rowspan };
//		});

		var columns = [
			{data : "start_date", className:"txtCenter"},
			{data : "position_cd",render:function(data,type,full,meta){
				return full.position;
			}, className:"txtCenter"},
			{data : "user_name", className:"txtCenter"},
			{data : "age", className:"txtCenter"},
			{data : "salary", className:"txtCenter", isSpan :false},
		];

//      rowspan 넣는지 안넣는지 여부 셋팅
		columns.map(function(item,index,array){
			if(item.hasOwnProperty("isSpan")){
				item.createdCell = function (td, cellData, rowData, row, col) {
			       $(td).attr('isSpan', item.isSpan);
			    }
		    }
		});

		var options = et.createDataTableSettings(columns, null, {}, self.dataTableDrawCallback,"",false,processedData);
		options.paging = false;
		self.table = $("#tbList").DataTable(options);

		var table = self.table;

	}

	ctrl.dataTableDrawCallback = function(settings){
		var self = et.vc;
		self.mergeRowSpan('#tbList');
	    self.mergeColSpan('#tbList');
	}

	/**
	 * 로우 병합
	 */
	ctrl.mergeRowSpan = function(tableId){
		var self = et.vc;
	 	var dimension_cells = new Array();
	    var dimension_col = null;
	    var columnCount = $(tableId+" tr:first th").length;
	    // -> rowspan

	    for (dimension_col = 0; dimension_col <= columnCount; dimension_col++) {
	        // first_instance holds the first instance of identical td
	        var first_instance = null;
	        var rowspan = 1;
	        // iterate through rows
	        $(tableId).find('tr').each(function () {
	            // find the td of the correct column (determined by the dimension_col set above)
	            var dimension_td = $(this).find('td:nth-child(' + dimension_col + ')');


				if(dimension_td.attr("isSpan") === undefined &&  !dimension_td.attr("isSpan")){

		            if (first_instance === null) {
		                // must be the first row
		                first_instance = dimension_td;
		            } else if (dimension_td.text() === first_instance.text()) {
		                // the current td is identical to the previous
		                // remove the current td
		               // dimension_td.remove();
		                dimension_td.attr('hidden', true);
		                ++rowspan;
		                // increment the rowspan attribute of the first instance
		                first_instance.attr('rowspan', rowspan);
		            } else { // 전이랑 다르면 다시 rowspan 1로
		                first_instance = dimension_td;
		                rowspan = 1;
		            }
				}

	        });
    	}
	}

	/**
	 * 행 병합
	 */
	ctrl.mergeColSpan = function(tableId){
		var self = et.vc;
	 	var dimension_row = null;
		var rowCount = $(tableId+" tr").length; // 총 행 수

		for (dimension_row = 0; dimension_row < rowCount; dimension_row++) {

		    // first_instance는 동일한 td의 첫 번째 인스턴스를 보관합니다.
		    var first_instance = null;
		    var colspan = 1;

		    // 각 행에 대해 반복합니다.
		    $(tableId+" tr:nth-child(" + (dimension_row + 1) + ")").find('td').each(function () {

		        var dimension_td = $(this);

		        if (first_instance === null) {
		            // 첫 번째 셀이어야 합니다.
		            first_instance = dimension_td;
		        } else if (dimension_td.text() === first_instance.text()) {// 현재 td가 이전과 동일

		            dimension_td.attr('hidden', true); // 현재 td를 제거
		            ++colspan;
		            // 첫 번째 인스턴스의 colspan 속성을 증가시킵니다.
		            first_instance.attr('colspan', colspan);
		        } else { // 이전 것과 다르다면, colspan을 1로 재설정하고 first_instance를 업데이트합니다.
		            first_instance = dimension_td;
		            colspan = 1;
		        }

		        // 병합한 셀의 정렬
		        if (colspan > 1) {
		            first_instance.css('text-align', 'center');
		        }
		    });
		}

	}

	ctrl.btnExcelDownHandler = function(){
		var self = et.vc;
		$("#tbList").tableExport({
			type:"excel",
			fileName:"excelTest240122"
		});
	}



	// ======================================================================

	return ctrl;
}));