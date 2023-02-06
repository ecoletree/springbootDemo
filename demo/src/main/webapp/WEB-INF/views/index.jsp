<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- ecoletree CSS -->
<link rel="stylesheet" href="${cp }/ecoletree/css/ecoletree_global.css">

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="/jslib/ecoletreeLibraryJS/plugin/lodash/lodash.min.js"></script>
<script src="/jslib/ecoletreeLibraryJS/plugin/jquery-validation_1.19.3/jquery.validate.js"></script>
<script src="/jslib/ecoletreeLibraryJS/util/serviceUtil.js"></script>
<script src="/jslib/ecoletreeLibraryJS/util/objectUtil.js"></script>
<script src="/jslib/ecoletreeLibraryJS/util/dateUtil.js"></script>
<script src="/jslib/ecoletreeLibraryJS/util/stringUtil.js"></script>
<script src="/jslib/ecoletreeLibraryJS/util/validationUtil.js"></script>
<script src="/jslib/ecoletreeLibraryJS/util/i18nextUtil.js"></script>
<script src="/jslib/ecoletreeLibraryJS/util/aesUtil.js"></script>
</head>
<script type="text/javascript"> 
function getContextPath() {
	return '';
}


	function test () {
			debugger;
		var params = {value:"data"};
		new ETService().setSuccessFunction(function(result){
		}).callService("/getList", params);
	}
</script>
<body>
test
<button onclick="test()"> test3333</button>
<img  src="${cp}/ecoletree/img/btn_cal.png">
</body>
</html>