<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Form Example</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
</head>
<body>
	<h2>Fill out the form:</h2>
	<form action="FormHandlerServlet" method="post">
		Name: <input type="text" name="name"><br> Email: <input
			type="text" name="email"><br> <input type="submit"
			value="Submit">
	</form>
	<form action="SendMailServlet" method="post">
		<label for="inputemail" class="form-label">Email</label> <input
			type="email" name="to" class="form-control">
			<label for="subject" class="form-label">Subject</label> <input
			type="text" name="subject" class="form-control">
			<label for="content" class="form-label">Content</label>
			<textarea rows="3" cols="3" name="content" class="form-control"></textarea>
			<button type="submit" class="btn btn-primary">Send</button>
			<button type="reset" class="btn btn-primary">Cancel</button>
	</form>
</body>
</html>
