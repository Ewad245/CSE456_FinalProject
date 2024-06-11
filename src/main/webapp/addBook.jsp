<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Book</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/dropzone/5.9.3/min/dropzone.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/dropzone/5.9.3/min/dropzone.min.js"></script>
<style>
.dropzone {
	border: 2px dashed #007bff;
	padding: 30px;
	background: #f9f9f9;
}
</style>
</head>
<body>
	<div class="container mt-5">
		<h2>Add New Book</h2>
		<form action="addBookServlet" method="post">
			<div class="form-group">
				<label for="title">Title:</label> <input type="text"
					class="form-control" id="title" name="title" required>
			</div>
			<div class="form-group">
				<label for="author">Author:</label> <input type="text"
					class="form-control" id="author" name="author" required>
			</div>
			<div class="form-group">
				<label for="isbn">ISBN:</label> <input type="text"
					class="form-control" id="isbn" name="isbn" required>
			</div>
			<div class="form-group">
				<label for="description">Description:</label>
				<textarea class="form-control" id="description" name="description"
					rows="3" required></textarea>
			</div>
			<div class="form-group">
				<label for="image">Book Image:</label>
				<div class="dropzone" id="myDropzone"></div>
				<input type="hidden" id="bookImage" name="imageUrl"
					required="required">
			</div>
			<button type="submit" class="btn btn-primary">Add Book</button>
		</form>
	</div>
	<script>
		Dropzone.autoDiscover = false;
		var myDropzone = new Dropzone(
				"#myDropzone",
				{
					url : "uploadServlet",
					paramName : "file", // The name that will be used to transfer the file
					maxFilesize : 2, // MB
					acceptedFiles : "image/*",
					addRemoveLinks : true,
					dictDefaultMessage : "Drag an image here to upload, or click to select one",
					init : function() {
						this.on("success", function(file, response) {
							var bookImage = document
									.getElementById("bookImage");
							bookImage.setAttribute("value", response);
							console.log("File uploaded successfully");
							// You can store the uploaded file information if needed
						});
					}
				});
	</script>
</body>
</html>