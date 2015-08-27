package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.EntertainerDao;
import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
import com.itstep.ppjava13v2.student.db.domain.Member;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EntertainerDaoTest {

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

		String sqlClearTable = "TRUNCATE TABLE entertainers;";// clear table entertainers
		Connection connection = null;
		Connection connection2 = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlClearTable);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM entertainers");
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
				connection.createStatement().execute(R.sqlInsertEntertainer);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
		assertEquals(sizeLine, entertainerDao.findAll().size());

	}

	@Test
	public void testFindById() throws Exception {
		String sqlFindById = "SELECT * FROM entertainers WHERE entertainers.entertainerId = 1;";


		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEntertainer);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			Entertainer entertainer = entertainerDao.findById(1);

			testEntertainer(entertainer, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	private void testEntertainer(Entertainer entertainer, ResultSet resultSet) throws Exception {
		assertEquals(entertainer.getEntertainerId(), resultSet.getLong("entertainerId"));
		assertEquals(entertainer.getEntertainerStageName(), resultSet.getString("entertainerStageName"));
		assertEquals(entertainer.getEntertainerStreetAddress(), resultSet.getString("entertainerStreetAddress"));
		assertEquals(entertainer.getEntertainerCity(), resultSet.getString("entertainerCity"));
		assertEquals(entertainer.getEntertainerState(), resultSet.getString("entertainerState"));
		assertEquals(entertainer.getEntertainerPhoneNumber(), resultSet.getString("entertainerPhoneNumber"));
		assertEquals(entertainer.getEntertainerWebPage(), resultSet.getString("entertainerWebPage"));
		assertEquals(entertainer.getEntertainerEmailAddress(), resultSet.getString("entertainerEmailAddress"));
		assertEquals(entertainer.getEntertainerDateEntered().getTime(), resultSet.getDate("entertainerDateEntered").getTime(), 1e+12);
		assertNotEquals(String.valueOf(new Date()), resultSet.getDate("entertainerDateEntered").getTime(), 1e+12);
	}

	@Test
	public void testUpdate() throws Exception {
		String sqlFindById = "SELECT * FROM entertainers WHERE entertainers.entertainerId = 1;";


		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEntertainer); // add entertainer to db

			connection2 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			Entertainer entertainer = entertainerDao.findById(1);
			entertainer.setEntertainerStageName("newEntertainerStageName");
			entertainer.setEntertainerStreetAddress("newEntertainerStreetAddress");
			entertainer.setEntertainerCity("newEntertainerCity");
			entertainer.setEntertainerState("newEntertainerState");
			entertainer.setEntertainerPhoneNumber("newEntertainerPhoneNumber");
			entertainer.setEntertainerWebPage("newEntertainerWebPage");
			entertainer.setEntertainerEmailAddress("newEntertainerEmailAddress");
			entertainer.setEntertainerDateEntered(new Date());

			entertainerDao.update(entertainer); // update entertainer to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindById); // get entertainer to db
			resultSet.next();

			testEntertainer(entertainer, resultSet); // change this entertainer and entertainer to db
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
			connection.createStatement().execute(R.sqlInsertEntertainer); // add entertainer to db


			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			entertainerDao.remove(1);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM entertainers");
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
	public void testEntertainer() throws Exception {

		Connection connection1 = null;
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;

		try {
			connection1 = connectionManager.getConnection();
			connection2 = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			connection4 = connectionManager.getConnection();
			connection5 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);

			Entertainer entertainer = new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date());
			entertainer.addMusicalStyle(new MusicalStyle("rock"));
			entertainer.addMember(new Member("FName", "LName", "KM", "DP"));
			entertainerDao.save(entertainer);

			ResultSet resultSet1 = connection1.createStatement().executeQuery("SELECT COUNT(*) FROM MusicalStyles");
			resultSet1.next();
			assertEquals(1, resultSet1.getLong(1));
			ResultSet resultSet2 = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM Members");
			resultSet2.next();
			assertEquals(1, resultSet2.getLong(1));
			entertainerDao.remove(entertainer);

			entertainer.addMusicalStyle(new MusicalStyle("pop"));
			entertainer.addMember(new Member("Name", " me", "KM", "DP"));
			entertainerDao.update(entertainer);

			ResultSet resultSet4 = connection4.createStatement().executeQuery("SELECT COUNT(*) FROM MusicalStyles");
			resultSet4.next();
			assertEquals(2, resultSet4.getLong(1));
			ResultSet resultSet5 = connection5.createStatement().executeQuery("SELECT COUNT(*) FROM Members");
			resultSet5.next();
			assertEquals(2, resultSet5.getLong(1));

			ResultSet resultSet = connection5.createStatement().executeQuery("SELECT COUNT(*) FROM entertainers");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
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
		}
	}

	@Test
	public void testSave() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);

		Entertainer entertainer = new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date());

		entertainerDao.save(entertainer);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM entertainers WHERE entertainerStageName='SName';");

			resultSet.next();

			testEntertainer(entertainer, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByEntertainerFirstName() throws Exception {
		String sqlFindById = "SELECT * FROM entertainers WHERE entertainerStageName = 'Chaplin';";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEntertainer);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			List<Entertainer> entertainer = entertainerDao.findByEntertainerFirstName("Chaplin");

			testEntertainer(entertainer.get(0), resultSet);
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