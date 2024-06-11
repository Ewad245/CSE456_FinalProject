package com.lib.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lib.DAO.BookDAO;
import com.lib.DAO.BookDAOImpl;
import com.lib.controller.services.HibernateController;
import com.lib.model.Book;
import com.lib.model.BorrowedBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = { "/addBookServlet", "/deleteBookServlet", "/editBookServlet", "/getBookServlet" })
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	@Override
	public void init() {
		bookDAO = new BookDAOImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		String title = "";
		String author = "";
		String isbn = "";
		String imageUrl = "";
		String description = "";
		long id = 0;
		switch (action) {
		case "/addBookServlet":
			title = request.getParameter("title");
			author = request.getParameter("author");
			isbn = request.getParameter("isbn");
			imageUrl = request.getParameter("imageUrl");
			description = request.getParameter("description");

			Book book = new Book(title, author, isbn, imageUrl, description);
			bookDAO.save(book);

			response.sendRedirect("manageBooks.jsp");
			break;
		case "/deleteBookServlet":
			long bookId = Long.parseLong(request.getParameter("id"));
			book = bookDAO.findById(bookId);
			if (book != null) {
				File oldImage = new File(getServletContext().getRealPath("") + File.separator + book.getImageUrl());
				if (oldImage.exists()) {
					oldImage.delete();
				}
			}
			boolean isDeleted = bookDAO.delete(bookId);
			if (isDeleted) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			break;
		case "/editBookServlet":
			id = Long.parseLong(request.getParameter("id"));
			title = request.getParameter("title");
			author = request.getParameter("author");
			isbn = request.getParameter("isbn");
			String oldImageUrl = request.getParameter("oldImageURL");
			imageUrl = request.getParameter("imageUrl");
			description = request.getParameter("description");

			// Handle file upload
			Book editbook = bookDAO.findById(id);
			if (editbook != null) {
				editbook.setTitle(title);
				editbook.setAuthor(author);
				editbook.setIsbn(isbn);
				editbook.setDescription(description);
				if (!imageUrl.equals("")) {
					editbook.setImageUrl(imageUrl);
					File oldImage = new File(getServletContext().getRealPath("") + File.separator + oldImageUrl);
					if (oldImage.exists()) {
						oldImage.delete();
					}
				}
				bookDAO.update(editbook);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			break;
		default:
			break;
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long id = Long.parseLong(req.getParameter("id"));
		Book book = bookDAO.findById(id);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("id", book.getId());
		jsonResponse.put("title", book.getTitle());
		jsonResponse.put("author",book.getAuthor());
		jsonResponse.put("isbn", book.getIsbn());
		jsonResponse.put("description",book.getDescription());
		jsonResponse.put("imageUrl", book.getImageUrl());
		PrintWriter writer = resp.getWriter();
		writer.write(jsonResponse.toString());
		writer.flush();
		writer.close();
	}
}
