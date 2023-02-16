<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="recordMonitoringWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>통화 모니터링</h1>
		<span>녹취 관리 &gt; 통화 모니터링</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<select id="selGroupList">
					<option>고객사 전체</option>
				</select>
				<select id="selCenterList">
					<option>센터 전체</option>
				</select>
				<select id="selTeamList">
					<option>팀 전체</option>
				</select>
				
				<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
			</div>
		</div>
		
		
		<!-- 로그인 한 상담원 tr class="login" -->
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 10%" title="고객사"><div>고객사</div></th>
							<th style="width: 10%" title="센터"><div>센터</div></th>
							<th style="width: 10%" title="상담원"><div>상담원</div></th>
							<th style="width: 3%" title=""><div>&nbsp;</div></th>
							<th style="width: 10%" title="In/Out"><div>In/Out</div></th>
							<th style="width: 10%" title="내선번호"><div>내선번호</div></th>
							<th style="width: 14%" title="전화번호"><div>전화번호</div></th>
							<th style="width: 10%" title="통화 시간"><div>통화 시간</div></th>
							<th style="width: 10%" title="오늘 통화 시간"><div>오늘 통화 시간</div></th>
							<th style="width: 10%" title="어제 통화 시간"><div>어제 통화 시간</div></th>
<!-- 							<th style="width: 3%" title=""><div>&nbsp;</div></th> -->
						</tr>
					</thead>
					<tbody>
<!-- 						<tr> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter">00:59</td> -->
<!-- 							<td class="txtCenter">60:59</td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 						</tr> -->
<!-- 						<tr class="login"> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td class="txtCenter"> -->
<%-- 								<img src="${cp }/resources/ecoletree/img/icon_call.png" alt="통화중"> --%>
<!-- 							</td> -->
<!-- 							<td class="txtCenter">OUT</td> -->
<!-- 							<td class="txtCenter">5001</td> -->
<!-- 							<td class="txtCenter">000****0000</td> -->
<!-- 							<td class="txtCenter">60:59</td> -->
<!-- 							<td class="txtCenter">60:59</td> -->
<!-- 							<td class="txtCenter">60:59</td> -->
<!-- 							<td class="txtCenter"> -->
<!-- 								<button type="button" class="btnWhite btnListen" title="듣기" disabled="disabled"></button> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr class="login"> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter">5002</td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter">10:59</td> -->
<!-- 							<td class="txtCenter">60:59</td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<div></div>
		<button id="btnExcel" class="btnMain" style="display:none;">엑셀 다운로드</button>
	</div>
	<!-- 좌측 끝: 리스트 -->

	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>데이터가 없습니다.</h6>
		<p>검색조건을 수정해 주세요.</p>
	</div>
	
	
</div>
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/recordMonitoring.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "recordMonitoring") {
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