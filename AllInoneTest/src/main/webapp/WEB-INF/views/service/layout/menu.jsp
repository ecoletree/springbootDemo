<!-- 
/*****************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * Author :  
 * Create Date : 2018. 06. 12.
 * DESC : [관리자웹] 메뉴
*****************************************************************/
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container">
	<a class="mainnav-toggle" data-toggle="collapse" data-target=".mainnav-collapse">
		<span class="sr-only">Toggle navigation</span>
		<i class="fa fa-bars"></i>
	</a>

	<nav class="collapse mainnav-collapse" role="navigation">
		<ul class="mainnav-menu">
			<li class="dropdown">
				<a href="${cp}/admin/aptMgt/openAptList"  class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
					주문관리 <i class="mainnav-caret"></i>
				</a>
				<ul class="dropdown-menu" role="menu">
					<li>
						<a href="${cp}/admin/aptMgt/openAptList" onclick="$('.mainnav-menu>li').removeClass('active');$(this).closest('li.dropdown').addClass('active');">
							<i class="fa fa-building-o dropdown-icon "></i> 아파트 관리
						</a>
					</li>
				</ul>
			</li>
		</ul>
	</nav>
</div>
<!-- /.container -->