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
	
	str = $("input[name=stock]").val().trim();
	if(! str || str < 1) {
		alert("수량을 입력하세요.");
		f.size.focus();
		return;
	}
	
	if(! confirm("등록하시겠습니까 ? ")) {
		return;
	}
	
	
	if(${mode=='write'}) {
		f.action = "${pageContext.request.contextPath}/shop/garment-detailwrite.do";
		f.submit();
	} else if(${mode=='update'}) {
		f.action = "${pageContext.request.contextPath}/shop/garment-detailupdateSubmit.do";
		f.submit();	
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
	</div>
	
	<table class="table">
			<tr>
				<td width="50%">
					${dataCount}개(${page}/${total_page} 페이지)
				</td>
				<td align="right">&nbsp;</td>
			</tr>
		</table>
	
	<div>
		<form name="formUpdate" method="post">
			<table class="table table-border table-form table-list">
				<tr>
					<th><input type="checkbox" class="chkAll"></th>
					<th>색상</th>
					<th>사이즈</th>
					<th>재고</th>
				</tr>
				<c:forEach var="colordto" items="${colorList}" varStatus="status">
					<tr>
						<td>
							<input type="checkbox" class="chk" name="cdnum" value="${colordto.cdnum}">
							<input type="hidden" class="ccnum" name="ccnum" value="${colordto.ccnum}">
							<input type="hidden" class="cnum" name="cnum" value="${colordto.cnum}">
						</td>
						<td style="text-align: left;">${colordto.color}</td>
						<td style="text-align: left;">${colordto.size}</td>
						<td style="text-align: left;">${colordto.stock}</td>
					</tr>
				</c:forEach>
			</table>
			<table class="table">
				<tr>
					<td>
						<button type="button" class="btn btnUpdate" onclick="updateSubmit()">수정</button>
					</td>
				</tr>
			</table>
			<div class="page-box">
			${dataCount == 0 ? "등록된 색상이 없습니다." : paging}
			</div>
		</form>
	</div>
	
	
	<div class="title">
		<h3><span>|</span> 색상/사이즈 ${mode=='update'?'수정':'추가'} </h3>
	</div>
	<form name="form" method="post">
		<table class="table table-border table-form table-list table-detail">
			<tr>
				<th>의류명</th>
				<td>
					<input type="text" name="clothname" class="boxTF" maxlength="33" value="${dto.clothname}${vo.clothname}" 
					readonly="readonly" style="background-color: #eee">
					<input type="hidden" name="cnum" value="${dto.cnum}">
					<input type="hidden" name="cdnum" value="${dto.cdnum}">
					<input type="hidden" name="ccnum" value="${dto.ccnum}">
				</td>
			</tr>
			<tr>
				<th>색상</th>
				<td style="text-align: left;">
					<input type="text" name="color" class="boxTF box1" style="width: 150px;" value="${vo.color}">
				</td>
			</tr>
			<tr>
				<th>사이즈</th>
				<td style="text-align: left;">
					<input type="text" class="boxTF box1" id="box1" name="size" style="width: 150px;" value="${vo.size}">
				</td>
			</tr>
			<tr>
				<th>재고</th>
				<td style="text-align: left;">
					<input type="number" class="boxTF"  name="stock" style="width: 150px;" value="${vo.stock}">
				</td>
			</tr>
		</table>
		
		<table class="table">
				<tr> 
					<td>
						<button type="button" class="btn" onclick="addDetail()">${mode=='update'?'수정완료':'추가'}</button>
					</td>
				</tr>
				<tr>
					<td>
						<button type="button" class="btn btnList" onclick="location.href='${pageContext.request.contextPath}/shop/garment-article.do?num=${dto.cnum}&page=${prePage}';">리스트</button>
					</td>
				</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
$(function () {
	$(".chkAll").click( function() {
		alert("수정은 한 개만 가능합니다.")
		$(".chkAll").prop("checked", false);
	});
});

function updateSubmit() {
	var f = document.formUpdate;
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
	
	if(! confirm("수정하시겠습니까 ? ")) {
		return;
	}
	f.action = "${pageContext.request.contextPath}/shop/garment-detailupdate.do";
	f.submit();
}

</script>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>