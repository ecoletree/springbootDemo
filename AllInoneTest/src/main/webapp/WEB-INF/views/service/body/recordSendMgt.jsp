<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="recordMonitoringWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>녹취 전송 관리</h1>
		<span>녹취 관리 &gt; 녹취 전송 관리</span>
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
				<input id="iptFtpIP" type="text" placeholder="IP"> 
				<label class="ecoleCheck"><input id="iptFailCntCheck" type="checkbox"><i></i>미전송 갯수 1개 이상만 보기</label>
				
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
							<th style="width: 10%" title="고객사"><div>고객사</div></th>
							<th style="width: 10%" title="센터"><div>센터</div></th>
							<th style="width: 10%" title="IP"><div>IP</div></th>
							<th style="width: 10%" title="포트"><div>포트</div></th>
							<th style="width: 10%" title="FTP ID"><div>FTP ID</div></th>
							<th style="width: 10%" title="FTP PW"><div>FTP PW</div></th>
							<th style="width: 14%" title="경로"><div>경로</div></th>
							<th style="width: 10%" title="미전송 갯수"><div>미전송 갯수</div></th>
							<th style="width: 6%" title=""><div>&nbsp;</div></th>
						</tr>
					</thead>
					<tbody>
<!-- 						<tr> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td class="txtCenter">xxx.xxx.xx.xx</td> -->
<!-- 							<td>xxxxxxxz</td> -->
<!-- 							<td>xxxxx</td> -->
<!-- 							<td>x</td> -->
<!-- 							<td>/</td> -->
<!-- 							<td class="txtRight">0</td> -->
<!-- 							<td class="txtCenter"> -->
<!-- 								<button type="button" class="btnWhite" disabled="disabled">수동전송</button> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td>userID001(0001)</td> -->
<!-- 							<td class="txtCenter">xxx.xxx.xx.xx</td> -->
<!-- 							<td>xxxxxxxz</td> -->
<!-- 							<td>xxxxx</td> -->
<!-- 							<td>x</td> -->
<!-- 							<td>/</td> -->
<!-- 							<td class="txtRight">1</td> -->
<!-- 							<td class="txtCenter"> -->
<!-- 								<button type="button" class="btnWhite">수동전송</button> -->
<!-- 							</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<div></div>
		
	</div>
	<!-- 좌측 끝: 리스트 -->

	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData contRightNoData2" style="display: block;">
		<h6>데이터가 없습니다.</h6>
		<p>검색조건을 수정해 주세요.</p>
	</div>
	
	
</div>
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/recordSendMgt.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "recordSendMgt") {
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