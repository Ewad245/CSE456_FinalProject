package com.lib.controller.web;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;

import com.lib.DAO.MemberDAO;
import com.lib.DAO.MemberDAOImpl;
import com.lib.model.Member;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3239399122644660105L;
	private MemberDAO memberDAO;

	public void init() {
		memberDAO = new MemberDAOImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");

		Member member = memberDAO.findByEmail(email);

		if (member != null) {
			String newPassword = generateRandomPassword();
			member.setPassword(newPassword);
			memberDAO.update(member);
			sendEmail(email, newPassword);
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("forgotPassword.jsp?error=true");
		}
	}

	private String generateRandomPassword() {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(10);
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < 10; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	private void sendEmail(String emailTo, String newPassword) {
		// TODO Auto-generated method stub
		final String username = "thien.nguyenphan.cit21@eiu.edu.vn";
		final String password = "Q62c9999!";
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(username, password);
			}
		});
		String emailSubject = "New Password For Library";
		String emailContent = newPassword;
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject(emailSubject);
			message.setText(emailContent);
			Transport.send(message);
			System.out.println("Done");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
