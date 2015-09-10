package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Member;

import java.util.Set;

/**

 */
public interface MemberDao {
	Set<Member> findAll() throws DaoException;

	Set<Member> findAllByEntertainerId(long entertainerId) throws DaoException;

	Member findById(long id) throws DaoException;

	void update(Member member) throws DaoException;

	void remove(Member member) throws DaoException;

	void remove(long id) throws DaoException;

	Member save(Member member) throws DaoException;

	Set<Member> findByMemberFirstName(String memberFirstName) throws DaoException;
}
