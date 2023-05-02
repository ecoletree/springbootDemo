<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<!DOCTYPE html>
<html>

<head>
<c:import url="/include.commonHeader.sp" charEncoding = "UTF-8"/>
<title>스프링부트 연습</title>
<!-- JavaScript plugin 관련 -->
<c:import url="/include.commonPlugin.sp" charEncoding = "UTF-8"/>
</head>
<body>

	<tiles:insertAttribute name="serviceHeader" />

	<div id="content">
		<tiles:insertAttribute name="serviceBody" />
	</div>

	<!-- alert 모달 -->
	<c:import url="/common.alert.sp" charEncoding="UTF-8" />
	
	
	<!-- 작업중 -->
	<div id="divProcess" class="bxProcess" style="display: none;">
		<div>
			<img src="${cp }/resources/ecoletree/svg/SwingPreloader.svg" alt="loading">
			<h6>검색중입니다.</h6>
			<p>검색이 완료될 때까지 잠시만 기다려주세요.</p>
		</div>
	</div>
</body>
</html>