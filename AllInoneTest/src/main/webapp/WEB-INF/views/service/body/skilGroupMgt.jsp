<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="skilGroupMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>스킬 그룹(큐) 관리</h1>
		<span>상담원 관리 &gt; 스킬 그룹(큐) 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup">
			</select>
			<select id="selGroupCenter">
			</select>
			<input id="iptSkilGroupList" type="text" placeholder="스킬 그룹">
			<input id="iptSkilGroupNameList" type="text" placeholder="스킬 그룹 명">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 8%" title="고객"><div>고객사</div></th>
							<th style="width: 8%" title="센터"><div>센터</div></th>
							<th style="width: 9%" title="스킬 그룹"><div>스킬 그룹</div></th>
							<th style="width: 9%" title="스킬 그룹 명"><div>스킬 그룹 명</div></th>
							<th style="width: 8%" title="타임아웃"><div>타임아웃</div></th>
							<th style="width: 14%" title="진입 멘트"><div>진입 멘트</div></th>
							<th style="width: 14%" title="대기 멘트"><div>대기 멘트</div></th>
							<th style="width: 14%" title="상담원 연결 멘트"><div>상담원 연결 멘트</div></th>
							<th style="width: 8%" title="녹음 여부"><div>녹음 여부</div></th>
							<th style="width: 8%" title="라우팅"><div>라우팅</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td class="txtCenter">3001</td> -->
<!-- 							<td>스킬 그룹 01</td> -->
<!-- 							<td class="txtRight"><span>100</span>초</td> -->
<!-- 							<td></td> -->
<!-- 							<td>filename000001.wav</td> -->
<!-- 							<td></td> -->
<!-- 							<td class="txtCenter">o</td> -->
<!-- 							<td class="txtCenter">L</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td class="txtCenter">3002</td> -->
<!-- 							<td>스킬 그룹 02</td> -->
<!-- 							<td class="txtRight"><span>100</span>초</td> -->
<!-- 							<td>filename000001.wav</td> -->
<!-- 							<td>filename000001.wav</td> -->
<!-- 							<td>filename000001.wav</td> -->
<!-- 							<td class="txtCenter">x</td> -->
<!-- 							<td class="txtCenter">L</td> -->
<!-- 						</tr> -->
						
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">스킬 그룹(큐) 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>스킬 그룹(큐) 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			
			<div class="formTableContainer">
				<form id="editForm" method="post" autocomplete="off">
				<fieldset>
				<div class="formWrap">
					<label class="formLabel">고객사</label>
					<div class="formInput">
						<input type="text" id="selGroupId" name="group_name" disabled/>
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">센터</label>
					<div class="formInput">
						<input type="text" id="selCenterId" name="tenant_name" data-soundlist="selEnterAnnounce,selReenterAnnounce,selConnectAnnounce" disabled/>
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">스킬 그룹</label>
					<div class="formInput">
						<input id="iptSkilGroup" name="grp_id" type="text" disabled="disabled">
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">스킬 그룹 명<span class="require">*</span></label>
					<div class="formInput">
						<input id="iptSkilGroupName" name="name" type="text" maxlength="32">
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">타임아웃</label>
					<div class="formInput">
						<div class="input-group">
							<input id="iptTimeOut" name="noanswer_timeout" type="text" class="txtRight" maxlength="11"> 
							<span class="input-group-addon">초</span>
						</div>
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">진입 멘트</label>
					<div class="formInput">
						<select id="selEnterAnnounce" name="enter_announce" class="select2" multiple="multiple">
							<option></option>
						</select>						
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">대기 멘트</label>
					<div class="formInput">
						<select id="selReenterAnnounce" name="reenter_announce" class="select2" multiple="multiple">
							<option></option>
						</select>						
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">상담원 연결 멘트</label>
					<div class="formInput">
						<select id="selConnectAnnounce" name="connect_announce" class="select2" multiple="multiple">
							<option></option>
						</select>						
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">녹음 여부</label>
					<div class="formInput">
						<select id="selRecording" name="recording">
							<option value="1">o</option>
							<option value="0">x</option>
						</select>						
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">라우팅</label>
					<div class="formInput">
						<select id="selAcd" name="acd">
							<option value="L">L: 최장 대기 상담원 연결</option>
							<option value="R">R: 순차적으로 연결</option>
						</select>						
					</div>
				</div>
				</fieldset>
				</form>
			</div>
			
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">스킬 그룹(큐) 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>스킬 그룹(큐)이 없습니다.</h6>
		<p>스킬 그룹(큐)을 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>스킬 그룹(큐) 추가</h5>
			</div>
			<div class="contWritetBody">
			
				<div class="formTableContainer" >
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
						<select id="selNGroup" name="group_id">
						</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터</label>
						<div class="formInput">
							<select name="tenant_id" id="selNGroupCenter"  data-soundlist="selNEnterAnnounce,selNReenterAnnounce,selNConnectAnnounce" >
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">스킬 그룹<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptNSkilGroup" name="grp_id" type="text" maxlength="4">
							<p class="note">※3000번대 사용을 추천</p>
							<p class="note">※4자리 숫자로 입력</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">스킬 그룹 명<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptNSkilGroupName" name="name" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">타임아웃</label>
						<div class="formInput">
							<div class="input-group">
								<input id="iptNTimeOut" name="noanswer_timeout" type="text" class="txtRight" maxlength="11">
								<span class="input-group-addon">초</span>
							</div>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">진입 멘트</label>
						<div class="formInput">
						<select id="selNEnterAnnounce" name="enter_announce" class="select2" multiple="multiple">
							<option></option>
						</select>			
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">대기 멘트</label>
						<div class="formInput">
							<select id="selNReenterAnnounce" name="reenter_announce" class="select2" multiple="multiple">
							<option></option>
						</select>						
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">상담원 연결 멘트</label>
						<div class="formInput">
							<select id="selNConnectAnnounce" name="connect_announce" class="select2" multiple="multiple">
							<option></option>
						</select>						
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">녹음 여부</label>
						<div class="formInput">
							<select id="selNRecording" name="recording">
								<option value="1">o</option>
								<option value="0">x</option>
							</select>						
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">라우팅</label>
						<div class="formInput">
							<select id="selNAcd" name="acd">
								<option value="L">L: 최장 대기 상담원 연결</option>
								<option value="R">R: 순차적으로 연결</option>
							</select>						
						</div>
					</div>
					</fieldset>
					</form>
				</div>
				
			</div>
		</div>
		<div class="footWriteWrap">
			<button id="btnSave" class="btnMain">추가</button>
			<button id="btnSaveCancel" class="btnGray">취소</button>
		</div>
		
	</div>
	<!-- 끝: 작성하기 -->
	

</div>
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/skilGroupMgt.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "skilGroupMgt") {
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