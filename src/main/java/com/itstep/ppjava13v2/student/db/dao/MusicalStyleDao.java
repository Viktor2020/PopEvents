package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;

import java.util.Set;

/**

 */
public interface MusicalStyleDao {

	Set<MusicalStyle> findAll() throws DaoException;

	Set<MusicalStyle> findAllByEntertainerId(long entertainerId) throws DaoException;

	Set<MusicalStyle> findAllByCustomerId(long customerId) throws DaoException;

	MusicalStyle findById(long id) throws DaoException;

	void update(MusicalStyle musicalStyle) throws DaoException;

	void remove(MusicalStyle musicalStyle) throws DaoException;

	void remove(long id) throws DaoException;

	MusicalStyle save(MusicalStyle musicalStyle) throws DaoException;

	Set<MusicalStyle> findByMusicalStyleName(String musicalStyleName) throws DaoException;
}
