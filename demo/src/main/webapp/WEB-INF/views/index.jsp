<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<title>Insert title here</title>
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />

</head>
<body>

test
<button id="btnList"> btngetList</button>
<button id="btnList2"> btngetList2</button>
<button id="btn"> testalert</button>
<button id="btn1"> Login</button>
<button id="btn2"> TEST</button>
<input id="iptId">
<input id="iptPw">
<img  src="${cp}/resources/ecoletree/img/btn_cal.png">

<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/index.js");
</script>
<!-- alert 모달 -->
<c:import url="/common.alert.sp" charEncoding="UTF-8" />
</body>
</html>