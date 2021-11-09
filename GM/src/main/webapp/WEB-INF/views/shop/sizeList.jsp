<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<option>::사이즈::</option>
	<c:forEach var="dto" items="${sizeList}" varStatus="status">
		<option value="${dto.cdnum}">${dto.size}</option>
	</c:forEach>
