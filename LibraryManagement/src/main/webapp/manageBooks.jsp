<%@page import="com.lib.model.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.lib.DAO.BookDAOImpl"%>
<%@page import="com.lib.DAO.BookDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Books</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/dropzone/5.9.3/min/dropzone.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/dropzone/5.9.3/min/dropzone.min.css">
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container mt-5">
		<h2>Manage Books</h2>
		<button class="btn btn-primary mb-3"
			onclick="window.location.href='addBook.jsp'">Add New Book</button>
		<table id="booksTable" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Image</th>
					<th>Title</th>
					<th>Author</th>
					<th>ISBN</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				// Fetch the list of books from the database
				BookDAO bookDAO = new BookDAOImpl();
				List<Book> books = bookDAO.findAll();
				for (Book book : books) {
				%>
				<tr>
					<td><img
						src="<%=request.getContextPath() + "/" + book.getImageUrl()%>"
						alt="Book Image" style="width: 50px; height: 50px;"></td>
					<td><%=book.getTitle()%></td>
					<td><%=book.getAuthor()%></td>
					<td><%=book.getIsbn()%></td>
					<td>
						<button class="btn btn-warning btn-sm"
							onclick="showEditModal(<%=book.getId()%>)">Edit</button>
						<button class="btn btn-danger btn-sm"
							onclick="deleteBook(<%=book.getId()%>)">Delete</button>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
	<div class="modal fade" id="editBookModal" tabindex="-1" role="dialog"
		aria-labelledby="editBookModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="editBookModalLabel">Edit Book</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="editBookForm">
						<input class="form-control" type="hidden" id="editBookId"
							name="id">
						<div class="form-group">
							<label for="editBookTitle">Title</label> <input type="text"
								class="form-control" id="editBookTitle" name="title" required>
						</div>
						<div class="form-group">
							<label for="editBookAuthor">Author</label> <input type="text"
								class="form-control" id="editBookAuthor" name="author" required>
						</div>
						<div class="form-group">
							<label for="editBookISBN">ISBN</label> <input type="text"
								class="form-control" id="editBookISBN" name="isbn" required>
						</div>
						<div class="form-group">
							<label for="editBookDescription">Description</label>
							<textarea class="form-control" id="editBookDescription"
								name="description" rows="3" required></textarea>
						</div>
						<div class="form-group">
							<label for="editBookImage">Book Image</label> <input
								type="hidden" class="form-control" id="editBookImageURL"
								name="imageURL" required> <input type="hidden"
								class="form-control" id="oldBookImageURL" name="oldImageURL"
								required>
							<div id="editDropzone" class="dropzone"></div>
						</div>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			$('#booksTable').DataTable();
		});
		function deleteBook(bookId) {
            if (confirm("Are you sure you want to delete this book?")) {
                $.ajax({
                    url: 'deleteBookServlet',
                    type: 'POST',
                    data: { id: bookId },
                    success: function(response) {
                        alert("Book deleted successfully!");
                        location.reload();
                    },
                    error: function() {
                        alert("Error deleting book.");
                    }
                });
            }
        }
        $('#editBookForm').on('submit', function(event) {
            event.preventDefault();
            var formData = new FormData(this);
            var bookId = document.getElementById("editBookId").value;
            var bookTitle = document.getElementById("editBookTitle").value;
            var bookAuthor = document.getElementById("editBookAuthor").value;
            var bookISBN = document.getElementById("editBookISBN").value;
            var description = document.getElementById("editBookDescription").value;
            var oldImageUrl = document.getElementById("oldBookImageURL").value;
            var imageUrl = document.getElementById("editBookImageURL").value;
            $.ajax({
                url: 'editBookServlet',
                type: 'POST',
                data: {id: bookId, title: bookTitle, author:bookAuthor, isbn: bookISBN, oldImageURL: oldImageUrl, imageUrl: imageUrl, description: description},
                success: function(response) {
                    alert("Book updated successfully!");
                    location.reload();
                },
                error: function() {
                    console.log(description);
                    alert("Error updating book.");
                }
            });
        });

    function showEditModal(bookId) {
        $.ajax({
            url: 'getBookServlet',
            type: 'GET',
            data: { id: bookId },
            success: function(response) {
                console.log(response);
                $('#editBookId').val(response.id);
                $('#editBookTitle').val(response.title);
                $('#editBookAuthor').val(response.author);
                $('#editBookISBN').val(response.isbn);
                $("#editBookDescription").val(response.description);
                $('#oldBookImageURL').val(response.imageUrl);
                $('#editBookModal').modal('show');

                // Initialize Dropzone
                Dropzone.autoDiscover = false;
                if (Dropzone.instances.length > 0) Dropzone.instances.forEach(dz => dz.destroy());

                var editDropzone = new Dropzone("#editDropzone", {
					url : "uploadServlet",
					paramName : "file", // The name that will be used to transfer the file
					maxFilesize : 2, // MB
					acceptedFiles : "image/*",
					addRemoveLinks : true,
					dictDefaultMessage : "Drag an image here to upload, or click to select one",
					init : function() {
						this.on("success", function(file, response) {
							let bookImage = document.getElementById("editBookImageURL");
							bookImage.setAttribute("value",response);
							console.log("File uploaded successfully");
							// You can store the uploaded file information if needed
						});
					}
				});
            },
            error: function() {
                alert("Error fetching book details.");
            }
        });
    }
	</script>
</body>
</html>