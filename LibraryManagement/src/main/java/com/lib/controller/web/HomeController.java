package com.lib.controller.web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.lib.controller.services.EncryptPass;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/homepage","/FormHandlerServlet" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
		rd.forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		// Do something with the data (for now, just print it)
		try {
			System.out.println("Name: " + EncryptPass.toHexString(EncryptPass.getSHA(name)));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Email: " + email);

		// Send a response back to the user
		/*response.setContentType("text/html");
		response.getWriter().println("<html><body>");
		response.getWriter().println("<h2>Form Submitted Successfully</h2>");
		response.getWriter().println("<p>Name: " + name + "</p>");
		response.getWriter().println("<p>Email: " + email + "</p>");
		response.getWriter().println("</body></html>");*/
		RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
		request.setAttribute("username", name);
		request.setAttribute("email", email);
		rd.forward(request, response);
	}

}
