package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.HibernateUtil;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
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
public class MusicalStyleDaoImpl implements MusicalStyleDao {

	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	public MusicalStyleDaoImpl() {
	}


	@Override
	public Set<MusicalStyle> findAll() throws DaoException {
		Set<MusicalStyle> musicalStyleList ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			musicalStyleList = new HashSet<>(session.createCriteria(MusicalStyle.class).list());
//			for (MusicalStyle style : musicalStyleList){
//				for (Customer  customer : style.getMusicalStyleCustomerList()) {
//					Hibernate.initialize(customer);
//				}
//			}
		} catch (Exception e) {
			log.error("Error MusicalStyle findAll ", e);
			throw new DaoException("Error MusicalStyle findAll  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return musicalStyleList;
	}

	@Override
	public Set<MusicalStyle> findAllByEntertainerId(long entertainerId) throws DaoException {
		Set<MusicalStyle> musicalStyleList ;
		String sql = "SELECT * FROM musicalStyles, entertainer_styles WHERE entertainer_styles.musicalStyleId = musicalstyles.musicalStyleId AND entertainer_styles.entertainerId = ?;";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			musicalStyleList = new HashSet<>(query.list());
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error musicalStyle findAllByEntertainerId " + entertainerId, e);
			throw new DaoException("Error musicalStyle findAllByEntertainerId " + entertainerId, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return musicalStyleList;
	}

	@Override
	public Set<MusicalStyle> findAllByCustomerId(long customerId) throws DaoException {
		Set<MusicalStyle> musicalStyleList ;
		String sql = "SELECT * FROM musicalStyles, customer_styles WHERE customer_styles.musicalStyleId = musicalstyles.musicalStyleId AND customer_styles.customerId = ?;";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			musicalStyleList = new HashSet<>(query.list());
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error musicalStyle findAllByCustomerId " + customerId, e);
			throw new DaoException("Error musicalStyle findAllByCustomerId " + customerId, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return musicalStyleList;
	}

	@Override
	public MusicalStyle findById(long id) throws DaoException {
		MusicalStyle musicalStyle = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			musicalStyle = session.load(MusicalStyle.class, id);
		} catch (Exception e) {
			log.error("Error musicalStyle findById ", e);
			throw new DaoException("Error musicalStyle findById  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return musicalStyle;
	}

	@Override
	public void update(MusicalStyle musicalStyle) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(musicalStyle);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error musicalStyle update ", e);
			throw new DaoException("musicalStyle Member update  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void remove(MusicalStyle musicalStyle) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(musicalStyle);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error musicalStyle remove ", e);
			throw new DaoException("Error musicalStyle remove  ", e);
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
	public MusicalStyle save(MusicalStyle musicalStyle) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(musicalStyle);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error musicalStyle save ", e);
			throw new DaoException("Error musicalStyle save  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return musicalStyle;
	}

	@Override
	public Set<MusicalStyle> findByMusicalStyleName(String musicalStyleName) throws DaoException {
		Set<MusicalStyle> musicalStyleList;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from MusicalStyle where musicalStyleName = :musicalStyleName")
					.setString("musicalStyleName", musicalStyleName);
			musicalStyleList = new HashSet<>(query.list());
			session.getTransaction().commit();

		} catch (Exception e) {
			log.error("Error MusicalStyle findByMusicalStyleFirstName " + musicalStyleName, e);
			throw new DaoException("Error MusicalStyle findByMusicalStyleFirstName " + musicalStyleName, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return musicalStyleList;
	}
}
