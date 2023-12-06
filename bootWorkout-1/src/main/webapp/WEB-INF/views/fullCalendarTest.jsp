<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<title>Insert title here</title>
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js"></script> -->
<script type="text/javascript" src="/full-calendar/full-calendar-core.js"></script>
<script type="text/javascript" src="/full-calendar/full-calendar-daygrid.js"></script>
<!-- 4.jQuery Date Range Picker -->
<!--  -->


</head> 
<body>
<br><br>
<div style="margin-left : 20px ">
	<h4>Full Calendar</h4>
	<br><br>
	<ul style="list-style-type: circle; margin-left : 20px ;">
		<li>문서 : <a href="https://fullcalendar.io/">https://fullcalendar.io/</a></li>
		<li>사용법 : <a href="https://e-7-e.tistory.com/70"> https://e-7-e.tistory.com/70</a></li>
	</ul>
	<br><br>
	<div>
	 <div id="yrModal" style="display: none;" >
        <div id="cont" style="text-align: center;  ">
            <br>
            <h1>예린 모달 모달</h1>
            시작일 <input type="text" id="schStart" value=""><br>
            종료일 <input type="text" id="schEnd" value=""><br>
            제목 <input type="text" id="schTitle" value=""><br>
            하루종일 <input type="checkbox" id="allDay"><br>
            배경색<input type="color" id="schBColor" value="">
            글자색<input type="color" id="schFColor" value="">
            <button onclick="fCalAdd()">추가</button><br>
            <button onclick="fMClose()">X</button>
        </div>
    </div>
		<div id="calendar"></div>
	</div>

</div>
	
<!-- <script src="//code.jquery.com/jquery-1.11.0.min.js"></script> -->
<!-- 끝: 작성하기 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/fullCalendarTest.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "fullCalendarTest") {
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