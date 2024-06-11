<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.lib.model.BorrowedBook"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Borrowed Books</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
</head>
<body>
	<%@ include file="memberNavbar.jsp"%>

	<div class="container mt-5">
		<h2>Borrowed Books</h2>
		<table id="borrowedBooksTable" class="table table-striped">
			<thead>
				<tr>
					<th>Title</th>
					<th>Author</th>
					<th>Borrow Date</th>
					<th>Due Date</th>
					<th>Return Date</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<BorrowedBook> list = (List<BorrowedBook>) request.getAttribute("borrowedBooks");
				for (BorrowedBook bookB : list) {
				%>
				<tr>
					<td><%=bookB.getBook().getTitle()%></td>
					<td><%=bookB.getBook().getAuthor()%></td>
					<td><%=bookB.getBorrowDate()%></td>
					<td><%=bookB.getDueDate()%></td>
					<%
					if (bookB.getReturnDate() == null) {
					%>
					<td>No info</td>
					<%
					} else {
					%>
					<td><%=bookB.getReturnDate()%></td>
					<%
					}
					%>
					<td><%=bookB.getReturnStatus()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
	<script>
		$(document).ready(function() {
			$('#borrowedBooksTable').DataTable();
		});
	</script>
</body>
</html>