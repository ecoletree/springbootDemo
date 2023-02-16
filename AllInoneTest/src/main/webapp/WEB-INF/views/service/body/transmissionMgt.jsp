<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="transmissionMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>전송 관리</h1>
		<span>애니매니저 &gt; 전송 관리</span>
	</div>
	
	<div class="transmissionMgtList">
	
		<!-- 리스트 -->
		<div class="contLeftWrap">
			<div class="search">
				<div>
					<select id="selGroup">
					</select>
					<select id="selGroupCenter" data-teamlist="selTeamList" >
					</select>
					<select id="selBroadBand">
						<option></option>
					</select>
					<label class="ecoleCheck"><input id="iptSetAutopreview" data-btnname="#iptCustomCheck" type="checkbox" ><i></i>자동 미리보기</label>
					<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
				</div>
				<div>
					<p class="marginR5">총 <span id="spAllCnt"></span>건</p>
					<button id="btnSendList"  class="btnSub" style="display:none;">전송하기</button>
				</div>
			</div>
			
			<div class="ecloeScrollTableContainer">
				<div class="ecloeScrollTableHeader"></div>
				<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
					<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
						<thead>
							<tr>
								<th id="userCheck" style="width: 2%;"><div><label class="ecoleCheck"><input type="checkbox" id="iptCustomCheck" data-btnname="input[name=center_id]" ><i></i></label></div></th>
								<th style="width: 34%" title="고객사"><div>고객사</div></th>
								<th style="width: 32%" title="센터"><div>센터</div></th>
								<th style="width: 32%" title="지역수"><div>지역수</div></th>
							</tr>
						</thead>
						<tbody class="cursorPoint">
							<tr>
<!-- 								<td><label class="ecoleCheck"><input type="checkbox" ><i></i></label></td> -->
<!-- 								<td>00001</td> -->
<!-- 								<td>00001</td> -->
<!-- 								<td class="txtCenter">000</td> -->
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- 리스트 -->
		
		<!-- 미리보기  style="display: block;" -->
		<div id="divTransmissionMgtDetail" class="transmissionMgtDetail" style="display: none;">
			
			<div class="contRightWrap">
				<div class="contRightHead">
					<h5>선택항목 미리보기</h5>
					<div>
						<p class="marginR5">총 <span id="spDetailCnt">12345</span>건</p>
						<button id="btnSendDetailList" class="btnSub" style="display:none;">전송하기</button>
					</div>
				</div>
				
				<div class="footRightWrap">
				</div>
		
				<div class="ecloeScrollTableContainer">
					<div class="ecloeScrollTableHeader"></div>
					<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
						<table id="tbListDetail" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
							<thead>
								<tr>
									<th style="width: 7%" title="등록"><div>등록</div></th>
									<th style="width: 7%" title="고객사"><div>고객사</div></th>
									<th style="width: 7%" title="센터"><div>센터</div></th>
									<th style="width: 7%" title="다이얼러 센터코드"><div>다이얼러 센터코드</div></th>
									<th style="width: 7%" title="다이얼러"><div>다이얼러</div></th>
									<th style="width: 7%" title="지역"><div>지역</div></th>
									<th style="width: 7%" title="지역번호"><div>지역번호</div></th>
									<th style="width: 7%" title="우편번호"><div>우편번호</div></th>
									<th style="width: 7%" title="발신번호"><div>발신번호</div></th>
									<th style="width: 7%" title="착신번호"><div>착신번호</div></th>
									<th style="width: 7%" title="상태코드"><div>상태코드</div></th>
									<th style="width: 7%" title="사용 시작일"><div>사용 시작일</div></th>
									<th style="width: 7%" title="사용 종료일"><div>사용 종료일</div></th>
									<th style="width: 9%" title="등록 일시"><div>등록 일시</div></th>
								</tr>
							</thead>
							<tbody>
<!-- 								<tr> -->
<!-- 									<td class="txtCenter">미등록</td> -->
<!-- 									<td>00001(0001)</td> -->
<!-- 									<td>00001(0001)</td> -->
<!-- 									<td>00001</td> -->
<!-- 									<td>00001(0001)</td> -->
<!-- 									<td>제주특별자치도</td> -->
<!-- 									<td class="txtCenter">000</td> -->
<!-- 									<td class="txtCenter">000</td> -->
<!-- 									<td class="txtCenter">000-0000-0000</td> -->
<!-- 									<td class="txtCenter">000-0000-0000</td> -->
<!-- 									<td>AM등록완료</td> -->
<!-- 									<td class="txtCenter">2020.05.05</td> -->
<!-- 									<td class="txtCenter">2020.05.05</td> -->
<!-- 									<td class="txtCenter">2020.05.05 09:00</td> -->
<!-- 								</tr> -->
							</tbody>
						</table>
					</div>
				</div>
				
			</div><!-- 미리보기 -->
		</div>
		
		
		
	</div>
	
	<!-- 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>전송할 데이터가 없습니다.</h6>
		<p>검색조건을 수정해 주세요.</p>
	</div>
	
	<!--  결과 style="display: block;" -->
	<div id="divSendResult" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>전송 결과</h5>
			</div>
			<div class="ecloeScrollTableContainer">
				<div class="ecloeScrollTableHeader"></div>
				<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
					<table id="tbList_result" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
						<thead>
							<tr>
								<th style="width: 80%" title="CSV 파일명"><div>CSV 파일명</div></th>
								<th style="width: 20%" title="전송 결과"><div>전송 결과</div></th>
							</tr>
						</thead>
						<tbody class="cursorPoint">
<!-- 							<tr> -->
<!-- 								<td>AIA_xxxx_20220505112939.csv</td> -->
<!-- 								<td class="txtCenter">성공</td> -->
<!-- 							</tr> -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="footWriteWrap">
			<button id="btnClose" class="btnMain">확인</button>
		</div>
		
	</div>
	<!-- 끝: 작성하기 -->


	<!-- 시작: 생성 및 전송하기 -->
	<div id="divSendModal" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog popW450" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">생성 및 전송하기</h4>
				</div>
				
				<div class="modal-body">
					<h6>고객사 전체를 생성하시겠습니까?</h6>
					<p>※반드시 아래 리스트를 확인 후 생성 및 전송하기 버튼을 눌러주세요.</p>
					
					
					<div class="ecloeScrollTableContainer popH400">
						<div class="ecloeScrollTableHeader"></div>
						<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
							<table id="tbSendTableList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
								<thead>
									<tr>
										<th style="width: 33%" title="고객"><div>고객사</div></th>
										<th style="width: 33%" title="센터"><div>센터</div></th>
										<th style="width: 33%" title="지역수"><div>지역수</div></th>
			 
									</tr>
								</thead>
								<tbody class="cursorPoint">
<!-- 									<tr> -->
<!-- 										<td>고객</td> -->
<!-- 										<td>센터</td> -->
<!-- 										<td class="text-center">18</td> -->
<!-- 									</tr> -->
								</tbody>
							</table>
						</div>
					</div><!-- ./ecloeScrollTableContainer -->
					
					
					
				</div>
				
				<div class="modal-footer margin0">
					<button id="btnCSVtrasnsmission" type="button" class="btnMain">생성 및 전송하기</button>
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 생성 및 전송하기 -->
	

</div>
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/transmissionMgt.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "transmissionMgt") {
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