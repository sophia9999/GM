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


<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_hg.css" type="text/css">


<style type="text/css">
 .img-width{
 	max-width: 200px;
 }
 
 .in-flex{
 	display: inline-flex;
 	
 }
.table tr > td:nth-child(2) {
	text-align: left;
}

</style>

<script type="text/javascript">

</script>

</head>
<body>
<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<div class="container">
	<form method="post">
	<div class="title">
		<h3><span>|</span> 주문상세</h3>
	</div>
		<table class="table table-border table-list">
		<colgroup>
			<col width="20%">
			<col/>
		</colgroup>
			
		
		<tbody>
				<tr>
					<td>주문번호</td>
					<td class="left">${dto.oNum}
					</td>
				</tr>
				<tr>
					<td>제품</td>
					<td class="left">${dto.clothName}
					</td>
				</tr>
				<tr>
					<td>가격</td>
					<td class="left">
						${dto.price }						
					</td>
				</tr>
				<tr>
					<td>수취인</td>
					<td class="left">
						${dto.recipient }
					</td>
				</tr>
				<tr>
					<td>연락처</td>
					<td class="left">
						${dto.recPhoneNum }
					</td>
				</tr>
				<tr>
					<td>배송지</td>
					<td class="left">
						${dto.daddress_detail }
					</td>
				</tr>
				<tr>
					<td>배송현황</td>
					<td class="left" >
						${dto.state }
					</td>
				</tr>
				<tr>
					<td>배송예정일</td>
					<td class="left" >
						${dto.arriveDate }
					</td>
				</tr>
				<tr>
					<td>
						 운송번호입력
					</td>
					<td align="left" >
						${dto.deNum }
					</td>
					
				</tr>
		</tbody>	
		</table>
		<button type="button" class="btn" style="float:left" onclick="location.href='${pageContext.request.contextPath}/order/order.do';">뒤로</button>
		<button type="button" class="btn" style="float:right" 
			onclick="location.href='${pageContext.request.contextPath}/order/delevely.do?odNum=${dto.odNum}&clothName=${clothName}';">${empty dto.deNum ? "배송처리" : "배송수정" } </button>
					

		
		
					
	</form>
</div>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>	

</body>
</html>