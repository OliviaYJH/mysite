<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="header">
	<!-- 모든 Header 부분을 header.jsp로 만들고 import 시키기 -->
	<h1>MySite</h1>
	<ul>
		<li><a href="<%=request.getContextPath()%>/user?a=loginform">로그인</a>
		<li>
		<li><a href="<%=request.getContextPath()%>/user?a=joinform">회원가입</a>
			<!-- 오늘 부분 -->
		<li>
		<li><a href="<%=request.getContextPath()%>/user?a=updateform">회원정보수정</a>
		<li>
		<li><a href="<%=request.getContextPath()%>/user?a=logoutform">로그아웃</a>
		<li>
		<li>님 안녕하세요 ^^;</li>
	</ul>
</div>