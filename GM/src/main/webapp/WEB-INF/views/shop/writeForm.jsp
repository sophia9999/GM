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
	width: 280px;
    height: 40px;
    font-size: 14px;
}
</style>
<script type="text/javascript">
function sendOk() {
	var f = document.photoForm;
	var str;
	
	str = f.clothname.value.trim();
	if(! str) {
		alert("의류명을 입력하세요.");
		f.clothname.focus();
		return;
	}
	
	str = $("select[name=type] option").index($("select[name=type] option:selected"))
	if(! str) {
		alert("분류를 선택하세요.");
		$("select[name=type]").focus();
		return;
	}
	
	str = $("select[name=displayYN] option").index($("select[name=displayYN] option:selected"));
	if(! str) {
		alert("공개여부를 선택하세요.")
		$("select[name=displayYN]").focus();
		return;
	}
	
	str = $("input[name=unitprice]").val().trim();
	if(! str) {
		alert("단가를 입력하세요.")
		$("input[name=unitprice]").focus();
		return;
	}
	
	str = $("input[name=price]").val().trim();
	if(! str) {
		alert("가격을 입력하세요.")
		$("input[name=price]").focus();
		return;
	}
	
	str = $("input[name=discount]").val().trim();
	if(! str) {
		alert("할인가 입력하세요.")
		$("input[name=discount]").focus();
		return;
	}
	
	str = $("input[name=season]:checked").length;
	if(! str) {
		alert("계절을 선택하세요.");
		$("input[name=season]").focus();
		return;
	}
	
	str = $("textarea[name=content]").val().trim();
	if(! str) {
		alert("설명을 입력해주세요.");
		$("textarea[name=content]").focus();
		return;
	}
	
	if(! confirm("${mode=='update'?'수정':'등록'}하시겠습니까 ? ")) {
		return;
	}
	
	var mode= "${mode}";
	if ( (mode === "write") && (!f.selectFile.value)){
		alert("이미지 파일을 추가 하세요. ");
        f.selectFile.focus();
        return;
	}
	
	f.action = "${pageContext.request.contextPath}/shop/garment-${mode}Submit.do";
	f.submit();
	
}

<c:if test="${mode=='update'}">
function deleteFile(fileNum) {
	if(! confirm("이미지를 삭제 하시겠습니까 ?")) {
		return;
	}
	
	var query = "num=${dto.cnum}&fileNum=" + fileNum + "&page=${page}";
	var url = "${pageContext.request.contextPath}/shop/deleteFile.do?" + query;
	location.href = url;
}
</c:if>


</script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<div class="body-container">
	<div class="title">
		<h3><span>|</span> 상품등록</h3>
	</div>
	<form name="photoForm" method="post" enctype="multipart/form-data">
		<table class="table table-border table-form">	
			<tr>
				<td>작성자</td>
				<td>${sessionScope.member.userName}</td>
			</tr>
			
			<tr>
				<td>의류명</td>
				<td>
					<input type="text" name="clothname" class="boxTF" maxlength="33" value="${dto.clothname}">
				</td>
			</tr>
			
			<tr>
				<td>분류</td>
				<td>
					<select name="type" class="btn boxTF">
						<option> :: 분류 선택 ::</option>
						<option value="top" ${dto.tnum == 1? "selected='selected'":"" }>상의</option>
						<option value="bottom" ${dto.tnum == 2? "selected='selected'":"" }>하의</option>
						<option value="outer" ${dto.tnum == 3? "selected='selected'":"" }>아우터</option>
						<option value="etc" ${dto.tnum == 4? "selected='selected'":"" }>기타</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>공개여부</td>
				<td>
					<select name="displayYN" class="btn boxTF">
						<option> :: 공개여부선택 :: </option>
						<option value="0" >No</option>
						<option value="1" >Yes</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>단가</td>
				<td>
					<input type="number" name="unitprice" class="boxTF" maxlength="10" value="${dto.unitPrice}">
				</td>
			</tr>
			
			<tr>
				<td>판매가격</td>
				<td>
					<input type="number" name="price" class="boxTF" maxlength="10" value="${dto.price}">
				</td>
			</tr>
			
			<tr>
				<td>할인가격</td>
				<td>
					<input type="number" name="discount" class="boxTF" maxlength="10" value="${dto.discount}">
				</td>
			</tr>
			
			<tr>
				<td>계절</td>
				<td>
					<input type="checkbox" name="season" value="봄">봄
					<input type="checkbox" name="season" value="여름">여름
					<input type="checkbox" name="season" value="가을">가을
					<input type="checkbox" name="season" value="겨울">겨울
				</td>
			</tr>
			
			<tr>
				<td valign="top">설명</td>
				<td>
					<textarea name="content" class="boxTF" >${dto.ccoment}</textarea>
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
							<c:forEach var="vo" items="${listFile}">
									<img src="${pageContext.request.contextPath}/uploads/photo/${vo.imageFilename}"
										onclick="deleteFile('${vo.fileNum}');">
							</c:forEach>
						</div>
					</td>
				</tr>
			</c:if>
		</table>
		
		<table class="table">
				<tr> 
					<td align="left" style="padding-left: 5px">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/shop/garment.do';">${mode=='update'?'수정취소':'등록취소'}</button>
					</td>
					<td align="center">
						<button type="reset" class="btn">다시입력</button>
					</td>
					<td align="right" style="padding-right: 5px">
						<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정하기':'등록하기'}</button>
					</td>
					<c:if test="${mode=='update'}">
						<input type="hidden" name="num" value="${dto.cnum}">
						<input type="hidden" name="page" value="${page}">
					</c:if>
				</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
$(function() {

	if("${dto.season}".indexOf("봄") !== -1){
		$('input[value="봄"]').prop("checked", true);
	} else if("${dto.season}".indexOf("여름") !== -1){
		$('input[value="여름"]').prop("checked", true);
	} else if("${dto.season}".indexOf("가을") !== -1){
		$('input[value="가을"]').prop("checked", true);
	} else if("${dto.season}".indexOf("겨울") !== -1){
		$('input[value="겨울"]').prop("checked", true);
	}
	
});
</script>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>