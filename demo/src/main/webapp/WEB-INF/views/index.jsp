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

test<br>
cp: ${pageContext.request.contextPath }
<button id="btnList"> btngetList</button>
<button id="btnList2"> btngetList2</button>
<button id="btn"> testalert</button>
<button id="btn1"> Login</button>
<button id="btn2"> TEST</button>
<input id="iptId">
<input id="iptPw" value="ccc12345!!!">
<img  src="${cp}/resources/ecoletree/img/btn_cal.png">
<div>
	<input type="file" id="txtFile"/>
	<button id="btnFile">upload</button>
</div>

<div>
	<input type="text" id="iptLink"/>
	<button id="btnLink">브로큰</button>
</div>

<div id="divGrid">
	<table id="tbList" class="ecoleScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
		<thead>
			<tr>
				<th style="width: 8%" title="캠페인 ID"><div>상태</div></th>
				<th style="width: 32%" title="캠페인 ID"><div>페이지</div></th>
				<th style="width: 40%" title="캠페인 명"><div>링크</div></th>
				<th style="width: 20%" title="캠페인 명"><div>메시지</div></th>
			</tr>
		</thead>
		<tbody class="cursorPoint">
		</tbody>
	</table>
</div>

<script type="text/javascript">

$.getScript(getContextPath() + "/resources/service/index.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "home") {
		var inter = setInterval(function(){
			ecoletree.promiseInit().then(function(){
				ecoletree.vc.init();
				clearInterval(inter);
			}, function (){})
		},300);
	} else {
		console.log("vc's name is not home : " + ecoletree.vc.name);
	}
}).fail(function(jqxhr, settings, exception) {
	console.log(arguments);
});
</script>
<!-- alert 모달 -->
</body>
</html>