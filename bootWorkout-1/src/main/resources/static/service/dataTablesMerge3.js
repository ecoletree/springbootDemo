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

	// ============================== 화면 컨트롤 ==============================
	/**
	 * init VIEW
	 */
	ctrl.init = function(initData) {
		var self = et.vc;
		self.createDataTablesMerging();
		$("#btn").click(self.btnClickDDDHandler);
	};
	// ==================================================================================


	ctrl.createDataTablesMerging = function(){
		var self = et.vc;
		self.table = $('#example').dataTable({
		            "scrollY": "600px",
		            "responsive" : false,
		            "ordering" : false,
		            "paging" : false,
		            "searching" : false
	    });

	    self.mergeRowSpan('#example');
	    self.mergeColSpan('#example');
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

		        // Apply text-align: center to the merged cell's data
		        if (colspan > 1) {
		            first_instance.css('text-align', 'center');
		        }
		    });
		}

	}

	ctrl.btnClickDDDHandler = function(){
		var self = et.vc;
		//tableExport gitHub: https://github.com/hhurz/tableExport.jquery.plugin/blob/master/tableExport.js
		$("#example").tableExport({
			type:"excel"
		});
	}

	// ======================================================================

	return ctrl;
}));