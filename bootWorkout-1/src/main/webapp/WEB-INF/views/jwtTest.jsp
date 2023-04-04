<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	var xss_token ;
	/*
	 * login + create token 
	 */
	var doLogin = function(){
		var user_id = $("#iptUserID").val();
		var user_pwd = $("#iptUserPwd").val();
		var param = {};
		param.user_id = user_id;
		param.user_pw = user_pwd;
		 $.ajax({
		        url : "/jwt/login",
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
	 * ���⿡�� 
	 * refresh :localStorage Ȥ�� cookie(Http only) �� ����
	 * access : �ڹٽ�ũ��Ʈ private ������ ����
	 * access�� ���ΰ�ħ �ÿ� ������Ƿ� ��������� refresh�� ��߱� �޴� api ������ ��
	 */
	var doLoginSuccessHandler = function(result){
		
		
		var access_token = result.access_token;
		var refresh_token = result.refresh_token;

		// access token - private ���� ����
		this.xss_token = xss_token(access_token);
		
		// refresh token - cookie(httpOnly)����
		document.cookie = "refresh_token="+refresh_token+";";
		console.log("refresh_token:"+getCookie("refresh_token")); // ��Ű
		
		$("#btnAccess,#btnRefresh").prop("disabled",false);
		
	}
	/*
	 * button : access token check 
	 */	 
	var accessTokenCheck = function(){
		var token = this.xss_token.get_token();
		var param = {};
		param.type = "access";
		param.token = token;
		
		this.checkTokenAjax(param);
		
	}
	/*
	 * button : refresh token check 
	 */	
	var refreshTokenCheck = function(){
		//��Ű �������� ,, ��Ű ������ �ٷ� false ���� �α���
		var token = getCookieValue("refresh_token");
		var param = {};
		param.type = "refresh";
		param.token = token;
		
		this.checkTokenAjax(param);
	}

	/*
	 * token check ajax
	 */	
	var checkTokenAjax = function(data){
		
		$.ajax({
			url:"/jwt/check",
			type :"POST",
			data : JSON.stringify(data),
			dataType:"text",
			contentType:"application/json; charset=UTF-8",
			success: function(data){
				checkTokenAjaxSuccessHandler(JSON.parse(data));
			}
		});
	        
	        
	}
	/*
	 * token check ajax success handler
	 */	
	var checkTokenAjaxSuccessHandler = function(result){
		var type = result.type;
		var valid = result.valid;
		$("#divResult").empty();
		var str = valid === true ? "<h3>"+type+" token ::: true</h3>" :"<h3>"+type+" token ::: false</h3>";
		$("#divResult").append(str);
		
	}
	/*
	 * cookie ���� 
	 */
	var getCookie = function(name) {
	  let matches = document.cookie.match(new RegExp(
	    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
	  ));
	  return matches ? decodeURIComponent(matches[1]) : undefined;
	}

	/*
	 * private vaiable 
	 */
	function xss_token(token){
		return{//�����Լ� 2���� ���� ��ü�� ��ȯ��
			get_token : function(){ //�����Լ�1
				return token;
			},
			set_token : function(_token){ //�����Լ�2
				token = _token
			}
		}
	} //��ü �ȿ� �Լ��� �����Ǿ� �ִ� ���� 
	
	/*
	 * cookie value�� �������� 
	 */	
	var getCookieValue = (key) => {
		  let cookieKey = key + "="; 
		  let result = "";
		  const cookieArr = document.cookie.split(";");
		  
		  for(let i = 0; i < cookieArr.length; i++) {
		    if(cookieArr[i][0] === " ") {
		      cookieArr[i] = cookieArr[i].substring(1);
		    }
		    
		    if(cookieArr[i].indexOf(cookieKey) === 0) {
		      result = cookieArr[i].slice(cookieKey.length, cookieArr[i].length);
		      return result;
		    }
		  }
		  return result;
		}	

    var sendToken = function(){
    	var access_token = this.xss_token.get_token();
    	var refresh_token = getCookieValue("refresh_token");
    	$.ajax({
    		url:"http://localhost:8080/jwt/test",
    		type:"POST",
    		dataType :"text",
    		contentType : "application/json; charset=UTF-8",
    		beforeSend : function(xhr){
    			xhr.setRequestHeader("Access-Control-Allow-Headers", "origin, x-requested-with, content-type, accept");
    			xhr.setRequestHeader("access_token", access_token); 
    			xhr.setRequestHeader("refresh_token", refresh_token); 
    			xhr.setRequestHeader("Content-type","application/json; charset=UTF-8");
    		},
    		success : function (data) {
	        	var result = JSON.parse(data);
	        	sendSuccessHandler(JSON.parse(data));
            }//success
    		
    	});
		
			
    }
    var sendSuccessHandler = function(result){
   		$("#divResult").empty();
    	if(result.valid === true){
    		$("#divResult").append("<h3>��ū ���� ����</h3>");
    	}else{
    		$("#divResult").append("<h3>��ū ��ȿ�Ⱓ ����</h3>");
    	}
    	
    }
</script>
</head> 
<body>
<h1>JWT Oauth send test bootTest 9999 to 8080</h1>
<div>
<div>
	���̵� : <input id="iptUserID" >
	�н����� : <input id="iptUserPwd"  >
	<button id="btnLogin" onclick="doLogin()">�α���</button>
</div>
<div>
	<button id="btnAccess" onclick="accessTokenCheck()" disabled="disabled">access token check</button>
	<button id="btnRefresh" onclick="refreshTokenCheck()" disabled="disabled">refresh token check</button>
	<button id="btnSendRedirect" onclick="sendToken()">url�̵� �� ��ū ����</button>
	<div id="divResult"></div>
</div>
</div>
</body>

</html>