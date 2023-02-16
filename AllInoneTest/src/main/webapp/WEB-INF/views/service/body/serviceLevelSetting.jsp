<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<div class="settingWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>서비스 레벨 관리</h1>
		<span>환경 설정 &gt; 서비스 레벨 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<select id="selGroupList">
				</select>
				<select id="selCenterList" >
				</select>
				<button id="btnSearch" class="btnMain">조회</button>
			</div>
		</div>
		<div class="ScrollContainerWithSearch">
			<form>
			<fieldset>
				<div class="bxTileWrap">
						
					<div id="divServiceLevel" class="bxCriticalRowWrap">
					</div><!-- ./bxCriticalRowWrap -->
					
				</div>
			</fieldset>
			</form>
		</div>
		
		
		
	</div>
	<div class="footLeftWrap">
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>서비스레벨을 설정할 센터가 없습니다.</h6>
		<p>센터를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/serviceLevelSetting.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "serviceLevelSetting") {
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