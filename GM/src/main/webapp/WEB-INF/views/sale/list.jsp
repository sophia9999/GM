<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GM : 판매내역 및 매출현황</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/tablestyle_ih.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/styleny.css" type="text/css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/ju/jszip-2.5.0/dt-1.11.3/af-2.3.7/b-2.0.1/b-html5-2.0.1/datatables.min.css"/>
 
<style type="text/css">
.buttons-excel{
    width: 50px;
    height: 30px;
    background: #eee;
    border: none;
    color: #b3b3b3;
    font-weight: bold

}
</style>

</head>
<body>
<header>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</header>
<div class="container">
	<div>
	<div class="title" >
		<h3><span>|</span> 판매내역</h3>
	</div>
	
			    
	<table id="saleslist" class="table table-border table-form " style="clear:both;">
		<thead>
			<tr>
				<th>주문번호</th>
				<th style="width: 120px;">상품코드</th>
				<th>상품명</th>
				<th>단가</th>
				<th>할인금액</th>
				<th>재고</th>
			</tr>		
		</thead>	
		<tbody>
			
		</tbody>
			
			
	</table>  
	
	
	 
	
	<div class="title" style="padding-top: 30px;">
		<h3 style="display: inline-block;"><span>|</span> 매출현황</h3>
		<select id="condition" name="condition" class="selectField">
						<option value="all" selected="selected">전체매출</option>
						<option value="year">연 매출</option>
						<option value="month">월 매출</option>
						<option value="day" >일 매출</option>
					</select>
	</div>
	
	<table class="table table-border table-form" id = "salesdetil">
		<thead>
				<tr>
				<th style="width: 120px;">
					기간
				</th>					
				<th>총매출</th>
				<th>원가</th>
				<th>할인금액</th>
				<th style="width: 120px;">순이익</th>
			</tr>
		</thead>
		<tbody></tbody>	
	</table>
	
	
	</div>
</div>
 

<footer>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</footer>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/jszip-2.5.0/dt-1.11.3/af-2.3.7/b-2.0.1/b-html5-2.0.1/datatables.min.js"></script>
<script type="text/javascript">
$(document).ready( function () {
	
    var lang_kor = {
            "decimal" : "",
            "emptyTable" : "데이터가 없습니다.",
            "info" : "_START_ - _END_ (총 _TOTAL_ 개)",
            "infoEmpty" : "0개",
            "infoFiltered" : "(전체 _MAX_ 개 중 검색결과)",
            "infoPostFix" : "",
            "thousands" : ",",
            "lengthMenu" : "_MENU_ 개씩 보기",
            "loadingRecords" : "로딩중...",
            "processing" : "처리중...",
            "search" : "검색 : ",
            "zeroRecords" : "검색된 데이터가 없습니다.",
            "paginate" : {
                "first" : "첫 페이지",
                "last" : "마지막 페이지",
                "next" : "다음",
                "previous" : "이전"
            },
            "aria" : {
                "sortAscending" : " :  오름차순 정렬",
                "sortDescending" : " :  내림차순 정렬"
            }
        };


	
	$("#saleslist").DataTable({
		language : lang_kor,
	
		 dom: 'Bfrtip',
		    buttons: [		       
		        'excel'
		    ],
		ajax:{
			url:"${pageContext.request.contextPath}/sale/clothes.do",
			type:"GET",
			data:""

		},
		columns:[
			
			{data : 'orderNum'},
			{data : 'cdNum'},
			{data :'clothName'},
			{data :'price'},
			{data :'discount'},
			{data :'stock'}
			
		],
		columnDefs: [
			{ targets: 3 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }
			,{ targets: 4 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }

			]
			
		
	}); 
	
	let date = {date : "all"};
	
	$("#salesdetil").DataTable({
		language : lang_kor,
		 dom: 'Bfrtip',
		    buttons: [		       
		        'excel'
		    ],
		ajax:{
			url:"${pageContext.request.contextPath}/sale/salesdetails.do",
			type:"GET",
			data:date

		},
		columns:[
			
			{data : 'order_date'},
			{data :'price'},
			{data :'unitPrice'},
			{data :'discount'},
			{data :'realPrice'}
	
			
		],
		columnDefs: [
			{ targets: 1 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }
			,{ targets: 2 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }
			,{ targets: 3 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }
			,{ targets: 4 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }

			]
    
	}); 
 
} );

$(function(){
	$("#condition").change(function(){

	    var lang_kor = {
	            "decimal" : "",
	            "emptyTable" : "데이터가 없습니다.",
	            "info" : "_START_ - _END_ (총 _TOTAL_ 개)",
	            "infoEmpty" : "0개",
	            "infoFiltered" : "(전체 _MAX_ 개 중 검색결과)",
	            "infoPostFix" : "",
	            "thousands" : ",",
	            "lengthMenu" : "_MENU_ 개씩 보기",
	            "loadingRecords" : "로딩중...",
	            "processing" : "처리중...",
	            "search" : "검색 : ",
	            "zeroRecords" : "검색된 데이터가 없습니다.",
	            "paginate" : {
	                "first" : "첫 페이지",
	                "last" : "마지막 페이지",
	                "next" : "다음",
	                "previous" : "이전"
	            },
	            "aria" : {
	                "sortAscending" : " :  오름차순 정렬",
	                "sortDescending" : " :  내림차순 정렬"
	            }
	        };


	
		
		let date = {date : $("#condition").val()};
		
		$("#salesdetil").DataTable().destroy();
		
		 $("#salesdetil").DataTable({
			language : lang_kor,
			 dom: 'Bfrtip',
			    buttons: [		       
			        'excel'
			    ],
			ajax:{
				url:"${pageContext.request.contextPath}/sale/salesdetails.do",
				type:"GET",
				data:date

			},
			columns:[
				
				{data : 'order_date'},
				{data :'price'},
				{data :'unitPrice'},
				{data :'discount'},
				{data :'realPrice'}
		
				
			],
			columnDefs: [
				{ targets: 1 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }
				,{ targets: 2 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }
				,{ targets: 3 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }
				,{ targets: 4 , render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원' ) }

				]
			
		});  
		
	});
	
	
});
</script>
</body>
</html>