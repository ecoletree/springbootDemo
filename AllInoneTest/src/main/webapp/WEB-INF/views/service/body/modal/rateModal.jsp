<!-------------------------------------------------------------
* Copyright (c) 2017 EcoleTree. All Rights Reserved. * 
* Author : boadl
* Created : 2022. 10. 19
* DESC : 
--------------------------------------------------------------->
<div id="modalAddList" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="false" data-backdrop="static" data-keyboard="false" >
	<div class="modal-dialog popW1500" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="false">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">요율 관리</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<!-- 리스트 -->
					<div class="col-sm-8">
						<div class="search">
							<div>
								<select id="selModalTelecom"><option>통신사 전체</option></select>
								<select id="selModalBillType"><option>통화 구분 전체</option></select>
								<button id="btnModalSearch" class="btnMain">조회</button>
							</div>
							<div></div>
						</div>
						<div class="ecloeScrollTableContainer popH400">
							<div class="ecloeScrollTableHeader"></div>
							<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
								<table id="tbRateDetailList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
									<thead>
										<tr>
											<th style="width: 8%" title="통신사" ><div>통신사</div></th>
											<th style="width: 14%" title="통화 구분" ><div>통화 구분</div></th>
											<th style="width: 10%" title="대역 구분" ><div>대역 구분</div></th>
											<th style="width: 10%" title="휴일 구분" ><div>휴일 구분</div></th>
											<th style="width: 9%" title="시작 시간" ><div>시작 시간</div></th>
											<th style="width: 9%" title="종료 시간" ><div>종료 시간</div></th>
											<th style="width: 10%" title="기준 시간(s)" ><div>기준 시간(s)</div></th>
											<th style="width: 10%" title="시간당 단가" ><div>시간당 단가</div></th>
											<th style="width: 20%" title="설명 " ><div>설명</div></th>
										</tr>
									</thead>
									<tbody class="cursorPoint">
									</tbody>
								</table>
							</div>
						</div><!-- ./ecloeScrollTableContainer -->
					</div>
					
					<!-- 상세 -->
					<div class="col-sm-4">
						<div class="contRightWrap">
							<form id="formEditRate" method="post" autocomplete="off">
								<fieldset>
									<input type="hidden" name="id"/>
									<div class="formTableContainer">
										<div class="formWrap">
											<label class="formLabel">통신사</label>
											<div class="formInput">
												<select id="selModalFormTelecom" name="telecom_code"></select>
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">통화구분</label>
											<div class="formInput">
												<select id="selModalFormBillType" name="bill_type"></select>
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">대역구분</label>
											<div class="formInput">
												<input type="text" class="txtRight" name="bill_range" maxlength="3">
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">휴일구분</label>
											<div class="formInput">
												<select name="holiday">
													<option value="N" selected>평일</option>
													<option value="Y">토/일/공휴일</option>
												</select>
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">시작 시간</label>
											<div class="formInput">
												<div class="input-group">
													<select id="selSHour" name="shour">
													</select>
													<span class="input-group-addon">:</span>
													<select id="selSMinute" name="sminute">
													</select>
												</div>
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">종료 시간</label>
											<div class="formInput">
												<div class="input-group">
													<select id="selEHour" name="ehour">
													</select>
													<span class="input-group-addon">:</span>
													<select id="selEMinute" name="eminute">
													</select>
												</div>
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">기준 시간<span class="require">*</span></label>
											<div class="formInput">
												<div class="input-group">
													<input type="text" class="txtRight" value="0" name="time" maxlength="4">
													<span class="input-group-addon">초</span>
												</div>
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">시간당 단가<span class="require">*</span></label>
											<div class="formInput">
												<div class="input-group">
													<input type="text" class="txtRight" value="0" name="rate" maxlength="13">
													<span class="input-group-addon">원</span>
												</div>
											</div>
										</div>
										<div class="formWrap">
											<label class="formLabel">설명</label>
											<div class="formInput">
												<input type="text" name="rate_name" maxlength="256">
											</div>
										</div>
										

									</div>
								</fieldset>
							</form>
							<div class="footRightWrap">
								<button id="btnRateDetailEdit" type="button"  class="btnSub is_update">수정</button>
								<button id="btnRateDetailDel" type="button" class="btnGray">삭제</button>
							</div>
							
							<!-- 수정: 요율 정보 없음 -->
							<div id="divRate" class="contRightNoData" style="display: block;">
								<h6>요율 정보가 없습니다.</h6>
								<p>요율 정보를 추가해 주세요.</p>
							</div>
						</div><!-- ./contRightWrap -->
						
					</div><!-- ./상세 -->
					
				</div><!-- ./row -->
				
				
				<!-- 신규 -->
				<div id="bxRateDetailWrite" class="conWrite" style="display: none;">
					<div class="contWriteWrap">
						<form id="formAddRate" method="post" autocomplete="off">
							<fieldset>
								<div class="formTableContainer">
									<div class="formWrap">
										<label class="formLabel">통신사</label>
										<div class="formInput">
											<select id="selModalFormNTelecom" name="telecom_code"></select>
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">통화구분</label>
										<div class="formInput">
											<select id="selModalFormNBillType" name="bill_type"></select>
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">대역구분</label>
										<div class="formInput">
											<input type="text" class="txtRight" name="bill_range" maxlength="3">
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">휴일구분</label>
										<div class="formInput">
											<select name="holiday">
												<option value="N" selected>평일</option>
												<option value="Y">토/일/공휴일</option>
											</select>
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">시작 시간</label>
										<div class="formInput">
											<div class="input-group">
												<select id="selNSHour" name="shour">
												</select>
												<span class="input-group-addon">:</span>
												<select id="selNSMinute" name="sminute">
												</select>
											</div>
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">종료 시간</label>
										<div class="formInput">
											<div class="input-group">
												<select id="selNEHour" name="ehour">
												</select>
												<span class="input-group-addon">:</span>
												<select id="selNEMinute" name="eminute">
												</select>
											</div>
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">기준 시간<span class="require">*</span></label>
										<div class="formInput">
											<div class="input-group">
												<input type="text" class="txtRight" value="0" name="time"  maxlength="4">
												<span class="input-group-addon">초</span>
											</div>
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">시간당 단가<span class="require">*</span></label>
										<div class="formInput">
											<div class="input-group">
												<input type="text" class="txtRight" value="0" name="rate" maxlength="13">
												<span class="input-group-addon">원</span>
											</div>
										</div>
									</div>
									<div class="formWrap">
										<label class="formLabel">설명</label>
										<div class="formInput">
											<input type="text" name="rate_name" maxlength="256">
										</div>
									</div>
									

								</div>
							</fieldset>
						</form>
						<div class="footWriteWrap">
							<button id="btnRateSave" type="button"  class="btnMain is_write">추가</button>
							<button type="button" class="btnGray" onclick="$('#bxRateDetailWrite').hide()">취소</button>
						</div>
	
					</div><!-- ./contWriteWrap -->
				</div><!-- ./신규 -->
				
				
			</div><!-- ./body -->
			
			
			<div class="modal-footer margin0">
				<button id="btnRateAdd" type="button" class="btnMain pull-left is_write">요율 추가</button>
				<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
			</div>
			
		</div>
		
	</div>
</div>
<!-- 끝: 요율 관리 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/js/service/modal/rateModal.js").done(function(script, textStatus) {
		if (!!ecoletree.rateModal && ecoletree.rateModal.name === "rateModal") {
			var inter = setInterval(function(){
				 ecoletree.promiseInit()
				.then(function(){
					clearInterval(inter);
					ecoletree.rateModal.init(${initData});
				}, function (){})
			},300);
			
		} else {
			console.log("vc's name is not index : " + ecoletree.rateModal.name);
		}
	}).fail(function(jqxhr, settings, exception) {
		console.log(arguments);
	});
</script>
