<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
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
		var params = {value:"data"};
		new ETService().setSuccessFunction(function(result){
			debugger;
		}).callService("/getList", params);
	}
</script>
<body>
test
<button onclick="test()"> test1</button>
</body>
</html>