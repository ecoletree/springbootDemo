<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<title>dynamic DOM - bootWorkout</title>
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />
<c:import url="/common.moduleItem.sp" charEncoding="UTF-8" />
<script src="https://unpkg.com/mustache@latest"></script>
</head>
<body>
<br>
<br>



<div id="container"></div>

<br>
<br>

</body>

<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/jsModuleTest.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "jsModuleTest") {
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
