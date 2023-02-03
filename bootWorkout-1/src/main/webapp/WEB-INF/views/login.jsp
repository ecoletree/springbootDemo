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
	var doLogin = function(){
		
		var user_id = $("#iptUserID").val();
		var user_pwd = $("#iptUserPwd").val();
		var param = {};
		param.user_id = user_id;
		param.user_pw = user_pwd;
		 $.ajax({
		        url : "/doLogin",
		        type : "POST",
		        data : JSON.stringify(param) ,
		        dataType: 'text',
		        contentType : "application/json; charset=UTF-8",
		        success : function (data) {
		        	var result = JSON.parse(data);
		        	doLoginSuccessHandler(JSON.parse(data));
		            }//success
		        });  //ajax
		
			
	}
	/*
	 * ��ư Ŭ�� - success function
	 */
	var doLoginSuccessHandler = function(result){
		
		var msg = result.resultMsg;
		
		var mainView = window.location.origin + "/main";
		
		if(msg === "log_in_success" ){
			window.location = mainView;
		}else if(msg === "no_user_data"){
			alert(msg+"\n���̵�� ��й�ȣ�� Ȯ���� �ּ���.");
		}else if(msg === "logged_in_data"){
			var check = confirm(msg+"\n�̹� �α��� �� ���̵��Դϴ�. �ٽ� �α��� �Ͻðڽ��ϱ�?");
			
			if(check === true){
				window.location = mainView;
			}
		}	
	}
	/*
	 * ����Ű
	 */	
	var enterKeyPress = function() {
		if (window.event.keyCode == 13) {
	    	$("#btnLogin").trigger("click");
	    }
	}	
</script>
</head> 
<body>
<h1>Spring boot</h1>
<h3>�α���</h3>
<div>
	���̵� : <input id="iptUserID" >
	�н����� : <input id="iptUserPwd" onkeydown="enterKeyPress()" >
	<button id="btnLogin" onclick="doLogin()">�α���</button>
</div>
</body>

</html>