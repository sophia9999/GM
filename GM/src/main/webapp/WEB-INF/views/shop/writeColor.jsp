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
.img-box {
	max-width: 600px;
	padding: 5px;
	box-sizing: border-box;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}
.img-box img {
	width: 37px; height: 37px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}
.btn {
	width: 100px;
    height: 40px;
    font-size: 14px;
}

.table tr td {
	color: #000;
}

.table-form tr th {
	text-align: center;
}

.table-form tr th:first-child {
	width: 70px;
	text-align: center;
}

.table-list {
	width: 500px;
	margin: 0 auto;
}

.btn {
	display: block;
	margin: 0 600px;
}

.title {
	text-align: center;
}

.box1 {
    display: inline;
}

.btn1 {
	color: #333;
	border: 1px solid #333;
	background-color: #fff;
	padding: 4px 10px;
	font-weight: 300;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
	margin : 0 5px;
}

.table-detail tr td {
	text-align: left;
}

.page-box {
	margin-top: 0;
}

.btnList {
	width: 300px;
    background: #000;
    color: #fff;
    margin: 15px 300px;
}
</style>
<script type="text/javascript">
function addBtn() {
	var $tr = $("#box1").closest("tr").clone(false);
	$tr.find("input").val("");
	$(".table-detail").after().append($tr);
}

function addDetail() {
	var f = document.form;
	var str;
	
	str = $("input[name=color]").val().trim();
	if(! str) {
		alert("색상을 입력하세요.");
		f.color.focus();
		return;
	}
	
	str = $("input[name=size]").val().trim();
	if(! str) {
		alert("사이즈를 입력하세요.");
		f.size.focus();
		return;
	}
	
	if(! confirm("등록하시겠습니까 ? ")) {
		return;
	}
}
</script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<div class="body-container">
	<div class="title">
		<h3><span>|</span> ${dto.clothname} 색상/사이즈 현황</h3>
		<form name="formDelete" method="post">
			<table class="table table-border table-form table-list">
				<tr>
					<th><input type="checkbox" class="chkAll"></th>
					<th>색상</th>
					<th>사이즈</th>
				</tr>
				<c:forEach var="colordto" items="${colorList}" varStatus="status">
					<tr>
						<td><input type="checkbox" class="chk"></td>
						<td style="text-align: left;">${colordto.color}</td>
						<td style="text-align: left;">${colordto.size}</td>
					</tr>
				</c:forEach>
			</table>
			<table class="table">
				<tr>
					<td>
						<button type="button" class="btn btnDelete">삭제</button>
					</td>
				</tr>
			</table>
			<div class="page-box">
			${dataCount == 0 ? "등록된 색상이 없습니다." : paging}
			</div>
		</form>
	</div>
	
	
	<div class="title">
		<h3><span>|</span> 색상/사이즈 추가</h3>
	</div>
	<form name="form" method="post">
		<table class="table table-border table-form table-list table-detail">
			<tr>
				<th>의류명</th>
				<td>
					<input type="text" name="clothname" class="boxTF" maxlength="33" value="${dto.clothname}" 
					readonly="readonly" style="background-color: #eee">
				</td>
			</tr>
			<tr>
				<th>색상</th>
				<td style="text-align: left;">
					<input type="text" name="color" class="boxTF box1" style="width: 150px;">
				</td>
			</tr>
			<tr>
				<th>사이즈</th>
				<td style="text-align: left;">
					<input type="text" class="boxTF box1" id="box1" name="size" style="width: 150px;">
					<button type="button" class="btn1" onclick="addBtn()">추가</button>
				</td>
			</tr>
		</table>
		
		<table class="table">
				<tr> 
					<td>
						<button type="button" class="btn" onclick="addDetail()">등록</button>
					</td>
				</tr>
				<tr>
					<td>
						<button type="button" class="btn btnList" onclick="location.href='${pageContext.request.contextPath}/shop/garment.do';">리스트</button>
					</td>
				</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
$(function () {
	$(".chkAll").click( function() {
		$(".chk").prop("checked", true);
	});
});
</script>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>