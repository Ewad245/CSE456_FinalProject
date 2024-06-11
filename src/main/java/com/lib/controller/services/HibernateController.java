package com.lib.controller.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateController {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	//Tạo mới sessionFactory
	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can not create SessionFactory! Please try again later!");
			return null;
		}
		
	}
	
	//Lấy sessionFactory
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	//Ngắt kết nối với sessionFactory
	public static void closeSessionFactory() {
		sessionFactory.close();
	}

}
