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
<link rel="stylesheet" href="../style_ny.css" type="text/css">
<script type="text/javascript">
/*    //수량에 따른 가격 변경 작동안됨 html코드로 적어서 그런 듯
var sell_price;
var amount;

function init () {
	sell_price = document.form.sell_price.value;
	amount = document.form.amount.value;
	document.form.sum.value = sell_price;
	change();
}

function add () {
	hm = document.form.amount;
	sum = document.form.sum;
	hm.value ++ ;

	sum.value = parseInt(hm.value) * sell_price;
}

function del () {
	hm = document.form.amount;
	sum = document.form.sum;
		if (hm.value > 1) {
			hm.value -- ;
			sum.value = parseInt(hm.value) * sell_price;
		}
}

function change () {
	hm = document.form.amount;
	sum = document.form.sum;

		if (hm.value < 0) {
			hm.value = 0;
		}
	sum.value = parseInt(hm.value) * sell_price;
}    
*/
</script>
</head>
<body>
<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
	<div class= "orderbox">
		<p class="orderbox_info">
			<span class="itemName"> Beige dotted onepiece</span><br>
			<span class="itemPrice">46,000 won</span><br>
			2,500won (70,000won 이상 구매 시 무료)<br>	
		</p>
			
		<form name="form" method="get">
			<select class="selectbox" id="sizeBox" required>
				<option value= "size">::사이즈::</option>
				<option value= "s">S</option>
				<option value= "m">M</option>
				<option value= "l">L</option>
				<option value= "xl">XL</option>
			</select><br>
			<input type=hidden name="sell_price" value="46000">
			<input type="text" name="amount" value="1" size="22" onchange="change();">
			<input type="button" value=" + " onclick="add();">
			<input type="button" value=" - " onclick="del();"><br>
			
			

			<p class="INFO">			
			 	 INFO <br>
				 신축성-없음｜계절감- 봄/여름｜안감-있음<br>
				 ｜비침-약간 있음｜촉감-부드러움<br><br>
			
				fablic<br>
				실크100%<br>
			
			</p>
			<table class="sizeDetail">
				<caption>상세사이즈</caption>
				<thead>
					<tr>
						<td>사이즈</td>
						<td>S</td>
						<td>M</td>
						<td>L</td>
						<td>XL</td>
					</tr>
					<tr>
						<td>길이</td>
						<td>80</td>
						<td>82</td>
						<td>84</td>
						<td>86</td>
					</tr>
					<tr>
						<td>어깨</td>
						<td>34</td>
						<td>36</td>
						<td>38</td>
						<td>40</td>
					</tr>
					<tr>
						<td>가슴</td>
						<td>38</td>
						<td>40</td>
						<td>42</td>
						<td>44</td>
					</tr>
				</thead>
			</table>
			<div class = "total">
			Total<br>
			<input id="totalPrice" type="text" name="sum" size="11" readonly>won<br><br>
			<input class="btn" id="buyBtn" type="submit" value = "Buy Now">
			<input class="btn" id="toCartBtn" type="submit"  value = "Add To Cart">
			</div>
		</form>
	</div>
</header>
<main>
	<div class= "detail_box item1" ></div>
	<p class="coment">
		트렌디한 무드의 베이지 검정도트 원피스입니다.<br>
		실크100 소재의 부드러운 텍스처로 편안한 착용이 가능합니다.<br>
		베이지색 바탕에 검정 도트무늬가 포인트가 되어 경쾌한 느낌을 줍니다.<br><br>	 
 
	</p>
</main>

<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>