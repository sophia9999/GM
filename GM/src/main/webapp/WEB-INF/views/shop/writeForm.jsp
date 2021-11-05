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
.body-container {
	width: 900px;  margin: 150px auto 70px;
}
</style>
</head>
<body>

<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</header>
<div class="body-container">
	<div class="title">
		<h3><span>|</span> 글쓰기폼[QnA, 상품글쓰기, 리뷰, 공지사항 ...]</h3>
	</div>
	<form name="photoForm" method="post" enctype="multipart/form-data">
		<table class="table table-border table-form">
			<tr>
				<td>제&nbsp;목</td>
				<td>
					<input type="text" name="name" class="boxTF" maxlength="6">
				</td>
			</tr>
			
			<tr>
				<td>글쓴이</td>
				<td>
					<input type="text" name="name" class="boxTF" >
				</td>
			</tr>
			
			<tr>
				<td valign="top">내&nbsp;용</td>
				<td>
					<textarea name="content" class="boxTF" ></textarea>
				</td>
			</tr>
			
			<tr>
				<td>이미지</td>
				<td style="text-align: left; padding-left: 25px;">
					<input type="file" name="selectFile" accept="image/*" multiple="multiple" class="boxTF">
				</td>
			</tr>
			
			<%--이미지 파일 있으면--%>
			<c:if test="${mode=='update'}">
				<tr>
					<td>첨부 이미지</td>
					<td>
						<div class="img-box">
							<%--
								여기에 foreach로 이미지 미리보기 출력
							 --%>
						</div>
					</td>
				</tr>
			</c:if>
		</table>
		
		<table class="table">
				<tr> 
					<td align="left" style="padding-left: 5px">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/shop/garment.do';">등록취소</button>
					</td>
					<td align="center">
						<button type="reset" class="btn">다시입력</button>
					</td>
					<td align="right" style="padding-right: 5px">
						<button type="button" class="btn" onclick="sendOk();">등록하기</button>
					</td>
				</tr>
		</table>
	</form>
</div>
<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>