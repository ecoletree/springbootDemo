<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="gateMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>애니 다이얼 관리</h1>
		<span>애니매니저 &gt; 애니 다이얼 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup">
			</select>
			<select id="selGroupCenter" data-teamlist="selTeamList" >
			</select>
			<select id="selBroadBand" >
				<option>지역 전체</option>
			</select>
			<select id="selStatusCode">
				<option>상태 전체</option>
			</select>
			<select id="selIsUse" >
				<option value="">사용유무 전체</option>
				<option value="Y">사용</option>
				<option value="N">사용 안함</option>
			</select>
			<input id="iptOutNo" type="text" placeholder="발신 번호" maxlength="32">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 8%" title="고객사"><div>고객사</div></th>
							<th style="width: 8%" title="센터"><div>센터</div></th>
							<th style="width: 8%" title="다이얼러 센터코드"><div>다이얼러 센터코드</div></th>
							<th style="width: 7%" title="다이얼러"><div>다이얼러</div></th>
							<th style="width: 8%" title="지역"><div>지역</div></th>
							<th style="width: 6%" title="지역 번호"><div>지역 번호</div></th>
							<th style="width: 9%" title="발신 번호"><div>발신 번호</div></th>
							<th style="width: 9%" title="착신 번호"><div>착신 번호</div></th>
							<th style="width: 7%" title="상태코드"><div>상태코드</div></th>
							<th style="width: 6%" title="사용유무"><div>사용유무</div></th>
							<th style="width: 7%" title="사용 시작일"><div>사용 시작일</div></th>
							<th style="width: 7%" title="사용 종료일"><div>사용 종료일</div></th>
							<th style="width: 10%" title="등록일시"><div>등록일시</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>00001</td> -->
