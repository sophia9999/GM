<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

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
				<p>메시지 출력/주문이완료되었습니다/회원가입을 축하합니다~ </p>		
	</div>

</div>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>

</footer>
</body>
</html>