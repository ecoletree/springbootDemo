<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="rateMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>일반 요율 관리</h1>
		<span>과금 관리 &gt; 일반 요율 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<input id="iptPrefixName" type="text" placeholder="Prefix 명">
				<input id="iptPrefix" type="text" placeholder="Prefix">
				<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
			</div>
			<button type="button" name="ratePopup" class="btnSub">요울 관리</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 20%" title="Prefix 명"><div>Prefix 명</div></th>
							<th style="width: 20%" title="Prefix"><div>Prefix</div></th>
							<th style="width: 20%" title="사용 통신사"><div>사용 통신사</div></th>
							<th style="width: 20%" title="요율 명칭"><div>요율 명칭</div></th>
							<th style="width: 20%" title="적용일"><div>적용일</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
						<tr>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">일반 요율 Prefix 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>일반 요율 Prefix 상세보기</h5>
			<button type="button" name="ratePopup" class="btnSub">요울 관리</button>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<input name="info_id" type="hidden" />
			<input name="info_type" type="hidden" />
			<fieldset>
				<div class="formTableContainer">
					
					<div class="formWrap">
						<label class="formLabel">Prefix 명<span class="require">*</span></label>
						<div class="formInput">
							<input name="prefix_name" type="text" maxlength="45">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">Prefix<span class="require">*</span></label>
						<div class="formInput">
							<input name="prefix" type="text" maxlength="32">
						</div>
					</div>
					<!-- div class="formWrap">
						<label class="formLabel">Route</label>
						<div class="formInput">
							<input name="route" type="text" >
						</div>
					</div-->
					
					<div class="formWrap">
						<label class="formLabel">적용일<span class="require">*</span></label>
						<div class="formInput">
							<div id="divApplyDttm" class="bxEcoleDate input-group date">
								<input name="apply_dttm" type="text" readonly="readonly"> 
								<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
							</div>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">사용 통신사</label>
						<div class="formInput">
							<select id="selTelecom" name="telecom_code">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">요율 명칭<span class="require">*</span></label>
						<div class="formInput">
							<select id="selRate" name="rate_code">
							</select>
						</div>
					</div>
					
					
					<div class="bxWall marginT10">
						<div class="wallHead">
							<p>요율 정보</p>
						</div>
						
						<div class="wallBody">
							<div class="ecloeTableWrap dataTables_wrapper form-inline">
								<table id="tbRateList" class="ecloeTable table table-hover dataTable" style="width: 100%">
									<thead>
										<tr>
											<th style="width: 11%" title="대역 구분" ><div>대역 구분</div></th>
											<th style="width: 14%" title="휴일 구분" ><div>휴일 구분</div></th>
											<th style="width: 12%" title="시작 시간" ><div>시작 시간</div></th>
											<th style="width: 12%" title="종료 시간" ><div>종료 시간</div></th>
											<th style="width: 14%" title="기준 시간(s)" ><div>기준 시간(s)</div></th>
											<th style="width: 14%" title="시간당 단가" ><div>시간당 단가</div></th>
											<th style="width: 23%" title="설명 " ><div>설명</div></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>	
						</div>
					</div><!-- ./bxWall -->
					
				</div>
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">일반 요율 Prefix 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div id="divRateInfo" class="contRightNoData" style="display: block;">
		<h6>일반 요율 Prefix가 없습니다.</h6>
		<p>일반 요율 Prefix를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>일반 요율 Prefix 추가</h5>
				<button id="btnRatePopup" name="ratePopup" class="btnMain">요율 관리</button>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel">Prefix 명<span class="require">*</span></label>
							<div class="formInput">
								<input name="prefix_name" type="text" maxlength="45">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">Prefix<span class="require">*</span></label>
							<div class="formInput">
								<input name="prefix" type="text" maxlength="32">
							</div>
						</div>
						<!-- div class="formWrap">
							<label class="formLabel">Route</label>
							<div class="formInput">
								<input name="route" type="text"  value="*">
							</div>
						</div-->
						
						<div class="formWrap">
							<label class="formLabel">적용일<span class="require">*</span></label>
							<div class="formInput">
								<div id="divNApplyDttm" class="bxEcoleDate input-group date">
									<input name="apply_dttm" type="text" readonly="readonly" > 
									<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
								</div>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">사용 통신사</label>
							<div class="formInput">
								<select id="selNTelecom" name="telecom_code">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">요율 명칭<span class="require">*</span></label>
							<div class="formInput">
								<select id="selNRate" name="rate_code">
								</select>
							</div>
						</div>
						
						
						<div class="bxWall marginT10">
							<div class="wallHead">
								<p>요율 정보</p>
							</div>
							
							<div class="wallBody">
								<div class="ecloeTableWrap dataTables_wrapper form-inline">
									<table id="tbNRateList" class="ecloeTable table table-hover dataTable" style="width: 100%">
										<thead>
											<tr>
												<th style="width: 11%" title="대역 구분" ><div>대역 구분</div></th>
												<th style="width: 14%" title="휴일 구분" ><div>휴일 구분</div></th>
												<th style="width: 12%" title="시작 시간" ><div>시작 시간</div></th>
												<th style="width: 12%" title="종료 시간" ><div>종료 시간</div></th>
												<th style="width: 14%" title="기준 시간(s)" ><div>기준 시간(s)</div></th>
												<th style="width: 14%" title="시간당 단가" ><div>시간당 단가</div></th>
												<th style="width: 23%" title="설명 " ><div>설명</div></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>	
							</div>
						</div><!-- ./bxWall -->
						
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


	<!-- 시작: 요율 관리 -->
	<c:import url="/service.body.modal.rateModal.sp" charEncoding="UTF-8" />
	<!-- 끝: 요율 관리 -->
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/rateMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "rateMgt") {
				var inter = setInterval(function(){
					 ecoletree.promiseInit()
					.then(function(){
						clearInterval(inter);
						ecoletree.vc.init(${initData});
					}, function (){})
				},800);
				
			} else {
				console.log("vc's name is not index : " + ecoletree.vc.name);
			}
		}).fail(function(jqxhr, settings, exception) {
			console.log(arguments);
		});
	</script>
	

</div>