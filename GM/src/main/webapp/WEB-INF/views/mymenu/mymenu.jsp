<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_ny.css" type="text/css">
<style type="text/css">


</style>
</head>
<body>
<header>

	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<div class="container">
	<form method="post">
	<div class="title">
		<h3> mypage</h3>
	</div>
		
		
		<table class="table table-border table-list">
			
			
			
				<tr>
					<td class="left">
						<a href="#" class="line-none">order주문내역 조회</a>
					</td>
					<td>홍길동</td>
					<td>9999-99-99</td>
					<td>9</td>
				</tr>
				
			
		</table>
		
	
	
	</form>
</div>			


<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	
</footer>	
</body>
</html>