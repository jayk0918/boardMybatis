<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link rel="stylesheet" type="text/css" href="/css/initialize.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/css/loginform.css">

<body>
	<h1>Board Project</h1>
	
	<div id="center-content">
		<form id="loginForm" action = "/board/login" method = "post">
			<!-- 유저 3명
				id : guest1, pw : 123
				id : guest2, pw : 123
				id : admin, pw : 123
			 -->
			<div id = "idInput">
				<p>Security login</p>
	      		<label for="userId">아이디</label>
		      	<input id = "userId" type="text" name="userId" placeholder = "아이디를 입력해주세요">
      		</div>
      		<div id = "passwordInput">
      			<label for = "password">패스워드</label>
      			<input id = "password" type="password" name="password">
     			</div>
      		<div id="btnArea">
				<button id = "btn-login" type="submit" class="btn btn-primary">로그인</button>
				<a href = "/board/joinform">회원가입</a>
			</div>
		</form>
	</div>
	
</body>

<!--  
<script type = "text/javascript">

$("#btn-login").on("click", function(){
	var userId = $("#userId").val();
	var password = $("#password").val();
	var userInfo={
		userId : userId,
		password : password
	};
	
	$.ajax({
		url : "/board/usercheck",
		type : "post",
		data : JSON.stringify(userInfo),
		contentType : "application/json",
		dataType : "json",
		success : function(result){
			if(result == false){
				alert("로그인 실패");
			}else{
				window.location.href = "/board/list";
			}
		},
		error : function(XHR, status, error) {
			alert("다시 입력해주세요");
		}
	});
});

</script>
-->
</html>