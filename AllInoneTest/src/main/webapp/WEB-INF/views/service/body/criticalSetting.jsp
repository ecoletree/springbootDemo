<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>

<div class="settingWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>임계점 관리</h1>
		<span>환경 설정 &gt; 임계점 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<select id="selGroupList">
				</select>
				<select id="selCenterList">
				</select>
				<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
			</div>
			<p id="pCenterName">
<!-- 			조회 된 센터: <span>센터명</span>(<span>센터아이디</span>) -->
			 </p>
		</div>
		<div class="ScrollContainerWithSearch">
			<form id="addForm" method="post" autocomplete="off">
			<fieldset>
				<div class="bxTileWrap">
					<!-- 상담사 모니터링 -->
					<div class="bxCriticalColWrap">
						<h6 class="txtCriticalMenu">상담사 모니터링</h6>
						
						<div class="bxCriticalRowWrap">
						
							<div class="bxWall bxCriticalColWrap" id="divAT000">
							</div><!-- ./wall -->
						</div><!-- ./bxCriticalRowWrap -->
					</div><!-- ./상담사 모니터링 -->
					
					
					<!-- 콜 모니터링 -->
					<div class="bxCriticalColWrap">
						<h6 class="txtCriticalMenu">콜 모니터링</h6>
						
						<div class="bxCriticalRowWrap">
							
							<!-- 평균 대기 시간 -->
							<div class="bxWall bxCriticalColWrap" id="divAW000">
							</div><!-- ./평균 대기 시간 -->
							
							<!-- 평균 통화 시간 -->
							<div class="bxWall bxCriticalColWrap"  id="divAC000">
							</div><!-- ./평균 통화 시간 -->
							
							<!-- 대기호 -->
							<div class="bxWall bxCriticalColWrap" id="divQU000">
							</div><!-- ./대기호 -->
							
							<!-- 서비스 레벨 -->
							<div class="bxWall bxCriticalColWrap" id="divSL000">
							</div><!-- ./서비스 레벨 -->
							
							<!-- 응대율 -->
							<div class="bxWall bxCriticalColWrap" id="divRR000">
							</div><!-- ./응대율 -->
							
							<!-- 업무단위 대기큐별 응대율 -->
							<div class="bxWall bxCriticalColWrap" id="divRW000">
							</div><!-- ./업무단위 대기큐별 응대율 -->
							
							
							<!-- 상담사 상태별 현황 -->
							<div class="bxWall bxCriticalColWrap" id="divAS000">
							</div><!-- ./상담사 상태별 현황 -->
							
						</div><!-- ./bxCriticalRowWrap -->
					</div><!-- ./콜 모니터링 -->
					
				</div>
			</fieldset>
			</form>
		</div>
		
		
		
	</div>
	<div class="footLeftWrap">
		<div></div>
		<button id="btnSave" class="btnMain" style="display:none;">저장</button>
	</div>
	
	<!-- 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>임계점을 설정할 센터가 없습니다.</h6>
		<p>센터를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 좌측 끝: 리스트 -->
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/criticalSetting.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "criticalSetting") {
				var inter = setInterval(function(){
					 ecoletree.promiseInit()
					.then(function(){
						clearInterval(inter);
						ecoletree.vc.init(${initData});
					}, function (){})
				},300);
				
			} else {
				console.log("vc's name is not index : " + ecoletree.vc.name);
			}
		}).fail(function(jqxhr, settings, exception) {
			console.log(arguments);
		});
	</script>

</div>