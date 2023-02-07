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
	 * ��ư Ŭ�� 
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
		            },//success
		        error: function(request,status,error,response){
		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		        	if(status === "error"){
		        		if (request.status === 500) {
		        			var view =window.location.origin +"/error/500";
		        			window.location = view;
						}
		        	}

		        }
		        });  //ajax
		
			
	}
	/*
	 * ��ư Ŭ�� - success function
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
            	 var result = $("<h3>�˻����: "+data.length+"��</h3>");
            	 sp.append(result).append(ol);
            	
            }else{
            	 var result = $("<h3>�˻���� ����</h3>");
            	 sp.append(result);
            }
    	$("#divResult").append(sp)
	}
	/*
	 * ����Ű
	 */	
	var enterKeyPress = function() {
		if (window.event.keyCode == 13) {
	    	$("#btnSearch").trigger("click");
	    }
	}	
	var doLogout = function(){
		var view = window.location.origin + "/login/logout";
		window.location = view;
	}
</script>
</head> 
<body>
<h1>Spring boot</h1>
<div>
���̵� : ${sessionVO.user_id}
</div>
<div>
	�޴��� : <input id="iptTest"  onkeydown="enterKeyPress()">
	<button id="btnSearch" onclick="btnTest()">�޴��� ��ȸ</button>
	<div id="divResult"></div>
</div>
<div > 
	<button id="btnLogout" onclick="doLogout()">�α׾ƿ�</button>
</div>
</body>

</html>