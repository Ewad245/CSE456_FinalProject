package com.lib.controller.web;

import java.io.IOException;
import java.util.List;

import com.lib.DAO.BookDAO;
import com.lib.DAO.BookDAOImpl;
import com.lib.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/searchBooksServlet")
public class SearchBooksServlet extends HttpServlet {
	private BookDAO bookDAO = new BookDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("query");
		List<Book> books = bookDAO.searchBooks(query);
		request.setAttribute("books", books);
		request.getRequestDispatcher("searchResultPage.jsp").forward(request, response);
	}
}
