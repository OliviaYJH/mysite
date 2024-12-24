<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${totalCount }" />
					<!--  ${page.totalCount} -->
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count - (pageNo - 1) * pageSize - status.index }</td>
							<td style="text-align:left; padding-left:${vo.depth * 20 }px">
								<c:if test='${vo.depth > 0 }'>
									<img
										src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if> <a
								href="${pageContext.request.contextPath }/board?a=view&id=${vo.id}">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test='${vo.userId == authUser.id }'>
									<td><a
										href="${pageContext.request.contextPath }/board?a=delete&boardId=${vo.id}"
										class="del">삭제</a></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<!-- prevPage부터 nextPage까지 for문 돌려 표시. 이때 pageNo와 동일하면 selected -->
					<ul>
						<c:choose>
							<c:when test="${nextPage > endPage }">
							</c:when>

							<c:otherwise>
								<c:choose>
									<c:when test="${pageNo-1 >= beginPage }">
										<li><a
											href="${pageContext.request.contextPath }/board?pageNo=${pageNo-1}">◀</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="">◀</a></li>
									</c:otherwise>
								</c:choose>


								<c:forEach var="no" begin="${prevPage }" end="${prevPage + 4 }">
									<c:choose>
										<c:when test='${pageNo == no }'>
											<li class="selected">${no }</li>
										</c:when>
										<c:when test='${no > nextPage }'>
											<li>${no }</li>
										</c:when>
										<c:otherwise>
											<li><a
												href="${pageContext.request.contextPath }/board?pageNo=${no}">${no }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>


								<c:choose>
									<c:when test="${pageNo + 1 <= endPage }">
										<li><a
											href="${pageContext.request.contextPath }/board?pageNo=${pageNo+1}">▶</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="">▶</a></li>
									</c:otherwise>
								</c:choose>

							</c:otherwise>
						</c:choose>

					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<c:if test='${not empty authUser }'>
						<a href="${pageContext.request.contextPath }/board?a=writeform"
							id="new-book">글쓰기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>