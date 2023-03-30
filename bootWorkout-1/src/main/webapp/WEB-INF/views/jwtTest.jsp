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
	 * 여기에서 
	 * refresh :localStorage 혹은 cookie(Http only) 에 저장
	 * access : 자바스크립트 private 변수로 저장
	 * access는 새로고침 시에 사라지므로 사라졌을때 refresh로 재발급 받는 api 만들어야 함
	 */
	var doLoginSuccessHandler = function(result){
		
		
		var access_token = result.access_token;
		var refresh_token = result.refresh_token;

		// access token - private 변수 저장
		this.xss_token = xss_token(access_token);
		
		// refresh token - cookie(httpOnly)저장
		document.cookie = "refresh_token="+refresh_token+";";
		console.log("refresh_token:"+getCookie("refresh_token")); // 쿠키
		
		$("#btnAccess").prop("disabled",false);
		
	}
	/*
	 * access token check
	 */	 
	var accessTokenCheck = function(){
		var token = this.xss_token.get_token();
		this.checkTokenAjax(token);
		
	}
	/*
	 * refresh token check 
	 */	
	var refreshTokenCheck = function(){
		debugger;
		
	}
	/*
	 * token check ajax
	 */	
	var checkTokenAjax = function(data){
		
		var param = {};
		param.token = data;
		$.ajax({
			url:"/jwt/check",
			type :"POST",
			data : JSON.stringify(param),
			dataType:"text",
			contentType:"application/json; charset=UTF-8",
			success: function(data){
				var result = JSON.parse(data);
				//access인지 refresh인지 구분자 넣는거 어떻게 할건지..?
						
			}
		});
	        
	        
	}
	
	/*
	 * cookie 저장 
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
		return{//내부함수 2개를 가긴 객체를 반환함
			get_token : function(){ //내부함수1
				return token;
			},
			set_token : function(_token){ //내부함수2
				token = _token
			}
		}
	} //객체 안에 함수가 구현되어 있는 형태 
	

    
</script>
</head> 
<body>
<h1>JWT Oauth login</h1>
<div>
<div>
	아이디 : <input id="iptUserID" >
	패스워드 : <input id="iptUserPwd"  >
	<button id="btnLogin" onclick="doLogin()">로그인</button>
</div>
<div>
	<button id="btnAccess" onclick="accessTokenCheck()" disabled="disabled">access token check</button>
	<button id="btnRefresh" onclick="refreshTokenCheck()" disabled="disabled">refresh token check</button>
</div>
</div>
</body>

</html>