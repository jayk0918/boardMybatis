<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link rel="stylesheet" type="text/css" href="/css/initialize.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/css/readcontent.css">

<title>글 읽기</title>

</head>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>

<body>
	<div id="wrap">
		<div id="container" class="clearfix">
			<div id="content">
				<h1>게시글 조회</h1>
				<div id="board">
					<div id="read">
						<!-- 작성자 -->
						<!--  <input id = "contentNo" type = "hidden" value = "${content.contentNo}">-->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${content.userName}</span>
						</div>
						
						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">${content.hit}</span>
						</div>
						
						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">${content.regdate}</span>
						</div>
						
						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span id = "originalTitle" class="form-value">${content.title}</span>
						</div>
					
						<!-- 내용 -->
						<div id="txt-content">
							<span id = "originalContent" class="form-value" >
								${content.content}
							</span>
						</div>
						
						<br>
						
						<div id = "buttons">
							<div id = "paging">
								<a id = "previousPage" href = "${pageContext.request.contextPath}/board/readcontent/${content.previousPage}" class = "btn">이전</a>
								<a id = "nextPage" href = "${pageContext.request.contextPath}/board/readcontent/${content.nextPage}" class = "btn">다음</a>
							</div>
							
							<div id = "config">
								<a id = "returnList" class = "btn" href="/board/list">목록</a>
								
								<c:if test = "${authUser.userNo == content.userNo}">
									<a id = "edit" href = "/board/editform/${content.contentNo}" class = "btn">수정</a>
								</c:if>
								
								<c:if test="${authUser.userNo == 1 || authUser.userNo == content.userNo}">
									<button type = "button" id = "deletion" class = "btn">삭제</button>
								</c:if>
							</div>
							
						</div>
					</div>
					<!-- //read -->
					
				</div>
				<!-- //board -->
			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->
	</div>
	<!-- //wrap -->
</body>

<script type = "text/javascript">

$("#previousPage").on("click", function(){
	var previousPage = ${content.previousPage};
	if(previousPage == -1){
		alert("첫번째 글입니다.")
		event.preventDefault();
	}
});

$("#nextPage").on("click", function(){
	var nextPage = ${content.nextPage};
	if(nextPage == -1){
		alert("마지막 글입니다.")
		event.preventDefault();
	}
});

$("#deletion").on("click", function(){
	var contentNo = ${content.contentNo}
	var selectDel = confirm("삭제하시겠습니까?");
	
	if(selectDel == true){
		$.ajax({
			url : "/board/"+contentNo,
			method: "delete",
			type : "post",
			data : JSON.stringify(contentNo),
			contentType : "application/json",
			dataType : "json",
			success : function(result){
				console.log(result);
				alert("삭제되었습니다");
				window.location.href = "/board/list";
			},
			error : function(XHR, status, error) {
				console.log(status + ' : ' + error);
			}
		})
	}else{
		alert("취소하였습니다.");
	}
	
});

</script>

</html>