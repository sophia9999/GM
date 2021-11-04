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
</head>
<body>
<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="container">
	<div class="item item1" OnClick="location.href='http://localhost:9090/study/practice_shoppingmall/detailpage.html'"style="cursor:pointer;">
		<div class = "text">
			<p>Beige dotted onepiece<br>46,000 won</p>
		</div>
	</div>
	<div class="item item2">
		<div class = "text">
			<p id="object1">Black jeans<br>52,000 won</p>
		</div>
	</div>
	<div class="item item3">
		<div class = "text">
			<p>Skyblue jeans<br>52,000 won</p>
		</div>
	</div>
	<div class="item item4">
		<div class = "text">
			<p>Training pants<br>43,000 won</p>
		</div>
	</div>
	<div class="item item5">
		<div class = "text">
			<p>Butter jacket<br>60,000 won</p>
		</div>
	</div>
	<div class="item item6">
		<div class = "text">
			<p>Black jacket<br>59,000 won</p>
		</div>
	</div>
	<div class="item item7">
		<div class = "text">
			<p>White pants<br>41,000 won</p>
		</div>
	</div>
	<div class="item item8">
		<div class = "text">
			<p>Checkered jacket<br>67,000 won</p>
		</div>
	</div>
	<div class="item item9">
		<div class = "text">
			<p>Dotted pants<br>46,000 won</p>
		</div>
	</div>
	<div class="item item10">
		<div class = "text">
			<p>Black shoes<br>40,000 won</p>
		</div>
	</div>
	<div class="item item11">
		<div class = "text">
			<p>Striped sleeveless shirt<br>41,000 won</p>
		</div>
	</div>
	<div class="item item12">
		<div class = "text">
			<p>Brwon hood jacket<br>55,000 won</p>
		</div>
	</div>
</div>
</main>

<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>