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
		//쿠키 가져오기 ,, 쿠키 없으면 바로 false 띄우고 로그인
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
	
	/*
	 * cookie value만 가져오기 
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

    
</script>
</head> 
<body>
<h1>JWT Oauth login</h1>
<h4>
<br>로그인 하면 access/refresh 토큰이 발급되고 refresh토큰은 서버에 저장됨. 
<br>access 토큰은 2분 refresh 토큰은 10분간 유효함.
<br>브라우저에서 access는 private변수에 저장, refresh는 쿠키에 저장. 
<br>토큰 검증 true/false 확인하는 것까지만 해둠.
</h4>
<div>
<div>
	아이디 : <input id="iptUserID" >
	패스워드 : <input id="iptUserPwd"  >
	<button id="btnLogin" onclick="doLogin()">로그인</button>
</div>
<div>
	<button id="btnAccess" onclick="accessTokenCheck()" disabled="disabled">access token check</button>
	<button id="btnRefresh" onclick="refreshTokenCheck()" disabled="disabled">refresh token check</button>
	<div id="divResult"></div>
</div>
</div>
</body>

</html>