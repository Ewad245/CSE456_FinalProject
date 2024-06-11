package com.lib.controller.web;

import java.io.IOException;

import com.lib.DAO.BookDAO;
import com.lib.DAO.BookDAOImpl;
import com.lib.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookDetail")
public class BookDetailServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7862526666623168069L;
	private BookDAO bookDAO = new BookDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String bookId = request.getParameter("id");
        if (bookId != null && !bookId.isEmpty()) {
            Book book = bookDAO.findById(Long.parseLong(bookId));
            request.setAttribute("book", book);
            request.getRequestDispatcher("bookDetail.jsp").forward(request, response);
        } else {
            response.sendRedirect("memberPage.jsp");
        }
    }
}
