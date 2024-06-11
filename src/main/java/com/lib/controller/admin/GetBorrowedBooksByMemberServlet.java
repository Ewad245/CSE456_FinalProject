package com.lib.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lib.DAO.BorrowedBookDAO;
import com.lib.DAO.BorrowedBookDAOImpl;
import com.lib.model.Book;
import com.lib.model.BorrowedBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getBorrowedBooksByMemberServlet")
public class GetBorrowedBooksByMemberServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8232076535900571611L;
	private BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		int memberId = Integer.parseInt(req.getParameter("memberId"));
		List<BorrowedBook> borrowedBooks = borrowedBookDAO.findByMemberId(memberId);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		JSONObject jsonResponse = new JSONObject();
		JSONArray jsonBorrowBooks = new JSONArray();
		for (BorrowedBook Bbook : borrowedBooks) {
			JSONObject jsonBook = new JSONObject();
			jsonBook.put("title", Bbook.getBook().getTitle());
			jsonBook.put("id", Bbook.getId());
			jsonBook.put("borrowDate", Bbook.getBorrowDate());
			jsonBook.put("dueDate", Bbook.getDueDate());
			jsonBook.put("returnDate", Bbook.getReturnDate());
			jsonBook.put("returnStatus", Bbook.getReturnStatus());
			jsonBorrowBooks.put(jsonBook);
		}
		jsonResponse.put("borrowedBooks", jsonBorrowBooks);
		writer.write(jsonResponse.toString());
		writer.flush();
		writer.close();
	}
}
