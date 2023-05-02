<!-- 
SESSIONERROR
/*****************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * Author : Hyungseok Kim
 * Create Date : 2020. 01. 09.
 * DESC : [관리자] 세션 타임아웃 화면
 * CODE : SESSIONERROR
*****************************************************************/
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
	<title>관리자</title>
	<!-- JavaScript ================================================== -->
	<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />
</head>
<body>
	<div class="bxMsgBg">

		<div class="bxBPMsgWrap">
			<div class="bxBPMsg">
			
				<img alt="icon" src="${cp }/resources/ecoletree/img/img_logout.png">
				<p>보안상의 이유로 로그아웃 되었습니다.<br>다시 로그인을 해주세요.</p>
				<a class="btnMain" href="${cp }/login">로그인 화면으로 이동</a>
			</div>
		</div>
	
	</div>
</body>
</html>