<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type = "text/javascript">
$(document).ready(function(){
	var sessionValue = '"${authUser.userName}"';
	if(sessionValue == null || sessionValue == '""'){
		alert("다시 로그인해주세요.");
		window.location.href = "/";
	}
	
});
</script>