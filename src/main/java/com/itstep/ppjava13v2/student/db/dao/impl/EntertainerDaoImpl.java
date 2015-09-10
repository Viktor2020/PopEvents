package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.EntertainerDao;
import com.itstep.ppjava13v2.student.db.dao.HibernateUtil;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
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
public class EntertainerDaoImpl implements EntertainerDao {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	public EntertainerDaoImpl() {
	}


	@Override
	public Set<Entertainer> findAll() throws DaoException {
		Set<Entertainer> entertainerList;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			entertainerList = new HashSet<>(session.createCriteria(Entertainer.class).list());
		} catch (Exception e) {
			log.error("Error Entertainer findAll ", e);
			throw new DaoException("Error Entertainer findAll  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return entertainerList;
	}

	@Override
	public Set<Entertainer> findAllByEngagementId(long engagementId) throws DaoException {
		Set<Entertainer> entertainerList;
		String sql = "SELECT * FROM entertainers WHERE entertainerEngagementId = ?;";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			entertainerList = new HashSet<>(query.list());
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Entertainer findAllByEngagementId " + engagementId, e);
			throw new DaoException("Error Entertainer findAllByEngagementId " + engagementId, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return entertainerList;
	}


	@Override
	public Entertainer findById(long id) throws DaoException {
		Entertainer entertainer = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			entertainer = session.load(Entertainer.class, id);
		} catch (Exception e) {
			log.error("Error Entertainer findById ", e);
			throw new DaoException("Error Entertainer findById  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return entertainer;
	}

	@Override
	public void update(Entertainer entertainer) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(entertainer);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Entertainer update ", e);
			throw new DaoException("Error Entertainer update  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void remove(Entertainer entertainer) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(entertainer);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Entertainer remove ", e);
			throw new DaoException("Error Entertainer remove  ", e);
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
	public Entertainer save(Entertainer entertainer) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(entertainer);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Entertainer save ", e);
			throw new DaoException("Error Entertainer save  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return entertainer;
	}

	@Override
	public Set<Entertainer> findByEntertainerFirstName(String entertainerStageName) throws DaoException {
		Set<Entertainer> entertainerList ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Entertainer where entertainerStageName = :entertainerStageName")
					.setString("entertainerStageName", entertainerStageName);
			entertainerList = new HashSet<>(query.list());
			session.getTransaction().commit();

		} catch (Exception e) {
			log.error("Error Entertainer findByEntertainerFirstName " + entertainerStageName, e);
			throw new DaoException("Error Entertainer findByEntertainerFirstName " + entertainerStageName, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return entertainerList;
	}

}
