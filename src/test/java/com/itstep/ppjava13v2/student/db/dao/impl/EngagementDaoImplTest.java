package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.*;
import com.itstep.ppjava13v2.student.db.domain.Agent;
import com.itstep.ppjava13v2.student.db.domain.Customer;
import com.itstep.ppjava13v2.student.db.domain.Engagement;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
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

public class EngagementDaoImplTest {

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
	private String sqlSelectById = "SELECT *" +
			"      FROM Customers, Agents, entertainers, Engagements " +
			"      WHERE Engagements.engagementId = Customers.customerEngagementId" +
			"      AND Engagements.engagementId = Agents.agentEngagementId" +
			"      AND Engagements.engagementId = Entertainers.entertainerEngagementId" +
			"      AND Engagements.engagementId = 1 OR Engagements.engagementId = 1;";

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

		String sqlClearTable = "TRUNCATE TABLE engagements;";// clear table engagement
		Connection connection = null;
		Connection connection2 = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlClearTable);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM engagements");
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

		int sizeLine = 3;
		for (int i = 0; i < sizeLine; i++) {
			Connection connection = null;
			try {
				connection = connectionManager.getConnection();
				connection.createStatement().execute(R.sqlInsertEngagement);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
			Connection connection2 = null;
			try {
				connection2 = connectionManager.getConnection();
				connection2.createStatement().execute(R.sqlInsertAgent);
			} finally {
				if (connection2 != null) {
					connection2.close();
				}
			}
			Connection connection3 = null;
			try {
				connection3 = connectionManager.getConnection();
				connection3.createStatement().execute(R.sqlInsertCustomer);
			} finally {
				if (connection3 != null) {
					connection3.close();
				}
			}
			Connection connection4 = null;
			try {
				connection4 = connectionManager.getConnection();
				connection4.createStatement().execute(R.sqlInsertEntertainer);
			} finally {
				if (connection4 != null) {
					connection4.close();
				}
			}
		}


		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
		AgentDao agentDao = new AgentDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
		EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
		assertEquals(sizeLine, engagementDao.findAll().size());
		assertEquals(sizeLine, engagementDao.findAll().get(0).getEngagementAgentList().size());
		assertEquals(sizeLine, engagementDao.findAll().get(0).getEngagementCustomerList().size());
		assertEquals(sizeLine, engagementDao.findAll().get(0).getEngagementEntertainerList().size());
	}

	@Test
	public void testFindById() throws Exception {
		Connection connection = null;
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;
		try {
			connection = connectionManager.getConnection();

			connection2 = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			connection4 = connectionManager.getConnection();
			connection5 = connectionManager.getConnection();

			connection.createStatement().execute(R.sqlInsertEngagement);
			connection3.createStatement().execute(R.sqlInsertAgent);
			connection4.createStatement().execute(R.sqlInsertCustomer);
			connection5.createStatement().execute(R.sqlInsertEntertainer);

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlSelectById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			Engagement engagement = engagementDao.findById(1);

			testEngagement(engagement, resultSet);
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
			if (connection4 != null) {
				connection4.close();
			}
			if (connection5 != null) {
				connection5.close();
			}
		}
	}

	private void testEngagement(Engagement engagement, ResultSet resultSet) throws Exception {
		assertEquals(engagement.getEngagementId(), resultSet.getLong("engagementId"));
		assertEquals(engagement.getEngagementStartDate().getTime(), resultSet.getDate("engagementStartDate").getTime(), 1e+12);
		assertEquals(engagement.getEngagementEndDate().getTime(), resultSet.getDate("engagementEndDate").getTime(), 1e+12);
		assertEquals(engagement.getEngagementPrice(), resultSet.getLong("engagementPrice"));

	}

	@Test
	public void testUpdateCustomerInEngagement() throws Exception {
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;
		try {
			connection2 = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			connection4 = connectionManager.getConnection();
			connection5 = connectionManager.getConnection();

			connection2.createStatement().execute(R.sqlInsertEngagement);
			connection3.createStatement().execute(R.sqlInsertAgent);
			connection4.createStatement().execute(R.sqlInsertCustomer);
			connection5.createStatement().execute(R.sqlInsertEntertainer);

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			Engagement engagement = engagementDao.findById(1);

			engagement.getEngagementCustomerList().add(new Customer("FName", "LName", "KM", "DP", "DN", "9379992"));
			engagementDao.save(engagement);

			engagement = engagementDao.findById(1);
			boolean flagUpdateCustomerInEngagement = false;
			for (Customer customer : engagement.getEngagementCustomerList()) {
				System.out.println(customer.getCustomerFirstName());
				if (customer.getCustomerFirstName().equals("FName")) {
					flagUpdateCustomerInEngagement = true;
				}
			}
			assertEquals(true, flagUpdateCustomerInEngagement);

		} finally {
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
	public void testUpdate() throws Exception {

		Connection connection = null;
		Connection connection2 = null;
		Connection connection3 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEngagement); // add engagement to db
			connection2 = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			ResultSet resultSet1 = connection3.createStatement().executeQuery("SELECT COUNT(*) FROM engagements");
			resultSet1.next();
			assertEquals(1, resultSet1.getLong(1));

			connection2 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			Engagement engagement = engagementDao.findById(1);
			engagement.setEngagementPrice(5000);

			engagementDao.update(engagement); // update engagement to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlSelectById); // get engagement to db
			resultSet.next();

			testEngagement(engagement, resultSet); // change this engagement and engagement to db
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
	public void testRemove() throws Exception {

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEngagement); // add engagement to db


			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			engagementDao.remove(1);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM engagements");
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
	public void testEngagement() throws Exception {

		Connection connection1 = null;
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;
		Connection connection6 = null;
		Connection connection7 = null;

		try {
			connection1 = connectionManager.getConnection();
			connection2 = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			connection4 = connectionManager.getConnection();
			connection5 = connectionManager.getConnection();
			connection6 = connectionManager.getConnection();
			connection7 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);

			Engagement engagement = new Engagement(new Date(), new Date(), 1000);
			Agent agent = new Agent("FName", "LName", "KM", "DP", "DN", "9379992", new Date(), 1, 1);
			Customer customer = new Customer("FName", "LName", "KM", "DP", "DN", "9379992");
			Entertainer entertainer = new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date());

			engagement.addEntertainer(entertainer);
			engagement.addCustomer(customer);
			engagement.addAgent(agent);
			engagementDao.save(engagement);

			ResultSet resultSet1 = connection1.createStatement().executeQuery("SELECT COUNT(*) FROM entertainers");
			resultSet1.next();
			assertEquals(1, resultSet1.getLong(1));
			ResultSet resultSet2 = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM customers");
			resultSet2.next();
			assertEquals(1, resultSet2.getLong(1));
			ResultSet resultSet3 = connection3.createStatement().executeQuery("SELECT COUNT(*) FROM agents");
			resultSet3.next();
			assertEquals(1, resultSet3.getLong(1));

			engagement.addEntertainer(new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date()));
			engagement.addCustomer(new Customer("FName", "LName", "KM", "DP", "DN", "9379992"));
			engagement.addAgent(new Agent("FName", "LName", "KM", "DP", "DN", "9379992", new Date(), 1, 1));
			engagementDao.update(engagement);

			ResultSet resultSet4 = connection4.createStatement().executeQuery("SELECT COUNT(*) FROM entertainers");
			resultSet4.next();
			assertEquals(2, resultSet4.getLong(1));
			ResultSet resultSet5 = connection5.createStatement().executeQuery("SELECT COUNT(*) FROM customers");
			resultSet5.next();
			assertEquals(2, resultSet5.getLong(1));
			ResultSet resultSet6 = connection6.createStatement().executeQuery("SELECT COUNT(*) FROM agents");
			resultSet6.next();
			assertEquals(2, resultSet6.getLong(1));

			engagementDao.remove(engagement);

			ResultSet resultSet7 = connection7.createStatement().executeQuery("SELECT COUNT(*) FROM engagements");
			resultSet7.next();
			assertEquals(0, resultSet7.getLong(1));
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
			if (connection6 != null) {
				connection6.close();
			}
			if (connection7 != null) {
				connection7.close();
			}
		}
	}

	@Test
	public void testSave() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
		AgentDao agentDao = new AgentDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
		EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);

		Engagement engagement = new Engagement(new Date(), new Date(), 1000);

		engagementDao.save(engagement);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM engagements WHERE engagementId = 1;");

			resultSet.next();

			testEngagement(engagement, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByEngagementPrice() throws Exception {
		String sql = "SELECT * FROM Engagements WHERE engagementPrice = 1000;";
		Connection connectionInsertAgent = null;
		Connection connectionFindByFirstName = null;
		try {
			connectionInsertAgent = connectionManager.getConnection();
			connectionInsertAgent.createStatement().execute(R.sqlInsertEngagement);

			connectionFindByFirstName = connectionManager.getConnection();
			ResultSet resultSet = connectionFindByFirstName.createStatement().executeQuery(sql);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);

			List<Engagement> engagement = engagementDao.findByEngagementPrice(1000);
			assertEquals(1, engagement.size());
			testEngagement(engagement.get(0), resultSet);
		} finally {
			if (connectionInsertAgent != null) {
				connectionInsertAgent.close();
			}
			if (connectionFindByFirstName != null) {
				connectionFindByFirstName.close();
			}
		}
	}
}