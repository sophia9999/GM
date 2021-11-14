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
.comment {
    color: black;
    width: 600px;
    margin: 10px auto;
    text-align: justify;
    font-size: 16px;
}

.itemName {
    font-weight: 600;
    font-size: 18px;
    color: #3f3f3f;
}

.orderbox_info {
    color: #3f3f3f;
}

.itemBox {
	width: 400px;
    height: 500px;
    margin: 20px auto;
}

.btn {
	width: 280px;
    height: 40px;
    font-size: 14px;
}

.btn1 {
	width: 130px;
    height: 40px;
    font-size: 14px;
    margin: 0 auto;
}

</style>
<script type="text/javascript">

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
			 if(jqXHR.status === 405) {
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

function imageViewer(img) {
	var viewer = $(".photo-layout");
	var s="<img src='"+img+"'>";
	viewer.html(s);
	
	$(".dialog-photo").dialog({
		title:"이미지",
		width: 600,
		height: 530,
		modal: true
	});
}

</script>
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	<div class= "orderbox">
		<p class="orderbox_info">
			<span class="itemName">${dto.clothname}</span><br>
			<span class="itemPrice">
			<c:if test="${dto.discount!=0}">
				<fmt:formatNumber value="${dto.price}" pattern="#,###"></fmt:formatNumber>￦ →
				<fmt:formatNumber value="${dto.price-dto.discount}" pattern="#,###"></fmt:formatNumber>￦
			</c:if>
				<c:if test="${dto.discount==0}">
				<fmt:formatNumber value="${dto.price}" pattern="#,###"></fmt:formatNumber>￦
			</c:if>
			</span><br>
			<br>배송비 ￦2,500<br>(￦70,000 이상 구매 시 무료)<br>	
		</p>
			
		<form name="form" method="post">
			<select class="selectbox" id="colorBox">
				<option value="">::컬러::</option>
				<c:forEach var="dto" items="${colorList}" varStatus="status">
					<option value="${dto.ccnum}">${dto.color}</option>
				</c:forEach>
			</select>
			<select class="selectbox" id="sizeBox" name="cdnum">
				<option>::사이즈::</option>
			</select><br>
				<input type="text" class="amount" name="amount" value="1" onchange="change()">
				<input type="button" value=" + " onclick="add();">
				<input type="button" value=" - " onclick="del();"><br>
			
			<div class = "total">
			Total<br>
				<input id="totalPrice" type="text" name="sum" size="11" readonly="readonly" value="${price}">￦<br><br>
				<input class="btn" id="buyBtn" type="text" value = "Buy Now" >
				<input class="btn" id="toCartBtn" type="text"  value = "Add To Cart">
			</div>
			
			<div class="detail-add">
				<c:if test="${sessionScope.member.userId=='admin'}">
					<button type="button" class="btn btn1" onclick="location.href='${pageContext.request.contextPath}/shop/garment-detail.do?num=${dto.cnum}&page=${page}';">색상/사이즈 관리</button>
				</c:if>
			</div>
		</form>
	</div>
</header>
<main>
	<div class= "detail_box" style="margin: 150px auto 0;">
		<img style="width: 100%; height: 100%;" src="${pageContext.request.contextPath}/uploads/photo/${dto.imageFilename}"
			onclick="imageViewer('${pageContext.request.contextPath}/uploads/photo/${dto.imageFilename}');">
	</div>
	<h4 style="font-size: 20px;">INFO</h4>
	<p class="comment">
		${dto.ccoment}
	</p>
		<c:forEach var="vo" items="${listFile}">
			<div class="itemBox">
				<img style="width: 100%; height: 100%;" src="${pageContext.request.contextPath}/uploads/photo/${vo.imageFilename}">
			</div>
		</c:forEach>
</main>
		<div>
			<c:if test="${sessionScope.member.userId=='admin'}">
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/shop/garment-update.do?num=${dto.cnum}&page=${page}';">수정</button>
			</c:if>
			<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/shop/garment.do?page=${page}';">목록</button>
		</div>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<div class="dialog-photo">
      <div class="photo-layout"></div>
</div>

<script type="text/javascript">

function add() {
	var amount = document.form.amount;
	amount.value = parseInt(amount.value) + 1;
	if(amount.value < 1) {
		amount.value = 1;		
	}
	var qty = amount.value;
	var sum = document.form.sum;
	sum.value = ${price} * qty;
}
function change() {
	var amount = document.form.amount;
	
	if(amount.value < 1){
		amount.value = 1;
		amount.focus();
		alert("정확한 수량을 입력해주세요.");
	}
	var qty = amount.value;
	var sum = document.form.sum;
	sum.value = ${price} * qty;
}
	
function del() {
	var amount = document.form.amount;
	if(amount.value > 1) {
		amount.value = parseInt(amount.value) - 1;		
	}
	var qty = amount.value;
	var sum = document.form.sum;
	sum.value = ${price} * qty;
}

$(function() {
	$("#colorBox").on("change", function() {
		if(this.value == ""){
			return false;
		}
		var ccnum = $(this).find(":selected").val();
		// alert(ccnum);
		
		var url = "${pageContext.request.contextPath}/shop/garment-sizeList.do";
		var query = "ccnum="+ccnum;
		var selector = "#sizeBox";
		var fn = function(data) {
			$(selector).empty();
			$(selector).append(data);
		};
		ajaxFun(url, "post", query, "html", fn)
		
	});
});

$(function(){
	var f = document.form;
	$("body").on("click", "#buyBtn", function() {
		if(! $("#colorBox option:selected").val() ){
			alert("컬러를 선택하세요.");
			return;
		}
		
		if(! $("#sizeBox option:selected").val() ){
			alert("사이즈를 선택하세요.");
			return;
		}
		
		f.action = "${pageContext.request.contextPath}/cart/buyNow.do";
		f.submit();
	});
});

$(function() {
	var f = document.form;
	$("body").on("click", "#toCartBtn", function() {
		
		if(! $("#colorBox option:selected").val() ){
			alert("컬러를 선택하세요.");
			return;
		}
		
		if(! $("#sizeBox option:selected").val() ){
			alert("사이즈를 선택하세요.");
			return;
		}
		
		f.action = "${pageContext.request.contextPath}/shop/addcart.do?page=${page}&num=${dto.cnum}";
		f.submit();
		alert("장바구니에 추가되었습니다.");
	});
	
});

</script>

</body>
</html>