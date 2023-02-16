<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="recordUploadWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>PBX 관리</h1>
		<span>음원 관리 &gt; PBX 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroupList">
			</select>
			<select id="selCenterList">
			</select>
			<input id="ipxSearchFileName" type="text" placeholder="파일명">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 4%;"><div><label class="ecoleCheck"><input id="chkAllCheck" type="checkbox" ><i></i></label></div></th>
							<th style="width: 18%" title="고객사"><div>고객사</div></th>
							<th style="width: 18%" title="센터"><div>센터</div></th>
							<th style="width: 30%" title="파일명"><div>파일명</div></th>
							<th style="width: 30%" title="듣기"><div>듣기</div></th>
 
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">음원 추가</button>
		<button id="btnDelete" class="btnGray" style="display:none;">음원 삭제</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	

	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>음원이 없습니다.</h6>
		<p>음원을 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	
	<!-- 시작: 음원 등록 -->
	<div id="divSound" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog popW450" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">음원 등록</h4>
				</div>
				
				<div class="modal-body">
					<h6>등록할 음원을 선택해 주세요.</h6>
					<p>※고객사을 확인 후 업로드 해주세요.</p>
					<div class="formTableContainer" >
						<form id="formUpload" method="post" enctype="multipart/form-data" autocomplete="off"><fieldset>
							<div class="formWrap">
								<label class="formLabel">고객사</label>
								<div class="formInput">
									<select id="selGroupId" name="group_id"></select>
								</div>
							</div>
							<div class="formWrap">
								<label class="formLabel">센터</label>
								<div class="formInput">
									<select id="selCenterId" name="tenant_id">
									</select>
								</div>
							</div>
							<div class="formWrap">
								<label class="formLabel">음원<span class="require">*</span></label>
								<div class="formInput">
									<div class="bxWP100 fileinput fileinput-new" data-provides="fileinput">
										<div class="ecoleFile">
											<button type="button" class="btnGray btnBlock" >음원 파일 선택</button>
											<input id='uploadByDec' type="file" name="file" data-max-name-length="100" accept=".wav" data-file_types="wav">
										</div>
										<span class="fileinput-filename"></span>
										<audio id="audioSound" controls="controls" style="display: none;">
											<source src="" id="srcSound">
										</audio>
									</div>
									<input type="hidden" name="file_path">
									<input type="hidden" name="file_size">
									<input type="hidden" name="file_name">
								</div>
							</div>
						</fieldset></form>
					</div>
				</div>
				
				<div class="modal-footer margin0">
					<button id="btnSave" type="button" class="btnMain">등록하기</button>
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 음원 등록 -->
	
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/pbxRecordUpload.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "pbxRecordUpload") {
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