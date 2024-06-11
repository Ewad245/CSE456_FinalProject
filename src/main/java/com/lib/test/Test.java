package com.lib.test;

import com.lib.DAO.BookDAOImpl;
import com.lib.DAO.LibrarianDAOImpl;
import com.lib.DAO.MemberDAO;
import com.lib.DAO.MemberDAOImpl;
import com.lib.controller.services.HibernateController;
import com.lib.model.Book;
import com.lib.model.Librarian;
import com.lib.model.Member;

public class Test {
	public static void main(String[] args) {
		LibrarianDAOImpl dao = new LibrarianDAOImpl();
		MemberDAO mem = new MemberDAOImpl();
		dao.save(new Librarian("admin", "admin@gmail.com", "admin"));
		mem.save(new Member("thien", "thien@gmail.com", "0936527127", "1234"));
		System.out.println(mem.findById(1).getEmail());
	}
}
