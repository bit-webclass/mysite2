<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
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
				<div id="guestbook" class="deleteform">
					<h2>방명록삭제</h2>
					
					<form class="form-box" method="get" action="/mysite2/gb">
						<div class="form-group">
							<label class="block-label">비밀번호</label> 
							<input type="password" name="password" value="">
						</div>
						<input type='text' name="action" value="delete">
						<input type='text' name="no" value="${param.no }">
						<input type="submit" value="확인">
					</form>
					<a href="/mysite2/gb">방명록 리스트</a>
					
				</div><!-- /guestbook -->
			</div><!-- /c_box -->
		</div><!-- /content -->
			
		<!-- footer -->	
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	
	</div><!-- /container -->
</body>
</html>