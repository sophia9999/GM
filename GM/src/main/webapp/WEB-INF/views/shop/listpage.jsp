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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">
<style type="text/css">

.btn {
	color: #333;
	border: 1px solid #333;
	background-color: #fff;
	padding: 4px 10px;
	font-weight: 300;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
	margin : 15px 5px;
}
.buttons {
	width: 900px;
	margin: 20px auto;
}
.btn:hover, .btn:active, .btn:focus {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}
</style>
</head>
<body>
<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="container">
		<div class="item item1">
			<img alt="&nbsp;" src="#" style="width: 100%; height: 100%;">
			<p>Beige dotted onepiece<br>46,000 won</p>
		</div>
	</div>
	
	
	<div class="page-box">
			여기는 페이지처리하시면됩니다. 
	</div>
	<c:if test="${sessionScope.member.userId=='admin'}">
		<div class="buttons">
			<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/shop/garment-write.do';">상품등록</button>
		</div>
	</c:if>
</main>
<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>