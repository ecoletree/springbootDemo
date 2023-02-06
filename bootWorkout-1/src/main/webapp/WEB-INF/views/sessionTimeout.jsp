<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	var goMain = function(){
		var view = window.location.origin + "/login";
		window.location = view;
	}
</script>
</head> 
<body>
<h1>Spring boot</h1>
<div>

<h3>Session Timeout </h3>
세션이 만료되었습니다.
</div>
<div>
<button onclick="goMain()"> 로그인화면으로 </button>
</div>
</body>

</html>