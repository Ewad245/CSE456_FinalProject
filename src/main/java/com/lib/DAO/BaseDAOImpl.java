package com.lib.DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lib.controller.services.HibernateController;

public abstract class BaseDAOImpl<T, ID extends Serializable> implements BaseDAO<T, ID> {
	private Class<T> entityType;

	@SuppressWarnings("unchecked")
	public BaseDAOImpl() {
		this.entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Override
	public void save(T entity) {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		}
	}

	@Override
	public T findById(ID id) {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			return session.get(entityType, id);
		}
	}

	@Override
	public List<T> findAll() {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			return session.createQuery("from " + entityType.getName(), entityType).list();
		}
	}

	@Override
	public void update(T entity) {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		}
	}

	@Override
	public void delete(T entity) {
		try (Session session = HibernateController.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();
		}
	}
}
