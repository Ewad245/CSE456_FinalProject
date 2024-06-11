<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Library Books</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
	    <script>
        $(document).ready(function() {
            loadBooks(1);

            function loadBooks(page) {
                $.get('memberBooksServlet', { page: page }, function(response) {
                    const books = response.books;
                    const totalPages = response.totalPages;
                    const booklist = document.getElementById("book-list");
                    $('#book-list').empty();
                    console.log(books)
                    books.forEach(book => {
                        $("#book-list").append(`
                            <div class="col-md-3">
                                <div class="card mb-4" onclick="window.location.href='bookDetail?id=\${book.id}'">
                                    <img src="<%=request.getContextPath() %>/\${book.image}" class="card-img-top" alt="\${book.title}">
                                    <div class="card-body">
                                        <h5 class="card-title">\${book.title}</h5>
                                    </div>
                                </div>
                            </div>
                        `);
                    });

                    // Pagination
                    $('#pagination').empty();
                    for (let i = 1; i <= totalPages; i++) {
                        $('#pagination').append(`
                            <li class="page-item \${i == page ? 'active' : ''}">
                                <a class="page-link" href="javascript:void(0);" data-page="\${i}">\${i}</a>
                            </li>
                        `);
                    }
                });
            }

            // Pagination click handler
            $('#pagination').on('click', 'a', function() {
                const page = $(this).data('page');
                loadBooks(page);
            });
        });
    </script>
</head>
<body>
	<%@ include file="memberNavbar.jsp"%>
	<div class="container mt-5">
		<h1 class="mb-4">Library Books</h1>
		<div id="book-list" class="row">
			
		</div>
		<nav aria-label="Page navigation">
			<ul class="pagination justify-content-center" id="pagination">
				<!-- Pagination will be loaded here -->
			</ul>
		</nav>
	</div>


</body>
</html>