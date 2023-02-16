<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="custMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>센터 관리</h1>
		<span>조직 관리 &gt; 센터 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup">
			</select>
			<input id="iptCenter" type="text" placeholder="센터명">
			<input id="iptSearchTenantMaster" type="text" placeholder="담당자명">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 8%" title="고객사"><div>고객사</div></th>
							<th style="width: 8%" title="센터명"><div>센터명</div></th>
							<th style="width: 8%" title="센터 일련번호"><div>센터 일련번호</div></th>
							<th style="width: 8%" title="다이얼러 센터 코드"><div>다이얼러 센터 코드</div></th>
							<th style="width: 10%" title="국제전화"><div>국제전화</div></th>
							<th style="width: 10%" title="부가 서비스"><div>부가 서비스</div></th>
							<th style="width: 10%" title="콜센터 서비스"><div>콜센터 서비스</div></th>
							<th style="width: 10%" title="최대 통화시간"><div>최대 통화시간</div></th>
							<th style="width: 8%" title="담당자명"><div>담당자명</div></th>
							<th style="width: 10%" title="주소"><div>주소</div></th>
							<th style="width: 10%" title="연락처"><div>연락처</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">센터 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>센터 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<div class="formTableContainer">
					
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<select id="selUGroup" name="group_id" disabled>
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터 일련번호</label>
						<div class="formInput">
							<input id="iptUTenantId" name="tenant_id" type="text" disabled="disabled">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터명<span class="require">*</span></label>
						<div class="formInput">
							<input name="group_name" type="text">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">다이얼러 센터코드</label>
						<div class="formInput">
							<input id="iptDialerCenterCode" name="dialer_center_code" type="text" maxlength="4" >
						</div>
					</div>
					
					<div class="formWrap">
						<label class="formLabel">국제전화</label>
						<div class="formInput">
							<select id="selInternationalCall" name="international_call">
								<option value="1">사용</option>
								<option value="0">사용 안함</option>
							</select>
							<p class="note">※국제전화(00X)번 발신허용 여부</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">부가 서비스</label>
						<div class="formInput">
							<select id="selVaCall" name="va_call">
								<option value="1">사용</option>
								<option value="0">사용 안함</option>
							</select>
							<p class="note">※부가 서비스전화(060)번 발신허용 여부</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">콜센터 서비스</label>
						<div class="formInput">
							<select id="selCallCenter" name="call_center">
								<option value="1">사용</option>
								<option value="0">사용 안함</option>
							</select>
							<p class="note">※콜센터 서비스 사용 여부</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">최대 통화시간</label>
						<div class="formInput">
							<div class="formInput-inline">
								<label class="ecoleRadio"><input type="radio" value="0" name="raSessionTime"><i></i>무제한</label>
								<label class="ecoleRadio"><input type="radio" value="1" name="raSessionTime" checked="checked" ><i></i>직접입력</label>
							</div>
							<div class="input-group">
								<input id="iptMaxSesstionTime" name="max_session_time" type="text" class="txtRight" maxlength="10"> 
								<span class="input-group-addon">초</span>
							</div>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">담당자명<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptTenantMaster" name="group_master" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">주소</label>
						<div class="formInput">
							<input id="iptAddress" name="address" type="text" maxlength="128">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">연락처</label>
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
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">센터 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>센터가 없습니다.</h6>
		<p>센터를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>센터 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel">고객사</label>
							<div class="formInput">
								<select id="selNGroup" name="group_id" >
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">센터 일련번호<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNTenantId" name="tenant_id" type="text">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">센터명<span class="require">*</span></label>
							<div class="formInput">
								<input name="group_name" type="text">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">다이얼러 센터코드</label>
							<div class="formInput">
								<input id="iptNDialerCenterCode" name="dialer_center_code" type="text" maxlength="4">
							</div>
						</div>
						
						<div class="formWrap">
							<label class="formLabel">국제전화</label>
							<div class="formInput">
								<select id="selNInternationalCall" name="international_call">
									<option value="1">사용</option>
									<option value="0">사용 안함</option>
								</select>
								<p class="note">※국제전화(00X)번 발신허용 여부</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">부가 서비스</label>
							<div class="formInput">
								<select id="selNVaCall" name="va_call">
									<option value="1">사용</option>
									<option value="0">사용 안함</option>
								</select>
								<p class="note">※부가 서비스전화(060)번 발신허용 여부</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">콜센터 서비스</label>
							<div class="formInput">
								<select id="selNCallCenter" name="call_center">
									<option value="1">사용</option>
									<option value="0">사용 안함</option>
								</select>
								<p class="note">※콜센터 서비스 사용 여부</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">최대 통화시간</label>
							<div class="formInput">
								<div class="formInput-inline">
									<label class="ecoleRadio"><input value="0" name="raNSessionTime" type="radio" checked="checked"><i></i>무제한</label>
									<label class="ecoleRadio"><input value="1" name="raNSessionTime" type="radio" ><i></i>직접입력</label>
								</div>
								<div class="input-group">
									<input id="iptNMaxSesstionTime" name="max_session_time" type="text" value="0" class="txtRight" disabled="disabled" maxlength="10"> 
									<span class="input-group-addon">초</span>
								</div>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">담당자명<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNTenantMaster" name="group_master" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">주소</label>
							<div class="formInput">
								<input id="iptNAddress" name="address" type="text" maxlength="128">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">연락처</label>
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
		$.getScript(getContextPath() + "/resources/service/js/service/centerMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "centerMgt") {
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