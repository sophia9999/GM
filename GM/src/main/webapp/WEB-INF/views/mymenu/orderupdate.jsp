<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GM: 주문변경 및 취소</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">


<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">

<style type="text/css">
 .img-width{
 	max-width: 200px;
 }
 
 .in-flex{
 	display: inline-flex;
 	
 }
.table tr > td:nth-child(2) {
	text-align: left;
}

</style>

<script type="text/javascript">
function deleteOrder(){
	let f = document.orderForm;
	if(!confirm("주문을 취소하시겠습니까?")){
		return false;
	}
	
	f.action = "${pageContext.request.contextPath}/mymenu/myorder_delete.do";
    f.submit();
	//action="${pageContext.request.contextPath}/mymenu/myorder_update_ok.do"
}
</script>

</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<div class="container">
	<form name = "orderForm" method="post" action="${pageContext.request.contextPath}/mymenu/myorder_update_ok.do" >
	<div class="title">
		<h3><span>|</span> 주문상세</h3>
	</div>
		<table class="table table-border table-list">
		<colgroup>
			<col width="20%">
			<col/>
		</colgroup>
			
		
		<tbody>
			
				<tr>
					<td>제품</td>
					<td class="left in-flex" >
						
							<img class="img-width" src="${pageContext.request.contextPath}/uploads/photo/${dto.fileName}">						
						
						<div>
						${dto.ccoment }
						</div>
					</td>
				</tr>
				<tr>
					<td>가격</td>
					<td class="left">
						${price}원	
					</td>
				</tr>
				<tr>
					<td>수취인</td>
					<td class="left">
						<input class="boxTF" name="recipient" value="${dto.recipient }"/>
					</td>
				</tr>
				<tr>
					<td>연락처</td>
					<td class="left">
						<input class="boxTF" name="recPhoneNum" value="${dto.recPhoneNum }"/>
					</td>
				</tr>
			<tr>
				<td>우편번호</td>
				<td>
					<input type="text"  name="dCode" id="dCode" maxlength="7" class="boxTF" value="${dto.dCode}" readonly="readonly" style="width: 50%;">
					<button type="button" class="btn" onclick="daumPostcode();">우편번호검색</button>
				</td>
			</tr>
			
			<tr>
				<td >배송지</td>
				<td>
					<p>
						<input type="text" name="dAddress" id="dAddress" maxlength="50" class="boxTF" value="${dto.dAddress}" readonly="readonly" style="width: 96%;">
					</p>
					<p class="block">
						<input type="text" name="dAddress_detail" id="dAddress_detail" maxlength="50" class="boxTF" value="${dto.dAddress_detail}" style="width: 96%;">
					</p>
				</td>
			</tr>
			<tr>
			<td>요청사항</td>
				<td>
					<input type="text"  name="request"  maxlength="10"   class="boxTF" value="${dto.request}"  style="width: 96%;">
				</td>
			</tr>
				
				
		</tbody>	
		</table>
		<input type="hidden" name="odNum" value="${dto.odNum}">
		<input type="hidden" name="oNum" value="${dto.oNum}">
		<button type="button" class="btn" style="float:left" onclick="javascript:location.href='${pageContext.request.contextPath}/mymenu/myorder.do${query}'">뒤로</button>
	
				<button type="button" onclick="deleteOrder();" class="btn" style="float:right">주문 취소</button>	
		<button type="submit" class="btn" style="float:right">수정완료</button>
	
		
					
		
		
					
	</form>
</div>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>	
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
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
                document.getElementById('dCode').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('dAddress').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('dAddress_detail').focus();
            }
        }).open();
    }
</script>

</body>
</html>