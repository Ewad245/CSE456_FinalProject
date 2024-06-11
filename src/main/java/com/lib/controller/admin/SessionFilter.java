package com.lib.controller.admin;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/manageBooks.jsp", "/manageMembers.jsp", "/addBook.jsp", "/editBook.jsp","/addMembers.jsp" })
public class SessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		boolean loggedIn = (session != null && session.getAttribute("librarian") != null);

		if (loggedIn) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
		}
	}

	@Override
	public void destroy() {
	}
}
