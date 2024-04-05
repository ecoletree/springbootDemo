<!--
/*****************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 *
 * Author :
 * Create Date : 2018. 06. 12.
 * DESC : [관리자웹] 팝업창
*****************************************************************/
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="templete1" type="x-tmpl-mustache" class="hide">
	<b>{{name}}</b> is babo. <br />
	<span style="font-size:11pt;color:red">{{age}} years old.</span>
</div>

<div id="templete2" type="x-tmpl-mustache" class="hide">
	{{#peoples}}
	* {{name}} is {{age}} years old. <br />
	{{/peoples}}
</div>

<script id="boxTemplate" type="text/x-template">
  <div class="person-info" style="width: 400px;border: 1px solid black;padding: 10px;margin-bottom: 10px;">
    <h2>{{name}}</h2>
    <p>성별: {{gender}}</p>
    <p>전화번호: {{phone}}</p>
  </div>
</script>

<script id="boxTemplate2" type="text/x-template">
	{{#dataSets}}
	<div class="person-info" style="width: 400px;border: 1px solid {{color}};padding: 10px;margin-bottom: 10px;">
  		<h2>{{name}}</h2>
  		<p>성별: {{gender}}</p>
  		<p>전화번호: {{phone}}</p>
	</div>
	{{/dataSets}}
</script>

