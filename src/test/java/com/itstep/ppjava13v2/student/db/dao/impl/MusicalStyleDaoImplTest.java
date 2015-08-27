package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
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

public class MusicalStyleDaoImplTest {


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

		String sqlClearTable = "TRUNCATE TABLE musicalStyles;";// clear table musicalStyle
		Connection connection = null;
		Connection connection2 = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlClearTable);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM musicalStyles");
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
				connection.createStatement().execute(R.sqlInsertMusicalStyle);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		assertEquals(sizeLine, musicalStyleDao.findAll().size());

	}

	@Test
	public void testFindById() throws Exception {
		String sqlFindMusicalStyleById = "SELECT * FROM musicalStyles WHERE musicalStyleId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMusicalStyle);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMusicalStyleById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MusicalStyle musicalStyle = musicalStyleDao.findById(1);

			testMusicalStyle(musicalStyle, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	private void testMusicalStyle(MusicalStyle musicalStyle, ResultSet resultSet) throws Exception {
		assertEquals(musicalStyle.getMusicalStyleId(), resultSet.getLong("musicalStyleId"));
		assertEquals(musicalStyle.getMusicalStyleName(), resultSet.getString("musicalStyleName"));
	}

	@Test
	public void testUpdate() throws Exception {
		String sqlFindMusicalStyleById = "SELECT * FROM musicalStyles WHERE musicalStyleId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMusicalStyle); // add musicalStyle to db

			connection2 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MusicalStyle musicalStyle = musicalStyleDao.findById(1);
			musicalStyle.setMusicalStyleName("newMusicalStyleName");

			musicalStyleDao.update(musicalStyle); // update musicalStyle to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMusicalStyleById); // get musicalStyle to db
			resultSet.next();

			testMusicalStyle(musicalStyle, resultSet); // change this musicalStyle and musicalStyle to db
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
			connection.createStatement().execute(R.sqlInsertMusicalStyle); // add musicalStyle to db


			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			musicalStyleDao.remove(1);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM musicalStyles");
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
	public void testMusicalStyle() throws Exception {
		Connection connection2 = null;
		try {
			MusicalStyle musicalStyle = new MusicalStyle("Name");

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			musicalStyleDao.save(musicalStyle);
			musicalStyleDao.remove(musicalStyle);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM musicalStyles");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testSave() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);

		MusicalStyle musicalStyle = new MusicalStyle("Name");

		musicalStyleDao.save(musicalStyle);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM musicalStyles WHERE musicalStyleName='Name';");

			resultSet.next();

			testMusicalStyle(musicalStyle, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByMusicalStyleName() throws Exception {
		String sqlFindMusicalStyleById = "SELECT * FROM musicalStyles WHERE musicalStyleName = 'musicalStyleName';";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMusicalStyle);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMusicalStyleById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			List<MusicalStyle> musicalStyle = musicalStyleDao.findByMusicalStyleName("musicalStyleName");

			testMusicalStyle(musicalStyle.get(0), resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}
}