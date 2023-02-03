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
	 * 버튼 클릭 - success function
	 */
	var doLoginSuccessHandler = function(result){
		
		var msg = result.resultMsg;
		
		var mainView = window.location.origin + "/main";
		
		if(msg === "log_in_success" ){
			window.location = mainView;
		}else if(msg === "no_user_data"){
			alert(msg+"\n아이디와 비밀번호를 확인해 주세요.");
		}else if(msg === "logged_in_data"){
			var check = confirm(msg+"\n이미 로그인 된 아이디입니다. 다시 로그인 하시겠습니까?");
			
			if(check === true){
				window.location = mainView;
			}
		}	
	}
	/*
	 * 엔터키
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
<h3>로그인</h3>
<div>
	아이디 : <input id="iptUserID" >
	패스워드 : <input id="iptUserPwd" onkeydown="enterKeyPress()" >
	<button id="btnLogin" onclick="doLogin()">로그인</button>
</div>
</body>

</html>