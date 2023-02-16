<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="trunkMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>국선 관리</h1>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroupList">
			</select>
			<select id="selCenterList">
			</select>
			<input id="iptTrunkList" type="text" placeholder="착신 번호">
			<input id="iptTransferIDList" type="text" placeholder="라우팅 대상">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 10%" title="고객사"><div>고객사</div></th>
							<th style="width: 10%" title="센터"><div>센터</div></th>
							<th style="width: 10%" title="고객사 일련번호"><div>고객사 일련번호</div></th>
							<th style="width: 20%" title="DNIS(착신) 번호"><div>DNIS(착신) 번호</div></th>
							<th style="width: 10%" title="라우팅 대상"><div>라우팅 대상</div></th>
							<th style="width: 20%" title="메모"><div>메모</div></th>
							<th style="width: 14%" title="등록일"><div>등록일</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td>tena01</td> -->
<!-- 							<td class="txtCenter">07000000000</td> -->
<!-- 							<td class="txtCenter">5000</td> -->
<!-- 							<td>메모메모메모메모메모메모자리</td> -->
<!-- 							<td class="txtCenter">2020.05.05</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">국선 번호 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>국선 번호 상세보기</h5>
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
						<input type="text" id="selCenterId" name="center_name" disabled/>
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">DNIS(착신) 번호</label>
					<div class="formInput">
						<input id="iptTrunk" name="trunk" type="text" disabled="disabled" maxlength="32">
						<p class="note">※고객사 명의의 착신번호</p>
						<p class="note">※이 번호로 인입된 호는 해당 고객사의 인바운드 전화로 인식</p>
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">라우팅 대상<span class="require">*</span></label>
					<div class="formInput">
						<div class="formInput-inline">
							<label class="ecoleRadio"><input id="iptExtension" name="transferSet" type="radio" value="extension" checked="checked" ><i></i>내선 직통</label>
							<label class="ecoleRadio"><input id="iptHunt" name="transferSet" type="radio" value="hunt" ><i></i>헌트 그룹</label>
							<label class="ecoleRadio"><input id="iptSkill" name="transferSet" type="radio" value="skill" ><i></i>스킬 그룹</label>
						</div>					
						<div class="formInput">
							<select id="selTransferID" class="select2" name="transfer_id" multiple="multiple">
							</select>
						</div>
					</div>
				</div>
				<div class="formWrap">
					<label class="formLabel">메모</label>
					<div class="formInput">
						<input id="iptMemo" name="memo" type="text" maxlength="128"> 
					</div>
				</div>
				</fieldset>
				</form>
			</div>
			
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">국선 번호 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>국선 번호가 없습니다.</h6>
		<p>국선 번호를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>국선 번호 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
				<div class="formTableContainer" >
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<select id="selNGroupId" name="group_id">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터</label>
						<div class="formInput">
							<select id="selNCenterId" name="tenant_id">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">DNIS(착신) 번호<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptNTrunk" name="trunk" type="text" maxlength="32">
							<p class="note">※고객사 명의의 착신번호</p>
							<p class="note">※이 번호로 인입된 호는 해당 고객사의 인바운드 전화로 인식</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">라우팅 대상<span class="require">*</span></label>
						<div class="formInput">
							<div class="formInput-inline">
							<label class="ecoleRadio"><input id="iptExtensionN" name="transferSetN" type="radio" value="extension" checked="checked" ><i></i>내선 직통</label>
							<label class="ecoleRadio"><input id="iptHuntN" name="transferSetN" type="radio" value="hunt" ><i></i>헌트 그룹</label>
							<label class="ecoleRadio"><input id="iptSkillN" name="transferSetN" type="radio" value="skill" ><i></i>스킬 그룹</label>
						</div>					
						<div class="formInput">
							<select id="selNTransferID" class="select2" name="transfer_id" multiple="multiple">
							</select>
						</div>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">메모</label>
						<div class="formInput">
							<input id="iptNMemo" name="memo" type="text" maxlength="128">
						</div>
					</div>
					
				</div>
				</fieldset>
				</form>
			</div>
		</div>
		<div class="footWriteWrap">
			<button id="btnSave"  class="btnMain">추가</button>
			<button id="btnSaveCancel"  class="btnGray">취소</button>
		</div>
		
	</div>
	<!-- 끝: 작성하기 -->
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/trunkMgt.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "trunkMgt") {
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