<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="custMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>고객사 업무시간 관리</h1>
		<span>조직 관리 &gt; 고객사 업무시간 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
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
							<th style="width: 20%" title="고객사"><div>고객사</div></th>
							<th style="width: 20%" title="센터"><div>센터</div></th>
							<th style="width: 20%" title="번호"><div>요일</div></th>
							<th style="width: 20%" title="메모"><div>시작시간</div></th>
							<th style="width: 20%" title="등록일"><div>종료시간</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">고객사 업무시간 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>고객사 업무시간 상세보기</h5>
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
						<label class="formLabel">요일</label>
						<div class="formInput">
							<select id="selWSDay" name="ws_day">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">시작시간</label>
						<div class="formInput">
							<div class="input-group">
								<select id="selWSStartH" name="ws_start_h">
								</select>
								<span class="input-group-addon">:</span>
								<select id="selWSStartM" name="ws_start_m">
								</select> 
							</div>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">종료시간</label>
						<div class="formInput">
							<div class="input-group">
								<select id="selWSEndH" name="ws_end_h">
								</select>
								<span class="input-group-addon">:</span>
								<select id="selWSEndM" name="ws_end_m">
								</select>
							</div>
						</div>
					</div>
				</fieldset>
				</form>
			</div>
			
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">고객사 업무시간 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>고객사 업무시간이 없습니다.</h6>
		<p>고객사 업무시간을 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>고객사 업무시간 추가</h5>
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
							<label class="formLabel">요일 타입</label>
							<div class="formInput-inline">
								<label class="ecoleRadio"><input type="radio" name="raDays" value="1" checked="checked"><i></i>개별</label>
								<label class="ecoleRadio"><input type="radio" name="raDays" value="2"><i></i>주중</label>
								<label class="ecoleRadio"><input type="radio" name="raDays" value="3"><i></i>주말</label>
								<label class="ecoleRadio"><input type="radio" name="raDays" value="4"><i></i>1주일</label>
							</div>
						</div>
						<div id="divDays" class="formWrap">
							<label class="formLabel">요일</label>
							<div class="formInput">
								<select id="selNWSDay" name="ws_day">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">시작시간</label>
							<div class="formInput">
								<div class="input-group">
									<select id="selNWSStartH" name="ws_start_h">
									</select>
									<span class="input-group-addon">:</span>
									<select id="selNWSStartM" name="ws_start_m">
									</select> 
								</div>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">종료시간</label>
							<div class="formInput">
								<div class="input-group">
									<select id="selNWSEndH" name="ws_end_h">
									</select>
									<span class="input-group-addon">:</span>
									<select id="selNWSEndM" name="ws_end_m">
									</select>
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
		$.getScript(getContextPath() + "/resources/service/js/service/custTimeMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "custTimeMgt") {
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