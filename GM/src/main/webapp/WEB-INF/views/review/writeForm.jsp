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
<script type="text/javascript">
function sendOk() {
    var f = document.reviewForm;
	var str;
	
	
    str = f.content.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/review/write_ok.do";
    f.submit();
}
</script>
</head>
<body>

<header>
   <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<div class="body-container">
	<div class="title">
		<h3><span>|</span> REVIEW 작성</h3>
	</div>
	<form name="reviewForm" method="post">
		<table class="table table-border table-form">
			<tr>
				<td>제&nbsp;목</td>
				<td>
					<input type="text" name="subject" value="${clothName}&nbsp[color:${color}]&nbsp[size:${sizes}]">
				</td>
			</tr>
			
			<tr>
				<td>글쓴이</td>
				<td>
					<p>${sessionScope.member.userName}</p>
				</td>
			</tr>
			
			<tr>
				<td valign="top">내&nbsp;용</td>
				<td>
					<textarea name="content" class="boxTF" >${dto.content}</textarea>
				</td>
			</tr>
			
			<!--  
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
			</c:if> -->
		</table>
		
		<table class="table">
				<tr> 
					<td align="left" style="padding-left: 5px">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/review/review-list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
					</td>
					<td align="center">
						<button type="reset" class="btn">다시입력</button>
					</td>
					<td align="right" style="padding-right: 5px">
						<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
						<input type="hidden" name="odNum" value="${odNum}">
						<c:if test="${mode=='update'}">
							<input type="hidden" name="rNum" value="${dto.rNum}">
							<input type="hidden" name="page" value="${page}">
						</c:if>
					</td>
				</tr>
		</table>
	</form>
</div>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>