<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">


	var send = function(data){
		
		var url = data.url;
		var param = data.sendData;
		
		$.ajax({
			url:url,
			type:"POST",
			data: JSON.stringify(param),
			dataType:'text', 
			contentType : "application/json; charset=UTF-8",
	        success : function (data) {
	        	var result = JSON.parse(data);
	        	btnSuccessHandler(JSON.parse(data));
	            },//success
	        error: function(request,status,error,response){
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        	if(status === "error"){
	        		if (request.status === 500) {
	        			var view =window.location.origin +"/error/500";
	        			window.location = view;
					}
	        	}

	        }
		});
	}
	/*
	 * 버튼 클릭 - success function
	 */
	var btnSuccessHandler = function(result){
		console.log(result.msg);
	}
	/*
	 * single schedule start
	 */
	var btnStart = function(){
		var data = {}
		var sendData = {};
		sendData.schedule = $("#iptSingleSchedule").val();
		data.url="/schedule/set";
		data.sendData = sendData;
		send(data);
	}
	/*
	 * single schedule stop
	 */
	var btnStop = function(){
		var data = {}
		var sendData = {};
		data.url="/schedule/stop";
		data.sendData = sendData;
		send(data);
	}
	/*
	 * muti schedule start
	 */
	var btnMultiStart = function(){
		var data = {}
		var sendData = {};
		sendData.schedule_name = $("#iptScheduleName").val();
		sendData.schedule = $("#iptMultiSchedule").val();
		data.url="/schedule/set/multi";
		data.sendData = sendData;
		send(data);
	}
	/*
	 * muti schedule stop
	 */
	var btnMultiStop = function(){
		var data = {}
		var sendData = {};
		sendData.schedule_name = $("#iptScheduleName").val();
		data.url="/schedule/stop/multi";
		data.sendData = sendData;
		send(data);
	}
	
	
</script>
</head> 
<body>
<h1>scheduler</h1>
<div>
	cron expression : <input id="iptSingleSchedule">
	<div>
		<button onclick="btnStart()">start</button>
		<button onclick="btnStop()">stop</button>
	</div>
</div>
<h1>multi scheduler</h1>
<div>
	cron name : <input id="iptScheduleName"><br>
	cron expression : <input id="iptMultiSchedule"><br>
	<div>
		<button onclick="btnMultiStart()">start</button>
		<button onclick="btnMultiStop()">stop</button>
	</div>
</div>
</body>

</html>