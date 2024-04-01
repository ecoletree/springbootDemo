<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<title>XSS DEFENDER TEST - bootWorkout</title>
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />
<!-- <script type="text/javascript" src="/dom_purify/purify.js"></script> -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/dompurify/2.3.3/purify.min.js"></script> -->
</head>
<body>
<div class="container">
<br><br>
<ul style="list-style-type: circle; margin-left : 20px ;">
	<li><a href="https://cdnjs.cloudflare.com/ajax/libs/dompurify/2.3.3/purify.js"> dom_purify 사용</a></li>
	<br>
	<li>XSS validation : validationUtil.js 라이브러리에 메소드 추가, 상수 추가해서 처리 -> 공통라이브러리에서 가져옴</li>
	<li>convertXSSFormToObject 메소드 추가 : 폼데이터에서 컨버팅할때 replace하는 식으로 처리</li>
	<li>사용 라이브러리 내부 경로</li>
	<p>/util/validationUtil.js</p>
	<p>/dom_purify/purify.js</p>
	</ul>
<div></div>
<br>
<br>
<form id="xssForm">
	<fieldset>
		<div class="formWrap">
			<label class="formLabel">sanitize_validator<span class="require" style="color:red;">*</span></label>
			<div class="formInput">
				<input type="text" name="xss_test" >
			</div>
		</div>
<!-- 		<div class="formWrap"> -->
<!-- 			<label class="formLabel">sanitize<span class="require" style="color:red;">*</span></label> -->
<!-- 			<div class="formInput"> -->
<!-- 				<input type="text" name="xss_sanitize" > -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="formWrap">
			<label class="formLabel">convert<span class="require" style="color:red;">*</span></label>
			<div class="formInput">
				<input type="text" name="xss_test2" >
			</div>
		</div>
	</fieldset>
</form>
<div>
<input id="btnSubmit" type="button" value ="DEFEND">
</div>
	<div id="divResult"></div>
</div>

</body>

<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/defendXSS.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "defendXSS") {
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
