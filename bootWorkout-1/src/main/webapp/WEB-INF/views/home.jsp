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
</head> 
<body>
<br><br>
<div style="margin-left : 20px ">
<div>
<h4>조회</h4>
<select id="selGroup" data-groupname ="selGroup"></select>
<select id="selCenter" data-groupname ="selGroup" ></select>
<select id="selTeam" data-groupname ="selGroup" ></select>
<button id="btnClear"data-groupname ="selGroup">select 초기화 (추가 팝업)</button>
</div>
<br>
<div>
<h4>수정</h4>
<select id="selGroup2" data-groupname ="selGroup2"></select>
<select id="selCenter2" data-groupname ="selGroup2" ></select>
<select id="selTeam2" data-groupname ="selGroup2" ></select>
<button id="btnClear2"data-groupname ="selGroup2">select 초기화 (추가 팝업)</button>
</div>
<div>
<input id="iptGroup"  data-groupname ="iptGroup"/>
<input id="iptCenter"  data-groupname ="iptGroup"/>
<input id="iptTeam"  data-groupname ="iptGroup"/>
<button id="btnEdit">select 수정</button>

</div>
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