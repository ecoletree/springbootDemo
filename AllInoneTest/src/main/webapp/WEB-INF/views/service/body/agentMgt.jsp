<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="agentMgtWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>상담원 관리</h1>
		<span>상담원 관리 &gt; 상담원 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<select id="selGroup" >
			</select>
			<select id="selGroupCenter" data-teamlist="selTeamList" >
			</select>
			<select id="selTeamList" >
			</select>
			<input id="iptSkilGroupNameList" type="text" placeholder="스킬 그룹">
			<input id="iptAgentNameList" type="text" placeholder="상담원 명">
			<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 7%" title="고객"><div>고객사</div></th>
							<th style="width: 7%" title="센터"><div>센터</div></th>
							<th style="width: 7%" title="팀"><div>팀</div></th>
							<th style="width: 7%" title="스킬 그룹"><div>스킬 그룹</div></th>
							<th style="width: 7%" title="상담 그룹 명"><div>스킬 그룹 명</div></th>
							<th style="width: 7%" title="상담원 ID"><div>상담원 ID</div></th>
							<th style="width: 7%" title="상담원 명"><div>상담원 명</div></th>
<!-- 							<th style="width: 7%" title="STATUS"><div>STATUS</div></th> -->
<!-- 							<th style="width: 7%" title="STATE"><div>STATE</div></th> -->
							<th style="width: 7%" title="In/Out"><div>In/Out</div></th>
							<th style="width: 12%" title="마지막 로그인"><div>마지막 로그인</div></th>
							<th style="width: 12%" title="마지막 로그아웃"><div>마지막 로그아웃</div></th>
							<th style="width: 10%" title="사용 전화"><div>사용 전화</div></th>
 
						</tr>
					</thead>
					<tbody class="cursorPoint">
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td class="txtCenter">3001</td> -->
<!-- 							<td>상담 그룹 0001</td> -->
<!-- 							<td>0001</td> -->
<!-- 							<td>가길동이</td> -->
<!-- 							<td class="txtCenter">LoggedOut</td> -->
<!-- 							<td class="txtCenter">Idel</td> -->
<!-- 							<td class="txtCenter">2020.01.01 01:00:00</td> -->
<!-- 							<td class="txtCenter">2020.01.01 01:00:00</td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td>tenant0001</td> -->
<!-- 							<td class="txtCenter">3001</td> -->
<!-- 							<td>상담 그룹 0001</td> -->
<!-- 							<td>0002</td> -->
<!-- 							<td>이길동이</td> -->
<!-- 							<td class="txtCenter">hangup</td> -->
<!-- 							<td class="txtCenter">Idel</td> -->
<!-- 							<td class="txtCenter">2020.01.01 01:00:00</td> -->
<!-- 							<td class="txtCenter"></td> -->
<!-- 							<td class="txtCenter">5001</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<button id="btnAdd" class="btnMain" style="display:none;">상담원 추가</button>
		<button id="btnAdd_list" class="btnMain" style="display:none;">상담원 일괄 추가</button>
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>상담원 상세보기</h5>
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
							<input type="text" id="selCenterId" name="tenant_name" data-skillgrp="selSkillGroup" data-teamlist="selTeam" disabled/>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">팀</label>
						<div class="formInput">
							<select id="selTeam" name="team_id">
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">스킬 그룹 명</label>
						<div class="formInput">
							<input type="text" id="selSkillGroup" name="view_grp_name" disabled/>
						</div>
					</div>

					<div class="formWrap">
						<label class="formLabel">상담원 ID</label>
						<div class="formInput">
							<input id="iptAgentID" name="agent_id" type="text" disabled="disabled">
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">비밀번호</label>
						<div class="formInput">
							<button id="btnPWChangeOpen" type="button" class="btnGray btnBlock">비밀번호 변경</button>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">상담원 명<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptAgentName" name="agent_name" type="text" >
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">In/Out</label>
						<div class="formInput-inline">
							<label class="ecoleCheck"><input type="checkbox" name="io_flag"  value="1"><i></i>인바운드</label>
							<label class="ecoleCheck"><input type="checkbox" name ="io_flag" value="2"><i></i>아웃바운드</label>
						</div>
					</div>
				</fieldset>
				</form>	
			</div>
			
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
		<button id="btnDelete" type="button" class="btnGray" style="display:none;">상담원 삭제</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>상담원이 없습니다.</h6>
		<p>상담원을 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<!-- 작성하기 style="display: block;" -->
	<div id="bxWrite" class="conWrite" style="display: none;">
		<div class="contWriteWrap">
			<div class="contWriteHead">
				<h5>상담원 추가</h5>
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
							<select id="selNGroupCenter" name="tenant_id" data-skillgroup="selNSkillGroup"  data-teamlist="selNTeam" >
							</select>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">팀</label>
						<div class="formInput">
							<select id="selNTeam" name="team_id">
