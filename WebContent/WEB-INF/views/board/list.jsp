<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
	<title>Mysite</title>
</head>
<body>
	<div id="container">
		
		<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		
		<!-- navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="c_box">
				<div id="board">
					<h2>게시판-리스트</h2>
					
					<form action="" method="post">
						<input type="text" id="kwd" name="kwd" value="">
						<input type="submit" value="찾기">
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
						
						<c:forEach items="${boardList }" var="boardVo" >
							<tr>
								<td>${boardVo.no}</td>
								<td><a href="${pageContext.request.contextPath }/board?action=read&no=${boardVo.no }">${boardVo.title }</a></td>
								<td>${boardVo.userName }</td>
								<td>${boardVo.hit }</td>
								<td>${boardVo.regDate }</td>
									
								<c:if test="${sessionScope.authUser.no == boardVo.userNo }">	
									<td><a href="${pageContext.request.contextPath }/board?action=delete&no=${boardVo.no }" class="del">삭제</a></td>
								</c:if>
								<c:if test="${sessionScope.authUser.no != boardVo.userNo }">	
									<td></td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
					<div class="pager">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li class="selected">3</li>
							<li><a href="">4</a></li>
							<li><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
					</div>				
					<c:if test="${sessionScope.authUser ne null }">					
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board?action=writeform" id="new-book">글쓰기</a>
						</div>
					</c:if>	
					
				</div><!-- /board -->
			</div><!-- /c_box -->
		</div><!-- /content -->
			
			
		<!-- footer -->	
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>