package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Customer;

import java.util.Set;

/**

 */
public interface CustomerDao {
	Set<Customer> findAll() throws DaoException;

	Set<Customer> findByEngagementFirstName(String customerFirstName) throws DaoException;

	Set<Customer> findAllByEntertainerId(long engagementId) throws DaoException;

	Customer findById(long id) throws DaoException;

	void update(Customer customer) throws DaoException;

	void remove(Customer customer) throws DaoException;

	void remove(long id) throws DaoException;

	Customer save(Customer customer) throws DaoException;
}
