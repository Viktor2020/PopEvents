package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;

import java.util.List;

/**

 */
public interface EntertainerDao {
	List<Entertainer> findAll() throws DaoException;

	List<Entertainer> findAllByEngagementId(long engagementId) throws DaoException;

	Entertainer findById(long id) throws DaoException;

	void update(Entertainer entertainer) throws DaoException;

	void remove(Entertainer entertainer) throws DaoException;

	void remove(long id) throws DaoException;

	Entertainer save(Entertainer entertainer) throws DaoException;

	List<Entertainer> findByEntertainerFirstName(String entertainerStageName) throws DaoException;
}
