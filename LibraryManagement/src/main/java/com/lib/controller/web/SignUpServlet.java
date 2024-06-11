package com.lib.controller.web;

import java.io.IOException;


import com.lib.DAO.MemberDAO;
import com.lib.DAO.MemberDAOImpl;
import com.lib.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 5881781410751088847L;
	private MemberDAO memberDAO;

    public void init() {
        memberDAO = new MemberDAOImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");


        try {
            Member newMember = new Member(name, email, phoneNumber, password);

            memberDAO.save(newMember);

            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("signUp.jsp");
        }
    }
}
