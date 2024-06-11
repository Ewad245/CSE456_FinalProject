package com.lib.controller.web;

import java.io.IOException;
import java.time.LocalDate;

import com.lib.DAO.BookDAO;
import com.lib.DAO.BookDAOImpl;
import com.lib.DAO.BorrowedBookDAO;
import com.lib.DAO.BorrowedBookDAOImpl;
import com.lib.model.Book;
import com.lib.model.BorrowedBook;
import com.lib.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/borrowBookServlet")
public class BorrowBookServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2148063872687295451L;
	private BookDAO bookDAO;
    private BorrowedBookDAO borrowedBookDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAOImpl();
        borrowedBookDAO = new BorrowedBookDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long bookId = Long.parseLong(request.getParameter("bookId"));
        Member member = (Member) request.getSession().getAttribute("loggedInMember");

        if (member != null) {
            Book book = bookDAO.findById(bookId);
            if (book != null) {
                BorrowedBook borrowedBook = new BorrowedBook(member, book, LocalDate.now(), LocalDate.now().plusWeeks(2)); // Set a default return date

                borrowedBookDAO.save(borrowedBook);

                response.getWriter().write("success");
            } else {
                response.getWriter().write("error");
            }
        } else {
            response.getWriter().write("not_logged_in");
        }
    }
}
