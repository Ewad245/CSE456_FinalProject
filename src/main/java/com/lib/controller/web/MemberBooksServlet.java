package com.lib.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lib.DAO.BookDAO;
import com.lib.DAO.BookDAOImpl;
import com.lib.model.Book;
import com.lib.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/memberBooksServlet")
public class MemberBooksServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2606522692672805285L;
	private BookDAOImpl bookDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int page = Integer.parseInt(request.getParameter("page"));

        int pageSize = 10; // adjust page size as needed
        List<Book> books = bookDAO.findBooksByPage(page, pageSize);
        int totalBooks = bookDAO.countBooks();
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonBooks = new JSONArray();
        for (Book book : books) {
            JSONObject jsonBook = new JSONObject();
            jsonBook.put("id", book.getId());
            jsonBook.put("title", book.getTitle());
            jsonBook.put("image", book.getImageUrl());
            jsonBooks.put(jsonBook);
        }
        jsonResponse.put("books", jsonBooks);
        jsonResponse.put("totalPages", totalPages);
        
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        writer.write(jsonResponse.toString());
        writer.flush();
        writer.close();
    }

    private int calculatePageSize(String userAgent) {
        // You can implement a logic to calculate page size based on the User-Agent
        // Here we just return a fixed number for simplicity
        return 10; // or return 20 for larger screens
    }
}
