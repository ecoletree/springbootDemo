<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="custMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>조직 관리자 관리</h1>
		<span>조직 관리 &gt; 조직 관리자 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selSearchGroupType">
				<option value="">분류 전체</option>
				<option value="group">고객사 관리자</option>
				<option value="center">센터 관리자</option>
				<option value="team">팀 관리자</option>
			</select>
			<select id="selGroupList">
			</select>
			
			<!--
				전체일 때: inline-block
				고객사 관리자 일때:display: none  
				센터 관리자 일때:display: inline-block
				팀 관리자 일때:display:inline-block  
			 -->
			<select id="selCenterList" >
			</select>
			
			<!-- 
				전체일 때: inline-block
				고객사 관리자 일때:display:  none
				센터 관리자 일때:display:  none
				팀 관리자 일때:display:inline-block  
			 -->
			<select id="selTeamList" >
			</select>
			
			<input id="ipxSearchName" type="text" placeholder="담당자명">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 14%" title="고객사"><div>고객사</div></th>
							<th style="width: 14%" title="센터"><div>센터</div></th>
							<th style="width: 14%" title="팀"><div>팀</div></th>
							<th style="width: 14%" title="ID"><div>ID</div></th>
							<th style="width: 10%" title="담당자명"><div>담당자명</div></th>
							<th style="width: 18%" title="연락처"><div>연락처</div></th>
							<th style="width: 16%" title="이메일"><div>이메일</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">조직 관리자 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>조직 관리자 상세보기</h5>
		</div>
		   
		<div class="contRightBody">
			
			<div class="formTableContainer">
				<form id="editForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formWrap">
						<label class="formLabel">분류</label>
						<div class="formInput">
							<select id="selGroupType" name="user_type">
								<option value="group">고객사 관리자</option>
								<option value="center">센터 관리자</option>
								<option value="team">팀 관리자</option>
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">고객사</label>
						<div class="formInput">
							<select id="selGroupCd" name="group_cd"></select>
						</div>
					</div>
					<!-- 
						고객사 관리자일때: disaplay: none
						센터 관리자일때: disaplay: table-cell 
						팀 관리자일때: disaplay: table-cell 
					-->
					<div class="formWrap" style="display: none;">
						<label class="formLabel">센터</label>
						<div class="formInput">
							<select id="selCenterCd" name="center_cd"></select>
						</div>
					</div>
					<!-- 
						고객사 관리자일때: disaplay: none
						센터 관리자일때: disaplay: none 
						팀 관리자일때: disaplay: table-cell 
					-->
					<div class="formWrap" style="display: none;">
						<label class="formLabel">팀</label>
						<div class="formInput">
							<select id="selTeamCd" name="team_cd"></select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">ID</label>
						<div class="formInput">
							<input name="user_id"  type="text" disabled>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">비밀번호</label>
						<div class="formInput">
							<!-- input name="admin_pw" type="password " maxlength="20"-->
							<button id="btnPWChangeOpen" type="button" class="btnGray btnBlock">비밀번호 변경</button>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">담당자명<span class="require">*</span></label>
						<div class="formInput">
							<input name="user_name"  type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">연락처</label>
						<div class="formInput">
							<input id="iptPhone" name="phone" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">이메일</label>
						<div class="formInput">
							<input name="email" type="text" maxlength="32">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">IP<span class="require">*</span></label>
						<div class="formInput">
							<div class="input-group">
								<input type="text" name="user_ip1" maxlength="3">
								<span class="input-group-addon">.</span>
								<input type="text" name="user_ip2" maxlength="3">
								<span class="input-group-addon">.</span>
								<input type="text" name="user_ip3" maxlength="3">
								<span class="input-group-addon">.</span>
								<input type="text" name="user_ip4" maxlength="3">
							</div>
							<p class="note">※반드시 IP주소를 사용</p>
						</div>
					</div>
				</fieldset>
				</form>
			</div>
			
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">조직 관리자 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>조직 관리자가 없습니다.</h6>
		<p>조직 관리자를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>조직 관리자 추가</h5>
			</div>
			<div class="contWritetBody">
				<form id="addForm" method="post" autocomplete="off">
				<fieldset>
					<div class="formTableContainer" >
						<div class="formWrap">
							<label class="formLabel">분류</label>
							<div class="formInput">
								<select id="selNGroupType" name="user_type">
									<option value="group">고객사 관리자</option>
									<option value="center">센터 관리자</option>
									<option value="team">팀 관리자</option>
								</select>
							</div>
						</div>
						
						<div class="formWrap">
							<label class="formLabel">고객사</label>
							<div class="formInput">
								<select id="selNGroupCd" name="group_cd">
								</select>
							</div>
						</div>
						<!-- 
							고객사 관리자일때: disaplay: none
							센터 관리자일때: disaplay: table-cell 
							팀 관리자일때: disaplay: table-cell 
						-->
						<div class="formWrap" style="display: none;">
							<label class="formLabel">센터</label>
							<div class="formInput">
								<select id="selNCenterCd" name="center_cd">
								</select>
							</div>
						</div>
						<!-- 
							고객사 관리자일때: disaplay: none
							센터 관리자일때: disaplay: none 
							팀 관리자일때: disaplay: table-cell 
						-->
						<div class="formWrap" style="display: none;">
							<label class="formLabel">팀</label>
							<div class="formInput">
								<select id="selNTeamCd" name="team_cd">
								</select>
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">ID<span class="require">*</span></label>
							<div class="formInput">
								<input name="user_id" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">비밀번호<span class="require">*</span></label>
							<div class="formInput">
								<input name="user_pw" type="password" maxlength="20">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">담당자명<span class="require">*</span></label>
							<div class="formInput">
								<input name="user_name" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">연락처</label>
							<div class="formInput">
								<input id="iptNPhone" name="phone" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">이메일</label>
							<div class="formInput">
								<input name="email" type="text" maxlength="32">
							</div>
						</div>
						<div class="formWrap">
							<label class="formLabel">IP<span class="require">*</span></label>
							<div class="formInput">
								<div class="input-group">
									<input type="text" name="user_ip1" maxlength="3">
									<span class="input-group-addon">.</span>
									<input type="text" name="user_ip2" maxlength="3">
									<span class="input-group-addon">.</span>
									<input type="text" name="user_ip3" maxlength="3">
									<span class="input-group-addon">.</span>
									<input type="text" name="user_ip4" maxlength="3">
								</div>
								<p class="note">※반드시 IP주소를 사용</p>
							</div>
						</div>	
					</div>
				</fieldset>
				</form>
				
			</div>
		</div>
		<div class="footWriteWrap">
			<button id="btnSave" class="btnMain">추가</button>
			<button  id="btnSaveCancel" class="btnGray">취소</button>
		</div>
		
	</div>
	<!-- 끝: 작성하기 -->
	
	<!-- 시작: 비밀번호 변경 -->
	<div id="divPwChange" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog popW450" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title">비밀번호 변경</h4>
				</div>
				
				<div class="modal-body">
					<p>변경할 비밀번호를 입력해 주세요.</p>
					<div class="formTableContainer" >
						<form id="pwForm" method="post" autocomplete="off"><fieldset>
							<div class="formWrap">
								<label class="formLabel">새로운 비밀번호<span class="require">*</span></label>
								<div class="formInput">
									<input id="iptCPassword" name="user_pw" type="password" maxlength="20">
								</div>
							</div>
						</fieldset></form>
					</div>
				</div>
				
				<div class="modal-footer margin0">
					<button id="btnPWChange" type="button" class="btnMain">변경하기</button>
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 비밀번호 변경 -->
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/custManagerMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "custManagerMgt") {
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