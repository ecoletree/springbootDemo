<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<title>Insert title here</title>
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />

<!-- <script  src="http://code.jquery.com/jquery-latest.min.js"></script> -->
<!-- 4.jQuery Date Range Picker -->
<link rel="stylesheet" href="/date_range_picker/jquery-date-range-picker/dist/daterangepicker.min.css">
<script type="text/javascript" src="/date_range_picker/moment/min/moment.min.js"></script>
<script type="text/javascript" src="/date_range_picker/jquery-date-range-picker/src/jquery.daterangepicker.js"></script>
<script type="text/javascript"  src="/util/dateRangePicker.js"></script>
<!--  -->


</head>
<body>
<br><br>
<div style="margin-left : 20px ">
	<h4>SSE 수동 구현 (Server-Sent-Event) </h4>
	<br><br>
	<div>
		<div>
			<ul style="list-style-type: circle; margin-left : 20px ;">
			<li>sse connect 수동삭제</li>
			</ul>
		</div>
		<div style="margin-top : 15px ;">
		id :
		<input type="text" id="iptSseId" value="kh201"/>
		<button id="btnRemove">remove</button>
		</div>

	</div>

</div>

<!-- <script src="//code.jquery.com/jquery-1.11.0.min.js"></script> -->
<!-- 끝: 작성하기 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/sseEmitterManual.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "sseEmitterManual") {
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