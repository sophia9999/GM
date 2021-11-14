<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문</title>
<!-- iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
  
<link rel="icon" href="data:;base64,iVBORw0KGgo=">   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/cart_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">

</head>
<body>
<div class="cart-container">
	<header>
	    <jsp:include page="../layout/header.jsp"></jsp:include>
	</header>
	<div class="title">
		<h3><span>|</span> order</h3>
	</div>      
	<table>
		<tr>
			<td align="left">상품개수(${orderCount})</td>
		</tr>
	</table>
	<form name="orderForm">
		<table class="table table-cart table-border">		
			<tr style="background: #eee; padding: 10px 0;">			
				<th>상품</th>
				<th>상품명</th>
				<th>사이즈/컬러</th>
				<th>수량</th>
				<th>가격</th>
			</tr>
				
			<c:forEach var="dto" items="${orderList}">				
				<tr>
					<td style="width: 200px;"><img class="img" src="${pageContext.request.contextPath}/uploads/photo/${dto.fileName}"></td>
					<td class="clothName">${dto.clothName}</td>
					
					<td>${dto.size}/${dto.color}
					<td style="text-align: center;">
					${dto.amount}
					</td>
					<td class="price" style="text-align: center;">${(dto.price-dto.discount) * dto.amount}원
					<input type="hidden" name="cartNum" value="${dto.cartNum}">
					<input type="hidden" name="cdNum" value="${dto.clothDetailNum}">
					<input type="hidden" name="amount" value="${dto.amount}">
					
					</td>					
				</tr>
			</c:forEach>
		</table>
	
	<br><br>
	<hr>
	<div class="title">
		<h3><span>|</span> 배송 정보</h3>
	</div>
	
	<table class="table table-border order-table-form">
		<tr>
			<td>배송지 선택</td>
			<td>
				<input type="radio" name="delivery" value="0" checked><span>회원정보와 동일</span>
				<input type="radio" name="delivery" value="1"><span>새로운 배송지</span>
			</td>
		</tr>
		<tr>
			<td>받으시는 분</td>
			<td>
				<input type="text" name="userName" id="userName" maxlength="10" class="boxTF" value="${dto.userName}" style="width: 50%;">
			</td>
		</tr>
	
		<tr>
			<td>이 메 일</td>
			<td>
				  <select name="selectEmail" id="selectEmail" class="selectField" onchange="changeEmail();">
						<option value="">선 택</option>
						<option value="naver.com"   ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
						<option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
						<option value="gmail.com"   ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
						<option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
						<option value="direct">직접입력</option>
				  </select>
				  <input type="text" name="email1" id="email1" maxlength="30" class="boxTF" value="${dto.email1}" style="width: 33%;"> @ 
				  <input type="text" name="email2" id="email2" maxlength="30" class="boxTF" value="${dto.email2}" style="width: 33%;" readonly="readonly">
			</td>
		</tr>
			
		<tr>
			<td>전화번호</td>
			<td>
				  <select name="tel1" id="tel1" class="selectField">
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
				  <input type="text" name="tel2" id="tel2" maxlength="4" class="boxTF" value="${dto.tel2}" style="width: 33%;"> -
				  <input type="text" name="tel3" id="tel3" maxlength="4" class="boxTF" value="${dto.tel3}" style="width: 33%;">
				  
			</td>
		</tr>
					<tr>
			<td>우편번호</td>
			<td>
				<input type="text" name="zip" id="zip" maxlength="7" class="boxTF" value="${dto.code}" readonly="readonly" style="width: 50%;">
				<button type="button" class="btn" onclick="daumPostcode();">우편번호검색</button>
			</td>
		</tr>
		
		<tr>
			<td valign="top">주&nbsp;&nbsp;&nbsp;&nbsp;소</td>
			<td>
				<p>
					<input type="text" name="addr1" id="addr1" maxlength="50" class="boxTF" value="${dto.address}" readonly="readonly" style="width: 96%;">
				</p>
				<p class="block">
					<input type="text" name="addr2" id="addr2" maxlength="50" class="boxTF" value="${dto.address_detail}" style="width: 96%;">
				</p>
			</td>
		</tr>
		
		<tr>
			<td>배송메시지</td>
			<td>
				<textarea name="request" id="request"></textarea>
			</td>
		</table>
		
		<div class="title">
			<h3><span>|</span> 결제 예정 금액</h3>
		</div>
		<table class="table table-pay table-border">
		
		<tr style="background: #eee;">
			<th>
				<span>총 상품금액</span>
			</th>
			<th>총 배송비</th>
			<th>총 할인금액</th>
			<th>결제예정금액</th>
		</tr>
		<tr style="height: 50px;">
			<td>
				<div><strong >${total_price}</strong>원</div>
				
			</td>
			<td>
				<div>
					<strong>+</strong><strong>${fee}</strong>원
					
				</div>
			</td>
			<td>
				<div>
					<strong>-</strong><strong>${disCount}</strong>원
				</div>
			</td>
			<td>
				<div>
					<strong>=</strong><strong>${total_price + fee - disCount}</strong>원
				</div>
				
				<input type="hidden" name="total" value="${total_price}">
				<input type="hidden" name="fee" value="${fee}">
				
			</td>
		</tr>
	</table>
	</form>
	<button type="button" class="btn" id="btnPay" style="float:center; height:60px; width: 300px; font-size: 18px;" 
				onclick="requestPay()">${total_price + fee}원 결제하기</button>
	</div>
	
	<footer>
		<jsp:include page="../layout/footer.jsp"></jsp:include>
	</footer>
