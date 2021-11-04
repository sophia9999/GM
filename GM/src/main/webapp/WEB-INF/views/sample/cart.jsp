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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/cart_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_hg.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">

</head>
<body>
<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</header>

<div class="cart-container">
	<div class="title">
		<h3><span>|</span> 장바구니</h3>
	</div>
	
	<form method="post">
		<table class="table table-cart table-border table-form ">
			<tr>
				<th style="width: 3px;"><input type="checkbox"></th>
				<th>상품</th>
				<th>상품명</th>
				<th>수량</th>
				<th>가격</th>
			</tr>
			<tr>
				<td><input type="checkbox"></td>
				<td><p> <img src="#" class="img"></p></td>
				<td>네이비 리본 프릴 원피스</td>
				<td style="text-align: center;"> <input type="text" class="boxTF">
				<button type="button" class="btn cart-btn">▲</button>
				<button type="button" class="btn cart-btn">▼</button>
				</td>
				<td style="text-align: right;">58,000원</td>
			</tr>
			<!-- 장바구니에 담긴 상품이 있으면! -->
			<c:if test="">
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</c:if>
		</table>
		<button type="button" class="btn" style="float:left">상품삭제</button>
		<button type="button" class="btn" style="float:right">주문하기</button>
		<!--  
		<table class="table">
				<tr> 
					<td align="left" style="padding-left: 5px">
						<button type="button" class="btn">상품삭제</button>
					</td>
					<td align="right" style="padding-right: 5px">
						<button type="button" class="btn">주문하기</button>
					</td>
				</tr>
		</table> -->
		<!-- 장바구니에 상품이 없으면 
		<table class="table">
			<tr>
				<td align="center">
					<span class="msg-box">장바구니에 상품이 없습니다.${message}</span>
				</td>
			</tr>
		</table>
		 -->
	</form>
</div>
<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>