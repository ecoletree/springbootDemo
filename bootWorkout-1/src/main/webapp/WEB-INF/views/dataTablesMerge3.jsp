<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />
  <title>DataTable Merge</title>
  <!-- Include jQuery and DataTables CSS/JS -->
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css">

  <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/dataTables_rowspan/dataTables.rowsGroup.js"></script>
<script type="text/javascript" src="/dataTables_excel/jquery.table2excel.js"></script>
<script type="text/javascript" src="/dataTables_excel/tableExport.js"></script>

</head>
<body>

<div>
	<button id="btn">ddd</button>
</div>
<div class="container">
    <table cellpadding="0" cellspacing="0" border="0" class="dataTable" id="example">
        <thead>
            <tr>
                <th>Rendering engine</th>
                <th>Browser</th>
                <th>Platform(s)</th>
                <th>Engine version</th>
                <th>CSS grade</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Trident</td>
                <td>Internet Explorer 4.0</td>
                <td>Internet Explorer 4.0</td>
                <td>4</td>
                <td>X</td>
            </tr>
            <tr>
                <td>Trident</td>
                <td>Internet Explorer 5.0</td>
                <td>Win 95+</td>
                <td>5</td>
                <td>C</td>
            </tr>
            <tr>
                <td>Trident</td>
                <td>Internet Explorer 5.5</td>
                <td>Win 95+</td>
                <td>5.5</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Trident</td>
                <td>Internet Explorer 6</td>
                <td>Win 98+</td>
                <td>6</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Trident</td>
                <td>Internet Explorer 7</td>
                <td>Win XP SP2+</td>
                <td>7</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Trident</td>
                <td>AOL browser (AOL desktop)</td>
                <td>Win XP</td>
                <td>6</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Firefox 1.0</td>
                <td>Win 98+ / OSX.2+</td>
                <td>1.7</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Firefox 1.0</td>
                <td>Win 98+ / OSX.2+</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Firefox 2.0</td>
                <td>Win 98+ / OSX.2+</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Firefox 3.0</td>
                <td>Win 2k+ / OSX.3+</td>
                <td>1.9</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Camino 1.0</td>
                <td>OSX.2+</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Camino 1.5</td>
                <td>OSX.3+</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Netscape 7.2</td>
                <td>Win 95+ / Mac OS 8.6-9.2</td>
                <td>1.7</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Netscape Browser 8</td>
                <td>Win 98SE+</td>
                <td>1.7</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Netscape Navigator 9</td>
                <td>Win 98+ / OSX.2+</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.0</td>
                <td>Win 95+ / OSX.1+</td>
                <td>1</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.0</td>
                <td>Win 95+ / OSX.1+</td>
                <td>1.1</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.2</td>
                <td>Win 95+ / OSX.1+</td>
                <td>1.2</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.3</td>
                <td>Win 95+ / OSX.1+</td>
                <td>1.3</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.4</td>
                <td>Win 95+ / OSX.1+</td>
                <td>1.4</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.5</td>
                <td>Win 95+ / OSX.1+</td>
                <td>1.5</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.6</td>
                <td>Win 95+ / OSX.1+</td>
                <td>1.6</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.7</td>
                <td>Win 98+ / OSX.1+</td>
                <td>1.7</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Mozilla 1.8</td>
                <td>Win 98+ / OSX.1+</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Seamonkey 1.1</td>
                <td>Win 98+ / OSX.2+</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Gecko</td>
                <td>Epiphany 2.20</td>
                <td>Gnome</td>
                <td>1.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Webkit</td>
                <td>Safari 1.2</td>
                <td>OSX.3</td>
                <td>125.5</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Webkit</td>
                <td>Safari 1.3</td>
                <td>OSX.3</td>
                <td>312.8</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Webkit</td>
                <td>Safari 2.0</td>
                <td>OSX.4+</td>
                <td>419.3</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Webkit</td>
                <td>Safari 3.0</td>
                <td>OSX.4+</td>
                <td>522.1</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Webkit</td>
                <td>OmniWeb 5.5</td>
                <td>OSX.4+</td>
                <td>420</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Webkit</td>
                <td>iPod Touch / iPhone</td>
                <td>iPod</td>
                <td>420.1</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Webkit</td>
                <td>S60</td>
                <td>S60</td>
                <td>413</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera 7.0</td>
                <td>Win 95+ / OSX.1+</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera 7.5</td>
                <td>Win 95+ / OSX.2+</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera 8.0</td>
                <td>Win 95+ / OSX.2+</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera 8.5</td>
                <td>Win 95+ / OSX.2+</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera 9.0</td>
                <td>Win 95+ / OSX.3+</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera 9.2</td>
                <td>Win 88+ / OSX.3+</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera 9.5</td>
                <td>Win 88+ / OSX.3+</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Opera for Wii</td>
                <td>Wii</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Nokia N800</td>
                <td>N800</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Presto</td>
                <td>Nintendo DS browser</td>
                <td>Nintendo DS</td>
                <td>8.5</td>
                <td>C/A<sup>1</sup>

                </td>
            </tr>
            <tr>
                <td>KHTML</td>
                <td>Konqureror 3.1</td>
                <td>KDE 3.1</td>
                <td>3.1</td>
                <td>C</td>
            </tr>
            <tr>
                <td>KHTML</td>
                <td>Konqureror 3.3</td>
                <td>KDE 3.3</td>
                <td>3.3</td>
                <td>A</td>
            </tr>
            <tr>
                <td>KHTML</td>
                <td>Konqureror 3.5</td>
                <td>KDE 3.5</td>
                <td>3.5</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Tasman</td>
                <td>Internet Explorer 4.5</td>
                <td>Mac OS 8-9</td>
                <td>-</td>
                <td>X</td>
            </tr>
            <tr>
                <td>Tasman</td>
                <td>Internet Explorer 5.1</td>
                <td>Mac OS 7.6-9</td>
                <td>1</td>
                <td>C</td>
            </tr>
            <tr>
                <td>Tasman</td>
                <td>Internet Explorer 5.2</td>
                <td>Mac OS 8-X</td>
                <td>1</td>
                <td>C</td>
            </tr>
            <tr>
                <td>Misc</td>
                <td>NetFront 3.1</td>
                <td>Embedded devices</td>
                <td>-</td>
                <td>C</td>
            </tr>
            <tr>
                <td>Misc</td>
                <td>NetFront 3.4</td>
                <td>Embedded devices</td>
                <td>-</td>
                <td>A</td>
            </tr>
            <tr>
                <td>Misc</td>
                <td>Dillo 0.8</td>
                <td>Embedded devices</td>
                <td>-</td>
                <td>X</td>
            </tr>
            <tr>
                <td>Misc</td>
                <td>Links</td>
                <td>Text only</td>
                <td>-</td>
                <td>X</td>
            </tr>
            <tr>
                <td>Misc</td>
                <td>Lynx</td>
                <td>Text only</td>
                <td>-</td>
                <td>X</td>
            </tr>
            <tr>
                <td>Misc</td>
                <td>IE Mobile</td>
                <td>Windows Mobile 6</td>
                <td>-</td>
                <td>C</td>
            </tr>
            <tr>
                <td>Misc</td>
                <td>PSP browser</td>
                <td>PSP</td>
                <td>-</td>
                <td>C</td>
            </tr>
            <tr>
                <td>Other browsers</td>
                <td>All others</td>
                <td>-</td>
                <td>-</td>
                <td>U</td>
            </tr>
        </tbody>
    </table>
</div>



</body>
<!-- 끝: 작성하기 -->
<script type="text/javascript">
	$.getScript(getContextPath() + "/resources/service/dataTablesMerge.js").done(function(script, textStatus) {
		if (!!ecoletree.vc && ecoletree.vc.name === "dataTablesMerge") {
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

</html>