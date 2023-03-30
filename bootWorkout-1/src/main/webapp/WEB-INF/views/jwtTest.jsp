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
	 * ��ư Ŭ�� 
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
		debugger;
		// refresh token - cookie(httpOnly)����
		document.cookie = "refresh_token="+refresh_token+";";
		console.log("refresh_token:"+getCookie("refresh_token")); // ��Ű
		
		
		
	}
	 
	var accessTokenCheck = function(){
		
	}
	
	var refreshTokenCheck = function(){
		
	}
// 	//===== cookie
	var getCookie = function(name) {
	  let matches = document.cookie.match(new RegExp(
	    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
	  ));
	  return matches ? decodeURIComponent(matches[1]) : undefined;
	}

// 	//===== private vaiable

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
	
	
	

    
</script>
</head> 
<body>
<h1>JWT Oauth login</h1>
<div>
<div>
	���̵� : <input id="iptUserID" >
	�н����� : <input id="iptUserPwd"  >
	<button id="btnLogin" onclick="doLogin()">�α���</button>
</div>
<div>
	<button id="btnLogin" onclick="accessTokenCheck()">access token check</button>
	<button id="btnLogin" onclick="refreshTokenCheck()">refresh token check</button>
</div>
</div>
</body>

</html>