package com.lib.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lib.controller.services.HibernateController;
import com.lib.model.Member;

public class MemberDAOImpl extends BaseDAOImpl<Member, Integer> implements MemberDAO {

	@Override
	public void delete(int id) {
		Transaction transaction = null;
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Member member = session.get(Member.class, id);
			if (member != null) {
				session.delete(member);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public Member findById(int id) {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			return session.get(Member.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Member findByEmailAndPass(String email, String pass) {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			Member member = session
					.createQuery("FROM Member WHERE email = :email AND password = :password", Member.class)
					.setParameter("email", email).setParameter("password", pass).uniqueResult();
			session.close();
			return member;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Member findByEmail(String email) {
		try (Session session = HibernateController.getSessionFactory().openSession()){
			Member member = session.createQuery("FROM Member WHERE email = :email", Member.class)
                    .setParameter("email", email)
                    .uniqueResult();
			session.close();
	        return member;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
