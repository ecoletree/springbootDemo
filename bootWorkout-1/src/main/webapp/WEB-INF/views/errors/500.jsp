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
  500 ERROR<br />
  서버에서 문제가 발생했습니다.<br />
  관리자에게 문의해주세요.<br />
<button onclick="goMain()"> 로그인화면으로 </button>
</div>
</body>
</html>