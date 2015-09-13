package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.AgentDao;
import com.itstep.ppjava13v2.student.db.dao.HibernateUtil;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Agent;
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
public class AgentDaoImpl implements AgentDao {

	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	public AgentDaoImpl() {
	}

	@Override
	public Set<Agent> findAll() throws DaoException {
		Set<Agent> agentList = new HashSet<>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			agentList = new HashSet<>(session.createCriteria(Agent.class).list());
		} catch (Exception e) {
			log.error("Error Agent findAll ", e);
			throw new DaoException("Error Agent findAll  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return agentList;
	}

	@Override
	public Set<Agent> findAllByEngagementId(long engagementId) throws DaoException {
		Set<Agent> agentList ;
		String sql = "SELECT * FROM agents WHERE agentEngagementId = ?;";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			agentList = new HashSet<>(query.list());
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Agent findByAgentFirstName " + engagementId, e);
			throw new DaoException("Error Agent findByAgentFirstName " + engagementId, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return agentList;
	}


	@Override
	public Agent findById(long id) throws DaoException {
		Agent agent = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			agent = session.load(Agent.class, id);
		} catch (Exception e) {
			log.error("Error Agent findById ", e);
			throw new DaoException("Error Agent findById  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return agent;
	}

	@Override
	public Set<Agent> findByAgentFirstName(String agentFirstName) throws DaoException {
		Set<Agent> agentList  ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Agent where agentFirstName = :agentFistName")
					.setString("agentFistName", agentFirstName);
			agentList = new HashSet<>(query.list());
			session.getTransaction().commit();

		} catch (Exception e) {
			log.error("Error Agent findByAgentFirstName " + agentFirstName, e);
			throw new DaoException("Error Agent findByAgentFirstName " + agentFirstName, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return agentList;
	}

	@Override
	public void update(Agent agent) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(agent);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Agent update ", e);
			throw new DaoException("Error Agent update  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void remove(Agent agent) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(agent);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Agent remove ", e);
			throw new DaoException("Error Agent remove  ", e);
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
	public Agent save(Agent agent) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(agent);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Agent save ", e);
			throw new DaoException("Error Agent save  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return agent;
	}
}
