<%@page import="com.lib.model.Member"%>
<%@page import="com.lib.DAO.MemberDAOImpl"%>
<%@page import="com.lib.DAO.MemberDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Manage Members</title>
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
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container mt-5">
		<h2>Manage Members</h2>
		<button class="btn btn-primary mb-3"
			onclick="window.location.href='addMember.jsp'">Add New
			Member</button>
		<table id="membersTable" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Phone Number</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				MemberDAO memberDAO = new MemberDAOImpl();
				List<Member> members = memberDAO.findAll();
				for (Member member : members) {
				%>
				<tr>
					<td><%=member.getName()%></td>
					<td><%=member.getEmail()%></td>
					<td><%=member.getPhoneNumber()%></td>
					<td>
						<button class="btn btn-warning btn-sm"
							onclick="showEditModal(<%=member.getId()%>)">Edit</button>
						<button class="btn btn-danger btn-sm"
							onclick="deleteMember(<%=member.getId()%>)">Delete</button>
						<button class="btn btn-info btn-sm"
							onclick="showBorrowedBooksModal(<%=member.getId()%>)">Show
							Borrowed Books</button>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>

	<!-- Edit Member Modal -->
	<div class="modal fade" id="editMemberModal" tabindex="-1"
		role="dialog" aria-labelledby="editMemberModalLabel"
		aria-hidden="true" >
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="editMemberModalLabel">Edit Member</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="editMemberForm" method="post" action="editMemberServlet">
					<div class="modal-body">
						<input type="hidden" id="editMemberId" name="id">
						<div class="form-group">
							<label for="editMemberName">Name</label> <input type="text"
								class="form-control" id="editMemberName" name="name" required>
						</div>
						<div class="form-group">
							<label for="editMemberEmail">Email</label> <input type="email"
								class="form-control" id="editMemberEmail" name="email" required>
						</div>
						<div class="form-group">
							<label for="editMemberPhoneNumber">Phone Number</label> <input
								type="text" class="form-control" id="editMemberPhoneNumber"
								name="phoneNumber" required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Show Borrowed Books Modal -->
	<div class="modal fade" id="borrowedBooksModal" tabindex="-1"
		role="dialog" aria-labelledby="borrowedBooksModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="borrowedBooksModalLabel">Borrowed
						Books</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" style="overflow-x: auto;">
					<table id="borrowedBooksTable"
						class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Title</th>
								<th>Borrow Date</th>
								<th>Due Date</th>
								<th>Return Date</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody id="borrowedBooksTableBody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script>
        $(document).ready(function() {
            $('#membersTable').DataTable();
        });

        function showEditModal(memberId) {
            $.ajax({
                type: 'GET',
                url: 'getMemberServlet',
                data: { id: memberId },
                success: function(member) {
                    $('#editMemberId').val(member.id);
                    $('#editMemberName').val(member.name);
                    $('#editMemberEmail').val(member.email);
                    $('#editMemberPhoneNumber').val(member.phoneNumber);
                    $('#editMemberModal').modal('show');
                },
                error: function() {
                    alert('Error fetching member details.');
                }
            });
        }

        function deleteMember(memberId) {
            if (confirm('Are you sure you want to delete this member?')) {
                $.ajax({
                    type: 'POST',
                    url: 'deleteMemberServlet',
                    data: { id: memberId },
                    success: function(response) {
                        alert('Member deleted successfully!');
                        location.reload();
                    },
                    error: function() {
                        alert('Error deleting member.');
                    }
                });
            }
        }

        function showBorrowedBooksModal(memberId) {
            $.ajax({
                type: 'GET',
                url: 'getBorrowedBooksByMemberServlet',
                data: { memberId: memberId },
                success: function(response) {
                    console.log(response)
                    const borrowedBooks = response.borrowedBooks;
                    $('#borrowedBooksTableBody').empty();
                    borrowedBooks.forEach(function(book) {
                        $('#borrowedBooksTableBody').append(`
                            <tr>
                                <td>\${book.title}</td>
                                <td>\${book.borrowDate}</td>
                                <td>\${book.dueDate}</td>
                                <td>\${book.returnDate}</td>
                                <td>\${book.returnStatus}</td>
                                <td><button class="btn btn-primary"
        							onclick="returnfuction(\${book.id})">Return</button></td>
                            </tr>
                        `);
                    });
                    $('#borrowedBooksModal').modal('show');
                },
                error: function() {
                    alert('Error fetching borrowed books.');
                }
            });
        }
        function returnfuction(id) {
        	$.ajax({
                type: 'GET',
                url: 'returnServlet',
                data: { borrowbookId: id },
                success: function(response) {
                    alert('Successful Update');
                },
                error: function(response) {
                    alert(response);
                }
            });
            }
    </script>
</body>
</html>