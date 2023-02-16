<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="gateMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>Trunk Gateway</h1>
		<span>AniMamager &gt; Trunk Gateway</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGWGroupList">
				<option>그룹 전체</option>
			</select>
			<select id="selGroupList">
			</select>
			<select id="selCenterList">
			</select>
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 12%" title="그룹"><div>그룹</div></th>
							<th style="width: 12%" title="고객사"><div>고객사</div></th>
							<th style="width: 12%" title="센터"><div>센터</div></th>
							<th style="width: 14%" title="PREFIX4"><div>PREFIX</div></th>
							<th style="width: 14%" title="GATEWAY ID"><div>GATEWAY ID</div></th>
							<th style="width: 20%" title="GATEWAY"><div>GATEWAY</div></th>
							<th style="width: 14%" title="GATEWAY PREFIX"><div>GATEWAY PREFIX</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">Trunk Gateway 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>Trunk Gateway 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			
			<div class="formTableContainer">
				<form id="editForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formWrap">
						<label class="formLabel formLabel120">그룹<span class="require">*</span></label>
						<div class="formInput">
							<select id="selUGWGroupList" class="marginB5" data-groupid = "iptUGWGroupId" data-groupname = "iptUGWGroupName">
								<option>선택한 그룹 아이디(이름)</option>
							</select>
							<input id="iptUGWGroupId" name="group_gw_id" type=text placeholder="새로운 그룹 ID" disabled="disabled" class="marginB5" maxlength="3">
							<input id="iptUGWGroupName" name="group_gw_name" type=text placeholder="새로운 그룹 명" maxlength="32"/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel formLabel120">고객사</label>
						<div class="formInput">
							<input type="text" id="selGroupId" name="group_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel formLabel120">센터</label>
						<div class="formInput">
							<input type="text" id="selCenterId" name="center_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel formLabel120">PREFIX</label>
						<div class="formInput">
							<input id="iptPrefix4" type="text" name ="prefix4" disabled="disabled" maxlength="4">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel formLabel120">GATEWAY ID</label>
						<div class="formInput">
							<input id="iptUGwId"  name="gw_id" type="text" disabled="disabled" maxlength="3">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel formLabel120">GATEWAY<span class="require">*</span></label>
						<div class="formInput">
							<input name="gateway" type="text" maxlength="64">
							<p class="note">※반드시 IP:PORT 주소를 사용</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel formLabel120">GATEWAY PREFIX<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptGWPrefix" name="gateway_prefix" type="text" maxlength="4">
						</div>
					</div>
				</fieldset>
				</form>
			</div>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit"  type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">Trunk Gateway 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>Trunk Gateway가 없습니다.</h6>
		<p>Trunk Gateway를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>Trunk Gateway 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel formLabel120">그룹<span class="require">*</span></label>
							<div class="formInput">
								<select id="selNGWGroupList" class="marginB5" data-groupid = "iptNGWGroupId" data-groupname = "iptNGWGroupName">
									<option>새로운 그룹</option>
								</select>
								<input id="iptNGWGroupId"  name="group_gw_id"  type=text placeholder="새로운 그룹 ID" class="marginB5" maxlength="3">
								<input id="iptNGWGroupName"  name="group_gw_name"  type=text placeholder="새로운 그룹 명" maxlength="32"/>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel formLabel120">고객사</label>
							<div class="formInput">
								<select id="selNGroupId" name="group_id">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel formLabel120">센터</label>
							<div class="formInput">
								<select id="selNCenterId" name="tenant_id">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel formLabel120">PREFIX<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNPrefix4" name="prefix4" type="text" maxlength="4">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel formLabel120">GATEWAY ID<span class="require">*</span></label>
							<div class="formInput">
								<input name="gw_id" type="text" maxlength="3">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel formLabel120">GATEWAY<span class="require">*</span></label>
							<div class="formInput">
								<input name="gateway" type="text" maxlength="64">
								<p class="note">※반드시 IP:PORT 주소를 사용</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel formLabel120">GATEWAY PREFIX<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNGWPrefix" name="gateway_prefix" type="text" maxlength="4">
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
		$.getScript(getContextPath() + "/resources/service/js/service/trunkGW.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "trunkGW") {
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