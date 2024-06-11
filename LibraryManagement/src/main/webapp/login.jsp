<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
</head>
<body>
	<div class="container">
		<h2 class="mt-5">Librarian Login</h2>
		<form action="login" method="post" class="mt-3">
			<div class="form-group">
				<label for="email">Email:</label> <input type="email"
					class="form-control" id="email" name="email" required>
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input type="password"
					class="form-control" id="password" name="password" required>
			</div>
			<button type="submit" class="btn btn-primary">Login</button>
			<c:if test="${param.error == 'true'}">
				<div class="alert alert-danger mt-3">Invalid email or password</div>
			</c:if>
		</form>
		<p>Do not have account? </p>
		<a href="signUp.jsp" class="btn btn-secondary">Sign Up here</a>
		<!-- Sign-Up Button -->
		<a href="forgotPassword.jsp" class="btn btn-link">Forgot Password?</a> <!-- Forgot Password Button -->
	</div>
</body>
</html>