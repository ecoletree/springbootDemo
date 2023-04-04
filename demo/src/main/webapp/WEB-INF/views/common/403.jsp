<!-- 
403ERROR
/*****************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * Author : Hyungseok Kim
 * Create Date : 2018. 09. 13.
 * DESC : [관리자] 403 에러 화면
 * CODE : 403ERROR
*****************************************************************/
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				<img src="${cp }/resources/ecoletree/img/img_etc.png" >
				<h1 >403 ERROR</h1>
				<h6>사용자가 많아 문제가 발생했습니다.</h6>
				<p>사용자가 많아 페이지를 표시함에 문제가 발생했습니다.<br/>
F5를 눌러 페이지를 새로고침 하거나 메인으로 이동해주세요.<br/><br/>
지속적으로 이 메시지가 보인다면 관리자에게 문의 부탁드립니다.</p>
				<a href="${cp}/login" class="btnMain" >메인으로 이동</a>
			</div>
		</div>
	
	</div>
</body>
</html>