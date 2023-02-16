<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="logWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>시스템 로그</h1>
		<span>환경 설정 &gt; 시스템 로그</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<div id="divStartDate" class="bxEcoleDate input-group date">
					<input type="text" readonly="readonly"> 
					<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
				</div>
				<span>~</span>
				<div id="divEndDate" class="bxEcoleDate input-group date">
					<input type="text" readonly="readonly"> 
					<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
				</div>
	<!-- 				<select> -->
	<!-- 					<option>로그 전체</option> -->
	<!-- 				</select> -->
				<input id="iptAgentId" type="text" placeholder="사용자 ID"> 
				
				<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
			</div>
			
		</div>
		
		
		<!-- 로그인 한 상담원 tr class="login" -->
		<div class="ecloeScrollTableContainer ecloeScrollTableContainer2">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 10%" title="로그 발생일 "><div>로그 발생일 </div></th>
							<th style="width: 10%" title="사용자"><div>사용자</div></th>
							<th style="width: 70%" title="메시지"><div>메시지</div></th>
							<th style="width: 10%" title="IP"><div>IP</div></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<div></div>
		<button id="btnExcelDownLoad" class="btnMain" style="display:none;">엑셀 다운로드</button>		
	</div>
	<!-- 좌측 끝: 리스트 -->

	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData contRightNoData2" style="display: block;">
		<h6>데이터가 없습니다.</h6>
		<p>검색조건을 수정해 주세요.</p>
	</div>
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/systemLog.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "systemLog") {
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