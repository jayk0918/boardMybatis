<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link rel="stylesheet" type="text/css" href="/css/initialize.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/css/loginform.css">

<body>

	<div id="center-content">
		<div>		
			<table>
		      	<colgroup>
					<col style="width: 100px;">
					<col style="width: 170px;">
					<col style="width: 100px;">
				</colgroup>
	      		<tr>
	      			<td><label for="inputId">아이디</label></td>
	      			<td><input id="inputId" type="text" name="id"></td>
	      			<td><button id="btnIdCheck" type="button">아이디체크</button></td>
	      			<td><input id="idChecked" type = "hidden" value = "0"></td>
	      		</tr>
	      		<tr>
	      			<td><label for="inputPassword">패스워드</label> </td>
	      			<td><input id="inputPassword" type="password" name="password"  value=""></td>   
	      			<td></td>  			
	      		</tr> 
	      		<tr>
	      			<td><label for="inputUserName">이름</label> </td>
	      			<td><input id="inputUserName" type="text" name="userName"  value=""></td>   
	      			<td></td>  			
	      		</tr>  
	      		<tr>
	      			<td><span>약관동의</span> </td>
	      			<td colspan="3">
	      				<input id="chkAgree" type="checkbox" name="agree" value="y">
	      				<label for="chkAgree">서비스 약관에 동의합니다.</label>
	      			</td>   
	      		</tr>   		
	      	</table>
      		<div id="btnArea">
				<button id="btnJoin" class="btn" type="button">회원가입</button>
				<a href = "/">취소</a>
			</div>
		</div>
	</div>
</body>

<script type = "text/javascript">

$("#btnIdCheck").on("click", function(){
	var inputId = $("#inputId").val();
	
	if(inputId == ""){
		alert("아이디를 입력해주세요");
	}else{
		$.ajax({
			url : "/board/idcheck",
			type : "post",
			data : JSON.stringify(inputId),
			contentType : "application/json",
			dataType : "json",
			success : function(result){
				console.log(result);
				if(result == true){
					alert("사용가능");
					$("#idChecked").val(1);
				}else{
					alert("중복된 아이디");
					$("#inputId").val("");
					$("#idChecked").val(0);
				}
			},
			error : function(XHR, status, error) {
				alert("오류");
			}
		});
	};
	
});


$("#btnJoin").on("click", function(){
	var userId = $("#inputId").val();
	var password = $("#inputPassword").val();
	var userName = 	$("#inputUserName").val();
	
	var userInfo={
		userId : userId,
		password : password,
		userName : userName
	}
	
	var agree = $("#chkAgree").is(":checked");
	var idChecked = $("#idChecked").val();
	
	if(idChecked == 0){
		alert("아이디 중복체크를 해주세요.")
	}else if(agree == false){
		alert("약관을 동의해 주세요.");
	}else{
		$.ajax({
			url : "/join",
			type : "post",
			data : JSON.stringify(userInfo),
			contentType : "application/json",
			dataType : "json",
			success : function(){
				alert("가입완료");
				window.location.href = "/";
			},
			error : function(XHR, status, error) {
				alert("오류");
			}
		});
	};
});

</script>

</html>