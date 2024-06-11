package com.lib.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.lib.controller.services.HibernateController;
import com.lib.model.Librarian;

public class LibrarianDAOImpl extends BaseDAOImpl<Librarian, Long> implements LibrarianDAO {
    public LibrarianDAOImpl() {
        super();
    }

    @Override
    public Librarian findByEmailAndPassword(String email, String password) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Query<Librarian> query = session.createQuery("FROM Librarian WHERE email = :email AND password = :password", Librarian.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }
}
