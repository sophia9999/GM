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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">


<style type="text/css">
 .img-width{
 	max-width: 200px;
 }
 
 .in-flex{
 	display: inline-flex;
 	
 }
.table tr > td:nth-child(2) {
	text-align: left;
}

</style>
<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 405) {
				alert("접근을 허용하지 않습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}
function deleteReview() {
    if(confirm("해당 리뷰를 삭제하시겠습니까 ? ")) {
	    var query = "${query}&rNum=${dto.rNum}";
	    var url = "${pageContext.request.contextPath}/review/delete.do?" + query;
    	location.href = url;
    }
}
</script>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<div class="container">
	<form method="post">
	<div class="title">
		<h3><span>|</span> 리뷰 상세보기</h3>
	</div>
		<table class="table table-border table-list">
		<colgroup>
			<col width="50%">
			<col width="50%">
		</colgroup>
			
		
		<tbody>
				<tr>
					<td colspan="2" align="center">${dto.allClothesName}</td>
				</tr>
				<tr>
					<td align="left">&nbsp&nbsp리뷰 번호 : ${dto.listNum}</td>
					<td align="right" style="text-align:right">작성자 : ${dto.userName} &nbsp&nbsp  작성일 : ${dto.r_reg_date}&nbsp&nbsp </td>
				</tr>
				<tr>
					<td colspan="2" align="left" height="200" valign="top">${dto.content}</td>
				</tr>

		</tbody>	
		</table>
		<table class="table">
			<tr>
				<td width="50%">
					<c:choose>
						<c:when test="${sessionScope.member.userId==dto.userId}">
							<button type="button" class="btn" style="float:left" onclick="location.href='${pageContext.request.contextPath}/review/updateReview.do?rNum=${dto.rNum}&page=${page}';">수정</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn" style="display:none">수정</button>
						</c:otherwise>
					</c:choose>
			    	
					<c:choose>
			    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
			    			<button type="button" class="btn" style="float:left" onclick="deleteReview();">삭제</button>
			    		</c:when>
			    		<c:otherwise>
			    			<button type="button" class="btn"style="display:none">삭제</button>
			    		</c:otherwise>
			    	</c:choose>
				</td>
				<td>
			    	<button type="button" class="btn" style="float:right" onclick="location.href='${pageContext.request.contextPath}/review/review-list.do';">리스트</button>
				
				</td>
				<!--  
				<c:if test="${sessionScope.member.userId=='admin'}"> 
				<td align="right">
					<button type="button" style="display:none" class="btn" onclick="location.href='${pageContext.request.contextPath}/review/write.do?rNum=${dto.rNum}&page=${page}';">리뷰등록</button>
				</td>
				</c:if>
				<c:if test="${sessionScope.member.userId==dto.userId}"> 
				<td align="right">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/review/write.do?rNum=${dto.rNum}&page=${page}';">리뷰등록</button>
				</td>
				</c:if>  -->
			</tr>
		</table>			
		<!--  
			<table class="table">
				
				<tr>
					
					<td align="right" >
						<button type="button" class="btn" >수정</button>
						<button type="button" class="btn" >뒤로</button>
					</td>
					
				</tr>
			</table>	-->		
	</form>
</div>

<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>