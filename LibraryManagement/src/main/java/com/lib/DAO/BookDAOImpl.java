package com.lib.DAO;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.lib.controller.services.HibernateController;
import com.lib.model.Book;

public class BookDAOImpl extends BaseDAOImpl<Book, Long> implements BookDAO {
    public BookDAOImpl() {
        super();
    }

    /*@Override
    public List<Book> findPaginated(int page, int pageSize) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            return session.createQuery("from Book", Book.class)
                    .setFirstResult((page - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
        }
    }*/

    /*@Override
    public long countBooks() {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            return session.createQuery("select count(b.id) from Book b", Long.class).uniqueResult();
        }
    }*/
    @Override
    public boolean delete(long id) {
        Transaction transaction = null;
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                session.delete(book);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<Book> findBooksByPage(int page, int pageSize) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        }
    }

    @Override
    public List<Book> searchBooks(String query) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Query<Book> bookQuery = session.createQuery("FROM Book WHERE title LIKE :query", Book.class);
            bookQuery.setParameter("query", "%" + query + "%");
            return bookQuery.getResultList();
        }
    }

    @Override
    public int countBooks() {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Long count = session.createQuery("select count(b) from Book b", Long.class).uniqueResult();
            return count.intValue();
        }
    }

    @Override
    public int countSearchBooks(String query) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Long count = session.createQuery("select count(b) from Book b where title like :query or description like :query", Long.class)
                                .setParameter("query", "%" + query + "%")
                                .uniqueResult();
            return count.intValue();
        }
    }
    public List<Book> findBooks(int page, int pageSize, String query) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Query<Book> bookQuery = session.createQuery("FROM Book WHERE title LIKE :query", Book.class);
            bookQuery.setParameter("query", "%" + query + "%");
            bookQuery.setFirstResult((page - 1) * pageSize);
            bookQuery.setMaxResults(pageSize);
            return bookQuery.getResultList();
        }
    }

    public int countBooks(String query) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Query<Long> countQuery = session.createQuery("SELECT COUNT(*) FROM Book WHERE title LIKE :query", Long.class);
            countQuery.setParameter("query", "%" + query + "%");
            return countQuery.getSingleResult().intValue();
        }
    }
}
