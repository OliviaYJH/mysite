<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="navigation">
	<!-- 모든 navigation 부분을 navigation.jsp로 만들고 import 시키기 -->
	<ul>
		<li><a href="${pageContext.request.contextPath }">유정현</a></li>
		<li><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
		<li><a href="${pageContext.request.contextPath }/board/${1 }">게시판</a></li>
	</ul>
</div>