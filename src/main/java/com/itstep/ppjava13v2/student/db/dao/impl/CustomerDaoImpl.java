package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.CustomerDao;
import com.itstep.ppjava13v2.student.db.dao.HibernateUtil;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Customer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	public CustomerDaoImpl() {}

	@Override
	public Set<Customer> findAll() throws DaoException {
		Set<Customer> customerList ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			customerList = new HashSet<>(session.createCriteria(Customer.class).list());
		} catch (Exception e) {
			log.error("Error Customer findAll ", e);
			throw new DaoException("Error Customer findAll  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return customerList;
	}

	@Override
	public Set<Customer> findByEngagementFirstName(String customerFirstName) throws DaoException {
		Set<Customer> customerList ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Customer where customerFirstName = :customerFistName")
					.setString("customerFistName", customerFirstName);
			customerList = new HashSet<>(query.list());
			session.getTransaction().commit();

		} catch (Exception e) {
			log.error("Error Agent findByEngagementFirstName " + customerFirstName, e);
			throw new DaoException("Error Agent findByEngagementFirstName " + customerFirstName, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return customerList;
	}

	@Override
	public Set<Customer> findAllByEntertainerId(long engagementId) throws DaoException {
		Set<Customer> customerList ;
		String sql = "SELECT * FROM customers WHERE customerEngagementId = ?;";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			customerList = new HashSet<>(query.list());
//			for(Customer customer : customerList) {
//				for (MusicalStyle style : customer.getCustomerMusicalStyleList())
//				Hibernate.initialize(style);
//			}
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Customer findAllByEntertainerId " + engagementId, e);
			throw new DaoException("Error Customer findAllByEntertainerId " + engagementId, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return customerList;
	}

	@Override
	public Customer findById(long id) throws DaoException {
		Customer customer = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			customer = session.load(Customer.class, id);
		} catch (Exception e) {
			log.error("Error Customer findById ", e);
			throw new DaoException("Error Customer findById  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return customer;
	}

	@Override
	public void update(Customer customer) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(customer);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Customer update ", e);
			throw new DaoException("Error Customer update  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void remove(Customer customer) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(customer);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Customer remove ", e);
			throw new DaoException("Error Customer remove  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void remove(long id) throws DaoException {
		remove(findById(id));
	}

	@Override
	public Customer save(Customer customer) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(customer);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Customer save ", e);
			throw new DaoException("Error Customer save  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return customer;
	}
}
