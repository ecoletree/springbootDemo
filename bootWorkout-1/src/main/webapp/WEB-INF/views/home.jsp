<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">


	/*
	 * 버튼 클릭 
	 */
	var btnTest = function(){
		
		$("#divResult").empty();
		var menu_name = $("#iptTest").val();
		var param = {};
		param.menu_name = menu_name;
		 $.ajax({
		        url : "/getTest",
		        type : "POST",
		        data : JSON.stringify(param) ,
		        dataType: 'text',
		        contentType : "application/json; charset=UTF-8",
		        success : function (data) {
		        	var result = JSON.parse(data);
		        	btnTestSuccessHandler(JSON.parse(data));
		            }//success
		        });  //ajax
		
			
	}
	/*
	 * 버튼 클릭 - success function
	 */
	var btnTestSuccessHandler = function(result){
		var sp = $("<div></div>")
         	console.log(result.message);
		
            if(result.message === "success"){
            	var ol = $('<ol></ol>');
            	var data = result.data;
            	for(var i in data){
            		var li = $("<li>"+JSON.stringify(data[i])+"</li>");
            		ol.append(li);
            	}                   
            	 var result = $("<h3>검색결과: "+data.length+"건</h3>");
            	 sp.append(result).append(ol);
            	
            }else{
            	 var result = $("<h3>검색결과 없음</h3>");
            	 sp.append(result);
            }
    	$("#divResult").append(sp)
	}
	/*
	 * 엔터키
	 */	
	var enterKeyPress = function() {
		if (window.event.keyCode == 13) {
	    	$("#btnSearch").trigger("click");
	    }
	}	
</script>
</head> 
<body>
<h1>Spring boot</h1>
<div>
	메뉴명 : <input id="iptTest"  onkeydown="enterKeyPress()">
	<button id="btnSearch" onclick="btnTest()">메뉴명 조회</button>
	<div id="divResult"></div>
</div>
</body>

</html>