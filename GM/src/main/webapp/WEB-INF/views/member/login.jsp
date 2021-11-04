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

<script type="text/javascript">
function bgLabel(obj, id) {
	if( ! obj.value ) {
		document.getElementById(id).style.display="";
	} else {
		document.getElementById(id).style.display="none";
	}
}

function inputsFocus( id ) {
	// 객체를 보이지 않게 숨긴다.
	document.getElementById(id).style.display="none";
}

function sendLogin() {
    var f = document.loginForm;

	var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
    f.submit();
}
</script>

</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<main>
	<div class="wrap  maincenter">
	<div class="login-box">
			<a class="loginlogo">GarmentMarket </a>
			<div class="login-basepadding">
				<form name="loginForm" method="post" action="">
				<h4>이메일 로그인</h4>
				<div class="div-text">
					<input class="div-input" name="userId" placeholder="이메일 주소">
				</div>
				<div class="div-text">
					<input class="div-input" name="userPwd"  placeholder="비밀번호">
				</div>
				<div class="pad-15tb">
					
					<span>
						<a href="#" class="login-a">비밀번호를 잊으셨나요?</a>
					</span>
				</div>
				<button type="button" class="login-butt login" onclick="sendLogin();" >로그인</button>
				<div class="footer">GarmentMarket에 처음이세요?
						<span style="padding-left: 15px;">
							<a href="#" class="login-a">회원가입하기</a>
					</span>
				</div>				
				</form>
			</div>					
	</div>
	
	
</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>

</footer>
    

</body>
</html>