<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Results</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<%@ include file="memberNavbar.jsp"%>
	<div class="container mt-5">
		<h1 class="mb-4">Search Results for "${param.query}"</h1>
		<div id="search-results" class="row">
			<c:forEach var="book" items="${books}">
				<div class="col-md-3">
					<div class="card mb-4"
						onclick="window.location.href='bookDetail.jsp?id=${book.id}'">
						<img src="<%=request.getContextPath() %>/${book.imageUrl}" class="card-img-top"
							alt="${book.title}">
						<div class="card-body">
							<h5 class="card-title">${book.title}</h5>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>