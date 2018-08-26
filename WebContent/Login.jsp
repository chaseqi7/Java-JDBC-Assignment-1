<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login .JSP</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/A1.css" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
</head>
<body>
	<h1 class="h1" id="login-title">Login</h1>
	<div class="container">
		<div class="card card-container">
			<form action="/PROG3060A01/LoginServlet" type="POST" class="form">		
				<label class="input-lbl" for="username">Username:</label>
				<input type="text" name="username" id="username" />
				<br>
				<label class="input-lbl" for="password">Password:</label>
				<input type="password" name="password" id="password" />
				<br>
				<button type="submit" class="btn" value="Login" name="login" id="btnLogin">Login</button>
			</form>
		</div>
	</div>
	<c:if test = "${not empty sessionScope.error}">
    	<div class="alert alert-danger" style="text-align: center;">${sessionScope.error}</div>
	</c:if>
</body>
</html>