package com.lib.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;

import com.lib.DAO.BorrowedBookDAO;
import com.lib.DAO.BorrowedBookDAOImpl;
import com.lib.model.BorrowedBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/returnServlet")
public class returnServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125843170266170743L;
	private BorrowedBookDAO dao;
	
	public returnServlet() {
		dao = new BorrowedBookDAOImpl();
	}
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("borrowbookId");
		PrintWriter writer = resp.getWriter();
		if(id!=null) {
			try {
				BorrowedBook borrBook = dao.findById(Integer.parseInt(id));
				borrBook.returned();
				dao.update(borrBook);
			} catch (Exception e) {
				writer.write(e.toString());
				writer.flush();
				writer.close();
			}
			
		} else {
			writer.write("Can not fetch ID");
			writer.flush();
			writer.close();
		}
	}

}
