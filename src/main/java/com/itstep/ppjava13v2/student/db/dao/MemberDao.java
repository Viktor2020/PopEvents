package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Member;

import java.util.List;

/**

 */
public interface MemberDao {
	List<Member> findAll() throws DaoException;

	List<Member> findAllByEntertainerId(long entertainerId) throws DaoException;

	Member findById(long id) throws DaoException;

	void update(Member member) throws DaoException;

	void remove(Member member) throws DaoException;

	void remove(long id) throws DaoException;

	Member save(Member member) throws DaoException;

	List<Member> findByMemberFirstName(String memberFirstName) throws DaoException;
}
