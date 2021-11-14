<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>

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
		<h3><span>|</span> cart</h3>
	</div>
	<c:choose>
        <c:when test="${cartCount == 0}">
            장바구니에 등록된 상품이 없습니다.
        </c:when>
        <c:otherwise>   
			<table>
				<tr>
					<td align="left">상품개수(${cartCount})</td>
				</tr>
			</table>
			<form name="cartForm" method="post">
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
				
				<c:forEach var="dto" items="${cartList}">				
					<tr>						
						<td><input type="checkbox" name="box" value="${dto.cartNum}"></td>
						<td><img src="${pageContext.request.contextPath}/uploads/photo/${dto.fileName}" class="img"></td>
						<td>${dto.clothName}</td>
						<td>${dto.size}/${dto.color}
						<td style="text-align: center;">
							<input type="text" class="spinner" name="amount" value="${dto.amount}" style="width: 20px;"><br>
							<button type="button" class="btn btnChangeAmount"
									data-cartNum="${dto.cartNum}"
									style="float:center; 
									width:40px; height:25px; margin:5px 0 0 0; padding: 0; font-size: 13px; border-radius: 3px;">변경</button>
											
						</td>
						<td style="text-align: center;">${(dto.price-dto.discount) * dto.amount}원</td>
						<td><button type="button" class="btn" id="btn_delete"
								onclick="location.href='${pageContext.request.contextPath}/cart/delete.do?cartNum=${dto.cartNum}';">삭제</button></td>
					</tr>
				</c:forEach>
			</table>
			</form>
			<!-- 
			<form method="post" action="${pageContext.request.contextPath}/cart/change.do">
				<input type="hidden" name="cartNum">
				<input type="hidden" name="amount">
			</form>
			 -->
			<table class="table table-pay table-border">
				<tr style="background: #eee; padding: 10px 0;">
					<th>
						<span>총 상품금액</span>
					</th>
					<th>총 배송비</th>
					<th>총 할인금액</th>
					<th>결제예정금액</th>
				</tr>
				<tr style="height: 50px;">
					<td>
						<div><strong >${total_price}</strong>원</div></td>
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
					</td>
				</tr>
			</table>	
			<button type="button" class="btn" id="btnDelete" style="float:left">선택상품삭제</button>
			<button type="button" class="btn" id="btnOrder" style="float:right">선택상품주문</button>
		</c:otherwise>
	</c:choose>
</div>
<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>

<script type="text/javascript">

$(document).ready(function(){
	$(".spinner").spinner({
	  	min: 1,
	  	step: 1
	});  
	
    $("#checkAll").click(function(){
        if($("#checkAll").prop("checked")){
            $("input[name=box]").prop("checked",true);
        }else{
            $("input[name=box]").prop("checked",false);
        }
    });
    
	$(".btnChangeAmount").click(function(){		
		var url = "${pageContext.request.contextPath}/cart/cartChange.do";
		var cartNum = $(this).attr("data-cartNum");
		var amount = $(this).closest("td").find("input[name=amount]").val();
		
		var query = "cartNum="+cartNum+"&amount="+amount;
		var url  = url + "?" + query;
		location.href=url;
	});
    
    $("#btnDelete").click(function(){
    	var cnt=$("input[name=box]:checked").length;
    	if(cnt==0) {
    		alert("삭제할 상품을 선택해주세요.");
    		return false;
    	}
    	var f = document.cartForm;
    	f.action="${pageContext.request.contextPath}/cart/deleteCartList.do";
    	f.submit(); 	
    });
    
    $("#btnOrder").click(function(){
    	var cnt=$("input[name=box]:checked").length;
    	if(cnt==0) {
    		alert("주문할 상품을 선택해주세요.");
    		return false;
    	}
    	var f = document.cartForm;
    	f.action="${pageContext.request.contextPath}/cart/orderList.do";
    	f.submit();
    });
});

</script>
</html>