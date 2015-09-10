package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.EngagementDao;
import com.itstep.ppjava13v2.student.db.dao.HibernateUtil;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Engagement;
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
public class EngagementDaoImpl implements EngagementDao {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	public EngagementDaoImpl() {}

	@Override
	public Set<Engagement> findAll() throws DaoException {
		Set<Engagement> engagementList ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			engagementList = new HashSet<>(session.createCriteria(Engagement.class).list());
		} catch (Exception e) {
			log.error("Error Engagement findAll ", e);
			throw new DaoException("Error Engagement findAll  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return engagementList;
	}

	@Override
	public Engagement findById(long id) throws DaoException {
		Engagement engagement = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			engagement = session.load(Engagement.class, id);
		} catch (Exception e) {
			log.error("Error Engagement findById ", e);
			throw new DaoException("Error Engagement findById  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return engagement;
	}

	@Override
	public void update(Engagement engagement) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(engagement);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Engagement update ", e);
			throw new DaoException("Error Engagement update  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void remove(Engagement engagement) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(engagement);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Engagement remove ", e);
			throw new DaoException("Error Engagement remove  ", e);
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
	public Engagement save(Engagement engagement) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(engagement);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Engagement save ", e);
			throw new DaoException("Error Engagement save  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return engagement;

	}

	@Override
	public Set<Engagement> findByEngagementPrice(long price) throws DaoException {
		Set<Engagement> engagementList ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Engagement where engagementPrice = :engagementPrice")
					.setLong("engagementPrice", price);
			engagementList = new HashSet<>(query.list());
			session.getTransaction().commit();

		} catch (Exception e) {
			log.error("Error Engagement findByEngagementPrice " + price, e);
			throw new DaoException("Error Engagement findByEngagementPrice " + price, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return engagementList;
	}
}
