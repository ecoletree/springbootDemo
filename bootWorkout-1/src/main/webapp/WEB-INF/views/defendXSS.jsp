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
