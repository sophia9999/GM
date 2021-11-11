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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
<style type="text/css">
.body-container {
	width: 900px;  margin: 150px auto 70px;
}

.table {
	width: 500px;
	margin: 10px auto;
}

.title {
	width: 500px;
	margin: 10px auto;
}

.btn {
	width: 100px;
    height: 40px;
    font-size: 14px;
}

.buttons {
	width: 100%;
	margin: 10px auto;
}

.btnTable {
	width: 100%;
}

.btnList {
	width: 300px;
    background: #000;
    color: #fff;
    margin: 15px 300px;
}

.page-box {
	margin-top: 0;
}

.table tr td {
	color: #000;
}
</style>
<script type="text/javascript">
function addColor() {
	var f = document.form;
	
	if(! $("input[name=color]").val().trim() ) {
		alert("색상을 입력하세요.");
		return;
	}
	
	if(! confirm("${mode=='update'?'수정':'추가'}하시겠습니까 ? ")) {
		return;
	}
	f.action = "${pageContext.request.contextPath}/shop/garment-color${mode}Submit.do";
	f.submit();
}

function updateColor() {
	var f = document.colorForm;
	var $chk =  $("input[name=ccnum]:checked").length;
	console.log($chk);
	if (! $chk){
		alert("수정할 항목을 선택하세요.");
		return;
	}
	if($chk != 1) {
		alert("하나의 항목을 선택하세요.");
		return;
	}

	f.action = "${pageContext.request.contextPath}/shop/garment-colorwrite.do";
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
		<h3><span>|</span> 색상목록</h3>
	</div>
	<form name="colorForm" method="post">
	<table class="table">
			<tr>
				<td width="50%" style="text-align: left;">
					${dataCount}개(${page}/${total_page} 페이지)
				</td>
				<td align="right">&nbsp;</td>
			</tr>
	</table>
	<table class="table table-border table-form">
		<tr>
			<th>
				<input type="checkbox">
			</th>
			<th>색상</th>
		</tr>
		
		<c:forEach var="colordto" items="${colorList}" varStatus="status">
			<tr>
				<td style="background: #fff;">
					<input type="checkbox" name="ccnum" value="${colordto.ccnum}">
					<input type="hidden"name="cnum" value="${colordto.cnum}">
				</td>
				<td>${colordto.color}</td>
			</tr>
		</c:forEach>
	</table>
	<table class="btnTable">
			<tr>
				<td>
					<button type="button" class="btn" onclick="updateColor()">색상수정</button>
				</td>
			</tr>
	</table>
	</form>	
	<div class="page-box">
		${dataCount == 0 ? "등록된 색상이 없습니다." : paging}
	</div>
	
	<div class="title">
		<h3><span>|</span> 색상${mode=='update'?'수정':'추가'}</h3>
	</div>
	<div>
		<form name="form" method="post">
			<table class="table table-border table-form">
				<tr>
					<th>색상</th>
					<td>
						<input type="text" class="boxTF" name="color" value="${dto.color}">
						<input type="hidden" name="ccnum" value="${dto.ccnum}">
						<input type="hidden" name="cnum" value="${dto.cnum}">
						<input type="hidden" name="num" value="${cnum}">
					</td>
				</tr>
			</table>
		<div class="buttons">
			<table class="btnTable">
				<tr>
					<td>
						<button type="button" class="btn" onclick="addColor()">${mode=='update'?'수정완료':'추가'}</button>
					</td>
				</tr>
				<tr>
					<td>
						<button type="button" class="btn btnList" onclick="location.href='${pageContext.request.contextPath}/shop/garment-detail.do?num=${cnum}';">뒤로가기</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
	</div>
</div>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>