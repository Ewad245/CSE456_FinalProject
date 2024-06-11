package com.lib.controller.admin;

import java.io.IOException;

import com.lib.DAO.LibrarianDAO;
import com.lib.DAO.LibrarianDAOImpl;
import com.lib.DAO.MemberDAO;
import com.lib.DAO.MemberDAOImpl;
import com.lib.model.Librarian;
import com.lib.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = -127780665856583544L;
	private LibrarianDAO librarianDAO;
	private MemberDAO memDao;

    @Override
    public void init() {
        librarianDAO = new LibrarianDAOImpl();
        memDao= new MemberDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Member member = memDao.findByEmailAndPass(email, password);
        Librarian librarian = librarianDAO.findByEmailAndPassword(email, password);

        if (librarian != null) {
            HttpSession session = request.getSession();
            session.setAttribute("librarian", librarian);
            response.sendRedirect("manageBooks.jsp");
        } else if(member!=null) {
        	HttpSession session = request.getSession();
            session.setAttribute("loggedInMember", member);
            response.sendRedirect("memberPage.jsp");
        } else {
        	response.sendRedirect("login.jsp?error=true");
        }
    }
}
