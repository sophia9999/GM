<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



	<a id="logo" href="${pageContext.request.contextPath}/main.do">Garment Market</a>

	<nav id="topMenu">
		<ul>
			<c:choose>
				<c:when test="${sessionScope.member.userId=='admin'}">
					<li><a class="menuLink" href="${pageContext.request.contextPath}/shop/garment.do">SHOP / 상품관리</a></li>
					<li><a class="menuLink" href="#">Q/A</a></li>
					<li><a class="menuLink" href="#">리뷰관리</a></li>
					<li><a class="menuLink" href="#">주문관리</a></li>
					<li><a class="menuLink" href="${pageContext.request.contextPath}/sale/sale.do">매출내역</a></li>
					<li><a class="menuLink" href="${pageContext.request.contextPath}/member/logout.do">LOGOUT</a></li>
				</c:when>
				<c:otherwise>
					<li><a class="menuLink" href="${pageContext.request.contextPath}/shop/garment.do">SHOP</a></li>
					<li><a class="menuLink" href="#">Q/A</a></li>
					<c:if test="${empty sessionScope.member }">
						<li><a class="menuLink" href="${pageContext.request.contextPath}/member/login.do">LOGIN</a></li>
						<li><a class="menuLink" href="${pageContext.request.contextPath}/member/join.do">JOIN US</a></li>	
					</c:if>
					<c:if test="${not empty sessionScope.member }">
						<li><a class="menuLink" href="#">MY PAGE</a></li>
						<li><a class="menuLink" href="#"> <span style="color:black;">${sessionScope.member.userName}</span>님</a></li>
						<li><a class="menuLink" href="${pageContext.request.contextPath}/member/logout.do">LOGOUT</a></li>
						<li><a class="menuLink" href="${pageContext.request.contextPath}/member/update.do">정보수정</a></li>
					</c:if>
					<li><a class="menuLink" href="${pageContext.request.contextPath}/cart/cart.do">CART</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
