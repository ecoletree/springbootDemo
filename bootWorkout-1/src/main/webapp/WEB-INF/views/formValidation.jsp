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
<div class="container">
<form id="testForm">
	<br>
	<br>
	<h4>input</h4>
	<input type="text" name="iptCheck" class="form-control" placeholder="숫자입력하기"/><br>
	<div>
	<h4>selectbox</h4>
	<select id="selNum" name="selCheck" class="form-control" >
		<option value = "">selectbox</option>
		<option value="1">1</option>
		<option value="2">2</option>
	</select>
	<br>
	<select id="selStime" name="selTime" style="width:80px; float:left;" class="form-control" >
		<option value = "01">01</option>
		<option value = "23">23</option>
	</select> <p style="float:left;">:</p>
	<select id="selSmin" name="selTime" style="width:80px; float:left;" class="form-control" >
		<option value = "00">00</option>
	</select>
	<p style="float:left;">&nbsp;&nbsp;</p>
	<select id="selEtime" name="selTime" style="width:80px; float:left;" class="form-control" >
		<option value = "01">01</option>
		<option value = "23">23</option>
	</select> <p style="float:left;">:</p>
	<select id="selEmin" name="selTime" style="width:80px;  " class="form-control" >
		<option value = "00">00</option>
	</select>
	</div>
	<br>
	<h4>addmethod</h4>  
	<input type="text" name="iptmaxLength" class="form-control" placeholder="maxLength (5)" /><br>
	<br>
	<br>
	<div>
	<h4>checkbox</h4>
		<input type="checkbox" name="color" value ="red"> red
		<input type="checkbox" name="color" value ="yellow"> yellow
		<input type="checkbox" name="color" value ="green">green
		<input type="checkbox" name="color" value ="bule">bule
	</div>
	<br>
	<h4>textarea</h4>
	<textarea class="form-control" name="txtCheck" rows="" cols="" placeholder="textarea"></textarea>
	<br>
	<h4>date</h4>
	<input id="startDate" name="startDate" type="date" />
	<input id="endDate"  name="endDate" type="date" />
	<br><br>
	<h4>file upload</h4>
	<input id="iptFile" name="file" type="file" />
	<br>
	<button type="submit" class="btn btn-primary">submit</button>
</form>
</div>


</body>

<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/formValidation.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "formValidation") {
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
