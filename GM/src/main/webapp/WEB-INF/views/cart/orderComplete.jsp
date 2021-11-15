<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GM: 완료</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_ny.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_hg.css" type="text/css">

<style type="text/css">
.msg-box {
	text-align: center; color: black;
	font-size: 16px;
}
</style>
</head>
<body>
<header>  
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<div class="wrap  maincenter">
	<div class="login-box">
		<a class="loginlogo">GarmentMarket </a>
			<h2>결제가 성공적으로 완료되었습니다.</h2>
			<p>저희 쇼핑몰을 이용해주셔서 감사합니다</p>
		<div class="btn-box">
			<button class="btn" type="button" style="border: none;" onclick="location.href='${pageContext.request.contextPath}/';" class="btnConfirm">HOME</button>
   	  	</div>
	</div>
</div>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>