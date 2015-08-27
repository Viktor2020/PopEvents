package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.CustomerDao;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Customer;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private DataSource connectionManager;

	@Autowired
	private MusicalStyleDao musicalStyleDao;

	public CustomerDaoImpl(DataSource connectionManager, MusicalStyleDao musicalStyleDao) {
		this.connectionManager = connectionManager;
		this.musicalStyleDao = musicalStyleDao;
	}

	public CustomerDaoImpl() {}

	@Override
	public List<Customer> findAll() throws DaoException {
		List<Customer> customerList = new ArrayList<>();
		String sql = "SELECT customerId, customerFirstName ,customerLastName, customerStreetAddress, customerCity, customerState, customerPhoneNumber FROM customers;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Customer customer = getCustomer(resultSet);
				customer.setCustomerMusicalStyleList(musicalStyleDao.findAllByCustomerId(customer.getCustomerId()));
				customerList.add(customer);
			}
		} catch (SQLException e) {
			log.error("Error customer ", e);
			throw new DaoException("Error customer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return customerList;
	}

	@Override
	public List<Customer> findByEngagementFirstName(String customerFirstName) throws DaoException {
		List<Customer> customerList = new ArrayList<>();
		String sql = "SELECT customerId, customerFirstName ,customerLastName, customerStreetAddress, customerCity, customerState, customerPhoneNumber FROM customers WHERE customerFirstName = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setString(1,customerFirstName);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Customer customer = getCustomer(resultSet);
				customer.setCustomerMusicalStyleList(musicalStyleDao.findAllByCustomerId(customer.getCustomerId()));
				customerList.add(customer);
			}
		} catch (SQLException e) {
			log.error("Error customer ", e);
			throw new DaoException("Error customer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return customerList;
	}

	@Override
	public List<Customer> findAllByEntertainerId(long engagementId) throws DaoException {
		List<Customer> customerList = new ArrayList<>();
		String sql = "SELECT customerId, customerFirstName ,customerLastName, customerStreetAddress, customerCity, customerState, customerPhoneNumber FROM customers WHERE customerEngagementId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, engagementId);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Customer customer = getCustomer(resultSet);
				customer.setCustomerMusicalStyleList(musicalStyleDao.findAllByCustomerId(customer.getCustomerId()));
				customerList.add(customer);
			}
		} catch (SQLException e) {
			log.error("Error customer ", e);
			throw new DaoException("Error customer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return customerList;
	}

	private Customer getCustomer(ResultSet resultSet) throws SQLException {
		Customer customer;
		if (resultSet != null) {
			customer = new Customer(
					resultSet.getLong("customerId")
					, resultSet.getString("customerFirstName")
					, resultSet.getString("customerLastName")
					, resultSet.getString("customerStreetAddress")
					, resultSet.getString("customerCity")
					, resultSet.getString("customerState")
					, resultSet.getString("customerPhoneNumber")
			);
		} else {
			throw new SQLException("Customer resultSet is null");
		}
		return customer;
	}

	private void establishRelations(long customerId, long musicalStyleId) throws DaoException {
		String sql = "INSERT INTO customer_styles(customerId, musicalStyleId) VALUES (?,?);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, customerId);
			statement.setLong(2, musicalStyleId);
			log.trace("get statement {}", statement);
			statement.executeUpdate();

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ignored){

		} catch (SQLException e) {
			log.error("Error customer_styles relations ", e);
			throw new DaoException("Error customer_styles relations", e);
		} finally {
			try {
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
	}

	private void setRelations(Customer customer) throws DaoException {
		for (MusicalStyle musicalStyle : customer.getCustomerMusicalStyleList()){
			log.trace("setRelations musicalStyle {} - customer {}",musicalStyle.getMusicalStyleId(),customer.getCustomerId());
			if (musicalStyle.getMusicalStyleId() > 0) {
				establishRelations(customer.getCustomerId(), musicalStyle.getMusicalStyleId());
			} else {
				establishRelations(customer.getCustomerId(), musicalStyleDao.save(musicalStyle).getMusicalStyleId());
			}
		}
	}

	@Override
	public Customer findById(long id) throws DaoException {
		Customer customer = null;
		String sql = "SELECT customerId, customerFirstName ,customerLastName, customerStreetAddress, customerCity, customerState, customerPhoneNumber FROM customers WHERE customerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			log.trace("get statement {}", statement);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				customer = getCustomer(resultSet);
				customer.setCustomerMusicalStyleList(musicalStyleDao.findAllByCustomerId(customer.getCustomerId()));
			} else {
				log.trace("not find customer by id = {}", id);
			}
		} catch (SQLException e) {
			log.error("Error get all customer ", e);
			throw new DaoException("Error customer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return customer;
	}

	@Override
	public void update(Customer customer) throws DaoException {
		if (customer.getCustomerId() <= 0) {
			save(customer);
			return;
		}
		String sql = "UPDATE customers SET " +
				"  customerFirstName = ?," +
				"  customerLastName = ?," +
				"  customerStreetAddress = ?," +
				"  customerCity = ?," +
				"  customerState = ?," +
				"  customerPhoneNumber = ?" +
				"  WHERE customerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getCustomerFirstName());
			statement.setString(2, customer.getCustomerLastName());
			statement.setString(3, customer.getCustomerStreetAddress());
			statement.setString(4, customer.getCustomerCity());
			statement.setString(5, customer.getCustomerState());
			statement.setString(6, customer.getCustomerPhoneNumber());
			statement.setLong(7, customer.getCustomerId());

			log.trace("get statement {}", statement);
			statement.executeUpdate();

			setRelations(customer);//set Relations
		} catch (SQLException e) {
			log.error("Error customer ", e);
			throw new DaoException("Error customer", e);
		} finally {
			try {
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
	}

	@Override
	public void remove(Customer customer) throws DaoException {
		remove(customer.getCustomerId());
	}

	@Override
	public void remove(long id) throws DaoException {
		String sql = "DELETE FROM Customers WHERE customerId = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			log.trace("get statement {}", statement);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("Error customer ", e);
			throw new DaoException("Error customer", e);
		} finally {
			try {
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
	}

	@Override
	public Customer save(Customer customer) throws DaoException {
		if (customer.getCustomerId() > 0) {
			update(customer);
			return customer;
		}
		String sql = "INSERT INTO Customers (customerFirstName, customerLastName, customerStreetAddress, customerCity, customerState, customerPhoneNumber) " +
				"VALUES (?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, customer.getCustomerFirstName());
			statement.setString(2, customer.getCustomerLastName());
			statement.setString(3, customer.getCustomerStreetAddress());
			statement.setString(4, customer.getCustomerCity());
			statement.setString(5, customer.getCustomerState());
			statement.setString(6, customer.getCustomerPhoneNumber());
			log.trace("get statement {}", statement);
			statement.executeUpdate();

			// get id customer
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				log.trace("try get id last added customer");
				if (generatedKeys.next()) {
					customer.setCustomerId(generatedKeys.getLong(1));
				} else {
					log.warn("Creating customer {} failed, no ID obtained", customer);
					throw new SQLException("Creating customer failed, no ID obtained.");
				}
			}
			setRelations(customer);
		} catch (SQLException e) {
			log.error("Error customer ", e);
			throw new DaoException("Error customer", e);
		} finally {
			try {
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return customer;
	}
}
