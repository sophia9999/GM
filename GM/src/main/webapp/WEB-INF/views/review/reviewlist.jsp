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

<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<style type="text/css">
.centerlist{
   width: 70%;
   margin: 0px auto 0px;
   color: #555555;
   }
.pagingBox {
	margin-top : 40px;
}
.h3css{
	display: inline-table;
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
		<h3 class="h3css"><span>|</span> REVIEW</h3>
		<!--  <button style="float: right;" type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/review/myreviewlist.do';">MY REVIEW</button>-->	
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
			<col width="10%">
			<col width="40%">
			<col width="20%">
			<col width="20%">
		</colgroup>
		<thead>
			<tr>
				<th class="rNum">번호</th>
				<th class="subject">상품이름</th>				
				<th class="userId">작성자</th>
				<th class="date">작성일</th>
			</tr>
		</thead>
			
		
		<tbody>
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.listNum}</td>
					<td class="left">
						<a href="${articleUrl}&rNum=${dto.rNum}" class="line-none">${dto.allClothesName}</a>
					</td>
					<td>${dto.userName}</td>
					<td>${dto.r_reg_date}</td>	
				</tr>
			</c:forEach>
		</tbody>	
		</table>
		<div class="pagingBox">  
			${reviewCount == 0 ? "등록된 게시물이 없습니다." : paging}
		</div>
		<table class="table">
			<tr>
				<td colspan="2" align="right" >
					<!-- <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/review/write.do?odNum=${dto.odNum}&page=${page}';">리뷰등록</button>	  -->			
				</td>
			</tr>
		</table>
	</form> 
				 	
		<!--  
		<table class="table">
			<tr>
				
				<td align="center" >
					<form name="searchForm" action="" method="post">
						<select name="condition" class="selectField">
							<option value="all"     >전체</option>
							<option value="reg_date"  >주문일</option>
							<option value="order"  >주문내용</option>
						</select>
						<input type="text" name="keyword" class="boxTF">
						<button type="button" class="btn" onclick="searchList();">검색</button>
					</form>
				</td>
				
			</tr>
		</table>	 -->
		

</div>	
<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>	

</body>
</html>