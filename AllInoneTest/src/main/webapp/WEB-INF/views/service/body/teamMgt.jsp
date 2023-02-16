<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="custMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>팀 관리</h1>
		<span>조직 관리 &gt; 팀 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup"  data-skillgroup="selGroupCenter">
			</select>
			<select id="selGroupCenter">
			</select>
			<input id="iptTeamName" type="text" placeholder="팀명">
			<input id="iptSearchTenantMaster" type="text" placeholder="담당자명">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 14%" title="고객사"><div>고객사</div></th>
							<th style="width: 14%" title="센터"><div>센터</div></th>
							<th style="width: 14%" title="팀"><div>팀</div></th>
							<th style="width: 14%" title="팀 일련번호"><div>팀 일련번호</div></th>
							<th style="width: 14%" title="담당자명"><div>담당자명</div></th>
							<th style="width: 16%" title="주소"><div>주소</div></th>
							<th style="width: 14%" title="연락처"><div>연락처</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">팀 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>팀 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<div class="formTableContainer">
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<input type="text" id="selGroupId" name="view_cust_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터</label>
						<div class="formInput">
							<input type="text" id="selCenterId" name="view_center_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">팀 일련번호</label>
						<div class="formInput">
							<input id="iptUteamId" name="team_id" type="text" disabled>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">팀명<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptUteamName" name="group_name" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">담당자명<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptUmasterName"  name="group_master" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">주소</label>
						<div class="formInput">
							<input name="address" type="text" maxlength="128">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">연락처<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptPhone" name="phone" type="text" maxlength="32">
						</div>
					</div>
				</div>
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">팀 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>팀이 없습니다.</h6>
		<p>팀을 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>팀 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel">고객사</label>
							<div class="formInput">
								<select id="selNGroup" name="group_id"  data-skillgroup="selNGroupCenter" >
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">센터</label>
							<div class="formInput">
								<select id="selNGroupCenter" name="tenant_id" >
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">팀 일련번호<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNteamId" name="team_id" type="text" maxlength="10">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">팀명<span class="require">*</span></label>
							<div class="formInput">
								<input name="group_name" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">담당자명<span class="require">*</span></label>
							<div class="formInput">
								<input name="group_master" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">주소</label>
							<div class="formInput">
								<input name="address" type="text" maxlength="128">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">연락처<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNPhone" name="phone" type="text" maxlength="32">
							</div>
						</div>
					</div>
				</fieldset>
				</form>
				
			</div>
		</div>
		<div class="footWriteWrap">
			<button id="btnSave" class="btnMain">추가</button>
			<button id="btnSaveCancel" class="btnGray">취소</button>
		</div>
		
	</div>
	<!-- 끝: 작성하기 -->
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/teamMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "teamMgt") {
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