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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">


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

</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<div class="orderCheck-container">
	<form method="post">
	<div class="title">
		<h3><span>|</span> 주문하기</h3>
	</div>
		<table class="table table-cart table-border table-form ">
			<tr>
				<th>상품</th>
				<th>상품명</th>
				<th>수량</th>
				<th>가격</th>
			</tr>
			<tr> <!-- 선택한 상품들 나오는 곳-->
				<td><p> <img src="#" class="img"></p></td>
				<td>네이비 리본 프릴 원피스</td>
				<td style="text-align: center;">1</td>
				<td style="text-align: right;">58,000원</td>
			</tr>
		</table>
	<table class="table">
			<tr>
				<td align="right">
					<span class="msg-box">총 주문가격 : 58,000원, 총 결제가격 : 58,000원(+배송비)</span>
					
				</td>
			</tr>
	</table>
	</form>
</div>

<div class="order-container">
	<form method="post">
		<div class="title">
			<h3><span>|</span> 배송정보</h3>
		</div>
		<table class="table table-order table-border">
			<tr>
				<td>결제고객 연락처</td>
				<td>
					  <select name="tel1" class="selectField" >
							<option value="">선 택</option>
							<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
							<option value="02"  ${dto.tel1=="02"  ? "selected='selected'" : ""}>02</option>
							<option value="031" ${dto.tel1=="031" ? "selected='selected'" : ""}>031</option>
							<option value="032" ${dto.tel1=="032" ? "selected='selected'" : ""}>032</option>
							<option value="033" ${dto.tel1=="033" ? "selected='selected'" : ""}>033</option>
							<option value="041" ${dto.tel1=="041" ? "selected='selected'" : ""}>041</option>
							<option value="042" ${dto.tel1=="042" ? "selected='selected'" : ""}>042</option>
							<option value="043" ${dto.tel1=="043" ? "selected='selected'" : ""}>043</option>
							<option value="044" ${dto.tel1=="044" ? "selected='selected'" : ""}>044</option>
							<option value="051" ${dto.tel1=="051" ? "selected='selected'" : ""}>051</option>
							<option value="052" ${dto.tel1=="052" ? "selected='selected'" : ""}>052</option>
							<option value="053" ${dto.tel1=="053" ? "selected='selected'" : ""}>053</option>
							<option value="054" ${dto.tel1=="054" ? "selected='selected'" : ""}>054</option>
							<option value="055" ${dto.tel1=="055" ? "selected='selected'" : ""}>055</option>
							<option value="061" ${dto.tel1=="061" ? "selected='selected'" : ""}>061</option>
							<option value="062" ${dto.tel1=="062" ? "selected='selected'" : ""}>062</option>
							<option value="063" ${dto.tel1=="063" ? "selected='selected'" : ""}>063</option>
							<option value="064" ${dto.tel1=="064" ? "selected='selected'" : ""}>064</option>
							<option value="070" ${dto.tel1=="070" ? "selected='selected'" : ""}>070</option>
					  </select>
					  <input type="text" name="tel2" maxlength="4" class="boxTF" value="${dto.tel2}" style="width: 15%;"> -
					  <input type="text" name="tel3" maxlength="4" class="boxTF" value="${dto.tel3}" style="width: 15%;">
				</td>
			</tr>
			<tr>
				<td>수 취 인</td>
				<td>
					<input type="text" name="name" class="boxTF" >
				</td>
			</tr>
			
			<tr>
				<td>수취인연락처</td>
				<td>
					  <select name="tel1" class="selectField">
							<option value="">선 택</option>
							<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
							<option value="02"  ${dto.tel1=="02"  ? "selected='selected'" : ""}>02</option>
							<option value="031" ${dto.tel1=="031" ? "selected='selected'" : ""}>031</option>
							<option value="032" ${dto.tel1=="032" ? "selected='selected'" : ""}>032</option>
							<option value="033" ${dto.tel1=="033" ? "selected='selected'" : ""}>033</option>
							<option value="041" ${dto.tel1=="041" ? "selected='selected'" : ""}>041</option>
							<option value="042" ${dto.tel1=="042" ? "selected='selected'" : ""}>042</option>
							<option value="043" ${dto.tel1=="043" ? "selected='selected'" : ""}>043</option>
							<option value="044" ${dto.tel1=="044" ? "selected='selected'" : ""}>044</option>
							<option value="051" ${dto.tel1=="051" ? "selected='selected'" : ""}>051</option>
							<option value="052" ${dto.tel1=="052" ? "selected='selected'" : ""}>052</option>
							<option value="053" ${dto.tel1=="053" ? "selected='selected'" : ""}>053</option>
							<option value="054" ${dto.tel1=="054" ? "selected='selected'" : ""}>054</option>
							<option value="055" ${dto.tel1=="055" ? "selected='selected'" : ""}>055</option>
							<option value="061" ${dto.tel1=="061" ? "selected='selected'" : ""}>061</option>
							<option value="062" ${dto.tel1=="062" ? "selected='selected'" : ""}>062</option>
							<option value="063" ${dto.tel1=="063" ? "selected='selected'" : ""}>063</option>
							<option value="064" ${dto.tel1=="064" ? "selected='selected'" : ""}>064</option>
							<option value="070" ${dto.tel1=="070" ? "selected='selected'" : ""}>070</option>
					  </select>
					  <input type="text" name="tel2" maxlength="4" class="boxTF" value="${dto.tel2}" style="width: 15%;"> -
					  <input type="text" name="tel3" maxlength="4" class="boxTF" value="${dto.tel3}" style="width: 15%;">
				</td>
			</tr>
			<tr>
				<td>우편번호</td>
				<td>
					<input type="text" name="zip" id="zip" maxlength="7" class="boxTF" value="${dto.zip}" readonly="readonly" style="width: 30%;">
					<button type="button" class="btn" onclick="daumPostcode();">우편번호검색</button>
				</td>
			</tr>
			
			<tr>
				<td valign="top">배 송 지</td>
				<td>
					<p>
						<input type="text" name="addr1" id="addr1" maxlength="50" class="boxTF" value="${dto.addr1}" readonly="readonly" style="width: 96%;">
					</p>
					<p class="block">
						<input type="text" name="addr2" id="addr2" maxlength="50" class="boxTF" value="${dto.addr2}" style="width: 96%;">
					</p>
				</td>
			</tr>
			<tr>
				<td>배송 요청사항</td>
				<td><input type="text" class="boxTF" style="width: 96%;"></td>
			</tr>
			<tr>
				<td>결제</td>
				<td>결제는 카드결제만가능 여기는 결제하는 것을 어떻게 짜야할지 코딩하면서 생각해봅시다</td>
			</tr>
			
		</table>
		
		<div class="buttons">
		<button type="button" class="btn" style="float:left">주문취소</button>
		<button type="reset" class="btn"style="float:left">다시입력</button>		
		<button type="button" class="btn" style="float:right">주문하기</button>
		</div>
		<!-- 
		<table class="table">
				<tr> 
					<td align="left" style="padding-left: 5px">
						<button type="button" class="btn">주문취소</button>
					</td>
					<td align="center">
						<button type="reset" class="btn">다시입력</button>
					</td>
					<td align="right" style="padding-right: 5px">
						<button type="button" class="btn">주문하기</button>
					</td>
				</tr>
		</table>  -->	
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