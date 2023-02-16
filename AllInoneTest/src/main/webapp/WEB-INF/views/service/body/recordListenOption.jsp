<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="columnSearchSettingWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>녹음청취 컬럼&#47;검색 관리</h1>
		<span>녹취 관리 &gt; 녹음청취 컬럼&#47;검색 관리</span>
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
			<h5>컬럼 설정</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<table class="ecloeTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;"
					id="tbColumnList">
					<thead>
						<tr>
							<th >컬럼명</th>
 							<th style="width: 0px"></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				
				
				
				
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap">
		<button id="btnColumnSave" type="button" class="btnSub" style="display:none;">저장</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<!-- 우측: 상세 -->
	<div class="contRightWrap2">
		<div class="contRightHead">
			<h5>검색 설정</h5>
		</div>
		   
		<div class="contRightBody">
			<form id="editForm" method="post" autocomplete="off">
			<fieldset>
				<table class="ecloeTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;"
				id="tbSearchList">
					<thead>
						<tr>
							<th >검색</th>
 							<th style="width: 0px"></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				
				
				
				
			</fieldset>
			</form>
		</div>
	</div>
	<div class="footRightWrap2">
		<button id="btnSearchSave" type="button"  class="btnSub" style="display:none;">저장</button>
	</div>
	<!-- 우측 끝: 상세 -->
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/recordListenOption.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "recordListenOption") {
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