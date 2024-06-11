package com.lib.controller.web;

import java.io.IOException;

import com.lib.model.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/memberPage.jsp")
public class SessionFilterMember implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Member loggedInMember = (Member) httpRequest.getSession().getAttribute("loggedInMember");

        if (loggedInMember == null && httpRequest.getRequestURI().endsWith("borrowBookServlet")) {
            httpResponse.sendRedirect("login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}