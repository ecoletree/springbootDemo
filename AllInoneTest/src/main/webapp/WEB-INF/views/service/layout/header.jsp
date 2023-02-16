<!-- 
/*****************************************************************
 * Copyright (c) 2017 Ecoletree. All Rights Reserved.
 * 
 * Author :  
 * Create Date : 2018. 06. 12.
 * DESC : [관리자웹] 헤더 
*****************************************************************/
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<header class="head">
	<img src="${cp }/resources/ecoletree/img/img_logo.png" class="logo">
	<ul class="menu">	
	<c:forEach var="item" items="${sessionScope.roleMenu}" varStatus="status">
			<c:choose>
				<c:when test="${status.index == 0}">
					<c:if test="${item.is_view ne 'N'}">
						<li class="sel"><a name="aHeaderTopMenu" href="${cp }${item.menu_url}" data-is_encrypt_download="${item.is_encrypt_download}" data-is_certification="${item.is_certification}" data-is_search_option="${item.is_search_option}" data-is_column="${item.is_column}" data-is_play="${item.is_play}" data-is_transfer="${item.is_transfer}" data-is_rec_download="${item.is_rec_download}" data-is_download="${item.is_download}" data-is_upload="${item.is_upload}" data-is_search="${item.is_search}" data-gourl="${item.menu_url}" data-is_update="${item.is_update}" data-is_write="${item.is_write}" data-is_delete="${item.is_delete}">${item.menu_name}</a>
						<c:if test="${item.children ne null}">
							<ul>
								<c:forEach var="child" items="${item.children}">
									<c:if test="${child.is_view ne 'N'}">
									<li><a name="aHeaderMenu" href="${cp }${child.menu_url}" data-is_encrypt_download="${child.is_encrypt_download}" data-is_certification="${child.is_certification}" data-is_search_option="${child.is_search_option}" data-is_column="${child.is_column}" data-is_play="${child.is_play}" data-is_transfer="${child.is_transfer}" data-is_rec_download="${child.is_rec_download}" data-is_download="${child.is_download}" data-is_upload="${child.is_upload}" data-is_search="${child.is_search}" data-gourl="${child.menu_url}" data-is_update="${child.is_update}" data-is_write="${child.is_write}" data-is_delete="${child.is_delete}">${child.menu_name}</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</c:if>
						</li>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${item.is_view ne 'N'}">
						<li><a name="aHeaderTopMenu" href="${cp }${item.menu_url}" data-is_encrypt_download="${item.is_encrypt_download}" data-is_certification="${item.is_certification}" data-is_search_option="${item.is_search_option}" data-is_column="${item.is_column}" data-is_play="${item.is_play}" data-is_transfer="${item.is_transfer}" data-is_rec_download="${item.is_rec_download}" data-is_download="${item.is_download}" data-is_upload="${item.is_upload}" data-is_search="${item.is_search}" data-gourl="${item.menu_url}" data-is_update="${item.is_update}" data-is_write="${item.is_write}" data-is_delete="${item.is_delete}">${item.menu_name}</a>
						<c:if test="${item.children ne null}">
							<ul>
								<c:forEach var="child" items="${item.children}">
									<c:if test="${child.is_view ne 'N'}">
									<li><a name="aHeaderMenu" href="${cp }${child.menu_url}" data-is_encrypt_download="${child.is_encrypt_download}" data-is_certification="${child.is_certification}" data-is_search_option="${child.is_search_option}" data-is_column="${child.is_column}" data-is_play="${child.is_play}" data-is_transfer="${child.is_transfer}" data-is_rec_download="${child.is_rec_download}" data-is_download="${child.is_download}" data-is_upload="${child.is_upload}" data-is_search="${child.is_search}" data-gourl="${child.menu_url}" data-is_update="${child.is_update}" data-is_write="${child.is_write}" data-is_delete="${child.is_delete}">${child.menu_name}</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</c:if>
						</li>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:forEach>

	</ul>
	<div class="headInfo">
		<img alt="icon" src="${cp }/resources/ecoletree/img/icon_user.png">
		<span>${sessionScope.sessionVO.user_info.user_id}</span>
		<span> | </span>
		<a id="btn_logout"href="${cp }/logout">로그아웃</a>
	</div>
</header>
<script>
	$(function() {
// 		$('a[name="aHeaderMenu"]').each(function (index,item) {
// 			$(item.parentElement).removeClass("sel");
// 			var gourl = $(item).data("gourl");
// 			if (-1 < gourl.indexOf(",")) {
// 				$(gourl.split(",")).each(function (i,data) {
// 					if (0 < location.href.indexOf(data)) {
// 						$(item.parentElement).addClass("sel");
// 						return;
// 					}
// 				});
				
// 			} else {
// 				if (0 < location.href.indexOf($(item).data("gourl"))) {
// 					$(item.parentElement).addClass("sel");
// 				}
// 			}
// 		});
		
		var loc =  location.href;
		$('a[name="aHeaderTopMenu"]').each(function (index,item) {
			$(item.parentElement).removeClass("sel");
			if (item.href === loc) {
				$(item).addClass("sel");
				$(item.parentElement).addClass("sel");
			}
		});
		$('a[name="aHeaderMenu"]').each(function (index,item) {
			$(item.parentElement).removeClass("sel");
			$(item.parentElement).data($(item).data());
			if (item.href === loc) {
				$(item.parentElement).addClass("sel");
				$(item).parents("li").find("a[name='aHeaderTopMenu']").addClass("sel");
			}
		});
	});
</script>

<!-- /.container -->