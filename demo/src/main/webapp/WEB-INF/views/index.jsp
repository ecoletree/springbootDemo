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
<button id="btn"> testalert</button>
<img  src="${cp}/resources/ecoletree/img/btn_cal.png">

<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/index.js");
</script>
<!-- alert 모달 -->
<c:import url="/common.alert.sp" charEncoding="UTF-8" />
</body>
</html>