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
.centerlist{
   width: 70%;
   margin: 0px auto 0px;
   color: #555555;
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
			<col width="10%">
			<col width="40%">
			<col width="20%">
			<col width="20%">
		</colgroup>
		<thead>
			<tr>
				<th >번호</th>
				<th >주문내용</th>				
				<th >배송현황</th>
				<th >구매일</th>
				<th >배송예정일</th>
			</tr>
		</thead>
			
		
		<tbody>
			
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none">모카핸드메이드자켓</a>
					</td>
					<td>배송전</td>
					<td>2021-11-02</td>					
					<td>2021-11-05</td>
				</tr>
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none">모카핸드메이드자켓</a>
					</td>
					<td>배송전</td>
					<td>2021-11-02</td>					
					<td>2021-11-05</td>
				</tr>
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none">모카핸드메이드자켓</a>
					</td>
					<td>배송전</td>
					<td>2021-11-02</td>					
					<td>2021-11-05</td>
				</tr>
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none">모카핸드메이드자켓</a>
					</td>
					<td>배송전</td>
					<td>2021-11-02</td>					
					<td>2021-11-05</td>
				</tr>
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none">모카핸드메이드자켓</a>
					</td>
					<td>배송전</td>
					<td>2021-11-02</td>					
					<td>2021-11-05</td>
				</tr>
				
		</tbody>	
		</table>
		
		<div class="page-box">
			
		</div>
		
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
		
	</form>
</div>	
<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>	
</footer>	

</body>
</html>