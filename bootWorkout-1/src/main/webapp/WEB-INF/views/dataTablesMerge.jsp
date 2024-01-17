<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<title>Insert title here</title>
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />


</head>
<body>
<!-- <div style="margin-left : 20px; width:400px " > -->


	<button id ="btnExcelDown" type="button" class="btnGray btnBlock">엑셀 다운로드</button>
<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 5%" title="status"><div>status</div></th>
 							<th style="width: 15%" title="state"><div>state</div></th>
							<th style="width: 30%" title="url"><div>url</div></th>
							<th style="width: 30%" title="parent"><div>linked from</div></th>
							<th style="width: 20%" title="source"><div>source</div></th>
					</thead>
					<tbody class="cursorPoint">
					</tbody>
				</table>
			</div>
		</div>
<!-- </div> -->
<!-- 끝: 작성하기 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/dataTablesMerge.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "dataTablesMerge") {
			var inter = setInterval(function(){
				 ecoletree.promiseInit()
				.then(function(){
					clearInterval(inter);
					ecoletree.vc.init(${initData});
				}, function (){})
			},300);

		} else {
			console.log("vc's name is not index : " + ecoletree.vc.name);
		}
	}).fail(function(jqxhr, settings, exception) {
		console.log(arguments);
	});
</script>
</body>

</html>