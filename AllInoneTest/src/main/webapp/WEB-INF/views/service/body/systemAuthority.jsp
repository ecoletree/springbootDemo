<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class=authorityMgt>

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>권한 관리</h1>
		<span>환경 설정 &gt; 권한 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 33%" title="권한 그룹 명"><div>권한 그룹 명</div></th>
							<th style="width: 33%" title="마지막 수정자"><div>마지막 수정자</div></th>
							<th style="width: 33%" title="최종 수정일"><div>최종 수정일</div></th>
						</tr>
					</thead>
					<tbody class="cursorPoint">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap">
		<div class="contRightHead">
			<h5>권한</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<!-- 조직 관리 -->
				<div class="accordion" id="menu01">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title" data-toggle="collapse" data-target="#menu01-c" aria-expanded="false">
								조직 관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu01-c" class="collapse in" data-parent="#menu01">
							<div class="card-body">
							
								<div class="subMenuWrap" id="M01">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch01-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch01-1">고객사 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M18">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch01-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch01-2">센터 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M19">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch01-3" name="is_use">
                                        <label class="custom-control-label" for="customSwitch01-3">팀 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M02">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch01-4" name="is_use">
                                        <label class="custom-control-label" for="customSwitch01-4">조직 관리자 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M03">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch01-5" name="is_use">
                                        <label class="custom-control-label" for="customSwitch01-5">블랙 리스트 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M04">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch01-6" name="is_use">
                                        <label class="custom-control-label" for="customSwitch01-6">고객사 업무 시간 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M23">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch01-7" name="is_use">
                                        <label class="custom-control-label" for="customSwitch01-7">다이얼러 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div><!-- ./조직 관리 -->
				
				<!--  내선 관리 -->
				<div class="accordion" id="menu02">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu02-c" aria-expanded="false">
								내선 관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu02-c" class="collapse" data-parent="#menu02">
							<div class="card-body">
							
								<div class="subMenuWrap" id="M06">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch02-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch02-1">PBX 헌트 그룹 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M07">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch02-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch02-2">내선 번호 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_upload"><i></i>일괄등록</label>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div><!-- ./ 내선 관리 -->
				
				<!--  상담원 관리 -->
				<div class="accordion" id="menu03">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu03-c" aria-expanded="false">
								상담원  관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu03-c" class="collapse" data-parent="#menu03">
							<div class="card-body">
							
								<div class="subMenuWrap" id="M09">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch03-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch03-1">스킬 그룹(큐) 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M10">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch03-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch03-2">상담사 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_upload"><i></i>일괄등록</label>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div><!-- ./ 상담원 관리 -->
				
				<!--  국선 관리 -->
				<div class="accordion" id="menu04">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu04-c" aria-expanded="false">
								 국선  관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu04-c" class="collapse" data-parent="#menu04">
							<div class="card-body">
							
								<div class="subMenuWrap" id="M11">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch04-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch04-1">국선 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div><!-- ./ 국선 관리 -->
				
				<!--  게이트 관리 -->
				<div class="accordion" id="menu05">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu05-c" aria-expanded="false">
								 게이트 관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu05-c" class="collapse" data-parent="#menu05">
							<div class="card-body">
							
								<div class="subMenuWrap" id="M13">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch05-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch05-1">Trunk Gateway</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M14">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch05-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch05-2">Anidial Gateway</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div><!-- ./ 게이트 관리 -->
				
				<!--  애니매니저 -->
				<div class="accordion" id="menu06">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu06-c" aria-expanded="false">
								 애니매니저
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu06-c" class="collapse" data-parent="#menu06">
							<div class="card-body">
								
								<div class="subMenuWrap" id="M25">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch06-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch06-1">애니 다이얼 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_upload"><i></i>엑셀 업로드</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_download"><i></i>엑셀 다운로드</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M26">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch06-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch06-2">전송 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_transfer"><i></i>전송</label>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div><!-- ./ 애니매니저 -->
				
				<!--  과금 관리 -->
				<div class="accordion" id="menu07">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu07-c" aria-expanded="false">
								 과금 관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu07-c" class="collapse" data-parent="#menu07">
							<div class="card-body">
							
								<div class="subMenuWrap" id="M28">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch07-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch07-1">내선별 과금 리포트</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_download"><i></i>엑셀 다운로드</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M29">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch07-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch07-2">일반 요율 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
							
							</div>
						</div>
					</div>
				</div><!-- ./ 과금 관리 -->
				
				<!--  과금 관리 -->
				<div class="accordion" id="menu08">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu08-c" aria-expanded="false">
								녹취 관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu08-c" class="collapse" data-parent="#menu08">
							<div class="card-body">
								
								<div class="subMenuWrap" id="M31">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch08-3" name="is_use">
                                        <label class="custom-control-label" for="customSwitch08-3">녹음 청취</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_download"><i></i>엑셀 다운로드</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_rec_download"><i></i>MP3 다운로드</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_encrypt_download"><i></i>암호화 다운로드</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_play"><i></i>재생</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_certification"><i></i>증서 번호 등록 및 전송</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M32">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch08-4" name="is_use">
                                        <label class="custom-control-label" for="customSwitch08-4">통화 모니터링</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_download"><i></i>엑셀 다운로드</label>
