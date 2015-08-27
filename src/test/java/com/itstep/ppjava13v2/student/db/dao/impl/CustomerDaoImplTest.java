package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.CustomerDao;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.domain.Customer;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class CustomerDaoImplTest {

	static DataSource connectionManager = new DataSource() {

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return null;
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return false;
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return null;
		}

		@Override
		public void setLogWriter(PrintWriter out) throws SQLException {

		}

		@Override
		public void setLoginTimeout(int seconds) throws SQLException {

		}

		@Override
		public int getLoginTimeout() throws SQLException {
			return 0;
		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return null;
		}

		@Override
		public Connection getConnection() throws SQLException {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/testPopEvent", "root", "qqqq");
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306", username, password);
		}

	};

	@BeforeClass
	public static void setUpClass() throws Exception {
		String sqlCreateTestDb = "CREATE DATABASE IF NOT EXISTS testPopEvent;";
		Connection connection = null;
		Connection connection1 = null;
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;
		Connection connection6 = null;
		Connection connection7 = null;
		Connection connection8 = null;
		try {
			connection = connectionManager.getConnection("root", "qqqq");
			connection.createStatement().execute(sqlCreateTestDb);
			connection.close();
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableAgents);

			connection1 = connectionManager.getConnection();
			connection1.createStatement().execute(R.sqlCreateTableCustomers);

			connection2 = connectionManager.getConnection();
			connection2.createStatement().execute(R.sqlCreateTableEngagements);

			connection3 = connectionManager.getConnection();
			connection3.createStatement().execute(R.sqlCreateTableEntertainers);

			connection4 = connectionManager.getConnection();
			connection4.createStatement().execute(R.sqlCreateTableMember);

			connection5 = connectionManager.getConnection();
			connection5.createStatement().execute(R.sqlCreateTableMusicalStyles);

			connection6 = connectionManager.getConnection();
			connection6.createStatement().execute(R.sqlCreateTableEntertainer_Members);

			connection7 = connectionManager.getConnection();
			connection7.createStatement().execute(R.sqlCreateTableEntertainer_Styles);

			connection8 = connectionManager.getConnection();
			connection8.createStatement().execute(R.sqlCreateTableCustomer_Styles);

		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection1 != null) {
				connection1.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
			if (connection3 != null) {
				connection3.close();
			}
			if (connection4 != null) {
				connection4.close();
			}
			if (connection5 != null) {
				connection5.close();
			}
			if (connection6 != null) {
				connection6.close();
			}
			if (connection7 != null) {
				connection7.close();
			}
			if (connection8 != null) {
				connection8.close();
			}
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		String sqlDropTestDb = "DROP DATABASE IF EXISTS testPopEvent;";
		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlDropTestDb);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Before
	public void setUp() throws Exception {

		String sqlClearTable = "TRUNCATE TABLE customers;";// clear table customers
		Connection connection = null;
		Connection connection2 = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlClearTable);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM customers");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testFindAll() throws Exception {

		int sizeLine = 10;
		for (int i = 0; i < sizeLine; i++) {
			Connection connection = null;
			try {
				connection = connectionManager.getConnection();
				connection.createStatement().execute(R.sqlInsertCustomer);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
		assertEquals(sizeLine, engagementDao.findAll().size());

	}

	@Test
	public void testFindById() throws Exception {
		String sql = "SELECT * FROM customers WHERE customers.customerId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertCustomer);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sql);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			Customer customer = engagementDao.findById(1);

			testCustomer(customer, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	private void testCustomer(Customer customer, ResultSet resultSet) throws Exception {
		assertEquals(customer.getCustomerId(), resultSet.getLong("customerId"));
		assertEquals(customer.getCustomerFirstName(), resultSet.getString("customerFirstName"));
		assertEquals(customer.getCustomerLastName(), resultSet.getString("customerLastName"));
		assertEquals(customer.getCustomerStreetAddress(), resultSet.getString("customerStreetAddress"));
		assertEquals(customer.getCustomerCity(), resultSet.getString("customerCity"));
		assertEquals(customer.getCustomerState(), resultSet.getString("customerState"));
		assertEquals(customer.getCustomerPhoneNumber(), resultSet.getString("customerPhoneNumber"));
	}

	@Test
	public void testUpdate() throws Exception {
		String sqlFindCustomerById = "SELECT * FROM customers WHERE customers.customerId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertCustomer); // add customer to db

			connection2 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			Customer customer = engagementDao.findById(1);
			customer.setCustomerFirstName("newCustomerFirstName");
			customer.setCustomerLastName("newCustomerLastName");
			customer.setCustomerStreetAddress("newCustomerStreetAddress");
			customer.setCustomerCity("newCustomerCity");
			customer.setCustomerState("newCustomerState");
			customer.setCustomerPhoneNumber("newCustomerPhoneNumber");

			engagementDao.update(customer); // update customer to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindCustomerById); // get customer to db
			resultSet.next();

			testCustomer(customer, resultSet); // change this customer and customer to db
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testRemove() throws Exception {

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertCustomer); // add customer to db


			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			engagementDao.remove(1);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM customers");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testCustomer() throws Exception {
		Connection connection1 = null;
		Connection connection2 = null;
		try {
			Customer customer = new Customer("FName", "LName", "KM", "DP", "DN", "9379992");
			MusicalStyle style = new MusicalStyle("test style");
			MusicalStyle style2 = new MusicalStyle("test2 style");
			customer.addMusicalStyle(style);

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			connection1 = connectionManager.getConnection();

			long id = engagementDao.save(customer).getCustomerId();
			customer.addMusicalStyle(style2);
			engagementDao.update(customer);

			ResultSet resultSetStyle = connection1.createStatement().executeQuery("SELECT COUNT(*) FROM musicalStyles");
			resultSetStyle.next();
			assertEquals(2, resultSetStyle.getLong(1));

			customer = engagementDao.findById(id);

			assertEquals(2, customer.getCustomerMusicalStyleList().size());

			engagementDao.remove(customer);
			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM customers");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
			if (connection1 != null) {
				connection1.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testSave() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);

		Customer customer = new Customer("FName", "LName", "KM", "DP", "DN", "9379992");

		engagementDao.save(customer);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM customers WHERE customers.customerFirstName='FName';");

			resultSet.next();

			testCustomer(customer, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByCustomerFirstName() throws Exception {
		String sql = "SELECT * FROM customers WHERE customers.customerId = 1;";

		Connection connection = null;
		Connection connection2 = null;
		Connection connection3 = null;

		try {
			connection = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertCustomer);
			connection3.createStatement().execute(R.sqlInsertCustomer);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sql);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			List<Customer> customer = engagementDao.findByEngagementFirstName("k.Pupok1");

			testCustomer(customer.get(0), resultSet);
			assertEquals(2, customer.size());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
			if (connection3 != null) {
				connection3.close();
			}
		}
	}

	@Test
	public void testFindAllByEngagementId() throws Exception {

	}
}