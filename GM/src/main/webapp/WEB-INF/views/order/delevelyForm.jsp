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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_hg.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">

<style type="text/css">
.orderCheck-container {
	width: 900px;  margin: 150px auto 0;
	border-radius: 10px;
 	display: grid;
 	grid-template:repeat(4, auto) / repeat(3, auto);
 	gap:15px;
}

.table-form tr > td:first-child, .table-form tr > th:first-child {
	text-align: center;
	background: #fff;
	width: 150px;
}

.table-form .img {
	max-width:100%; height:auto; resize:both;
}

.table-cart tr:first-child > td {
	text-align: center;
	background: #eee;
}

.table-form tr > td:nth-child(2) {
	text-align: center;
	padding-left: 0;
	height: 150px;
	width: 500px;
}

.msg-box {
	text-align: center; color: black;
	font-weight: bold; font-size: 15px;
}

.order-container {
	width: 900px;  margin: 0px auto 70px;
	border-radius: 10px;
 	display: grid;
 	grid-template:repeat(4, auto) / repeat(3, auto);
 	gap:15px;
}
.table-order tr > td:nth-child(2) {
	text-align: left;
}
</style>

<script type="text/javascript">
function sendOk() {
	var f = document.delevelyForm;
	
	var mode = "${empty dto.deNum ? 'insert':'update'}";
	if(mode == "insert") {
		f.action="${pageContext.request.contextPath}/order/delevely_ok.do";
	} else {
		f.action="${pageContext.request.contextPath}/order/delevely_update.do";
	}
			
	f.submit();
}
</script>

</head>

<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<div class="orderCheck-container">
	<div class="title">
		<h3><span>|</span> 배송관리</h3>
	</div>
</div>

<div class="order-container">
	<form name="delevelyForm" method="post">

		<table class="table table-order table-border">
			<tr>
				<td>상품명</td>
				<td>
					  ${clothName}
				</td>
			</tr>
		
			<tr>
				<td>운송장 번호</td>
				<td>
					  <input type="text" name="deNum" value="${dto.deNum}" ${empty dto.deNum ? "":"readonly='readonly'" }>
				</td>
			</tr>
			<tr>
				<td>배송상태</td>
				<td>
					<select name="state">
						<option value="배송준비" ${dto.state=="배송준비"?"selected='selected'":""}>배송준비</option>
						<option value="배송사전달" ${dto.state=="배송사전달"?"selected='selected'":""}>배송사전달</option>
						<option value="배송중" ${dto.state=="배송중"?"selected='selected'":""}>배송중</option>
						<option value="배달완료" ${dto.state=="배달완료"?"selected='selected'":""}>배달완료</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>보낸날짜</td>
				<td>
					  <input type="date" name="senddate" value="${dto.sendDate}">
				</td>
			</tr>
			<tr>
				<td>받는 날짜</td>
				<td>
					  <input type="date" name="arrivedate" value="${dto.arriveDate}">
				</td>
			</tr>
			
		</table>
		
		<div class="buttons">
		<input type="hidden" name="odNum" value="${odNum}">
		<c:if test="${empty dto.deNum || dto.state != '배달완료' }">
			<button type="button" class="btn" style="float:left" onclick="sendOk();">${empty dto.deNum ? "등록하기":"수정완료"}</button>
		</c:if>
		<button type="button" class="btn" style="float:right" onclick="location.href='${pageContext.request.contextPath}/order/order.do';">뒤로가기</button>
		</div>
		
	</form>
</div>

<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>

</footer>


<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();
            }
        }).open();
    }
</script>
</body>

</html>