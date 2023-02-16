<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="ftpMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>녹취전송 FTP 관리</h1>
		<span>녹취 관리 &gt; 녹취전송 FTP 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup" data-skillgroup="selGroupCenter">
			</select>
			<select id="selGroupCenter">
			</select>
			<select id="selFtpType">
				<option value="">FTP Type 전체</option>
				<option value="DB">DB</option>
				<option value="CUST">CUST</option>
			</select>
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
<!-- 			<button id="btnSearch" class="btnMain" >조회</button> -->
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 13%" title="고객사"><div>고객사</div></th>
							<th style="width: 13%" title="센터"><div>센터</div></th>
							<th style="width: 12%" title="FTP Type"><div>FTP Type</div></th>
							<th style="width: 14%" title="FTP IP"><div>FTP IP</div></th>
							<th style="width: 12%" title="FTP Port"><div>FTP Port</div></th>
							<th style="width: 12%" title="FTP User"><div>FTP User</div></th>
							<th style="width: 12%" title="FTP PW"><div>FTP PW</div></th>
							<th style="width: 12%" title="FTP Dir"><div>FTP Dir</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>고객사(00000)</td> -->
<!-- 							<td>샌터(00000)</td> -->
<!-- 							<td>CUST</td> -->
<!-- 							<td class="txtCenter">000.000.00.00</td> -->
<!-- 							<td class="txtCenter">10</td> -->
<!-- 							<td>useridid</td> -->
<!-- 							<td>passssss</td> -->
<!-- 							<td>/dir</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">FTP 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>녹취전송 FTP 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<div class="formTableContainer">
					
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<input type="text" id="selGroupId" name="view_group_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">센터</label>
						<div class="formInput">
							<input type="text" id="selCenterId" name="view_center_name" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">FTP Type</label>
						<div class="formInput">
							<input type="text" name="ftp_type" disabled/>
						</div>
<!-- 						<div class="formInput"> -->
<!-- 							<select name="ftp_type"> -->
<!-- 								<option>DB</option> -->
<!-- 								<option>CUST</option> -->
<!-- 							</select> -->
<!-- 						</div> -->
					</div>
					<div class="formWrap">
						<label class="formLabel">FTP IP<span class="require">*</span></label>
						<div class="formInput">
							<div class="input-group">
								<input name="ftp_ip" type="text" maxlength="3">
								<span class="input-group-addon">.</span>
								<input name="ftp_ip" type="text" maxlength="3">
								<span class="input-group-addon">.</span>
								<input name="ftp_ip" type="text" maxlength="3">
								<span class="input-group-addon">.</span>
								<input name="ftp_ip" type="text" maxlength="3">
							</div>
							<p class="note">※반드시 IP주소를 사용</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">FTP Port<span class="require">*</span></label>
						<div class="formInput">
							<input name="ftp_port" type="text" maxlength="15">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">FTP User<span class="require">*</span></label>
						<div class="formInput">
							<input name="ftp_user" type="text" maxlength="30">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">FTP Password<span class="require">*</span></label>
						<div class="formInput">
							<input name="ftp_pw" type="text" maxlength="50">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">FTP Dir<span class="require">*</span>
						</label>
						<div class="formInput">
							<input name="ftp_dir" type="text" maxlength="2000">
						</div>
					</div>
				</div>
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">FTP 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>녹취전송 FTP가 없습니다.</h6>
		<p>FTP를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>녹취전송 FTP 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer">
						
						<div class="formWrap">
							<label class="formLabel">고객사</label>
							<div class="formInput">
								<select id="selNGroup" name="group_id"  data-skillgroup="selNGroupCenter" >
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">센터</label>
							<div class="formInput">
								<select id="selNGroupCenter" name="center_id" >
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">FTP Type</label>
							<div class="formInput">
								<select name="ftp_type">
									<option value="DB">DB</option>
									<option value="CUST">CUST</option>
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">FTP IP<span class="require">*</span></label>
							<div class="formInput">
								<div class="input-group">
									<input name="ftp_ip" type="text" maxlength="3">
									<span class="input-group-addon">.</span>
									<input name="ftp_ip" type="text" maxlength="3">
									<span class="input-group-addon">.</span>
									<input name="ftp_ip" type="text" maxlength="3">
									<span class="input-group-addon">.</span>
									<input name="ftp_ip" type="text" maxlength="3">
								</div>
								<p class="note">※반드시 IP주소를 사용</p>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">FTP Port<span class="require">*</span></label>
							<div class="formInput">
								<input name="ftp_port" type="text" maxlength="15">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">FTP User<span class="require">*</span></label>
							<div class="formInput">
								<input name="ftp_user" type="text" maxlength="30">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">FTP Password<span class="require">*</span></label>
							<div class="formInput">
								<input name="ftp_pw" type="text" maxlength="50">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">FTP Dir<span class="require">*</span>
							</label>
							<div class="formInput">
								<input name="ftp_dir" type="text" maxlength="2000">
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
		$.getScript(getContextPath() + "/resources/service/js/service/ftpMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "ftpMgt") {
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
