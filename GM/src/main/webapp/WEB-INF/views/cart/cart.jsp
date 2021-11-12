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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/cart_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style_hg.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">

<style type="text/css">
.cart {
    border: 1px solid #777;
    color: #000;
    line-height: 1.5;
}
</style>

</head>
<body>
<div class="cart-container">
	<header>
	    <jsp:include page="../layout/header.jsp"></jsp:include>
	</header>
	<div class="title">
		<h3><span>|</span> 장바구니</h3>
	</div>
	
	
	<c:choose>
        <c:when test="${dataCount == 0}">
            장바구니에 등록된 상품이 없습니다.
        </c:when>
        <c:otherwise>
			<table>
				<tr>
					<td align="left">상품개수(${dataCount})</td>
				</tr>
			</table>
			<form name="cartForm" method="post" action="${pageContext.request.contextPath}/cart/change.do">
			<table class="table table-cart table-border table-form">		
			
				<tr>
					<th style="width: 3px;"><input type="checkbox" id="checkAll"></th>
					<th>상품</th>
					<th>상품명</th>
					<th>사이즈/컬러</th>
					<th>수량</th>
					<th>가격</th>
					<th>삭제</th>
				</tr>
				
				<c:forEach var="dto" items="${list}">				
					<tr>						
						<td><input type="checkbox" name="box"></td>
						<td><p> <img src="#" class="img"></p></td>
						<td>${dto.clothName}</td>
						<td>${dto.size}/${dto.color}
						<td style="text-align: center;">
							<input type="text" class="spinner" name="amount" value="${dto.amount}" style="width: 20px;"><br>
							<button type="submit" class="btn" style="float:center; 
									width:40px; height:25px; margin:5px 0 0 0; padding: 0; font-size: 13px; border-radius: 3px;">변경</button>
						<input type="hidden" name="cartNum" value="${dto.cartNum}">						
						</td>
						<td style="text-align: center;">${dto.price * dto.amount}원</td>
						<td><button type="button" class="btn" name="delete_btn" onclick="location.href='${pageContext.request.contextPath}/cart/delete.do?cartNum=${dto.cartNum}';">삭제</button></td>
					</tr>
				</c:forEach>
			</table>
			<table class="table table-pay table-border">
				<tr style="background: #eee;">
					<th>
						<span>총 상품금액</span>
					</th>
					<th>총 배송비</th>
					<th>총 할인금액</th>
					<th>결제예정금액</th>
				</tr>
				<tr>
					<td>
						<div><strong >${total_price}</strong>원</div></td>
					<td>
						<div>
							<strong>+</strong><strong>${fee}</strong>원
						</div>
					</td>
						<td>
						<div>
							<strong>-</strong><strong>0</strong>원
						</div>
					</td>
					<td>
						<div>
							<strong>=</strong><strong>${total_price + fee}</strong>원
						</div>
					</td>
				</tr>
			</table>	
			<button type="button" class="btn delete_btn" name="delete_btn" style="float:left"
				onclick="deleteCart();">상품삭제</button>
			<div style="float:right">
				<script src="https://nsp.pay.naver.com/sdk/js/naverpay.min.js"
					data-client-id="u86j4ripEt8LRfPGzQ8"
					data-mode="production"
					data-merchant-user-key="ffffff"
					data-merchant-pay-key="123123"
					data-product-name="GarmentMarket"
					data-total-pay-amount="${total_price}"
					data-tax-scope-amount="${total_price}"
					data-tax-ex-scope-amount="0"
					data-return-url="사용자 결제 완료 후 결제 결과를 받을 URL">
				</script>
			</div>
			</form>
		</c:otherwise>
	</c:choose>

</div>
<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>

<script type="text/javascript">
/*
function changeOk() {
	var f = document.cartForm;
	
	f.action = "${pageContext.request.contextPath}/cart/change.do";
	f.submit();
}
*/
$(document).ready(function(){
	$(function() {  
	    $(".spinner").spinner({
	    	min: 1,
	    	step: 1
	    });  
	});
});
 

$(document).ready(function(){
    $("#checkAll").click(function(){
        if($("#checkAll").prop("checked")){
            $("input[name=box]").prop("checked",true);
        }else{
            $("input[name=box]").prop("checked",false);
        }
    });
});
</script>
</html>