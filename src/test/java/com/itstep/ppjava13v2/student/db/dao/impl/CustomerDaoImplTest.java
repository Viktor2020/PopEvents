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
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerDaoImplTest {

	static DataSource connectionManager = R.connectionManager;

	@BeforeClass
	public static void setUpClass() throws Exception {
		R.createDB();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		R.deleteDB();
	}

	@Before
	public void setUp() throws Exception {
		R.clearTables();
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

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertCustomer); // add customer to db

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			engagementDao.remove(1);

			assertEquals(0, R.countRecordsInTable("customers"));
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testCustomer() throws Exception {

		Customer customer = new Customer("FName", "LName", "KM", "DP", "DN", "9379992");
		MusicalStyle style = new MusicalStyle("test style");
		MusicalStyle style2 = new MusicalStyle("test2 style");
		customer.addMusicalStyle(style);

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		CustomerDao engagementDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);


		long id = engagementDao.save(customer).getCustomerId();
		customer.addMusicalStyle(style2);
		engagementDao.update(customer);

		assertEquals(2, R.countRecordsInTable("musicalStyles"));

		customer = engagementDao.findById(id);

		assertEquals(2, customer.getCustomerMusicalStyleList().size());

		engagementDao.remove(customer);
		assertEquals(0, R.countRecordsInTable("customers"));

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