<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forgot Password</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<div class="container mt-5">
		<h2>Forgot Password</h2>
		<form action="ForgotPasswordServlet" method="post">
			<div class="form-group">
				<label for="email">Enter your email address:</label> <input
					type="email" class="form-control" id="email" name="email" required>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
			<c:if test="${param.error == 'true'}">
				<div class="alert alert-danger mt-3">Email invalid</div>
			</c:if>
		</form>
	</div>
</body>
</html>