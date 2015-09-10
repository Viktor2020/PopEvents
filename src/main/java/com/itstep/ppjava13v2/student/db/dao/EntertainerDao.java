package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;

import java.util.Set;

/**

 */
public interface EntertainerDao {
	Set<Entertainer> findAll() throws DaoException;

	Set<Entertainer> findAllByEngagementId(long engagementId) throws DaoException;

	Entertainer findById(long id) throws DaoException;

	void update(Entertainer entertainer) throws DaoException;

	void remove(Entertainer entertainer) throws DaoException;

	void remove(long id) throws DaoException;

	Entertainer save(Entertainer entertainer) throws DaoException;

	Set<Entertainer> findByEntertainerFirstName(String entertainerStageName) throws DaoException;
}
