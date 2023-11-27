<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<title>Insert title here</title>
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />

<!-- <script  src="http://code.jquery.com/jquery-latest.min.js"></script> -->
<!-- 4.jQuery Date Range Picker -->
<link rel="stylesheet" href="/date_range_picker/jquery-date-range-picker/dist/daterangepicker.min.css">
<script type="text/javascript" src="/date_range_picker/moment/min/moment.min.js"></script>
<!-- <script type="text/javascript" src="jquery.min.js"></script> -->
<script type="text/javascript" src="/date_range_picker/jquery-date-range-picker/src/jquery.daterangepicker.js"></script>
<script type="text/javascript"  src="/util/dateRangePicker.js"></script>
<!--  -->


</head> 
<body>
<button id="btnSelectGroupSample">트리구조 셀렉트 유틸 샘플 페이지 --></button>
<button id="btnJqueryDateRangePickers">jquery date picker --></button>
<!-- <button id="btnCompareDateRangePickers">데이트 피커 플러그인 비교 --></button> -->
<br><br>
<div style="margin-left : 20px; width:400px " >

<!-- <input id="iptPwd" value="6003"> -->
<!-- 	<button id="btnLogin">login</button> -->
<!-- </div> -->
<div class="formWrap">
	<label class="formLabel">알림 제목</label>
	<div class="formInput">
		<input type="text" id="iptTitle" name="message_title" maxlength="30" />
	</div>
</div>
<div class="formWrap">
	<label class="formLabel">알림 텍스트</label>
	<div class="formInput">
		<textarea type="text" id="iptText" name="message_text" rows="5" cols="" maxlength="150"></textarea>
	</div>
</div>
<div class="formWrap">
	<label class="formLabel">알림 이름</label>
	<div class="formInput">
		<input type="text" id="iptName" name="message_name" maxlength="30"/>
	</div>
</div>
<div class="formWrap">
	<label class="formLabel"></label>
	<div class="formInput">
		<button id ="btnSendPushAlert" type="button" class="btnGray btnBlock">알림 전송</button>
		
	</div>
</div>
<div class="formInput">
	<button id ="btnWebtoApp" type="button" class="btnGray btnBlock">돌아가기</button>
</div>

<br><br><br><br>
<div class="formInput">
	<label class="formLabel">암호화 내용</label>
	<div class="formInput">
		<input type="text" id="iptEncryptText"  maxlength="30" />
	</div>
	<button id ="btnCBCEncrypt" type="button" name="cbc_encrypt" class="btnGray btnBlock">CBC암호화</button>
	<button id ="btnCBCDecrypt" type="button" name="cbc_decrypt" class="btnGray btnBlock">CBC복호화</button>
</div>
<!-- <div class="formWrap"> -->
<!-- 	<label class="formLabel">링크 체크할 사이트</label> -->
<!-- 	<div class="formInput"> -->
<!-- 		<input type="text" id="iptSiteUrl" name="message_title" value="http://www.ecoletree.com/"/> -->
<!-- <!-- 		<input type="text" id="iptSiteUrl" name="message_title" value="http://ecoletree.com:8080"/> -->
<!-- <!-- 		<input type="text" id="iptSiteUrl" name="message_title" value="https://www.samsung.com/nl"/> -->
<!-- 		<button id ="btnCheckLink" type="button" class="btnGray btnBlock">check link</button> -->
<!-- 	</div> -->
<!-- </div> -->
	
<!-- 	<button id ="btnExcelDown" type="button" class="btnGray btnBlock">엑셀 다운로드</button> -->
<!-- </div> -->
<!-- <div class="ecloeScrollTableContainer"> -->
<!-- 			<div class="ecloeScrollTableHeader"></div> -->
<!-- 			<div class="ecloeScrollTableWrap dataTables_wrapper form-inline"> -->
<!-- 				<table id="tbList" class="ecloeScrollTable table table-hover dataTable" style="width: 100%;margin-top: 0px !important;"> -->
<!-- 					<thead> -->
<!-- 						<tr> -->
<!-- 							<th style="width: 5%" title="status"><div>status</div></th> -->
<!-- <!-- 							<th style="width: 15%" title="state"><div>state</div></th> -->
<!-- 							<th style="width: 30%" title="url"><div>url</div></th> -->
<!-- 							<th style="width: 30%" title="parent"><div>linked from</div></th> -->
<!-- 							<th style="width: 20%" title="source"><div>source</div></th> -->
<!-- 					</thead> -->
<!-- 					<tbody class="cursorPoint"> -->
<!-- 					</tbody> -->
<!-- 				</table> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- <script src="//code.jquery.com/jquery-1.11.0.min.js"></script> -->
<!-- 끝: 작성하기 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/home.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "home") {
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
</body>

</html>