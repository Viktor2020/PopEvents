package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Engagement;

import java.util.Set;

/**

 */
public interface EngagementDao {
	Set<Engagement> findAll() throws DaoException;

	Engagement findById(long id) throws DaoException;

	void update(Engagement customer) throws DaoException;

	void remove(Engagement customer) throws DaoException;

	void remove(long id) throws DaoException;

	Engagement save(Engagement customer) throws DaoException;

	Set<Engagement> findByEngagementPrice(long price) throws DaoException;
}