<!-- 										<label class="ecoleCheck"><input type="checkbox" name="is_play"><i></i>실시간 재생</label> -->
									</div>
								</div>
							
								<div class="subMenuWrap" id="M33">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch08-5" name="is_use">
                                        <label class="custom-control-label" for="customSwitch08-5">녹취 전송 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_transfer"><i></i>수동전송</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M34">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch08-6" name="is_use">
                                        <label class="custom-control-label" for="customSwitch08-6">녹취 자동 삭제 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M37">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch08-7" name="is_use">
                                        <label class="custom-control-label" for="customSwitch08-7">녹음 청취 조회 컬럼 설정</label>
                                    </div>
									<div class="formInput-inline">
<!-- 										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label> -->
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M38">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch08-8" name="is_use">
                                        <label class="custom-control-label" for="customSwitch08-8">녹음 청취 컬럼/검색 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>컬럼설정 저장</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>검색설정 저장</label>
									</div>
								</div>
								<div class="subMenuWrap" id="M40">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch08-9" name="is_use">
                                        <label class="custom-control-label" for="customSwitch08-9">녹취전송 FTP 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- ./ 녹취 관리 -->
				
				<!--  음원 관리 -->
				<div class="accordion" id="menu09">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu09-c" aria-expanded="false">
								음원 관리
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu09-c" class="collapse" data-parent="#menu09">
							<div class="card-body">
								
								<div class="subMenuWrap" id="M16">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch09-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch09-1">PBX 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>추가</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
								
								<div class="subMenuWrap" id="M17">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch09-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch09-2">IVR 관리</label>                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>추가</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- ./ 음원 관리 -->
				
				<!-- 환경설정 -->
				<div class="accordion" id="menu10">
					<div class="card">
						<div class="card-header">
							<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#menu10-c" aria-expanded="false">
								 환경설정
								<span class="btnWhite">
								    <span class="collapsed-reveal">
								        <i class="fas fa-angle-up"></i>
								    </span>
								    <span class="collapsed-hidden">
								        <i class="fas fa-angle-down"></i>
								    </span>
								</span>
							</a>
						</div>
						<div id="menu10-c" class="collapse" data-parent="#menu10">
							<div class="card-body">
							
								<div class="subMenuWrap" id="M21">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch10-1" name="is_use">
                                        <label class="custom-control-label" for="customSwitch10-1">임계점 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M22">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch10-2" name="is_use">
                                        <label class="custom-control-label" for="customSwitch10-2">서비스 레벨 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M36">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch10-3" name="is_use">
                                        <label class="custom-control-label" for="customSwitch10-3">시스템 관리자 관리</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_write"><i></i>생성</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_delete"><i></i>삭제</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M39">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch10-4" name="is_use">
                                        <label class="custom-control-label" for="customSwitch10-4">권한 관리</label>
                                    </div>
									<div class="formInput-inline">
<!-- 										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label> -->
										<label class="ecoleCheck"><input type="checkbox" name="is_update"><i></i>수정</label>
									</div>
								</div>
							
								<div class="subMenuWrap" id="M35">
									<div class="custom-switch">
                                        <input type="checkbox" class="custom-control-input" id="customSwitch10-5" name="is_use">
                                        <label class="custom-control-label" for="customSwitch10-5">시스템 로그</label>
                                    </div>
									<div class="formInput-inline">
										<label class="ecoleCheck"><input type="checkbox" name="is_search"><i></i>조회</label>
										<label class="ecoleCheck"><input type="checkbox" name="is_download"><i></i>엑셀 다운로드</label>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div><!-- ./ 환경설정 -->
				
				
				
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnEdit" type="button"  class="btnSub" style="display:none;">수정</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/systemAuthority.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "systemauth") {
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