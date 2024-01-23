<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- Core JS -->
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/jquery/dist/jquery.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/select2/dist/js/select2.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/lodash/lodash.min.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/global/js/mvpready-core.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/global/js/mvpready-helpers.js"></script>

<!-- fontawesome -->
<script defer src="${cp }/jslib/ecoletreeLibraryJS/theme/fontawesome-free/js/all.js"></script>

<!-- Plugin JS : bootstrap datepicker -->
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/bootstrap/dist/js/bootstrap.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/datepicker/bootstrap-datepicker.ko.js" charset="UTF-8"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/datepicker/bootstrap-datepicker.ko.js" charset="UTF-8"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/datepicker/dateFormat.js" charset="UTF-8"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/jquery-ui-1.12.1/jquery-ui.min.js"></script>

<!-- Plugin JS : dataTables -->
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.js"></script>

<!-- Plugin JS : jQuery Base64 -->
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/jquery-base64/jquery.base64.min.js"></script>

<!-- Plugin JS : fileInut -->
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/bootstrap-jasny/js/fileinput.js"></script>

<!-- Plugin JS : magnific-popup-->
<script src="${cp }/jslib/ecoletreeLibraryJS/theme/mvpReady/bower_components/magnific-popup/dist/jquery.magnific-popup.min.js"></script>

<!-- Plugin JS : validate -->
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/jquery-validation_1.19.3/jquery.validate.js"></script>

<!-- Plugin JS : Summernote -->
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin//summernote-0.8.18-dist/summernote.js"></script>

<!-- Plugin JS : i18next -->
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/i18next/i18n/i18next.min.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/i18next/backend/i18nextHttpBackend.min.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/i18next/i18n/jquery-i18next.min.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/i18next/languageDetector/i18nextBrowserLanguageDetector.min.js"></script>

<!-- Plugin JS : XLSX -->
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/sheetjs-master/xlsx.full.min.js"></script>

<!-- Plugin JS : Cropper -->
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/cropper/cropper.js"></script>

<!-- Plugin JS : ctypto -->
<script src="${cp }/jslib/ecoletreeLibraryJS/plugin/crypto-js/crypto-js.js"></script>

<!-- EcoleTree Util JS FILE -->
<script src="${cp }/jslib/ecoletreeLibraryJS/util/serviceUtil.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/util/objectUtil.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/util/dateUtil.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/util/stringUtil.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/util/validationUtil.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/util/i18nextUtil.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/util/aesUtil.js"></script>
<script src="${cp }/jslib/ecoletreeLibraryJS/util/excelUtil.js"></script>

<!-- App VIEW Controller -->
<script src="${cp }/jslib/ecoletreeLibraryJS/common/common.js"></script>

<script type="text/javascript">
	/**
	 * 컨텍스트 패스 획득
	 */
	function getContextPath() {
		var cp = '${cp}';
		if (cp == "" || cp == '') {
			cp = "/";
		}
		return '${cp}';
	};

	/**
	 * country 코드로 언어코드 획득
	 * @param isCode true면 country 코드를 바로 반환
	 * @return _lang 반환. kr 일 경우 "_kor"반환
	 */
	function getLang(isCode) {
		var country = 'kr';
		if (isCode === true) {
			return !!country ? country : "kr";
		}

		var lang = "_kor";

		if (country === "kr") {
			lang = "_kor";
		} else if (country === "en") {
			lang = "_eng";
		}

		return lang;
	};

</script>