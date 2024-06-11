package com.lib.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Set;

import org.json.JSONObject;

import com.lib.DAO.BorrowedBookDAO;
import com.lib.DAO.BorrowedBookDAOImpl;
import com.lib.DAO.MemberDAO;
import com.lib.DAO.MemberDAOImpl;
import com.lib.model.BorrowedBook;
import com.lib.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/addMemberServlet","/deleteMemberServlet","/getMemberServlet","/editMemberServlet"})
public class ManageMemberServlet extends HttpServlet {
	private MemberDAO memberDAO;
	private BorrowedBookDAO borrowBookDAO;
	
	public ManageMemberServlet() {
		memberDAO = new MemberDAOImpl();
		borrowBookDAO = new BorrowedBookDAOImpl();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5125545099528853093L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Member member = memberDAO.findById(id);
        PrintWriter writer = response.getWriter();
        JSONObject jsonresponse = new JSONObject();
        jsonresponse.put("id", member.getId());
        jsonresponse.put("name", member.getName());
        jsonresponse.put("email", member.getEmail());
        jsonresponse.put("phoneNumber", member.getPhoneNumber());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.write(jsonresponse.toString());
        writer.flush();
        writer.close();
        
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        int id=0;
        String name ="";
        String email = "";
        String phoneNumber ="";
        switch (action) {
		case "/addMemberServlet":
			name = request.getParameter("name");
	        email = request.getParameter("email");
	        phoneNumber = request.getParameter("phoneNumber");

	        Member member = new Member();
	        member.setName(name);
	        member.setEmail(email);
	        member.setPhoneNumber(phoneNumber);

	        memberDAO.save(member);

	        response.sendRedirect("manageMembers.jsp");
			break;
		case "/deleteMemberServlet":
			id = Integer.parseInt(request.getParameter("id"));
	        memberDAO = new MemberDAOImpl();
	        Member mem = memberDAO.findById(id);
	        Set<BorrowedBook> borrowedBooks = mem.getBorrowedBooks();
	        for(BorrowedBook book: borrowedBooks) {
	        	borrowBookDAO.delete(book);
	        }
	        borrowedBooks.clear();
	        memberDAO.delete(id);
	        response.sendRedirect("manageMembers.jsp");
	        break;
		case "/editMemberServlet":
			id = Integer.parseInt(request.getParameter("id"));
	        name = request.getParameter("name");
	        email = request.getParameter("email");
	        phoneNumber = request.getParameter("phoneNumber");

	        member = memberDAO.findById(id);
	        if (member != null) {
	            member.setName(name);
	            member.setEmail(email);
	            member.setPhoneNumber(phoneNumber);
	            memberDAO.update(member);
	        }

	        response.sendRedirect("manageMembers.jsp");
			break;
		default:
			break;
		}
    }
}
