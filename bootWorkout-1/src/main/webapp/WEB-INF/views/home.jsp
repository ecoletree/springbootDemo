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
<!-- <script type="text/javascript" src="jquery.min.js"></script> -->
<script type="text/javascript" src="/date_range_picker/jquery-date-range-picker/src/jquery.daterangepicker.js"></script>
<script type="text/javascript"  src="/util/dateRangePicker.js"></script>
<!--  -->


</head> 
<body>
<button id="btnSelectGroupSample">트리구조 셀렉트 유틸 샘플 페이지 --></button>
<button id="btnJqueryDateRangePickers">jquery date picker --></button>
<button id="btnCompareDateRangePickers">데이트 피커 플러그인 비교 --></button>
<br><br>
<div style="margin-left : 20px ">
<input id="iptPwd" value="6003">
	<button id="btnLogin">login</button>
</div>
<br><br>
<div>

</div>
<!-- <script src="//code.jquery.com/jquery-1.11.0.min.js"></script> -->
<!-- 끝: 작성하기 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/home.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "home") {
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