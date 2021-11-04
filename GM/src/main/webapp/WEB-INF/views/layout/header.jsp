<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



	<a id="logo" href="#">Garment Market</a>

	<nav id="topMenu">
		<ul>
			<li><a class="menuLink" href="#">SHOP</a></li>
			<li><a class="menuLink" href="#">Q/A</a></li>
			<li><a class="menuLink" href="#">MY PAGE</a></li>
			<li><a class="menuLink" href="${pageContext.request.contextPath}/member/login.do">LOGIN</a></li>
			<li><a class="menuLink" href="#">JOIN US</a></li>
			<li><a class="menuLink" href="#">CART</a></li>
		</ul>
	</nav>
