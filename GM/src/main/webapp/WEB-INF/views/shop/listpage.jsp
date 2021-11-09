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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">
<style type="text/css">
.table {
	width: 900px;  margin: 150px auto;
	margin-bottom: 50px;
}

.item img {
	position: relative;
}

.item p {
	position: absolute;
	display: inline-block;
    margin-inline-start: -295px;
    margin-block: 0px;
    width: 295px;
    font-weight: 700;
    font-size: 18px;
    letter-spacing: -1px;
    text-shadow: 1px 1px 5px #000;
    color: #fff;
}

.btn {
	color: #333;
	border: 1px solid #333;
	background-color: #fff;
	padding: 4px 10px;
	font-weight: 300;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
	margin : 15px 5px;
	width: 280px;
    height: 40px;
    font-size: 14px;
}
.buttons {
	width: 900px;
	margin: 20px auto;
}
.btn:hover, .btn:active, .btn:focus {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}
</style>
</head>
<body>
<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</header>

<main>
		<table class="table">
			<tr>
				<td width="50%" align="left">
					${dataCount}개(${page}/${total_page} 페이지)
				</td>
				<!-- 
				<td align="right">
					<input type="checkbox" name="season" value="봄">봄
					<input type="checkbox" name="season" value="여름">여름
					<input type="checkbox" name="season" value="가을">가을
					<input type="checkbox" name="season" value="겨울">겨울
				</td>
				 -->
			</tr>
		</table>
	
	<div class="container" style="margin-top: 0;">
		<c:forEach var="dto" items="${list}" varStatus="status">
			<div class="item item1"
				onclick="location.href='${articleUrl}&num=${dto.cnum}';">
				<img alt="&nbsp;" src="${pageContext.request.contextPath}/uploads/photo/${dto.imageFilename}" 
					style="width: 100%; height: 100%;">
				<p>${dto.clothname}<br><fmt:formatNumber value="${dto.price}" pattern="#,###"></fmt:formatNumber></p>
			</div>
		</c:forEach>
	</div>
	
	
	<div class="page-box">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
	</div>
	<c:if test="${sessionScope.member.userId=='admin'}">
		<div class="buttons">
			<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/shop/garment-write.do';">상품등록</button>
		</div>
	</c:if>
</main>
<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>