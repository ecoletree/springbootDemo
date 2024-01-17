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
	ctrl.dummyData = [
	  { start_date: '2023-04-20', user_name: 'Bob Johnson', position: 'Analyst', age: 28, salary: 55000 },
	  { start_date: '2023-04-20', user_name: 'Mia Harris', position: 'Analyst', age: 25, salary: 48000 },
	  { start_date: '2023-04-20', user_name: 'David Lee', position: 'Analyst', age: 26, salary: 50000 },
	  { start_date: '2023-04-20', user_name: 'Grace Turner', position: 'Analyst', age: 29, salary: 58000 },
	  { start_date: '소계', user_name: '소계', position: '소계', age: "소계", salary: 53000 },
	  { start_date: '2023-04-28', user_name: 'John Doe', position: 'Engineer', age: 30, salary: 60000 },
	  { start_date: '2023-04-28', user_name: 'Alice Brown', position: 'Developer', age: 32, salary: 65000 },
	  { start_date: '2023-04-28', user_name: 'Emma White', position: 'Developer', age: 28, salary: 62000 },
	  { start_date: '2023-04-28', user_name: 'Henry Harris', position: 'Developer', age: 31, salary: 70000 },
	  { start_date: '2023-04-28', user_name: 'Kate Miller', position: 'Developer', age: 34, salary: 75000 },
	  { start_date: '소계', user_name: '소계', position: '소계', age: "소계", salary: 53000 },
	  { start_date: '2023-05-05', user_name: 'Jane Smith', position: 'Manager', age: 35, salary: 80000 },
	  { start_date: '2023-05-05', user_name: 'Charlie Wilson', position: 'Manager', age: 40, salary: 90000 },
	  { start_date: '2023-05-05', user_name: 'Frank Taylor', position: 'Manager', age: 33, salary: 85000 },
	  { start_date: '2023-05-05', user_name: 'Isabel Walker', position: 'Manager', age: 36, salary: 92000 },
	  { start_date: '2023-05-05', user_name: 'Leo Turner', position: 'Manager', age: 38, salary: 88000 },
	  { start_date: '소계', user_name: '소계', position: '소계', age: "소계", salary: 53000 },
	];

	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;

		self.createDataTables();
	};
	// ==================================================================================
	ctrl.createDataTables = function(){
		var self = et.vc;
		var testData = self.dummyData;

		var processedData = testData.map(function (item, index, array) {
		var rowspan = item.start_date === (array[index + 1]?.start_date || '') ? 'Y' : 'N';
		return { ...item, rowspan: rowspan };
		});

		var columns = [
			{data : "start_date" },
			{data : "user_name"},
			{data : "position"},
			{data : "age"},
			{data : "salary"},
		];

		var table = et.createDataTableSettings(columns, null, {}, self.dataTableDrawCallback,"",false,processedData);
		table.paging = false;
		table.rowGroup=
		{
	      "dataSrc": "start_date", // start_date 열을 기준으로 그룹화
	      "startRender": function(rows, group) {
	        var rowspan = rows.count(); // 그룹에 속하는 로우의 개수
	        return '<tr><td rowspan="' + rowspan + '">' + group + '</td></tr>';
	      },
	      "endRender": null // 그룹의 끝에 추가할 내용이 있을 경우 설정
	    }
		console.log(table);
		debugger;
		$("#tbList").DataTable(table);
	}

	ctrl.dataTableDrawCallback = function(settings){
		var self = et.vc;
//		debugger;

	}






	// ======================================================================

	return ctrl;
}));