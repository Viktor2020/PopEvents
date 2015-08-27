package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Customer;

import java.util.List;

/**

 */
public interface CustomerDao {
	List<Customer> findAll() throws DaoException;

	List<Customer> findByEngagementFirstName(String customerFirstName) throws DaoException;

	List<Customer> findAllByEntertainerId(long engagementId) throws DaoException;

	Customer findById(long id) throws DaoException;

	void update(Customer customer) throws DaoException;

	void remove(Customer customer) throws DaoException;

	void remove(long id) throws DaoException;

	Customer save(Customer customer) throws DaoException;
}
