<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="recordMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>녹음 청취</h1>
		<span>녹취 관리 &gt; 녹음 청취</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<div>
					<div id="divStartDate" class="bxEcoleDate input-group date">
						<input type="text" readonly="readonly"> 
						<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
					</div>
					<div id="divSTime" class="input-group time">
						<select id="selSHour">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selSMinute">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selSSecond">
						</select>
					</div>
					<span>~</span>
					<div id="divEndDate" class="bxEcoleDate input-group date">
						<input type="text" readonly="readonly"> 
						<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
					</div>
					<div id="divETime" class="input-group time">
						<select id="selEHour">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selEMinute">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selESecond">
						</select>
					</div>
				</div>
				
				<div>
					<label class="ecoleCheck"><input id="chkIn" type="checkbox" checked="checked"><i></i>In</label>
					<label class="ecoleCheck"><input id="chkOut" type="checkbox" checked="checked"><i></i>Out</label>
				</div>
				
				
				<div>
					<span>통화 시간</span>
					<div class="input-group number">
						<select id="selSDuration">
						</select>
						<span class="input-group-addon">~</span>
						<select id="selEDuration">
						</select>
					</div>
				</div>
				<button id="btnSearch" class="btnMain">조회</button>
			</div>
			<div id="divSearch" class="row2">
				<input id="iptPhone" type="text" placeholder="내선번호">
				<input id="iptOther" type="text" placeholder="전화번호">
				<input id="iptUuid" type="text" placeholder="녹취 ID">
				<select id="selGroupList">
					<option>고객사 전체</option>
				</select>
				<select id="selCenterList">
					<option>센터 전체</option>
				</select>
				<select id="selTeamList">
					<option>팀 전체</option>
				</select>
				
				<input id="iptAgentName" type="text" placeholder="상담사 명">
			</div>
		</div>
		
		<!-- 전송하기
		 	버튼 안누른 항목: <button type="button" class="btnWhite btnReady" title="전송하기"></button>
		 	버튼 누른 항목: <button type="button" class="btnWhite btnTrans" title="전송하기"></button>
		 -->
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnExcel" class="btnMain" style="display:none;">엑셀 다운로드</button>
	</div>
	<!-- 좌측 끝: 리스트 -->

	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>데이터가 없습니다.</h6>
		<p>검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 시작: 다운로드 하기 -->
	<div id="divDownload" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog popW250" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">다운로드 하기</h4>
				</div>
				
				<div class="modal-body">
					<button id="btnDownload" type="button" class="btnMain btn-block is_rec_download">MP3 다운로드</button>
					<button id="btnEncDownload" type="button" class="btnSub btn-block marginT15 is_encrypt_download">암호화 다운로드</button>
				</div>
				
				<div class="modal-footer margin0">
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 다운로드 하기 -->
	
	
	<!-- 시작: 전송 하기 -->
	<div id="divTrans" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog popW550" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">전송 하기</h4>
				</div>
				
				<div class="modal-body">
					<div class="formInlineContainer marginB20">
						<div>
							<p>증서 번호(1)</p>
							<input id="txtCert0" type="text" maxlength="45">
						</div>
						<div>
							<p>녹취 구분값(1)</p>
							<select id="selGubun0">
							</select>
						</div>
						<div>
							<p>전송(1)</p>
							<select id="selTrans0">
								<option value="N">미정</option>
								<option value="Y">전송</option>
							</select>
						</div>
						<div>
							<p>전송 상태(1)</p>
							<input id="txtStatus0" type="text" disabled="disabled">
						</div>
					</div>
					
					<div class="formInlineContainer marginB20">
						<div>
							<p>증서 번호(2)</p>
							<input id="txtCert1" type="text" maxlength="45">
						</div>
						<div>
							<p>녹취 구분값(2)</p>
							<select id="selGubun1">
							</select>
						</div>
						<div>
							<p>전송(2)</p>
							<select id="selTrans1">
								<option value="N">미정</option>
								<option value="Y">전송</option>
							</select>
						</div>
						<div>
							<p>전송 상태(2)</p>
							<input id="txtStatus1" type="text" disabled="disabled">
						</div>
					</div>
					
					<div class="formInlineContainer marginB20">
						<div>
							<p>증서 번호(3)</p>
							<input id="txtCert2" type="text" maxlength="45">
						</div>
						<div>
							<p>녹취 구분값(3)</p>
							<select id="selGubun2">
							</select>
						</div>
						<div>
							<p>전송(3)</p>
							<select id="selTrans2">
								<option value="N">미정</option>
								<option value="Y">전송</option>
							</select>
						</div>
						<div>
							<p>전송 상태(3)</p>
							<input id="txtStatus2" type="text" disabled="disabled">
						</div>
					</div>
					
					<div class="formInlineContainer marginB20">
						<div>
							<p>증서 번호(4)</p>
							<input id="txtCert3" type="text" maxlength="45">
						</div>
						<div>
							<p>녹취 구분값(4)</p>
							<select id="selGubun3">
							</select>
						</div>
						<div>
							<p>전송(4)</p>
							<select id="selTrans3">
								<option value="N">미정</option>
								<option value="Y">전송</option>
							</select>
						</div>
						<div>
							<p>전송 상태(4)</p>
							<input id="txtStatus3" type="text" disabled="disabled">
						</div>
					</div>
					
					<div class="formInlineContainer marginB20">
						<div>
							<p>고객명</p>
							<input id="txtClientName" type="text">
						</div>
						<div>
							<p>고객명이 누락 된 경우 직접 입력해 주세요.</p>
						</div>
					</div>
					
				</div><!-- ./modal-body -->
				
				<div class="modal-footer margin0">
					<button id="btnTrans" type="button" class="btnMain">전송 하기</button>
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 다운로드 하기 -->
	
	<!-- 시작: 플레이어 -->
	<div class="popPlayer" style="display:none">
		<div class="bxPlayerWrap">
			<div class="bxPlaerInfoWrap">
				<div class="bxPlaerInfo">
					<p>시작시간</p>
					<p id="pStartStamp"></p>
				</div>
				<div class="bxPlaerInfo">
					<p>내선번호</p>
					<p id="pPhone"></p>
				</div>
				<div class="bxPlaerInfo">
					<p>상담원</p>
					<p id="pAgentName"></p>
				</div>
			</div>
			<div class="bxPlayer">
				<audio id="ecolePlayer" preload="none" controls style="max-width:100%;">
                    <source src="http://www.largesound.com/ashborytour/sound/AshboryBYU.mp3" type="audio/mp3">
                </audio>
			</div><!-- ./bxPlayer -->
			<button id="btnPlayerClose" type="button" class="btnClose"> &times;</button>
		</div><!-- ./bxPlayerWrap -->
	</div>
	<!-- 끝: 플레이어 -->
	
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/recordListening.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "recordListening") {
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