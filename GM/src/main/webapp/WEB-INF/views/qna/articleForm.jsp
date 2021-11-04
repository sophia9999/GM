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

</head>
<body>
<header>
	<jsp:include page="${pageContext.request.contextPath}/layout/header.jsp"></jsp:include>
</header>


<div class="container">
	<form method="post">
		<div class="title">
		<h3><span>|</span> 글보기</h3>
	</div>
		<table class="table table-border table-form">
			<tr>
				<td>제&nbsp;목</td>
				<td>
					테스트입니다.
				</td>
			</tr>
			
			<tr>
				<td>글쓴이</td>
				<td>
					관리자
				</td>
			</tr>
			
			<tr>
				<td valign="top">내&nbsp;용</td>
				<td>
					테스트 내용입니다.<br>
					테스트 내용입니다.<br>
					테스트 내용입니다.<br>
					테스트 내용입니다.<br>
					테스트 내용입니다.<br>
					테스트 내용입니다.<br>
				</td>
			</tr>
			<%--이미지 파일 있으면--%>
			<c:if test="">
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
			<tr>
				<td colspan="2" style="background: text-align: center;">이전글 : 제목</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">다음글 : 제목</td>
			</tr>
		</table>
		
		<div class="buttons">
			<button type="button" class="btn" style="float:left">수정</button>
			<button type="reset" class="btn" style="float:left">삭제</button>
			<button type="button" class="btn" style="float:right">목록</button>
		</div>
<!--  
		<table class="table">
				<tr> 
					<td align="left" style="padding-left: 5px">
						<button type="button" class="btn">수정</button>
					</td>
					<td align="left">
						<button type="reset" class="btn">삭제</button>
					</td>
					<td align="right" style="padding-right: 5px">
						<button type="button" class="btn">목록</button>
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