<!-- 							<td>00001</td> -->
<!-- 							<td>00001</td> -->
<!-- 							<td>00001</td> -->
<!-- 							<td>제주특별자치도</td> -->
<!-- 							<td class="txtCenter">000</td> -->
<!-- 							<td class="txtCenter">00000000000</td> -->
<!-- 							<td class="txtCenter">00000000000</td> -->
<!-- 							<td class="txtCenter">AM등록완료</td> -->
<!-- 							<td class="txtCenter">사용</td> -->
<!-- 							<td class="txtCenter">2020.05.05</td> -->
<!-- 							<td class="txtCenter">2020.05.05</td> -->
<!-- 							<td class="txtCenter">2020.05.05 09:00</td> -->
							
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain">애니 다이얼 추가</button>
		<div>
			<button id="btnExcelUpLoad" class="btnMain" style="display:none;">엑셀 업로드</button>
			<button id="btnExcelTemplateDownload" class="btnMain" onclick="location.href=' ${cp }/resources/template/ANIDIALER_SAMPLE_EXCEL.xlsx'" >엑셀 양식 내려받기</button>
			<button id="btnExcelDownLoad" class="btnMain" style="display:none;">엑셀 다운로드</button>
		</div>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>애니 다이얼 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<div class="formTableContainer">
					
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<input type="text" id="iptGroupId" name="group_name" disabled>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터</label>
						<div class="formInput">
							<input type="text" id="iptCenterId" name="center_name" disabled>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">다이얼러 센터 코드</label>
						<div class="formInput">
							<input id="iptUdialerCenterCode" name="center_code" type="text" disabled="disabled">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">다이얼러</label>
						<div class="formInput">
							<select id="selUDialerCode" name="dialer_cd">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">지역</label>
						<div class="formInput">
							<select id="selUBroadBandCode" name="broadband_code">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">발신번호<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptUOutNo" name="out_no" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">착신번호<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptUInNo" name="in_no" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">메모</label>
						<div class="formInput">
							<input id="iptUMemo" name="memo" type="text" maxlength="4000">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">사용 유무</label>
						<div class="formInput">
							<select id="selUIsUse" name="is_use">
								<option value="Y">사용</option>
								<option value="N">사용 안함</option>
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">상태코드</label>
						<div class="formInput">
							<select id="selUStatusCode" name="status_code">
								<option></option>
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">사용 시작일<span class="require">*</span></label>
						<div class="formInput">
							<div id="divUstartDate" class="bxEcoleDate input-group date">
								<input id="iptUstartDate" type="text" readonly="readonly" name="start_date" disabled="disabled"> 
								<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
							</div>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">사용 종료일<span class="require">*</span></label>
						<div class="formInput">
							<div id="divUendDate" class="bxEcoleDate input-group date">
								<input id="iptUendDate" type="text" readonly="readonly" name="end_date" disabled="disabled"> 
								<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
							</div>
						</div>
					</div>
					
				</div>
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">애니 다이얼 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>애니 다이얼이 없습니다.</h6>
		<p>애니 다이얼을 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>애니 다이얼 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel">고객사</label>
							<div class="formInput">
								<select id="selNGroup" name="group_id"></select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">센터</label>
							<div class="formInput">
								<select id="selNGroupCenter" name="center_id"></select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">다이얼러 센터 코드</label>
							<div class="formInput">
								<input id="iptNdialerCenterCode" name="center_code" type="text" disabled="disabled">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">다이얼러</label>
							<div class="formInput">
								<select id="selNDialerCode" name="dialer_cd">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">지역</label>
							<div class="formInput">
								<select id="selNBroadBandCode" name="broadband_code">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">발신번호<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNOutNo" type="text" name="out_no" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">착신번호<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNInNo" type="text" name="in_no" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">메모</label>
							<div class="formInput">
								<input type="text" name="memo"  maxlength="4000">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">사용 유무</label>
							<div class="formInput">
								<select name="is_use">
									<option value="Y">사용</option>
									<option value="N">사용 안함</option>
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">상태코드</label>
							<div class="formInput" >
								<select id="selNStatusCode" name="status_code">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">사용 시작일<span class="require">*</span></label>
							<div class="formInput">
								<div id="divNstartDate" class="bxEcoleDate input-group date">
									<input id="iptNstartDate" type="text" name="start_date" readonly="readonly" disabled="disabled"> 
									<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
								</div>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">사용 종료일<span class="require">*</span></label>
							<div class="formInput">
								<div id="divNendDate" class="bxEcoleDate input-group date">
									<input id="iptNendDate"  type="text" name="end_date" readonly="readonly" disabled="disabled"> 
									<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
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


	<!-- 시작: 엑셀 업로드 -->
	<div id="modalAddList" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog popW450" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">엑셀 업로드</h4>
				</div>
				
				<div class="modal-body">
					<h6>업로드 할 파일을 선택해 주세요.</h6>
					<p>※반드시 엑셀 양식 내려받기로 받은 파일을 이용해 주세요.</p>
					<div class="formTableContainer" >
						<div class="formWrap">
							<div class="formInput">
								<div class="bxWP100 fileinput fileinput-new" data-provides="fileinput">
									<div class="ecoleFile">
										<button class="btnGray btnBlock fileinput-new" >엑셀 파일 선택</button>
										<input id='uploadByDec' type="file" name="file" data-max-name-length="100">
										<button class="btnGray btnBlock fileinput-exists" >파일 교체</button>
									</div>
									<span class="fileinput-filename"></span>
									<a href="#" id="fileDel" class="btnWhite fileinput-exists" data-dismiss="fileinput" style="float: none">파일 삭제</a>
									<a id="link_on_detailFile" class='fileinput-exists-download attach btnDownload' style="display:none;" >첨부파일 다운로드</a>
								</div>
								<input type="hidden" name="file_path">
								<input type="hidden" name="file_size">
								<input type="hidden" name="file_name">
							</div>
						</div>
					</div>
				</div>
				
				<div class="modal-footer margin0">
<!-- 					<a id="btnDownLoadTemplate" class="btnSub pull-left" >템플릿 내려받기</a> -->
					<a id="btnDownLoadTemplate" class="btnSub pull-left" href="${cp }/resources/template/ANIDIALER_SAMPLE_EXCEL.xlsx" >엑셀 양식 내려받기</a>
					<button id="btnSaveList" type="button" class="btnMain">등록하기</button>
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 엑셀 업로드 -->
	

</div>
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/anidialMgt.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "anidialMgt") {
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