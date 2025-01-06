<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post"
					action="${pageContext.request.contextPath }/user/join">
					
					<label class="block-label" for="name"><spring:message code="user.join.label.name"/></label>
					 <input id="name"name="name" type="text" value=""> 
					 
					 <label class="block-label" for="email"><spring:message code="user.join.label.email"/></label> 
					 <input id="email" name="email" type="text" value=""> 
					<input type="button" value="<spring:message code='user.join.label.email.check'/>"> 
					 
					 <label class="block-label"><spring:message code="user.join.label.password"/></label> 
					 <input name="password" type="password" value="">

					<fieldset>
						<legend><spring:message code="user.join.label.gender"/></legend>
						<label>여</label> 
						<input type="radio" name="gender" value="female" checked="checked"> 
						<label>남</label> 
						<input type="radio" name="gender" value="male">
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>