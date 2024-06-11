<%@page import="com.lib.model.BorrowedBook.ReturnStatus"%>
<%@page import="com.lib.model.BorrowedBook"%>
<%@page import="java.util.Set"%>
<%@page import="com.lib.model.Member"%>
<%@page import="com.lib.model.Book"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<%
	Book book = (Book) request.getAttribute("book");
	%>
	<%@ include file="memberNavbar.jsp"%>
	<div class="container mt-5">
		<div class="row">
			<div class="col-md-4">
				<img src="<%=request.getContextPath() %>/<%=book.getImageUrl() %>"
					class="img-fluid" alt="${book.title}">
			</div>
			<div class="col-md-8">
				<h1>${book.title}</h1>
				<p>${book.description}</p>
				<%
				Member mem = (Member) session.getAttribute("loggedInMember");
				if (mem != null) {
					boolean isBorrowing = false;
					Set<BorrowedBook> borrowbooks = mem.getBorrowedBooks();
					for (BorrowedBook borrbook : borrowbooks) {
						if (borrbook.getBook().getId()==book.getId()) {
					if (!borrbook.getReturnStatus().equals(ReturnStatus.NOT_RETURNED)) {
						isBorrowing = true;
				%>
				<button class="btn btn-secondary">Borrowing</button>
				<%
				}
				break;
				}
				}
				if (!isBorrowing) {
				%>
				<button class="btn btn-primary" onclick="borrowBook(${book.id})">Borrow</button>
				<%
				}
				} else {
				%>
				<p class="text-muted">Log in to borrow this book.</p>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<script>
        function borrowBook(bookId) {
            $.post('borrowBookServlet', { bookId: bookId }, function(response) {
                alert(response);
                location.reload();
            });
        }
    </script>
</body>
</html>