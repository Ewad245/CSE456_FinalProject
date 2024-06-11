package com.lib.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.lib.controller.services.HibernateController;
import com.lib.model.BorrowedBook;

public class BorrowedBookDAOImpl extends BaseDAOImpl<BorrowedBook, Integer> implements BorrowedBookDAO {

	@Override
	public void delete(int id) {
		Transaction transaction = null;
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			BorrowedBook borrowedBook = session.get(BorrowedBook.class, id);
			if (borrowedBook != null) {
				session.delete(borrowedBook);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}

	}

	@Override
	public BorrowedBook findById(int id) {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			return session.get(BorrowedBook.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BorrowedBook> findOverdueBooks() {
		// TODO Auto-generated method stub
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			return session.createQuery("from BorrowedBook where returnStatus = :status", BorrowedBook.class)
					.setParameter("status", BorrowedBook.ReturnStatus.OVERDUE).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
    public List<BorrowedBook> findByMemberId(int memberId) {
        try (Session session = HibernateController.getSessionFactory().openSession()) {
            Query<BorrowedBook> query = session.createQuery("from BorrowedBook where member.id = :memberId", BorrowedBook.class);
            query.setParameter("memberId", memberId);
            return query.list();
        }
    }

}
