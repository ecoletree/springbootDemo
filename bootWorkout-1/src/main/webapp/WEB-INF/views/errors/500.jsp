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
  �������� ������ �߻��߽��ϴ�.<br />
  �����ڿ��� �������ּ���.<br />
<button onclick="goMain()"> �α���ȭ������ </button>
</div>
</body>
</html>