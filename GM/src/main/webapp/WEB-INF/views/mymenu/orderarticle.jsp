<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GM: 주문내역</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">


<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">

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
					<td>제품</td>
					<td class="left in-flex" >
						
							<img class="img-width" src="${pageContext.request.contextPath}/uploads/photo/${dto.fileName}">						
						
						<div>
						${dto.ccoment }
						</div>
					</td>
				</tr>
				<tr>
					<td>가격</td>
					<td class="left">
						${price}원	
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
						${dto.dAddress }${dto.dAddress_detail }		
					</td>
				</tr>
				<tr>
					<td>배송현황</td>
					<td class="left" >
						${empty dto.state? "배송전" : dto.state }		
					</td>
				</tr>
				<tr>
					<td>배송예정일</td>
					<td class="left" >
						${empty dto.arrivedate ? "배송전" : dto.state }	
					</td>
				</tr>
			<!-- 관리자일시 배송 현황과 예정일을 뺴고 이부분을 쓰시면될것같아요 -->
				<tr>
					<td>
						 운송번호
					</td>
					<td align="left" >
						${ dto.deNum == 0 ? "배송전" : dto.deNum} 
					</td>
					
				</tr>
		</tbody>	
		</table>
		<button type="button" class="btn" style="float:left" onclick="javascript:location.href='${pageContext.request.contextPath}/mymenu/myorder.do${query}'">뒤로</button>
	
		<c:choose>
			<c:when test="${dto.state=='배달완료'&&empty dto.rNum}">
				<button type="button" class="btn" style="float:right" onclick="location.href='${pageContext.request.contextPath}/review/write.do?odNum=${dto.odNum}&cdNum=${dto.cdnum}';" >리뷰작성</button>
			</c:when>
	
			<c:when test="${empty dto.state}">
				<button type="button" class="btn" style="float:right" onclick="javascript:location.href='${pageContext.request.contextPath}/mymenu/myorder_update.do${query}&odNum=${dto.odNum}'" >배송지 수정 및 주문취소</button>
			</c:when>			
		<%-- 	<c:when test="${empty dto.state}">
				<button type="button" class="btn" style="float:right" onclick="javascript:location.href='${pageContext.request.contextPath}/mymenu/myorder.do${query}&dto=${dto}'" >배송 수정 및 주문취소</button>
			</c:when> --%>
		</c:choose>
		
					
		
		
					
	</form>
</div>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>	

</body>
</html>