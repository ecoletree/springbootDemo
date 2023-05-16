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
<!-- 1. Date Range Picker -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<!--  -->
<!-- 2. Litepicker : npm 설치 오류나서 그냥 cdn붙임-->
<script src="https://cdn.jsdelivr.net/npm/litepicker/dist/litepicker.js"></script>
<!--  -->
<!-- 4.jQuery Date Range Picker -->
<link rel="stylesheet" href="/date_range_picker/jquery-date-range-picker/dist/daterangepicker.min.css">
<script type="text/javascript" src="/date_range_picker/moment/min/moment.min.js"></script>
<!-- <script type="text/javascript" src="jquery.min.js"></script> -->
<script type="text/javascript" src="/date_range_picker/jquery-date-range-picker/dist/jquery.daterangepicker.min.js"></script>
<!--  -->
<!-- 5. light picker : npm install lightpick -->
<link rel="stylesheet" type="text/css" href="/date_range_picker/lightpick/css/lightpick.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script src="/date_range_picker/lightpick/lightpick.js"></script>
<!--  -->


</head> 
<body>
<br><br>
<div style="margin-left : 20px ">
https://ourcodeworld.com/articles/read/1326/top-5-best-open-source-date-range-pickers
	<br><br>
	<div>
		<h4>1. Date Range Picker</h4>
		<div>
			<ul style="list-style-type: circle; margin-left : 20px ;">
			<li>https://www.daterangepicker.com/</li>
			<li>커스텀 리스트로 언어 변경 가능</li>
			<li>상위 div id로</li>
			<li>maxSpan으로 최대 선택일 설정 가능</li>
			<li>날짜 형태 변경 가능</li>
			<li>apply, clear 기능은 내부에 있고 label명 변경 가능, 사용은 트리거</li>
			<li>시작,종료일 순서 제한하는거 기본으로 되어있음</li>
			
			</ul>
		</div>
		<br>

		<div style="margin-top : 15px ;">
		</div>
		<div id="divDatePickerRange">
		calendar :
		<input id = "iptStartDate" type="text" name="daterange" />
		<input id = "iptEndDate" type="text" name="daterange" />
		<button id="btnSetDatePickerDate">set date range</button>
		</div>
	</div>
	
	<br><br>
	<div>
		<h4>2. Litepicker</h4>
	</div>

	<br><br>
	<div>
		<h4>3. Hotel Datepicker</h4>
	</div>
	<br><br>
	<div>
		<h4>4. jQuery Date Range Picker</h4>
		<div>
			<ul style="list-style-type: circle; margin-left : 20px ;">
			<li>https://github.com/longbill/jquery-date-range-picker</li>
			<li>사용법 : https://longbill.github.io/jquery-date-range-picker/</li>
			<li>언어 셋팅 기본으로 있음 (한국어 "ko" 영어 "en" 일본어 "ja" 등등)</li>
			<li>상위 div id로</li>
			<li>선택 범위 min,max 설정 가능</li>
			<li>초기 값 설정 커스텀 되는지</li>
			<li>날짜 형태 변경 가능</li>
			<li>시작,종료일 순서 제한하도록 설정하는거 있음</li>
			<li>개별 버튼(open,close,clear 등) 설정 가능</li>
<!-- 			<li>getValue : 데이터 가져올 때 형태 커스텀 할 수 있음</li> -->
			<li>setValue : 화면에 만든 input에 선택한 날짜값 나타 낼때 커스텀 할 수 있음 </li>
			</ul>
		</div>
		<br>

		<div>
		<input type="text" id="iptSetJQueryRange1"/>-
		<input type="text" id="iptSetJQueryRange2"/>
		<button id="btnJqSetRangeDate">set date range</button>
		</div>
		
		<div style="margin-top : 15px ;">
		<button id="btnJqOpen">open</button>
		<button id="btnJqClose">close</button>
		<button id="btnJqClearSection">clear section</button>
		<button id="btnJqDestroy">destroy</button>
		<button id="btnJqResetMonth">reset months</button>
		</div>
		<div id="divJQueryRange">
		calendar :
		<input type="text" id="iptJQueryRange1"/>-
		<input type="text" id="iptJQueryRange2"/>
		</div>

	</div>
	
		<br><br>
	<div>
		<h4>5. Lightpick</h4>
		<div>
			<ul style="list-style-type: circle ;margin-left : 20px ;">
			<li>https://wakirin.github.io/Lightpick/</li>
			<li> auto, system language 외에 러시아어</li>
			<li> 날짜 포맷 변경 됨</li>
			<li> 달력 보여지는 갯수 조절 됨</li>
			<li> 시작,종료일 순서 제한하도록 설정하는거 있음 </li>
			<li> 달력 내부 버튼 label 설정하는거 있음 </li>
			</ul>
		</div>
		<br>
		calendar :
		<input type="text" id="iptLightPick1"/>
		<input type="text" id="iptLightPick2"/>
		<p id="result-3"></p>
		<button id="btnSetLightPick">날짜 셋팅</button>
	</div>
</div>
	
<!-- <script src="//code.jquery.com/jquery-1.11.0.min.js"></script> -->
<!-- 끝: 작성하기 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/datePickers.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "datePickers") {
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