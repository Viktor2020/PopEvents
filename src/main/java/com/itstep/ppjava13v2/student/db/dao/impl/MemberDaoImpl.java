package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.HibernateUtil;
import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Member;
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
public class MemberDaoImpl implements MemberDao {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	public MemberDaoImpl() {
	}

	@Override
	public Set<Member> findAll() throws DaoException {
		Set<Member> memberList  ;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			memberList = new HashSet<>(session.createCriteria(Member.class).list());
		} catch (Exception e) {
			log.error("Error Member findAll ", e);
			throw new DaoException("Error Member findAll  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return memberList;
	}

	@Override
	public Set<Member> findAllByEntertainerId(long entertainerId) throws DaoException {
		Set<Member> memberList ;
		String sql = "SELECT * FROM members , entertainer_members WHERE members.memberId = entertainer_members.memberId AND entertainer_members.entertainerId = ?;";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			memberList = new HashSet<>(query.list());
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Member findAllByEntertainerId " + entertainerId, e);
			throw new DaoException("Error Member findAllByEntertainerId " + entertainerId, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return memberList;
	}

	@Override
	public Member findById(long id) throws DaoException {
		Member member = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			member = session.load(Member.class, id);
		} catch (Exception e) {
			log.error("Error Member findById ", e);
			throw new DaoException("Error Member findById  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return member;
	}

	@Override
	public void update(Member member) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(member);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Member update ", e);
			throw new DaoException("Error Member update  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void remove(Member member) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(member);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Member remove ", e);
			throw new DaoException("Error Member remove  ", e);
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
	public Member save(Member member) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(member);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Error Member save ", e);
			throw new DaoException("Error Member save  ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return member;
	}

	@Override
	public Set<Member> findByMemberFirstName(String memberFirstName) throws DaoException {
		Set<Member> memberList;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Member where memberFirstName = :memberFirstName")
					.setString("memberFirstName", memberFirstName);
			memberList = new HashSet<>(query.list());
			session.getTransaction().commit();

		} catch (Exception e) {
			log.error("Error Member findByMemberFirstName " + memberFirstName, e);
			throw new DaoException("Error Member findByMemberFirstName " + memberFirstName, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return memberList;
	}
}
