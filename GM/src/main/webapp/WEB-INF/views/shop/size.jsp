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

function updateSize() {
	var f = document.sizeForm;
	var $chk =  $("input[name=cdnum]:checked").length;
	console.log($chk);
	if (! $chk){
		alert("수정할 항목을 선택하세요.");
		return;
	}
	if($chk != 1) {
		alert("하나의 항목을 선택하세요.");
		return;
	}

	f.action = "${pageContext.request.contextPath}/shop/garment-sizewrite.do";
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
		<h3><span>|</span> 사이즈 목록</h3>
	</div>
	<form method="post" name="sizeForm">
	<table class="table table-border table-form">
		<tr>
			<th>
				<input type="checkbox" name="chkAll">
			</th>
			<th>컬러</th>
			<th>사이즈</th>
			<th>재고</th>
		</tr>
		<c:forEach varStatus="status" var="dto" items="${list}">
			<tr>
				<td>
					<input type="checkbox" name="cdnum" value="${dto.cdnum}">
					<input type="hidden" name="cnum" value="${dto.cnum}">
					<input type="hidden"name="ccnum" value="${dto.ccnum}">
				</td>
				<td>${dto.color}</td>
				<td>${dto.size}</td>
				<td>${dto.stock}</td>
			</tr>
		</c:forEach>
	</table>
	<table class="btnTable">
			<tr>
				<td>
					<button type="button" class="btn" onclick="updateSize()">사이즈수정</button>
				</td>
			</tr>
	</table>
	</form>
	<div class="page-box">
		${dataCount == 0 ? "등록된 사이즈가 없습니다." : paging}
	</div>
	
	<div class="title">
		<h3><span>|</span> 사이즈${mode=='update'?'수정':'추가'}</h3>
	</div>
	<div>
		<form name="form" method="post">
			<table class="table table-border table-form">
			
				<tr>
					<th>&nbsp;</th>
					<c:choose>
					<c:when test="${mode=='update'}">
						<td>
							<p class="colorName">${updateDto.color}</p>
							<input type="hidden" value="${updateDto.ccnum}">
						</td>
					</c:when>
					<c:otherwise>
						<td style="text-align: left;">
							<select id="selectBox" style="width: 96%" class="boxTF" name="ccnum">
								<option value="">:: 사이즈 ${mode == 'update'?'수정':'추가'}할 색상선택</option>
								<c:forEach varStatus="status" var="dto1" items="${realColorList}">
									<option value="${dto1.ccnum}">${dto1.color}</option>
								</c:forEach>
							</select>
						</td>
					</c:otherwise>
					</c:choose>
				</tr>
	
				<tr>
					<th>사이즈</th>
					<td style="text-align: left;">
						<input type="text" class="boxTF" name="size" value="${updateDto.size}">
						<input type="hidden" name="cdnum" value="${updateDto.cdnum}">
						<input type="hidden" name="cnum" value="${cnum}">
					</td>
				</tr>
				<tr>
					<th>재고</th>
					<td style="text-align: left;">
						<input type="number" style="width: 96%" class="boxTF" name="stock" value="${updateDto.stock}">
					</td>
				</tr>
			</table>
		<div class="buttons">
			<table class="btnTable">
				<tr>
					<td>
						<button type="button" class="btn" onclick="addSize()">${mode=='update'?'수정완료':'추가'}</button>
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
<script type="text/javascript">
function addSize() {
	var f = document.form;

	
	if( '${mode}'=='write') {
		if(! $("#selectBox option:selected").val() ) {
			alert("색상을 선택하세요");
			return;
		} 
	}
	
	
	if(! $("input[name=size]").val().trim() ) {
		alert("사이즈를 입력하세요.");
		return;
	}
	
	if(! $("input[name=stock]").val().trim() ) {
		alert("재고를 입력하세요.");
		return;
	}
	
	if(! confirm("${mode=='update'?'수정':'추가'}하시겠습니까 ? ")) {
		return;
	}
	f.action = "${pageContext.request.contextPath}/shop/garment-size${mode}Submit.do";
	f.submit();
}

</script>

</body>
</html>