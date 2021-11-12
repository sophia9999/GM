<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GM: 주문내역리스트</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_ny.css" type="text/css">

<style type="text/css">
.centerlist{
   width: 70%;
   margin: 0px auto 0px;
   color: #555555;
   }

</style>

<script type="text/javascript">
function searchList() {
	var f = document.searchForm;
	f.submit();
}
</script>

</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<div class="container">
	<div>
	<div class="title">
		<h3><span>|</span> 주문내역</h3>
	</div>
 <!-- 
	<table class="table">
			<tr>
				<td width="50%">
					
					&nbsp;
				</td>
				<td align="right">10개(10/10 페이지)</td>
			</tr>
		</table>   -->
		
		
		<table class="table table-border table-list">
		<colgroup>
			<col width="5%">
			<col width="10%">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="15%">
			<col width="15%">
		</colgroup>
		<thead>
			<tr>
				<th >번호</th>
				<th >주문번호</th>
				<th >상세번호</th>
				<th >주문한 옷</th>				
				<th >배송현황</th>
				<th >구매일</th>
				<th >배송예정일</th>
			</tr>
		</thead>
			
		
		<tbody>
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.listNum}</td>
					<td>${dto.oNum}</td>
					<td>${dto.odNum}</td>
					<td class="left">
						<a href="${articleUrl}&odNum=${dto.odNum}">${dto.clothName}</a>
					</td>
					<td>${empty dto.state ? "배송전":dto.state }</td>
					<td>${dto.order_date}</td>
					<td>${dto.arrivedate}</td>
				</tr>
			</c:forEach>
			
				
		</tbody>	
		</table>
		
		<div class="page-box">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
		</div>
		
	 
		<table class="table">
			<tr>
				
				<td align="center" >
					<form name="searchForm" action="${pageContext.request.contextPath}/mymenu/myorder.do" method="post">
						<select name="condition" class="selectField">
							<option value="all"     >전체</option>
							<option value="order_date"  >주문일</option>
							<option value="clothName"  >주문내용</option>
						</select>
						<input type="text" name="keyword" class="boxTF">
						<button type="button" class="btn" onclick="searchList();">검색</button>
					</form>
				</td>
				
			</tr>
		</table>	 
		
</div>
</div>	
<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>	
</footer>	

</body>
</html>