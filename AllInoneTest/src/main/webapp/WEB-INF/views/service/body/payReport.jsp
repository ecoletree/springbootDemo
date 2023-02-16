<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="payReportWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>내선별 과금 리포트</h1>
		<span>과금 관리 &gt; 내선별 과금 리포트</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<select id="selGroup"  data-skillgroup="selGroupCenter">
				</select>
				<select id="selGroupCenter">
				</select>
			
				<div class="input-group number">
					<input id="iptSPhone" type="text" placeholder="내선번호 선택">
					<span class="input-group-addon">~</span>
					<input id="iptEPhone" type="text" placeholder="내선번호 선택">
				</div>
				<div>
					<span>통화 시작</span>
					<div id="divStartDate" class="bxEcoleDate input-group date">
						<input type="text" readonly="readonly"> 
						<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
					</div>
					<div id="divSTime" class="input-group time">
						<select id="selSHour">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selSMinute">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selSSecond">
						</select>
					</div>
					<span>~</span>
					<span>종료</span>
					<div id="divEndDate" class="bxEcoleDate input-group date">
						<input type="text" readonly="readonly"> 
						<span class="input-group-addon"><img src="${cp }/resources/ecoletree/img/btn_cal.png" /></span>
					</div>
					<div id="divETime" class="input-group time">
						<select id="selEHour">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selEMinute">
						</select>
						<span class="input-group-addon">:</span>
						<select id="selESecond">
						</select>
					</div>
				</div>
				<div class="input-group">
					<span class="input-group-addon">요금</span>
					<input id="iptBiling" type="text" class="txtRight" value="0">
					<select id="selAbove">
						<option value="above">이상</option>
						<option value="below">이하</option>
					</select>
				</div>
				<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
			</div>
			<div class="row2">
			</div>
		</div>
		
		<div class="ecloeScrollTableContainer">
			<div class="ecloeScrollTableHeader"></div>
			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline">
				<table id="tbList" class="ecloeScrollTable table dataTable sum" style="width: 100%;margin-top: 0px !important;">
					<thead>
						<tr>
							<th style="width: 9%" title="내선번호"><div>내선번호</div></th>
							<th style="width: 8%" title="센터"><div>센터</div></th>
							<th style="width: 7%" title="시내"><div>시내</div></th>
							<th style="width: 7%" title="시외"><div>시외</div></th>
							<th style="width: 7%" title="국제"><div>국제</div></th>
							<th style="width: 7%" title="이동"><div>이동</div></th>
							<th style="width: 7%" title="안내"><div>안내</div></th>
							<th style="width: 7%" title="기타"><div>기타</div></th>
							<th style="width: 7%" title="평생번호"><div>평생번호</div></th>
							<th style="width: 8%" title="무료"><div>무료</div></th>
							<th style="width: 8%" title="인터넷 요금"><div>인터넷 요금</div></th>
							<th style="width: 8%" title="대표전화"><div>대표전화</div></th>
							<th style="width: 10%" title="합계"><div>합계</div></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footLeftWrap">
		<div></div>
		<button id="btnExcelDownLoad" class="btnMain" style="display:none;">엑셀 다운로드</button>
	</div>
	<!-- 좌측 끝: 리스트 -->

	
	<!-- 우측: 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>리포트 데이터가 없습니다.</h6>
		<p>검색조건을 수정해 주세요.</p>
	</div>
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/payReport.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "payReport") {
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