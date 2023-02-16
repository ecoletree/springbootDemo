<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="custMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>다이얼러 관리</h1>
		<span>조직 관리 &gt; 다이얼러 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup">
			</select>
			<input id="iptDialerName" type="text" placeholder="다이얼러 명">
			<input id="iptDialerType" type="text" placeholder="타입">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 14%" title="고객사"><div>고객사</div></th>
							<th style="width: 16%" title="다이얼러 코드"><div>다이얼러 코드</div></th>
							<th style="width: 16%" title="다이얼러 명"><div>다이얼러 명</div></th>
							<th style="width: 14%" title="타입"><div>타입</div></th>
							<th style="width: 14%" title="IP"><div>IP</div></th>
							<th style="width: 14%" title="Port"><div>Port</div></th>
							<th style="width: 14%" title="사용유무"><div>사용유무</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>00001</td> -->
<!-- 							<td>00001</td> -->
<!-- 							<td>00001</td> -->
<!-- 							<td>00001</td> -->
<!-- 							<td class="txtCenter">123123456</td> -->
<!-- 							<td class="txtCenter">20</td> -->
<!-- 							<td class="txtCenter">사용</td> -->
							
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">다이얼러 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>다이얼러 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<div class="formTableContainer">
					
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<select id="selUGroup" name="group_cd">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">다이얼러 코드<span class="require">*</span></label>
						<div class="formInput">
							<input name="dialer_cd" id="iptDialerCode" type="text" maxlength="50" disabled="disabled">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">다이얼러 명<span class="require">*</span></label>
						<div class="formInput">
							<input name="dialer_name" type="text" maxlength="30">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">타입<span class="require">*</span></label>
						<div class="formInput">
							<input name="dialer_type" type="text" maxlength="50">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">IP<span class="require">*</span></label>
						<div class="formInput">
							<div id="divDialerIp" class="input-group">
								<input name="dialer_ip" type="text" maxlength="3">
								<span class="input-group-addon">.</span>
								<input name="dialer_ip" type="text" maxlength="3">
								<span class="input-group-addon">.</span>
								<input name="dialer_ip" type="text" maxlength="3">
								<span class="input-group-addon">.</span>
								<input name="dialer_ip" type="text" maxlength="3">
							</div>
							<p class="note">※반드시 IP주소를 사용</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">Port<span class="require">*</span></label>
						<div class="formInput">
							<input name="dialer_port" type="text" maxlength="15">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">User ID<span class="require">*</span></label>
						<div class="formInput">
							<input name="dialer_user" type="text" maxlength="30">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">Password<span class="require">*</span></label>
						<div class="formInput">
							<input name="dialer_pw" type="text" maxlength="50">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">업로드 경로
<!-- 						<span class="require">*</span> -->
						</label>
						<div class="formInput">
							<input name="dialer_dir" type="text" maxlength="2000">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">사용 유무</label>
						<div class="formInput">
							<select name="is_use" >
								<option value="Y">사용</option>
								<option value="N">사용 안함</option>
							</select>
						</div>
					</div>
					
				</div>
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">다이얼러 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>다이얼러가 없습니다.</h6>
		<p>다이얼러를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>다이얼러 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel">고객사</label>
							<div class="formInput">
								<select id="selNGroup" name="group_cd">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">다이얼러 코드<span class="require">*</span></label>
							<div class="formInput">
								<input name="dialer_cd" type="text"  maxlength="50">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">다이얼러 명<span class="require">*</span></label>
							<div class="formInput">
								<input name="dialer_name" type="text" maxlength="30">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">타입<span class="require">*</span></label>
							<div class="formInput">
								<input name="dialer_type" type="text" maxlength="50">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">IP<span class="require">*</span></label>
							<div class="formInput">
								<div class="input-group">
									<input name="dialer_ip" type="text" maxlength="3">
									<span class="input-group-addon">.</span>
									<input name="dialer_ip" type="text" maxlength="3">
									<span class="input-group-addon">.</span>
									<input name="dialer_ip" type="text" maxlength="3">
									<span class="input-group-addon">.</span>
									<input name="dialer_ip" type="text" maxlength="3">
								</div>
								<p class="note">※반드시 IP주소를 사용</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">Port<span class="require">*</span></label>
							<div class="formInput">
								<input name="dialer_port" type="text" maxlength="15">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">User ID<span class="require">*</span></label>
							<div class="formInput">
								<input name="dialer_user" type="text" maxlength="30">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">Password<span class="require">*</span></label>
							<div class="formInput">
								<input name="dialer_pw" type="text" maxlength="50">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">업로드 경로
<!-- 							<span class="require">*</span> -->
							</label>
							<div class="formInput">
								<input name="dialer_dir" type="text" maxlength="2000">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">사용 유무</label>
							<div class="formInput">
								<select name="is_use" >
									<option value="Y">사용</option>
									<option value="N">사용 안함</option>
								</select>
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


</div>
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/js/service/dialerMgt.js").done(function(script, textStatus) {
	if (!!ecoletree.vc && ecoletree.vc.name === "dialerMgt") {
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