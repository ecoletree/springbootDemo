<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
	<title>관리자</title>
	<!-- JavaScript ================================================== -->
	<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />
</head>
<body>
	<!-- 로그인 -->
	<div class="bxLoginBg">
		<form id="loginForm" method="post" autocomplete="off">
			<fieldset>
			<div class="bxLoginWrap">
				<img alt="icon" src="${cp }/resources/ecoletree/img/img_login.png">
				<div class="bxLogin">
					<p>관리자</p>
					<div class="formInput-inline">
						<label class="ecoleRadio"><input type="radio" name="user_type" value="system" checked="checked" ><i></i>시스템</label>
						<label class="ecoleRadio"><input type="radio" name="user_type" value="group" ><i></i>고객사</label>
						<label class="ecoleRadio"><input type="radio" name="user_type" value="center" ><i></i>센터</label>
						<label class="ecoleRadio"><input type="radio" name="user_type" value="team" ><i></i>팀</label>
					</div>
					<p>아이디</p>
					<input id="ipxUserId" name="user_id" type="text">
					<p>비밀번호</p>
					<input id="ipxUserPW" name="user_pw" type="password">
					<a id="btnLogin" class="btnMain">로그인</a>
				</div>
			</div>
			</fieldset>
		</form>
	
	</div><!-- 로그인 -->
	
	<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/js/home.js");
	</script>
	
	<!-- alert 모달 -->
	<c:import url="/common.alert.sp" charEncoding="UTF-8" />

</body>
</html>
