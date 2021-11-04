<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GM : 판매내역 및 매출현황</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">

<style type="text/css">

</style>
</head>
<body>
<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</header>
<div class="container">
	<form>
	<div class="title" >
		<h3><span>|</span> 판매내역</h3>
	</div>
	      
	<table class="table table-border table-form" style="clear:both;">
			<tr>
				<th>주문번호</th>
				<th style="width: 120px;">상품코드</th>
				<th>상품명</th>
				<th>단가</th>
				<th>재고</th>
			</tr>
			<tr>
				<td>20211103-01</td>
				<td>여기에는 코드</td>
				<td>블랙 진</td>
				<td style="text-align: right;">8,000</td>
				<td style="text-align: right;">20</td>
			</tr> 
	</table>  
	
	<div class="page-box">
			여기는 페이지처리하시면됩니다.
	</div>
	 
	
	<div class="title" style="padding-top: 30px;">
		<h3><span>|</span> 매출현황</h3>
	</div>
	
	<table class="table table-border table-form">
			<tr>
				<th style="width: 120px;">
					<select name="condition" class="selectField">
						<option value="all">전체매출</option>
						<option value="month">연 매출</option>
						<option value="month">월 매출</option>
						<option value="day" selected="selected">일 매출</option>
					</select>
				</th>
				<th style="width: 120px;">상품코드</th>
				<th>상품명</th>
				<th>단가</th>
				<th style="width: 120px;">매출금액</th>
			</tr>
			<tr>
				<td>오늘 2021-11-03</td>
				<td>상품코드코드코드</td>
				<td>상품명상품명 바지바지옷</td>
				<td style="text-align: right;">단가단가</td>
				<td style="text-align: right;">2,000,000원</td>
			</tr>
	</table>
	
	<div class="page-box">
			여기는 페이지처리하시면됩니다. 
	</div>
	</form>
</div>
<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>