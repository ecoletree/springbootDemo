<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="huntGroupMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>PBX 헌트 그룹 관리</h1>
		<span>내선 관리 &gt; PBX 헌트 그룹 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup"  data-skillgroup="selGroupCenter">
			</select>
			<select id="selGroupCenter">
<!-- 				<option>고객을 선택해주세요</option> -->
<!-- 				<option>센터 전체</option> -->
			</select>
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 13%" title="고객사"><div>고객사</div></th>
							<th style="width: 13%" title="센터"><div>센터</div></th>
							<th style="width: 15%" title="헌트 그룹 ID"><div>헌트 그룹 ID</div></th>
							<th style="width: 15%" title="헌트 그룹 명"><div>헌트 그룹 명</div></th>
							<th style="width: 15%" title="타임아웃"><div>타임아웃</div></th>
							<th style="width: 29%" title="소속 내선"><div>소속 내선</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td class="txtCenter">4001</td> -->
<!-- 							<td>헌트 그룹 01</td> -->
<!-- 							<td class="txtRight"><span>100</span>초</td> -->
<!-- 							<td></td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td class="txtCenter">4002</td> -->
<!-- 							<td>헌트 그룹 02</td> -->
<!-- 							<td class="txtRight"><span>100</span>초</td> -->
<!-- 							<td>1500, 1501, 1502, 1503</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">헌트 그룹 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>헌트 그룹 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			
			<div class="formTableContainer">
				<form id="editForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<input id="selUGroup" type="text" name="group_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터</label>
						<div class="formInput">
							<input id="selUGroupCenter" type="text"  name="tenant_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">헌트 그룹 ID</label>
						<div class="formInput">
							<input id="iptGroupId" name="grp_id" type="text" disabled="disabled">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">헌트 그룹 명<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptGroupName" name="grp_name" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">타임아웃</label>
						<div class="formInput">
							<div class="input-group">
								<input id="iptNoanswer_timeout" name="noanswer_timeout" type="text" class="txtRight" maxlength="5"> 
								<span class="input-group-addon">초</span>
							</div>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">소속 내선</label>
						<div class="formInput">
							<ul id="ulStationList" class="ulSubList">
<!-- 								<li>1510</li> -->
<!-- 								<li>1511</li> -->
							</ul>						
						</div>
					</div>
				</fieldset>
				</form>
				
			</div>
			
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">헌트 그룹 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>헌트 그룹이 없습니다.</h6>
		<p>헌트 그룹을 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>헌트 그룹 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel">고객사</label>
							<div class="formInput">
								<select id="selNGroup" name="group_id"  data-skillgroup="selNGroupCenter">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">센터</label>
							<div class="formInput">
								<select id="selNGroupCenter" name="tenant_id">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">헌트 그룹 ID<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNGroupId" name="grp_id"  type="text" maxlength="4">
								<p class="note">※2000번대 사용을 추천(9로 시작은 불가)</p>
								<p class="note">※4자리 숫자로 입력</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">헌트 그룹 명<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNGroupName" name="grp_name" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">타임아웃</label>
							<div class="formInput">
								<div class="input-group">
									<input id="iptNNoanswer_timeout" name="noanswer_timeout"  type="text" class="txtRight" maxlength="5"> 
									<span class="input-group-addon">초</span>
								</div>
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
	$.getScript(getContextPath() + "/resources/service/js/service/huntGroupMgt.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "huntGroupMgt") {
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