<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 리스트</title>
</head>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link rel="stylesheet" type="text/css" href="/css/initialize.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/css/list.css">

<body>
	<div id="wrap">
		<div id="container" class="clearfix">
			<div id="content">
				<h1>게시판</h1>
				<div id="board">
					<div id = "upperMenu">
						<div id = "userIdentity" class = "clearfix">
							<p>안녕하세요 ${authUser.userName}님  <a href = "/board/logout">로그아웃</a></p>
						</div>

						<!------------------ 검색 form -------------------->
						<!-- 제목 / 글쓴이 / 내용 3가지 카테고리로 input의 값 검색 -->
						<form id = "search" name = "search" method = "get" action = "list">
							<select id = "searchCategory" name = 'searchCategory'>
								<option value='0'>선택하세요</option>
								<option value='1'>제목</option>
								<option value='2'>글쓴이</option>
								<option value='3'>내용</option>
							</select>
							<input id = "searchKeyword" type= "text" name = "searchKeyword">
							<!-- 기존 검색된 파라미터값 저장태그 -->
							<button type = "submit" class = "btn">검색</button>
						</form>
						<input id = "searchedCategory" name = "searchedCategory" type = "hidden" value = "${searchedCategory}">
						<input id = "searchedKeyword" name = "searchedKeyword" type = "hidden" value = "${searchedKeyword}">
						
						<br>
						
						<div id = "totalCount">
							<p>게시글 수 : ${totalCount}</p>
						</div>
						
					</div>
					
					<div id="list">
						<!-- 리스트 table -->
						<table class = "table-bordered">
							<thead>
								<tr>
									<th width = "10%">번호</th>
									<th width = "60%">제목</th>
									<th width = "10%">글쓴이</th>
									<th width = "10%">조회수</th>
									<th width = "10%">작성일</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items = "${list}" var = "list" varStatus = "status">
									<tr>
										<td>${status.count}</td>
										<td><a href="/board/readcontent/${list.contentNo}">${list.title}</a></td>
										<td>${list.userName}</td>
										<td>${list.hit}</td>
										<td>${list.regdate}</td>
									</tr>
								</c:forEach>
							</tbody>
							
						</table>
						
					</div>
					<!-- //list -->
					<a id="btn_add" class = "btn" href="/board/writeform">등록</a>
					<a id="returnHome" class = "btn" href="/board/list">초기화</a>
				</div>
				<!-- //board -->
			</div>
			
			<div id="paginationBox">
				<ul class="pagination">
					<c:if test="${pagination.prev}">
						<li class="page-item">
							<a class="page-link" href="#" onClick="fn_prev('${pagination.page}', '${pagination.range}', '${pagination.rangeSize}')">Previous</a>
						</li>
					</c:if>
		
					<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
						<li class="page-item <c:out value="${pagination.page == idx ? 'active' : ''}"/> ">
							<a class="page-link" href="#" onClick="fn_pagination('${idx}', '${pagination.range}', '${pagination.rangeSize}')"> ${idx} </a>
						</li>
					</c:forEach>
		
					<c:if test="${pagination.next}">
						<li class="page-item">
							<a class="page-link" href="#" onClick="fn_next('${pagination.range}', 
							'${pagination.range}', '${pagination.rangeSize}')" >Next</a>
						</li>
					</c:if>
				</ul>
			</div>
			
			<!--  
			<nav aria-label="Page navigation example">
				<div id = "pagination">
					<ul class= "pagination">
						<c:forEach var = "i" begin = "1" end ="${totalPage}">
							<li class = "page-item">
								<a class = "page-link" href = "/board/list?pageNo=${i}" > ${i}</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</nav>
			-->
			<!-- pagination -->
			
			<!-- //content  -->
		</div>
		<!-- //container  -->
	</div>
	<!-- //wrap -->
</body>
<c:import url = "/WEB-INF/views/includes/sessionValue.jsp"></c:import>
<script type = "text/javascript">

/*** 검색 값 없을 시 form 처리 방지, 내용 입력 alert ***/
$("#search").on("submit", function(event){
	var keyword = $("#searchKeyword").val();
	if(keyword == ""){
		alert("내용을 입력해주세요");
		event.preventDefault();
	}
	
	var searchedCategory = $("#searchedCategory").val();
	var searchedKeyword = $("#searchedKeyword").val();
	
	if(searchedCategory != 0){
		$("searchedCategory").val();
	}
	
});


// pagination
// 이전 버튼 이벤트

function fn_prev(page, range, rangeSize) {
	var page = ((range - 2) * rangeSize) + 1;
	var range = range - 1;
	var url = "${pageContext.request.contextPath}/board/list";
	
	url = url + "?page=" + page;
	url = url + "&range=" + range;
	location.href = url;
}

// 페이지 번호 클릭
function fn_pagination(page, range, rangeSize, searchType, keyword) {
	var url = "${pageContext.request.contextPath}/board/list";
	url = url + "?page=" + page;
	url = url + "&range=" + range;
	url = url + "&searchCategory=" + document.getElementById("searchedCategory").value;
	url = url + "&searchKeyword=" + document.getElementById("searchedKeyword").value;

	location.href = url;	

}

//다음 버튼 이벤트
function fn_next(page, range, rangeSize) {
	var page = parseInt((range * rangeSize)) + 1;
	var range = parseInt(range) + 1;
	var url = "${pageContext.request.contextPath}/board/list";

	url = url + "?page=" + page;
	url = url + "&range=" + range;

	location.href = url;
}

</script>

</html>