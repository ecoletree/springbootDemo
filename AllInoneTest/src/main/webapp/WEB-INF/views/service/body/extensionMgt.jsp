<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="extensionMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>내선 번호 관리</h1>
		<span>내선 관리 &gt; 내선 번호 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup">
			</select>
			<select id="selGroupCenter"  data-huntgroup="selHuntGroupList">
<!-- 				<option>고객을 선택해주세요</option> -->
<!-- 				<option>센터 전체</option> -->
			</select>
			<input id="ipxSearchNum" type="text" placeholder="내선 번호">
			<input id="ipxSearchDisplayNumGW" type="text" placeholder="발신 Gateway">
			<input id="ipxSearchDisplayNum" type="text" placeholder="발신 번호">
			<select id="selHuntGroupList">
<!-- 				<option>헌트 그룹 전체</option> -->
			</select>
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 9%" title="고객사"><div>고객사</div></th>
							<th style="width: 9%" title="센터"><div>센터</div></th>
							<th style="width: 9%" title="내선 번호"><div>내선 번호</div></th>
							<th style="width: 9%" title="전화기 인증암호"><div>전화기 인증암호</div></th>
							<th style="width: 9%" title="발신 Gateway"><div>발신 Gateway</div></th>
							<th style="width: 9%" title="발신 번호"><div>발신 번호</div></th>
							<th style="width: 9%" title="헌트 그룹 ID"><div>헌트 그룹 ID</div></th>
							<th style="width: 9%" title="헌트 그룹 명"><div>헌트 그룹 명</div></th>
							<th style="width: 9%" title="전화기 상태"><div>전화기 상태</div></th>
							<th style="width: 10%" title="상태 시작 시간"><div>상태 시작 시간</div></th>
							<th style="width: 9%" title="내선 IP"><div>내선 IP</div></th>
 
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td class="txtCenter">5001</td> -->
<!-- 							<td>1234567890</td> -->
<!-- 							<td class="txtCenter">07000000000</td> -->
<!-- 							<td class="txtCenter">07000000000</td> -->
<!-- 							<td class="txtCenter">4001</td> -->
<!-- 							<td>헌트 그룹 01</td> -->
<!-- 							<td class="txtCenter">hangup</td> -->
<!-- 							<td class="txtCenter">2020.01.01 01:00</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">내선 번호 추가</button>
		<button id="btnAdd_list" class="btnMain" style="display:none;">내선 번호 일괄 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>내선 번호 상세보기</h5>
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
							<input id="selUGroupCenter" type="text"  name="tenant_name" data-huntgroup="selHuntGroup" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">내선 번호</label>
						<div class="formInput">
							<input id="iptPhone" name="phone" type="text" disabled="disabled">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">전화기 인증암호<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptPassword" name="password" type="text" >
							<p class="note">※SIP 등록시 사용하는 전화기 패스워드</p>
							<p class="note">※고객사 단위로 다른 값 사용 추천</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">발신 Gateway<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptGateway" name="gateway" type="text" >
							<p class="note">※아웃바운드 시 사용할 게이트웨이</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">발신 번호</label>
						<div class="formInput">
							<input id="iptDisplayNum" name="display_num" type="text" >
							<p class="note">※아웃바운드 시 사용할 발신 번호</p>
						</div>
					</div>
					
					<div class="formWrap">
						<label class="formLabel">헌트 그룹</label>
						<div class="formInput">
							<select id="selHuntGroup" name="station_grp" class="select2" multiple="multiple">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">내선 IP<span class="require">*</span></label>
						<div class="formInput">
							<input name="station_ip" type="text" maxlength="16">
							<p class="note">※내선 전화기 IP</p>
						</div>
					</div>
				</fieldset>
				</form>
			</div>
			
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">내선 번호 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>내선 번호가 없습니다.</h6>
		<p>내선 번호를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>내선 번호 추가</h5>
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
							<label class="formLabel">센터</label>
							<div class="formInput">
								<select id="selNGroupCenter" name="tenant_id" data-huntgroup="selNHuntGroup">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">내선 번호<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNPhone" name="phone" type="text" maxlength="5">
								<p class="note">※50000번대 사용을 추천(9로 시작은 불가)</p>
								<p class="note">※5자리 숫자로 입력</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">전화기 인증암호<span class="require">*</span></label>
							<div class="formInput">
								<input  id="iptNPassword" name="password" type="text" >
								<p class="note">※SIP 등록시 사용하는 전화기 패스워드</p>
								<p class="note">※고객사 단위로 다른 값 사용 추천</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">발신 Gateway<span class="require">*</span></label>
							<div class="formInput">
								<input id="iptNGateway" name="gateway"  type="text" >
								<p class="note">※아웃바운드 시 사용할 게이트웨이</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">발신 번호</label>
							<div class="formInput">
								<input id="iptNDisplayNum" name="display_num"  type="text" >
								<p class="note">※아웃바운드 시 사용할 발신 번호</p>
							</div>
						</div>
						
						<div class="formWrap">
							<label class="formLabel">헌트 그룹</label>
							<div class="formInput">
								<select id="selNHuntGroup" name="station_grp" class="select2" multiple="multiple">
								</select>		
							</div>
						</div>
						
						<div class="formWrap">
							<label class="formLabel">내선 IP<span class="require">*</span></label>
							<div class="formInput">
								<input name="station_ip" type="text" maxlength="16">
								<p class="note">※내선 전화기 IP</p>
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
	
	
	<!-- 시작: 일괄 등록 -->
	<div id="modalAddList" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog popW450" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">내선 번호 일괄 등록</h4>
				</div>
				
				<div class="modal-body">
					<h6>등록할 파일을 선택해 주세요.</h6>
					<p>※가급적 템플릿 내려받기로 받은 파일을 이용해 주세요.</p>
					<div class="formTableContainer" >
						<div class="formWrap">
							<div class="formInput">
								<div class="bxWP100 fileinput fileinput-new" data-provides="fileinput">
									<div class="ecoleFile">
										<button class="btnGray btnBlock fileinput-new" >CSV 파일 선택</button>
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
								<p class="note">※포멧예시 <br>
고객사 ID, 센터 ID, 내선 번호, 전화기 인증암호, 발신 Gateway, 발신 번호, 헌트 그룹 ID, 내선 IP</p>
							</div>
						</div>
					</div>
				</div>
				
				<div class="modal-footer margin0">
					<a id="btnDownLoadTemplate" class="btnSub pull-left" href="${cp }/resources/template/EXTENSION_SAMPLE_CSV.csv" >템플릿 내려받기</a>
					<button id="btnSaveList" type="button" class="btnMain">등록하기</button>
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 일괄 등록 -->

</div>
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/extensionMgt.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "extensionMgt") {
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