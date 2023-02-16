<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<div class="settingWrap">

	<!-- 타이틀 -->
	<div class="titleWrap">
		<h1>녹취 자동 삭제 관리</h1>
		<span>녹취 관리 &gt; 녹취 자동 삭제 관리</span>
	</div>
	
	<!-- 좌측: 리스트 -->
	<div class="contLeftWrap">
		<div class="search">
			<div>
				<select id="selGroupList">
				</select>
				<select id="selCenterList" >
				</select>
				<button id="btnSearch" class="btnMain" style="display:none;">조회</button>
			</div>
		</div>
		<div class="ScrollContainerWithSearch">
			<form>
			<fieldset>
				<div class="bxTileWrap">
					
						
					<div id="divRecordRemove" class="bxCriticalRowWrap">
<!-- 						Set -->
<!-- 						<div class="bxWall bxCriticalColWrap type3"> -->
<!-- 							<h6>센터 이름(ID)</h6> -->
<!-- 							<div class="formWrap"> -->
<!-- 								<label class="formLabel">실행 시간</label> -->
<!-- 								<div class="formInput"> -->
<!-- 									<div class="input-group"> -->
<!-- 										<select> -->
<!-- 											<option>23</option> -->
<!-- 										</select> -->
<!-- 										<span class="input-group-addon">:</span> -->
<!-- 										<select> -->
<!-- 											<option>59</option> -->
<!-- 										</select> -->
<!-- 										<span class="input-group-addon">:</span> -->
<!-- 										<select> -->
<!-- 											<option>59</option> -->
<!-- 										</select> -->
<!-- 									</div> -->
<!-- 								</div> -->
	
<!-- 							</div>./formWrap -->
<!-- 							<div class="formWrap"> -->
<!-- 								<label class="formLabel">보관 기간</label> -->
<!-- 								<div class="formInput"> -->
<!-- 									<div class="formInput"> -->
<!-- 										<div class="input-group"> -->
<!-- 											<input type="text" value="36">  -->
<!-- 											<span class="input-group-addon">개월</span> -->
<!-- 											<input type="text" value="1">  -->
<!-- 											<span class="input-group-addon">일</span> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
	
<!-- 							</div>./formWrap -->
<!-- 							<div class="formWrap"> -->
<!-- 								<label class="formLabel">사용 유무</label> -->
<!-- 								<div class="formInput"> -->
<!-- 									<div class="formInput-inline"> -->
<!-- 										<label class="ecoleRadio"><input type="radio" checked="checked"><i></i>사용함</label> -->
<!-- 										<label class="ecoleRadio"><input type="radio" ><i></i>사용 안함</label> -->
<!-- 									</div> -->
<!-- 								</div> -->
	
<!-- 							</div>./formWrap -->
							
<!-- 							<div class="foot"> -->
<!-- 								<button type="button" class="btnMain">저장하기</button> -->
<!-- 							</div> -->
							
<!-- 						</div>./Set -->
						
						
					</div><!-- ./bxCriticalRowWrap -->
					
				</div>
			</fieldset>
			</form>
		</div>
		
		
		
	</div>
	<div class="footLeftWrap">
	</div>
	<!-- 좌측 끝: 리스트 -->
	
	<!-- 값없음  style="display: block;" -->
	<div class="contRightNoData" style="display: block;">
		<h6>녹취 자동 삭제를 설정할 센터가 없습니다.</h6>
		<p>센터를 추가하거나 검색조건을 수정해 주세요.</p>
	</div>
	
	<script type="text/javascript">
		$.getScript(getContextPath() + "/resources/service/js/service/recordFileDeleteMgt.js").done(function(script, textStatus) {
			if (!!ecoletree.vc && ecoletree.vc.name === "recordFileDeleteMgt") {
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