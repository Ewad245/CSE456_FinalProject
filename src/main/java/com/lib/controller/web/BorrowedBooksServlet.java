package com.lib.controller.web;

import java.io.IOException;
import java.util.List;

import com.lib.DAO.BorrowedBookDAO;
import com.lib.DAO.BorrowedBookDAOImpl;
import com.lib.model.BorrowedBook;
import com.lib.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BorrowedBooksServlet")
public class BorrowedBooksServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1737539034989432870L;
	private BorrowedBookDAO borrowedBookDAO;

    public void init() {
        borrowedBookDAO = new BorrowedBookDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member mem =(Member) session.getAttribute("loggedInMember");

        if (mem != null) {
            List<BorrowedBook> borrowedBooks = borrowedBookDAO.findByMemberId(mem.getId());
            for(BorrowedBook book: borrowedBooks) {
            	book.checkOverdue();
            	borrowedBookDAO.update(book);
            }
            request.setAttribute("borrowedBooks", borrowedBooks);
            request.getRequestDispatcher("borrowedBooks.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