<!-- 								<option>첫번째</option> -->
							</select>
						</div>
					</div>
					
					<!-- div class="formWrap">
						<label class="formLabel">스킬 그룹 명</label>
						<div class="formInput">
							<select id="selNSkillGroup" name="grp_id" class="select2" multiple="multiple" >
								<option></option>
							</select>
						</div>
					</div-->
					
					<div class="formWrap">
						<label class="formLabel">스킬 그룹 명<span class="require">*</span></label>
						<div class="formInput">
							<div class="input-group">
								<input id="iptNSkillGroup" type="text" name="grp_name" disabled="disabled"> 
								<div class="input-group-addon input-group-addon-btn">
									<a id="btnOpenSkillModal" class="btn"><i class="fa fas fa-search"></i></a>
								</div>
							</div>
						</div>
					</div>
					
					<div class="formWrap">
						<label class="formLabel">상담원 ID<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptNAgentID" name="agent_id" type="text" maxlength="6" >
							<p class="note">※CTI 로그인 시 사용하는 계정 ID </p>
							<p class="note">※6자리 숫자로 입력</p>
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">비밀번호<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptNPassword" name="agent_pw" type="text" >
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">상담원 명<span class="require">*</span></label>
						<div class="formInput">
							<input id="iptNAgentName" name="agent_name" type="text" >
						</div>
					</div>
					<div class="formWrap">
						<label class="formLabel">In/Out</label>
						<div class="formInput-inline">
							<label class="ecoleCheck"><input type="checkbox" name="io_flag" value="1"><i></i>인바운드</label>
							<label class="ecoleCheck"><input type="checkbox" name="io_flag" value="2"><i></i>아웃바운드</label>
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
					<h4 class="modal-title" id="myModalLabel">상담원 일괄 등록</h4>
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
고객사 ID, 센터 ID , 팀 ID , 스킬 그룹, 상담원 ID, 비밀번호, 상담원 명, In/Out</p>
							</div>
						</div>
					</div>
				</div>
				
				<div class="modal-footer margin0">
					<a id="btnDownLoadTemplate" class="btnSub pull-left"  href="${cp }/resources/template/AGENT_SAMPLE_CSV.csv" >템플릿 내려받기</a>
					<button id="btnSaveList" type="button" class="btnMain">등록하기</button>
					<button type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 일괄 등록 -->

	
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
									<input id="iptCPassword" name="agent_pw" type="password" maxlength="20">
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
	
	<!-- 시작: 스킬그룹 검색 -->
	<div id="modalSkillList" class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog popW850" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">스킬그룹 검색</h4>
				</div>
				
				<div class="modal-body">
					<div class="search">
						<select id="selGroupSkill" disabled="disabled"></select>
						<select id="selTenantSkill"  disabled="disabled"></select>
						<input id="iptSkillName" type="text" placeholder="스킬 그룹 명">
						<input id="iptSkillId" type="text" placeholder="스킬 그룹 ID">
						<button id="btnSearchSkill" class="btnMain">조회</button>
					</div>
					
					<div class="ecloeScrollTableContainer popH400">
						<div class="ecloeScrollTableHeader"></div>
						<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
							<table id="tbSkillTableList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
								<thead>
									<tr>
										<th style="width: 4%;"><div></div></th>
										<th style="width: 20%" title="고객"><div>고객사</div></th>
										<th style="width: 20%" title="센터"><div>센터</div></th>
										<th style="width: 20%" title="스킬 그룹"><div>스킬 그룹</div></th>
										<th style="width: 20%" title="상담 그룹 명"><div>스킬 그룹 명</div></th>
										<th style="width: 16%" title="사용 여부"><div>사용 여부</div></th>
			 
									</tr>
								</thead>
								<tbody class="cursorPoint">
<!-- 									<tr> -->
<!-- 										<td><label class="ecoleCheck"><input type="checkbox" ><i></i></label></td> -->
<!-- 										<td>고객</td> -->
<!-- 										<td>센터</td> -->
<!-- 										<td>id</td> -->
<!-- 										<td>이름</td> -->
<!-- 										<td class="text-center">o</td> -->
<!-- 									</tr> -->
								</tbody>
							</table>
						</div>
					</div><!-- ./ecloeScrollTableContainer -->
					
					
					
				</div>
				
				<div class="modal-footer margin0">
					<button id="btnSaveSkill" type="button" class="btnMain">선택 등록하기</button>
					<button id="btnCloseSkillModal" type="button" class="btnGray" data-dismiss="modal" aria-hidden="true"> 창닫기</button>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 끝: 스킬그룹 검색 -->
	
	
	
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/agentMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "agentMgt") {
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