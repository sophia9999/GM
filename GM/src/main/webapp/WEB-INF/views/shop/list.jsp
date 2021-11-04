<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
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


</style>
</head>
<body>
<header>

	<jsp:include page="${pageContext.request.contextPath}/layout/header.jsp"></jsp:include>
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
		</table>    -->
		
		<table class="table table-border table-list">
			<tr>
				<th class="num">번호</th>
				<th class="subject">제목</th>
				<th class="name">작성자</th>
				<th class="date">작성일</th>
				<th class="hit">조회수</th>
			</tr>
			
			
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none">테스트</a>
					</td>
					<td>홍길동</td>
					<td>9999-99-99</td>
					<td>9</td>
				</tr>
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none" >테스트</a>
					</td>
					<td>홍길동</td>
					<td>9999-99-99</td>
					<td>9</td>
				</tr>
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none" >테스트</a>
					</td>
					<td>홍길동</td>
					<td>9999-99-99</td>
					<td>9</td>
				</tr>
				<tr>
					<td>1</td>
					<td class="left">
						<a href="#" class="line-none" >테스트</a>
					</td>
					<td>홍길동</td>
					<td>9999-99-99</td>
					<td>9</td>
				</tr>
			
		</table>
		
		<div class="page-box">
			페이지처리 
		</div>
	
		<div class="buttons">
			<button type="button" class="btn" style="float:left">새로고침</button>
			<select name="condition" class="selectField">
				<option value="all"     >제목+내용</option>
				<option value="userName">작성자</option>
				<option value="reg_date"  >등록일</option>
				<option value="subject"  >제목</option>
				<option value="content" >내용</option>
			</select>
			<input type="text" name="keyword" class="boxTF" size="30">
			<button type="button" class="btn" onclick="searchList();">검색</button>
			<button type="button" class="btn" style="float:right">글올리기</button>
			
		</div> 
 	
 	<!--  
		<table class="buttons">
			<tr>
				<td width="100">
					<button type="button" class="btn" style="float:left">새로고침</button>
				</td>
				<td align="center">
					<form name="searchForm" action="" method="post">
						<select name="condition" class="selectField">
							<option value="all"     >제목+내용</option>
							<option value="userName">작성자</option>
							<option value="reg_date"  >등록일</option>
							<option value="subject"  >제목</option>
							<option value="content" >내용</option>
						</select>
						<input type="text" name="keyword" class="boxTF">
						<button type="button" class="btn" onclick="searchList();">검색</button>
					</form>
				</td>
				<td align="right" width="100">
					<button type="button" class="btn"style="float:right">글올리기</button>
				</td>
			</tr>
		</table>
	-->
	</form>
</div>			


<footer>
	<jsp:include page="${pageContext.request.contextPath}/layout/footer.jsp"></jsp:include>

</footer>
</body>
</html>