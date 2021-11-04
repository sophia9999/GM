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
						
							<img class="img-width" src="${pageContext.request.contextPath}/resource/images/1.jpg">						
						
						<div>[ more information ] 아카이브 제이투유 사이트는 오직 결제를 위한 플랫폼입니다. 
						제품의 상세페이지는 블로그를 통하여 확인하실 수 있습니다.
						 아래 이미지를 클릭하시면 제품 상세페이지로 이동합니다.
						 </div>
					</td>
				</tr>
				<tr>
					<td>가격</td>
					<td class="left">
						39,800원
					</td>
				</tr>
				<tr>
					<td>수취인</td>
					<td class="left">
						홍홍홍
					</td>
				</tr>
				<tr>
					<td>연락처</td>
					<td class="left">
						010-1234-1234
					</td>
				</tr>
				<tr>
					<td>배송지</td>
					<td class="left">
						서울특별시 영등포구 버드나루로5길 투프레이즈오피스텔 (12312)
					</td>
				</tr>
				<tr>
					<td>배송현황</td>
					<td class="left" >
						배송중
					</td>
				</tr>
				<tr>
					<td>배송예정일</td>
					<td class="left" >
						2021-11-05
					</td>
				</tr>
			<!-- 관리자일시 배송 현황과 예정일을 뺴고 이부분을 쓰시면될것같아요 -->
				<tr>
					<td>
						 운송번호입력
					</td>
					<td align="left" >
						<input placeholder="운송장번호입력" name="dNum">
					</td>
					
				</tr>
		</tbody>	
		</table>
		<button type="button" class="btn" style="float:left">뒤로</button>
		<button type="button" class="btn" style="float:right">수정</button>
					
		<!--  
			<table class="table">
				
				<tr>
					
					<td align="right" >
						<button type="button" class="btn" >수정</button>
						<button type="button" class="btn" >뒤로</button>
					</td>
					
				</tr>
			</table>	-->
		
		
					
	</form>
</div>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>	

</body>
</html>