</body>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript"> 

$("input:radio[name=delivery]").click(function(){
	if($("input:radio[name=delivery]:checked").val()=='0'){
		$("#userName").val("${dto.userName}");
		$("#selectEmail").val("${dto.email2}");
		$("#email1").val("${dto.email1}");
		$("#email2").val("${dto.email2}");
		$("#tel1").val("${dto.tel1}");
		$("#tel2").val("${dto.tel2}");
		$("#tel3").val("${dto.tel3}");
		$("#zip").val("${dto.code}");
		$("#addr1").val("${dto.address}");
		$("#addr2").val("${dto.address_detail}");
	} else {
		$("#userName").val("");
		$("#selectEmail").val("");
		$("#email1").val("");
		$("#email2").val("");
		$("#tel1").val("");
		$("#tel2").val("");
		$("#tel3").val("");
		$("#zip").val("");
		$("#addr1").val("");
		$("#addr2").val("");
	}
});

function changeEmail() {
    var f = document.memberForm;
	    
    var str = f.selectEmail.value;
    if(str!="direct") {
        f.email2.value=str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    }
    else {
        f.email2.value="";
        f.email2.readOnly = false;
        f.email1.focus();
    }
}

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

IMP.init('imp88854768');
function requestPay() {
	var f = document.orderForm;
	var str;
	
    str = f.userName.value;
    if( !/^[가-힣]{2,5}$/.test(str) ) {
        alert("이름을 다시 입력하세요. ");
        f.userName.focus();
        return;
    }
    
    str = f.tel1.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }

    str = f.tel2.value;
    if( !/^\d{3,4}$/.test(str) ) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    str = f.tel3.value;
    if( !/^\d{4}$/.test(str) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }
	/*
    var msg = '결제에 성공하였습니다.';
	// var f = document.orderForm;
	
	f.action="${pageContext.request.contextPath}/cart/order_ok.do";
	f.submit();
	
	alert(msg);
	*/
	
	IMP.request_pay({
	    pg : 'inicis', // version 1.1.0부터 지원.
	    pay_method : 'card',
	    merchant_uid : 'merchant_' + new Date().getTime(),
	    /*
	    if(${orderCount} != 1){
	    	name : $(".clothName").text() + " 외 ${orderCount-1}개",
	    } else { 	
		    name : $(".clothName").text(),
	    }
		*/
		name : $(".clothName").text() + " 외 ${orderCount-1}개",
	    amount : 100, // 임시금액
	    // amount : "${total_price + fee - disCount}", //판매 가격
	    buyer_email : "${dto.email1}@${dto.email2}",
	    buyer_name : $("#userName").val(),
	    buyer_tel : $("#tel1").val()-$("#tel2").val()-$("#tel3").val(),
	    buyer_addr : $("#addr1").val(),
	    buyer_postcode : $("#zip").val()
	}, function(rsp) {
	    if ( rsp.success ) {
	    	var msg = '결제에 성공하였습니다.';
	    	// var f = document.orderForm;
	    	f.action="${pageContext.request.contextPath}/cart/order_ok.do";
	    	f.submit();
	    } else {
	        var msg = '결제에 실패하였습니다.';
	        msg += '\n실패사유 : ' + rsp.error_msg;
	    }
	    alert(msg);
	});
}
</script>
</html>