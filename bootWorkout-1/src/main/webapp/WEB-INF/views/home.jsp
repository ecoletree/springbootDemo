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
	 * ��ư Ŭ�� - ��Ʈ�ѷ����� �ð�, �Է¿��� ��������
	 */
	var btnTest = function(){
		
		$("#divResult").empty();
		var txt = $("#iptTest").val();
		var param = {};
		param.test = "test";
		param.text = txt;
		 $.ajax({
		        url : "/getTest",
		        type : "POST",
		        data : JSON.stringify(param) ,
		        dataType: 'text',
		        contentType : "application/json; charset=UTF-8",
		        success : function (data) {
		        	var result = JSON.parse(data);
		        	btnTestSuccessHandler(JSON.parse(data));
		            }//success
		        });  //ajax
		
			
	}
	/*
	 * ��ư Ŭ�� - success function
	 */
	var btnTestSuccessHandler = function(result){
		var sp = ""
            if(result.message === "success"){
            	sp = '<span>'+result.now+'</span><br><span>�Է°�: '+result.text+'</span>';
            }else{
            	sp = '<span>'+result.now+'</span><br><span>�Է¾���</span>';
            }
    	$("#divResult").append(sp)
	}
</script>
</head>
<body>
<h1>Spring boot</h1>
<input id="iptTest">
<button onclick="btnTest()">ajax test</button>
<div id="divResult"></div>
</body>

</html>